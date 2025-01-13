package com.example.demo.board.kim.kquestion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GangQuestionRepository extends JpaRepository<GangQuestion, Integer>{

	/*
	 * GangQuestion findBySubject(String subject); GangQuestion
	 * findBySubjectAndContent(String subject, String content); GangQuestion
	 * findByContentAndId(String content, Integer id);
	 */
	Page<GangQuestion> findAll(Pageable pageable);
}
