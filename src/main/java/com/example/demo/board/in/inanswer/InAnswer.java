package com.example.demo.board.in.inanswer;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.demo.board.in.inquestion.InQuestion;
import com.example.demo.user.SiteUser;
import org.springframework.data.annotation.CreatedDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class InAnswer {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @CreatedDate
    private LocalDateTime createDate;

    
    @ManyToOne
    private InQuestion inQuestion;
    
    @ManyToOne
    private SiteUser author;
    
    
    private LocalDateTime modifyDate;
    
    
    @ManyToMany
    Set<SiteUser> voter;
    
    
    
    
}
