package com.example.demo.board.kim.kquestion;

import java.security.Principal;

import com.example.demo.user.SiteUser;
import com.example.demo.user.SiteUserSerevice;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kquestion")
public class GangQuestionController {

	private final GangQuestionService gangQuestionService;
	private final SiteUserSerevice siteUserSerevice;
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0")int page) {
		Page<GangQuestion> paging = this.gangQuestionService.getlist(page);
		 model.addAttribute("paging", paging);
		 return "kim/question_list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		GangQuestion gangQuestion = this.gangQuestionService.getGangQuestion(id);
		model.addAttribute("gangQuestion", gangQuestion);
		return "kim/question_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(GangQuestionForm gangQuestionForm) {
		return "kim/question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String questionCreate(@Valid GangQuestionForm gangQuestionForm, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "kim/question_form";
		}
		SiteUser siteUser = this.siteUserSerevice.getUser(principal.getName());
		this.gangQuestionService.create(gangQuestionForm.getSubject(), gangQuestionForm.getContent(), siteUser);
		return "redirect:/kquestion/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(GangQuestionForm gangQuestionForm, @PathVariable("id") Integer id, Principal principal) {
		GangQuestion gangQuestion =this.gangQuestionService.getGangQuestion(id);
		if(!gangQuestion.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없슴");
		}
		gangQuestionForm.setSubject(gangQuestion.getSubject());
		gangQuestionForm.setContent(gangQuestion.getContent());
		return "kim/question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid GangQuestionForm gangQuestionForm, BindingResult bindingResult, Principal principal,@PathVariable("id") Integer id) {
		if(bindingResult.hasErrors()) {
			return "kim/question_form";
		}
		GangQuestion gangQuestion = this.gangQuestionService.getGangQuestion(id);
		if(!gangQuestion.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한 없음");
		}
		this.gangQuestionService.modify(gangQuestion, gangQuestionForm.getSubject(), gangQuestionForm.getContent());
		return String.format("redirect:/kquestion/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		GangQuestion gangQuestion = this.gangQuestionService.getGangQuestion(id);
		if(!gangQuestion.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한 없다");
		}
		this.gangQuestionService.delete(gangQuestion);
		return "redirect:/kquestion/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		GangQuestion gangQuestion = this.gangQuestionService.getGangQuestion(id);
		SiteUser siteUser = this.siteUserSerevice.getUser(principal.getName());
		this.gangQuestionService.vote(gangQuestion, siteUser);
		return String.format("redirect:/kquestion/detail/%s", id);
	}
}
