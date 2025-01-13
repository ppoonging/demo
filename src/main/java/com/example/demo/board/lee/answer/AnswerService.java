package com.example.demo.board.lee.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.DataNotFoundException;
import com.example.demo.board.lee.question.Question;
import com.example.demo.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

	private final AnswerRepository answerRepository;
	
	/*타입을 void로 잡은 이유는 insert로 저장만 하면되고 
	리턴을 할 필요가 없기 때문임*/
	//리턴 타입을 answer로 잡앗기 때문에 리턴도 answer로 잡아야함.
	public Answer create(Question question , String content, SiteUser author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		this.answerRepository.save(answer);
		return answer;
	}
	public Answer getAnswer(Integer id) {
		Optional<Answer> answer = this.answerRepository.findById(id);
		if(answer.isPresent()) {
			return answer.get();
		}else {
			throw new DataNotFoundException("객체가 없습니다.");
		}
	}
	
	public void modify(Answer answer,String content) {
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}
	public void delete(Answer answer) {
		this.answerRepository.delete(answer);
	}
	public void vote(Answer answer, SiteUser siteUser) {
		answer.getVoter().add(siteUser);
		this.answerRepository.save(answer);
	}
	
}
