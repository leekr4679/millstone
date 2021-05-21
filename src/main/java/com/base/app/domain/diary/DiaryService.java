package com.base.app.domain.diary;

import com.base.app.domain.BaseService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import javafx.util.Builder;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DiaryService extends BaseService<Diary, Long> {
    private DiaryRepository diaryRepository;

    @Inject
    public DiaryService(DiaryRepository diaryRepository) {
        super(diaryRepository);
        this.diaryRepository = diaryRepository;
    }

    /*
     *   목록 불러오기
     *   PARAMETER : filter(검색어), option(검색옵션)
     *   RETURN : LIST
     *   FUNCTION : 검색어와 검색조건이 없으면 모든 목록 불러옴, 검색어와 검색옵션이 있으면 조건에 검색조건에 맞춰 목록 불러옴
     * */
    public PageImpl list(Pageable pageable) {

        QueryResults results = select().from(qDiary)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qDiary.diaryId.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public PageImpl list(String filter, String option, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(filter)) {
            if (option.equals("title")) {
                builder.and(qDiary.title.contains(filter));
            }

            if (option.equals("content")) {
                builder.and(qDiary.content.contains(filter));
            }

            if (option.equals("writer")) {
                builder.and(qDiary.writer.contains(filter));
            }
        }

        QueryResults results = select().from(qDiary)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qDiary.diaryId.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    /*
     *   일기 저장
     *   PARAMETER : diary(title, writer, content, imagePath), MultipartFile
     *   RETURN : void
     *   FUNCTION : 제목, 작성자, 내용, c:/upload/diary 안에 이미지(파일) 저장
     *   ETC : diary 저장 전에 이미지(파일) 저장 중에 폴더 생성 및 이미지(파일) 중복 시 이름 변경되서 저장
     * */
    public void write(Diary diary, MultipartFile multipartFile) {
        File path = new File(new File("").getAbsolutePath() + "/src/main/resources/static/img");
        File storePath = new File(path, String.join("/", "diary")); //기본 경로 이후의 경로
        // c:/upload 파일안에 diary 파일이 없으면 생성
        if (!storePath.exists()) {
            try {
                storePath.mkdirs(); // storePath가 없으면 폴더생성
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 초기 파일 이름
        String originalFileName = multipartFile.getOriginalFilename();
        File file = new File(storePath,  originalFileName);

        // 초기 파일 이름 변경 ex) originalFileName.jpg --> 현재시간(yyMMddHHmmss)_originalFileName.jpg
        if (originalFileName != null && !originalFileName.equals("")) {
            if (file.exists()) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
                String currentTime = format.format(calendar.getTime());
                originalFileName = currentTime + "_" + originalFileName;
                file = new File(storePath, originalFileName);
            }
        }

        // 파일 저장
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // basePath 없이 저장시키고 싶으면 -> file.getAbsolutePath().replace(basePath.getAbsolutePath(), "")
        diary.setImagePath("img/diary/" + originalFileName);
        diaryRepository.save(diary);
    }

    /*
     *   일기 수정
     *   PARAMETER : diaryId
     *   RETURN : Diary
     *   FUNCTION : 컨트롤러에서 전달받은 diaryId로 해당 데이터들 불러오기
     * */
    public Diary modify(Long diaryId) {
        return diaryRepository.findAllByDiaryId(diaryId);
    }

    /*
     *   일기 수정
     *   PARAMETER : diary(title, writer, content)
     *   RETURN : void
     *   FUNCTION : diary 객체(title, writer, content)를 수정(저장)
     * */
    public void modify(Diary diary, MultipartFile multipartFile) {
        File path = new File(new File("").getAbsolutePath() + "/src/main/resources/static/img");
        File storePath = new File(path, String.join("/", "diary"));

        if (!storePath.exists()) {
            try {
                storePath.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String originalFileName = multipartFile.getOriginalFilename();
        File file = new File(storePath, originalFileName);

        if (originalFileName != null && !originalFileName.equals("")) {
            if (file.exists()) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
                String currentTime = format.format(calendar.getTime());
                originalFileName = currentTime + "_" + originalFileName;
                file = new File(storePath, originalFileName);
            }
        }

        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        diary.setImagePath("img/diary/" + originalFileName);
        diaryRepository.save(diary);
    }

    /*
     *   일기 삭제
     *   PARAMETER : diaryId
     *   RETURN : void
     *   FUNCTION : 컨트롤러에서 전달받은 diaryId로 해당 데이터들 삭제
     * */
    public void delete(Long diaryId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qDiary.diaryId.eq(diaryId));

        Diary diary = select().from(qDiary).where(builder).fetchOne();
        File file = new File(new File("").getAbsolutePath() + "/src/main/resources/static/" + diary.getImagePath());
        System.out.println("file : " + file.toString());

        if (!deleteFile(file)) {
            deleteFile(file);
        }

        diaryRepository.deleteById(diaryId);
    }

    private boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            deleteFile(file);
        } else {
            try {
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
                }
        }

        return file.delete();
    }
}