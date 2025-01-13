package com.example.demo.board.in.inanswer;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.demo.DataNotFoundException;
import com.example.demo.board.in.inquestion.InQuestion;
import com.example.demo.user.SiteUser;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InAnswerService {

	private final InAnswerRepository inAnswerRepository;
	
	public InAnswer create(InQuestion inQuestion, String content, SiteUser author) {
		InAnswer answer = new InAnswer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setInQuestion(inQuestion);
		answer.setAuthor(author);
		this.inAnswerRepository.save(answer);
		return answer;
	}
	
	public InAnswer getAnswer(Integer id) {
		
		Optional<InAnswer> answer = this.inAnswerRepository.findById(id);
		
		if(answer.isPresent()){
		return answer.get();
		}else {
			throw new DataNotFoundException("데이터 ㄴㄴ");
		}
			
	}
		public void modify(InAnswer inAnswer,String content) {
			inAnswer.setContent(content);
			inAnswer.setModifyDate(LocalDateTime.now());
			this.inAnswerRepository.save(inAnswer);
		}
		
		
		public void delete(InAnswer inAnswer) {
			this.inAnswerRepository.delete(inAnswer);
		}
		
		
		public void vote(InAnswer inAnswer,SiteUser inUser) {
			inAnswer.getVoter().add(inUser);
			this.inAnswerRepository.save(inAnswer);
		}
		
	
	
	
}
