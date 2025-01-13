package com.example.demo.board.kim.kquestion;

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
public class GangQuestionService {

	private final GangQuestionRepository gangQuestionRepository;
	
	
	public Page<GangQuestion> getlist(int page) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.gangQuestionRepository.findAll(pageable);
	}
	
	public GangQuestion getGangQuestion(Integer id) {
		Optional<GangQuestion> gangQuestion = this.gangQuestionRepository.findById(id);
		
		if(gangQuestion.isPresent()) {
			return gangQuestion.get();
		}else {
			throw new DataNotFoundException("검색한 데이터가 없습니다");
		}
	}
	
	public void create(String subject, String content, SiteUser siteUser) {
		GangQuestion q = new GangQuestion();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(siteUser);
		this.gangQuestionRepository.save(q);
	}
	
	public void modify(GangQuestion gangQuestion, String subject, String content) {
		gangQuestion.setSubject(subject);
		gangQuestion.setContent(content);
		gangQuestion.setModifyDate(LocalDateTime.now());
		this.gangQuestionRepository.save(gangQuestion);
	}
	
	public void delete(GangQuestion gangQuestion) {
		this.gangQuestionRepository.delete(gangQuestion);
	}
	
	public void vote(GangQuestion gangQuestion, SiteUser gangUser) {
		gangQuestion.getVoter().add(gangUser);
		this.gangQuestionRepository.save(gangQuestion);
	}
}
