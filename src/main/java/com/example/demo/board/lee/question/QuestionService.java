package com.example.demo.board.lee.question;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.DataNotFoundException;
import com.example.demo.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

	private final QuestionRepository questionRepository;
	

/*
	public Page<Question> getList(int page){
		List<Sort.Order> sorts= new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,10 , Sort.by(sorts));
		return this.questionRepository.findAll(pageable);
	
	}*/
		
	public void create(String subject, String content, SiteUser user, String category) {
		Question q= new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		q.setCategory(category);
		this.questionRepository.save(q);
	}
	
	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepository.findById(id);
		if(question.isPresent()) {
			Question question2 = question.get();
			question2.setView(question2.getView()+1);
			this.questionRepository.save(question2);
			return question2;
		}else {
			throw new DataNotFoundException("question not found");
		}
	}
	
	public void modify(Question question, String subject, String content, String category) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		question.setCategory(category);
		this.questionRepository.save(question);
	}
	public void delete(Question question) {
		this.questionRepository.delete(question); //question에서 객체를 삭제하자 라는 뜻.
	}
	
	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
	

	public Page<Question> getList(int page, String kw){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.questionRepository.findAllByKeyword(kw, pageable);
	}
	
	//상세보기에서 글 목록을 보기 위한 메서드
	public Page<Question> getListo(int page){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.questionRepository.findAll(pageable);
}
}
