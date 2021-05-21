package com.base.app.domain.diary;

import com.base.app.domain.BaseJpaModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "DIARY")
public class Diary extends BaseJpaModel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIARY_ID", precision = 19, nullable = false)
    private Long diaryId; // 일기 id

    @Column(name = "writer")
    private String writer; //일기 작성자

    @Column(name = "title")
    private String title; //일기 제목

    @Column(name = "content")
    private String content; //일기 내용

    @Column(name = "imagePath")
    private String imagePath; //이미지 url

    @Column(name = "filePath")
    private String filePath;

    @Override
    public Long getId() {
        return diaryId;
    }
}
