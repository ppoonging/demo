package com.example.demo.board.kim.kquestion;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GangQuestionForm {

	@NotEmpty(message = "제목은 반드시 입력하셔야 합니다")
	@Size(max = 200)
	private String subject;
	
	@NotEmpty(message = "내용은 반드시 입력하셔야 합니다")
	private String content;
}
