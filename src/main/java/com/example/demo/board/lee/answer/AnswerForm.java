package com.example.demo.board.lee.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

	
	@NotEmpty(message ="답변을 입력하쇼")
	public String content;
	
	
	
}
