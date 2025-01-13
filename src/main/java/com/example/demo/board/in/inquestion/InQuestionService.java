package com.example.demo.board.in.inquestion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.DataNotFoundException;
import com.example.demo.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;





@RequiredArgsConstructor
@Service
public class InQuestionService {

private final InQuestionRepository  inQuestionRepository;
	
	public Page<InQuestion> getList(int page){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
		return this.inQuestionRepository.findAll(pageable);
	}
	
	public InQuestion getQuestion(Integer id) {
		
		Optional<InQuestion> question = this.inQuestionRepository.findById(id);
		
		if(question.isPresent()) {
			return question.get();
		}else {
			throw new DataNotFoundException("검색한 데이터가 없습니다");
		}
		
	}
	
	public void create(String subject , String content , SiteUser user) {
		
		InQuestion q = new InQuestion();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		this.inQuestionRepository.save(q);
		
	}
	
	public void modify(InQuestion inQuestion, String subject,String content) {
		inQuestion.setSubject(subject);
		inQuestion.setContent(content);
		inQuestion.setModifyDate(LocalDateTime.now());
		this.inQuestionRepository.save(inQuestion);
	}
	
	
	public void delete(InQuestion inQuestion){
		this.inQuestionRepository.delete(inQuestion);
	}
	
	
	public void vote(InQuestion inQuestion,SiteUser inUser) {
		inQuestion.getVoter().add(inUser);
		this.inQuestionRepository.save(inQuestion);
	}
	
	
	
	
	
	
	
	
	
}
