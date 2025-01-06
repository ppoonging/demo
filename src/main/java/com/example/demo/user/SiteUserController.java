package com.example.demo.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class SiteUserController {


    private final SiteUserSerevice diaryUserService;


    @GetMapping("/signup")
    public String userCreate(SiteUserForm diaryUserForm) {
        return "signup_form";
    }


    @PostMapping("/signup")
    public String userCreate(@Valid SiteUserForm diaryUserForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if(!diaryUserForm.getPassword1().equals(diaryUserForm.getPassword2())){
            bindingResult.rejectValue("passwword2", "passwordError", "두 개의 비밀번호가 다릅니다.");
            return "signup_form";
        }
        try{
            diaryUserService.create(diaryUserForm.getMyName() ,diaryUserForm.getUsername()
                , diaryUserForm.getPassword1(), diaryUserForm.getEmail(), diaryUserForm.getPhoneNumber());

        }catch(DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("fall","이미 등록된 사용자입니다");
            return "signup_form";
        }catch(Exception e){
            e.printStackTrace();
            bindingResult.reject("fall",e.getMessage());
            return "signup_form";
        }
        return"redirect:/";
    }
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }


}
