package com.example.demo.board.lee.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {

	@NotEmpty(message = "제목을 입력하쇼")// 문자가 들어오지않으면 저장되지 않게 만들기위해
	@Size(max =200)//제목 사이즈
	private String subject;
	
	@NotEmpty(message = "내용을 입력하쇼")
	private String content;
	
	@NotBlank(message = "카테고리선택은 필수항목입니다.")
	private String category;
}
