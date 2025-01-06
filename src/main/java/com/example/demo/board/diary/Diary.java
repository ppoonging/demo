package com.example.demo.board.diary;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Diary {

    @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 30)
    private String username;
    @Column(length = 30)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDateTime;



}
