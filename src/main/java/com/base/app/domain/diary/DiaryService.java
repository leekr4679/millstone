package com.base.app.domain.diary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    /*
    *   목록 불러오기
    *   PARAMETER : filter(검색어), option(검색옵션)
    *   RETURN : LIST
    *   FUNCTION : 검색어와 검색조건이 없으면 모든 목록 불러옴, 검색어와 검색옵션이 있으면 조건에 검색조건에 맞춰 목록 불러옴
    * */
    public List<Diary> list(String filter, String option) {
        if (filter == null) {
            return diaryRepository.findAllByOrderByDiaryIdDesc();
        } else {
            List<Diary> list = new ArrayList<>();

            if (option.equals("title")) {
                list = diaryRepository.findAllByTitleContainsOrderByDiaryIdDesc(filter);
            }

            if (option.equals("content")) {
                list = diaryRepository.findAllByContentContainsOrderByDiaryIdDesc(filter);
            }

            if (option.equals("writer")) {
                list = diaryRepository.findAllByWriterContainsOrderByDiaryIdDesc(filter);
            }

            return list;
        }
    }

    /*
     *   일기 저장
     *   PARAMETER : diary(title, writer, content)
     *   RETURN : void
     *   FUNCTION : diary 객체(title, writer, content) 저장
     * */
    public void write(Diary diary) { diaryRepository.save(diary); }

    /*
     *   일기 수정
     *   PARAMETER : diaryId
     *   RETURN : Diary
     *   FUNCTION : 컨트롤러에서 전달받은 diaryId로 해당 데이터들 불러오기
     * */
    public Diary modify(Long diaryId) { return diaryRepository.findAllByDiaryId(diaryId); }

    /*
     *   일기 수정
     *   PARAMETER : diary(title, writer, content)
     *   RETURN : void
     *   FUNCTION : diary 객체(title, writer, content)를 수정(저장)
     * */
    public void modify(Diary diary) { diaryRepository.save(diary); }

    /*
     *   일기 삭제
     *   PARAMETER : diaryId
     *   RETURN : void
     *   FUNCTION : 컨트롤러에서 전달받은 diaryId로 해당 데이터들 삭제
     * */
    public void delete(Long diaryId) { diaryRepository.deleteById(diaryId); }
}