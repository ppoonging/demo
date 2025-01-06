package com.example.demo.board.diary;


import com.example.demo.user.SiteUser;
import com.example.demo.user.SiteUserSerevice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequiredArgsConstructor
@Controller
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;
    private final SiteUserSerevice diaryUserSerevice;

    @GetMapping("/main")
    public String mainDiary() {
        return "diary/mainDiary_form";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String diaryCreate(DiaryForm diaryForm) {
        return "diary/diaryCreate_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String diaryCreate( @Valid DiaryForm diaryForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "diary/diaryCreate_form";
        }
        SiteUser diaryUser = this.diaryUserSerevice.getUser(principal.getName());
            this.diaryService.createDiary(diaryForm.getUsername()
                    , diaryForm.getSubject(), diaryForm.getContent());
            return "redirect:/diary/list";


    }

     @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page",defaultValue = "0") int page) {
        Page<Diary> paging =  this.diaryService.getList(page);
        model.addAttribute("paging", paging);
        return "diary/diary_list";
    }
}
