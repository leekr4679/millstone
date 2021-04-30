package com.base.app.domain.diary;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
    public void write(Diary diary, HttpServletRequest request, MultipartFile files) {
        String domain = request.getServerName();
        String path = "/imageFile";

        String file_path = request.getSession().getServletContext().getRealPath("imageFile");
        File file = new File(file_path);
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String image_path = domain + ":" + request.getLocalPort() + path + "/" + diary.getImagePath();
        diary.setImagePath(image_path);

        diaryRepository.save(diary); }

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
