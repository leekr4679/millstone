package com.base.app.controllers;

import com.base.app.domain.diary.Diary;
import com.base.app.domain.diary.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private DiaryService diaryService;

    /*
     *   목록 불러오기
     *   PARAMETER : filter(검색어), option(검색옵션)
     *   SERVICE : list(String filter, String option)
     *   FUNCTION : 검색어와 검색조건이 없으면 모든 목록 불러옴, 검색어와 검색옵션이 있으면 조건에 검색조건에 맞춰 목록 불러옴
     * */
    @RequestMapping(value = "")
    public String main(Model model, String filter, String option) {
        List<Diary> list = diaryService.list(filter, option);
        model.addAttribute("diary", list);
        return "main";
    }

    /*
     *   일기 저장
     *   PARAMETER : 없음
     *   SERVICE : 없음
     *   FUNCTION : 일기 저장 페이지로 이동
     * */
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String write() {
        return "/diary/write";
    }

    /*
     *   일기 저장
     *   PARAMETER : diary
     *   SERVICE : write(Diary diary)
     *   FUNCTION : diary 객체(title, writer, content) 저장
     * */
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String write(Diary diary, HttpServletRequest request, MultipartFile files) {
        diaryService.write(diary, request, files);
        return "redirect:/";
    }

    /*
     *   일기 수정
     *   PARAMETER : diaryId
     *   SERVICE : modify(Long diaryId)
     *   FUNCTION : diaryId로 해당 데이터들 불러오기
     * */
    @RequestMapping(value = "/modify/{diaryId}", method = RequestMethod.GET)
    public String modify(Model model, @PathVariable Long diaryId) {
        model.addAttribute("diary", diaryService.modify(diaryId));
        return "/diary/modify";
    }

    /*
     *   일기 수정
     *   PARAMETER : diary
     *   SERVICE : modify(Diary diary)
     *   FUNCTION : diary 객체(title, writer, content) 수정(저장)
     * */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modify(Diary diary) {
        diaryService.modify(diary);
        return "redirect:/";
    }

    /*
     *   일기 삭제
     *   PARAMETER : diaryId
     *   SERVICE : delete(Long diaryId)
     *   FUNCTION : diaryId로 해당 데이터들 삭제
     * */
    @RequestMapping(value = "/delete/{diaryId}", method = RequestMethod.GET)
    public String delete(@PathVariable Long diaryId) {
        diaryService.delete(diaryId);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
