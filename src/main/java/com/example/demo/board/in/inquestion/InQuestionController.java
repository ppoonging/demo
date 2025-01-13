package com.example.demo.board.in.inquestion;

import java.security.Principal;

import com.example.demo.board.in.inanswer.InAnswerForm;
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

@RequiredArgsConstructor
@Controller
@RequestMapping("/inQuestion")
public class InQuestionController {

	private final SiteUserSerevice siteUserSerevice;
	private final InQuestionService inQuestionService; 
	
	@GetMapping("/list")
	    public String list(Model model , @RequestParam(value = "page",defaultValue = "0") int page
	    		){
		Page<InQuestion> paging = this.inQuestionService.getList(page);
		model.addAttribute("paging",paging);
		
		return "ins/inQuestion_list";
	}
	   
	   @GetMapping("/detail/{id}")
	  public String detail(Model model,@PathVariable("id")Integer id , InAnswerForm inAnswerForm ) {
		   InQuestion inQuestion = this.inQuestionService.getQuestion(id);
		   model.addAttribute("inQuestion",inQuestion);
		  return "ins/inQuestion_detail";
	  }
	   
	   @PreAuthorize("isAuthenticated()")
	   @GetMapping("/create")
		public String questionCreate(InQuestionForm inQuestionForm ) {
			return "ins/inQuestion_form";
		} 
	  
	   /*
	   @PostMapping("/create")
	   public String questionCreate(@RequestParam(value="subject")String subject,
	       @RequestParam(value="content") String content ) {
		   this.questionService.create(subject, content);
		   return "redirect:/question/list";
	   }
	   */
	   
	   @PreAuthorize("isAuthenticated()")
	   @PostMapping("/create")
	   public String questionCreate(@Valid InQuestionForm inQuestionForm , BindingResult bindingResult ,Principal principal) {
		   if(bindingResult.hasErrors()) {
			   return "ins/question_form";
		   }
		   SiteUser inUser = this.siteUserSerevice.getUser(principal.getName());
		   this.inQuestionService.create(inQuestionForm.getSubject(), inQuestionForm.getContent(),inUser);
		   return "redirect:/inQuestion/list";
	   }
	   
	   @PreAuthorize("isAuthenticated()")
	   @GetMapping("/modify/{id}")
	   public String questionModify(InQuestionForm inQuestionForm,@PathVariable("id") Integer id, Principal principal) {
		   InQuestion inQuestion = this.inQuestionService.getQuestion(id);
		   if(!inQuestion.getAuthor().getUsername().equals(principal.getName())) {
			   throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
		   }
		   inQuestionForm.setSubject(inQuestion.getSubject());
		   inQuestionForm.setContent(inQuestion.getContent());
		   return "ins/inQuestion_form";
	   }
	
	   
	   @PreAuthorize("isAuthenticated()")
	   @PostMapping("/modify/{id}")
	   public String questionModify(@Valid InQuestionForm inQuestionForm , BindingResult bindingResult,Principal principal,
			  @PathVariable("id")Integer id) {
		   if(bindingResult.hasErrors()) {
			   return "ins	/inQuestion_form";
		   }
		   
		   InQuestion inQuestion = this.inQuestionService.getQuestion(id);
		   if(!inQuestion.getAuthor().getUsername().equals(principal.getName())) {
			   throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
		   }
		   this.inQuestionService.modify(inQuestion, inQuestionForm.getSubject(), inQuestionForm.getContent());
		   return String.format("redirect:/inQuestion/detail/%s", id);
	   }
	   
	   
	   @PreAuthorize("isAuthenticated()")
	   @GetMapping("/delete/{id}")
	   public String questionDelete(Principal principal,@PathVariable("id")Integer id){
		 InQuestion inQuestion = this.inQuestionService.getQuestion(id);
		   if(!inQuestion.getAuthor().getUsername().equals(principal.getName())) {
			   throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다");
		   }
		   this.inQuestionService.delete(inQuestion);
		   return "redirect:/";
	   }
	  
	   @PreAuthorize("isAuthenticated()")
	   @GetMapping("/vote/{id}")
	   public String questionVote(Principal principal,@PathVariable("id") Integer id) {
		   InQuestion inQuestion = this.inQuestionService.getQuestion(id);
		   SiteUser inUser = this.siteUserSerevice.getUser(principal.getName());
		   this.inQuestionService.vote(inQuestion, inUser);
		   return String.format("redirect:/inQuestion/detail/%s", id);
	   }
	   
	   
	   
	
}
