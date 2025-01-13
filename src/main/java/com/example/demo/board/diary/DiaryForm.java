package com.example.demo.board.diary;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryForm {

    @Size(min = 1, max = 20)
    @NotEmpty(message = "제목을 입력해주세요")
    private String subject;

    @Size(min = 10, max = 500)
    @NotEmpty(message = "내용을 입력해주세요")
    private String content;


}
