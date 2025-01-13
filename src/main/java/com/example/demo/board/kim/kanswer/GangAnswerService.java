package com.example.demo.board.kim.kanswer;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.demo.DataNotFoundException;
import com.example.demo.board.kim.kquestion.GangQuestion;
import com.example.demo.user.SiteUser;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GangAnswerService {

	private final GangAnswerRepository answerRepository;
	
	public void create(GangQuestion gangQuestion, String content, SiteUser author) {
		GangAnswer answer = new GangAnswer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setGangQuestion(gangQuestion);
		answer.setAuthor(author);
		this.answerRepository.save(answer);
	}
	
	public GangAnswer getAnswer(Integer id) {
		Optional<GangAnswer> answer = this.answerRepository.findById(id);
		if(answer.isPresent()) {
			return answer.get();
		}else {
			throw new DataNotFoundException("답변이 없음");
		}
	}
	
	public void modify(GangAnswer gangAnswer, String content) {
		gangAnswer.setContent(content);
		gangAnswer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(gangAnswer);
	}
	
	public void delete(GangAnswer gangAnswer) {
		this.answerRepository.delete(gangAnswer);
	}
	
	public void vote(GangAnswer gangAnswer, SiteUser gangUser) {
		gangAnswer.getVoter().add(gangUser);
		this.answerRepository.save(gangAnswer);
	}
}
