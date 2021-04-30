package com.base.app.domain.diary;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "DIARY")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIARY_ID", precision = 19, nullable = false)
    private Long diaryId; // 일기 id

    @Column
    private String writer; //일기 작성자

    @Column
    private String title; //일기 제목

    @Column
    private String content; //일기 내용

    @Column
    private String imagePath; //이미지 url
}
