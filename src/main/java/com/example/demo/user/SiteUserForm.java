package com.example.demo.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserForm {
    @NotEmpty(message = "이름을 입력하세요")
    private String myName; //아이디

    @NotEmpty(message = "아이디를 입력하세요")
    private String username; //이름




    private String password1;

    private String password2;

    @Email
    private String email;

    private String phoneNumber;





}
