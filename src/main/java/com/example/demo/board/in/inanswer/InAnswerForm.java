package com.example.demo.board.in.inanswer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InAnswerForm {

	@NotEmpty(message = "내용은 반드시 입력하셔야 합니다")
	public String content;
	
	
	
}
