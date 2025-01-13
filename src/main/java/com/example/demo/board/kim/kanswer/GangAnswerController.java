package com.example.demo.board.kim.kanswer;

import java.security.Principal;

import com.example.demo.board.kim.kquestion.GangQuestion;
import com.example.demo.board.kim.kquestion.GangQuestionService;
import com.example.demo.user.SiteUser;
import com.example.demo.user.SiteUserSerevice;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kanswer")
public class GangAnswerController {
	
	private final GangQuestionService gangQuestionService;
	private final GangAnswerService gangAnswerService;
	private final SiteUserSerevice siteUserService;
	
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createGangAnswer(Model model, @PathVariable("id") Integer id, 
			@Valid GangAnswerForm gangAnswerForm, BindingResult bindingResult, Principal principal) {
	
		GangQuestion gangQuestion = this.gangQuestionService.getGangQuestion(id);
		SiteUser siteUser = this.siteUserService.getUser(principal.getName());
		
		if(bindingResult.hasErrors()) {
		model.addAttribute("gangQuestion", gangQuestion);
		return "kim/question_detail";
	}
		this.gangAnswerService.create(gangQuestion, gangAnswerForm.getContent(), siteUser);
		return String.format("redirect:/kquestion/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(@Valid GangAnswerForm answerForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
		
		if(bindingResult.hasErrors()) {
			return "kim/answer_form";
		}
		GangAnswer gangAnswer = this.gangAnswerService.getAnswer(id);
		
		this.gangAnswerService.modify(gangAnswer, gangAnswer.getContent());
		return String.format("redirect:/kquestion/detail/%s", gangAnswer.getGangQuestion().getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid GangAnswerForm gangAnswerForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			return "kim/question_detail";
			
		}
		GangAnswer a = this.gangAnswerService.getAnswer(id);
		if(!a.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없어 시발");
			
		}
	this.gangAnswerService.modify(a, gangAnswerForm.getContent());
		return String.format("redirect:/kquestion/detail/%s",a.getGangQuestion().getId());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
		GangAnswer gangAnswer = this.gangAnswerService.getAnswer(id);
		
		this.gangAnswerService.delete(gangAnswer);
		return String.format("redirect:/kquestion/detail/%s", gangAnswer.getGangQuestion().getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(Principal principal, @PathVariable("id") Integer id) {
		GangAnswer gangAnswer = this.gangAnswerService.getAnswer(id);
		SiteUser gangUser = this.siteUserService.getUser(principal.getName());
		this.gangAnswerService.vote(gangAnswer, gangUser);
		return String.format("redirect:/kquestion/detail/%s", gangAnswer.getGangQuestion().getId());
	}
	
}
