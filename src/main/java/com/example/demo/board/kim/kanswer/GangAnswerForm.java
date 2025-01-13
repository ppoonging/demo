package com.example.demo.board.kim.kanswer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GangAnswerForm {

	@NotEmpty(message = "내용은 반드시 입력")
	public String content;
}
