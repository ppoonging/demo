package com.example.demo.board.in.inquestion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


import com.example.demo.board.in.inanswer.InAnswer;


import com.example.demo.user.SiteUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class InQuestion {
        
	    @Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
	    
	    @Column(length = 200)
	    private String subject;
	    
	    @Column(columnDefinition = "TEXT")
	    private String content;
	
	    private LocalDateTime createDate;
	    
	    @OneToMany(mappedBy = "inQuestion", cascade =  CascadeType.REMOVE)
	    private List<InAnswer> answerList;
	   
	    
	    @ManyToOne
	    private SiteUser author;
	   
	    
	    private LocalDateTime modifyDate;
	    
	    
	    @ManyToMany
	    Set<SiteUser> voter;
	    
	    
	                    
	   
}
