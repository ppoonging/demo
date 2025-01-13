package com.example.demo.board.lee.question;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	
	Question findBySubject(String subject);
	//Question findBySubjectAndContent(String subject, String Content);
	//List<Question> findBySubjectLike(String subject);
     Page<Question> findAll(Pageable pageable);
     
     @Query("select distinct q from Question q "
    		+"left outer join SiteUser u1 on q.author =u1 " 
    		+"left outer join Answer a  on a.question =q "
    		+"left outer join SiteUser u2 on a.author =u1 "
    		+"Where "
    		+"q.subject like %:kw% " 
    		+"or q.content like %:kw% "//작성자
    		+"or u1.username like %:kw% "
    		+"or a.content like %:kw% " //답변
    		+"or u2.username like %:kw% ")
     Page<Question> findAllByKeyword(@Param("kw")String kw, Pageable pageable);
     
	
	
}
