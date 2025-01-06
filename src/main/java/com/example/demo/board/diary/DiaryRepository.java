package com.example.demo.board.diary;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DiaryRepository extends JpaRepository<Diary, String> {

    Page<Diary> findAll(Pageable pageable);
}
