package com.example.demo.board.in.inanswer;

import java.security.Principal;

import com.example.demo.board.in.inquestion.InQuestion;
import com.example.demo.board.in.inquestion.InQuestionService;
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
@RequestMapping("/inAnswer")
@RequiredArgsConstructor
public class InAnswerController {

	
	
	private final InQuestionService inQuestionService;
	private final InAnswerService inAnswerService;
	private final SiteUserSerevice siteUserSerevice;
	
	
	
	/*
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id,
			@RequestParam(value = "content") String content ) {
		Question question = this.questionService.getQuestion(id);
		this.answerService.create(question, content);
		return String.format("redirect:/question/detail/%s", id);
	} */
	
	
	@GetMapping("/create/{id}")
	public String createAnswerForm( @PathVariable("id") Integer id, Model model) {
	    
	    InQuestion inQuestion = this.inQuestionService.getQuestion(id);

	   
	    model.addAttribute("inQuestion", inQuestion);
	    model.addAttribute("inAnswerForm", new InAnswerForm());  

	    return "ins/inAnswer_create";
	}

	 
	 
	 
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createAnswer(Model model,@PathVariable("id") Integer id,@Valid InAnswerForm inAnswerForm
	, BindingResult  bindingResult,Principal principal) {
		
		InQuestion inQuestion = this.inQuestionService.getQuestion(id);
		SiteUser inUser = this.siteUserSerevice.getUser(principal.getName());
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("inQuestion",inQuestion);
			return "ins/inAnswer_create";
		}
		InAnswer inAnswer = this.inAnswerService.create(inQuestion, inAnswerForm.getContent(), inUser); 
		return String.format("redirect:/inQuestion/detail/%s",inAnswer.getInQuestion().getId());
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(InAnswerForm inAnswerForm,@PathVariable("id") Integer id,Principal principal) {
		InAnswer inAnswer = this.inAnswerService.getAnswer(id);
		
		if(!inAnswer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
		}
		
		inAnswerForm.setContent(inAnswer.getContent());
		return "ins/inAnswer_form";
	}
	
	 @PreAuthorize("isAuthenticated()")
	 @PostMapping("/modify/{id}")
	public String anwerModify( @Valid InAnswerForm inAnswerForm ,BindingResult bindingResult , @PathVariable("id") Integer id,
			Principal principal) { 
		 if(bindingResult.hasErrors()) {
			 return "ins/inQuestion_detail";
		 }
		 InAnswer inAnswer = this.inAnswerService.getAnswer(id);
		 if(!inAnswer.getAuthor().getUsername().equals(principal.getName())) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
		 }
		 this.inAnswerService.modify(inAnswer, inAnswerForm.getContent());
		 return String.format("redirect:/inQuestion/detail/%s",inAnswer.getInQuestion().getId());
		 
	 }
	 
	 
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	 public String answerDelete(Principal principal,@PathVariable("id") Integer id) {
         
       InAnswer inAnswer = this.inAnswerService.getAnswer(id);
       if(!inAnswer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
		}
       this.inAnswerService.delete(inAnswer);
       return String.format("redirect:/inQuestion/detail/%s",inAnswer.getInQuestion().getId());
	}
	
	 
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(Principal principal,@PathVariable("id") Integer id) {
		InAnswer inAnswer = this.inAnswerService.getAnswer(id);
		SiteUser inUser = this.siteUserSerevice.getUser(principal.getName());
		this.inAnswerService.vote(inAnswer, inUser);
		return String.format("redirect:/inQuestion/detail/%s",inAnswer.getInQuestion().getId());
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
