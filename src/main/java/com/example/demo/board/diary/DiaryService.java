package com.example.demo.board.diary;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;


    public void createDiary(String username, String subject, String content ){
        Diary d=new Diary();
        d.setUsername(username);
        d.setSubject(subject);
        d.setContent(content); // 줄바꿈 코드 content.replace("\n", "<br/>"));
        d.setCreateDateTime(LocalDateTime.now());
        this.diaryRepository.save(d);
    }


    public Page<Diary> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDateTime"));
                Pageable pageable =PageRequest.of(page, 10,Sort.by(sorts));
                return this.diaryRepository.findAll(pageable);
    }
}
