package com.example.demo.board.lee.answer;

import java.security.Principal;

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

import com.example.demo.board.lee.question.Question;
import com.example.demo.board.lee.question.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

	
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final SiteUserSerevice siteUserSerevice;
	/*
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id,
			@Valid AnswerForm answerForm , BindingResult bindingResult) {
		Question question = this.questionService.getQuestion(id);
		this.answerService.create(question, answerForm.getContent());  //답변을 등록(저장)
		return String.format("redirect:/question/detail/%s", id);
	}*/
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id")Integer id,
			@Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.siteUserSerevice.getUser(principal.getName());
		if(bindingResult.hasErrors()) {
			model.addAttribute("question",question);
			return "question_detail";
		}
		Answer answer =  this.answerService.create(question, answerForm.getContent(), siteUser);
		return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerForm answerForm,BindingResult bindingResult,
			Principal principal, @PathVariable("id") Integer id) {
		if(bindingResult.hasErrors()) {
			return "answer_form";
		}			
	Answer	answer  = this.answerService.getAnswer(id);
	if(!answer.getAuthor().getUsername().equals(principal.getName())){
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
	}
	this.answerService.modify(answer, answerForm.getContent());
	return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	}
		
	
	
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
		Answer	answer = this.answerService.getAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
		}
	
answerForm.setContent(answer.getContent());
	return"answer_form";
	}	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id")Integer id) {
		Answer	answer= this.answerService.getAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다." );
		}
		this.answerService.delete(answer);
		return String.format("redirect:/question/datail/%s", answer.getQuestion().getId());
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(Principal principal, @PathVariable("id") Integer id) {
		Answer answer =this.answerService.getAnswer(id);
		SiteUser siteUser =this.siteUserSerevice.getUser(principal.getName());
		this.answerService.vote(answer, siteUser);
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId()); 
	}
}

	
	


