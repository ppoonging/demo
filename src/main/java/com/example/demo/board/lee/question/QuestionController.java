package com.example.demo.board.lee.question;

import java.security.Principal;

import com.example.demo.user.SiteUserSerevice;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.board.lee.answer.AnswerForm;
import com.example.demo.user.SiteUser;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequestMapping("/question")
@Controller
@RequiredArgsConstructor // 생성자 까지 만들어준다
@EnableMethodSecurity(prePostEnabled = true) //질문등록 답변등록 메소드 위에 설정한@PreAuthorize("isAuthenticated()")를 사용할 수 있게 설정해줌
public class QuestionController {
	//findAll 메소드를 이용하여 게시판의 글 목록을 검색할거

	private final QuestionService questionService;
	private final SiteUserSerevice siteUserSerevice;





	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page" , defaultValue = "0")int page,
			@RequestParam(value = "kw", defaultValue = "")String kw) {
		Page<Question> paging =this.questionService.getList(page, kw);
		model.addAttribute("paging",paging);  //(담길곳,담을것)
		model.addAttribute("kw", kw);
		return "lee/question_list";
	}

	//질문작성 페이지로 이동 리승용만들꺼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "lee/question_form";
	}

	/* 질문저장
		@PostMapping("/create")
		public String questionCreate(@RequestParam(value= "subject") String subject,
				@RequestParam(value = "content") String content) {
			this.questionService.create(subject, content);
			return "redirect:/question/list";
		}
	 */		


	//
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm ,BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "lee/question_form";
		} 
		SiteUser siteUser = this.siteUserSerevice.getUser(principal.getName());
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser,questionForm.getCategory());
		return "redirect:/question/list";
	}


	//리승용작성
	/*@GetMapping("/detail/{id}")
	public String detail(Model model , @PathVariable("id") Integer id ,AnswerForm answerForm) {
		Question question=this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
		*/
	
	
		//상세보기에서 글 목록을 띄우기 위한 메서드
		@GetMapping("/detail/{id}")
		public String detail(Model model , @PathVariable("id") Integer id ,AnswerForm answerForm, 
				@RequestParam(value = "page" , defaultValue = "0")int page) {
			Question question=this.questionService.getQuestion(id);
			Page<Question> paging =this.questionService.getListo(page);
			model.addAttribute("paging",paging); 
			model.addAttribute("question", question);
			return "lee/question_detail";
		
		
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm,BindingResult bindingResult,
			Principal principal, @PathVariable("id") Integer id) {
		if(bindingResult.hasErrors()) {
			return "lee/question_form";
		}			
	Question question = this.questionService.getQuestion(id);
	if(!question.getAuthor().getUsername().equals(principal.getName())){
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
	}
	this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent(), questionForm.getCategory());
	return String.format("redirect:/question/detail/%s", id);
	}
	
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionForm, 
			@PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
		}
	questionForm.setSubject(question.getSubject());
	questionForm.setContent(question.getContent());
	questionForm.setCategory(question.getCategory());
	return"lee/question_form";
	}	
	
	@PreAuthorize("isAuthenticated()") 
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		Question question =this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다."); 
		}
		
		this.questionService.delete(question);
		return "redirect:/";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.siteUserSerevice.getUser(principal.getName());
		this.questionService.vote(question, siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}
}