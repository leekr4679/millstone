package com.base.app.domain.diary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    // 데이터들 전부 찾기(내림차순)
    List<Diary> findAllByOrderByDiaryIdDesc();

    // 검색어로 title 찾기(내림차순)
    List<Diary> findAllByTitleContainsOrderByDiaryIdDesc(String filter);

    // 검색어로 content 찾기(내림차순)
    List<Diary> findAllByContentContainsOrderByDiaryIdDesc(String filter);

    // 검색어로 writer 찾기(내림차순)
    List<Diary> findAllByWriterContainsOrderByDiaryIdDesc(String filter);

    // diaryId로 해당 데이터들 가져옴
    Diary findAllByDiaryId(Long diaryId);
}
