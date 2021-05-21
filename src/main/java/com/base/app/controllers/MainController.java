package com.base.app.controllers;

import com.base.app.domain.diary.Diary;
import com.base.app.domain.diary.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class MainController {

    @Autowired
    private DiaryService diaryService;

    /*
     *   목록 불러오기
     *   METHOD : GET
     *   PARAMETER : filter(검색어), option(검색옵션), pageable(페이징)
     *   SERVICE : list(String filter, String option)
     *   FUNCTION : 검색어와 검색조건이 없으면 모든 목록 불러옴, 검색어와 검색옵션이 있으면 조건에 검색조건에 맞춰 목록 불러옴
     * */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(Model model, Pageable pageable) {
        Page<Diary> list = diaryService.list(pageable);
        model.addAttribute("diary", list);
        return "/main";
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Page<Diary> list(@RequestBody int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 20, Sort.by("id"));
        Page<Diary> list = diaryService.list(pageable);
        /*model.addAttribute("diary", list);
        model.addAttribute("diarySize", list.getSize());*/
        return list;
    }

    /*
     *   목록 불러오기
     *   METHOD : POST
     *   PARAMETER : filter(검색어), option(검색옵션)
     *   SERVICE : list(String filter, String option)
     *   FUNCTION : 검색어와 검색조건이 없으면 모든 목록 불러옴, 검색어와 검색옵션이 있으면 조건에 검색조건에 맞춰 목록 불러옴
     * */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, String filter, String option, Pageable pageable) {
        Page<Diary> list = diaryService.list(filter, option, pageable);
        model.addAttribute("diary", list);
        model.addAttribute("filter", filter);
        model.addAttribute("option", option);
        return "/diary/search";
    }

    @ResponseBody
    @RequestMapping(value = "/search/{option}/{filter}", method = RequestMethod.POST)
    public Page<Diary> lists(@RequestBody int pageNum, @PathVariable String filter, @PathVariable String option) {
        Pageable pageable = PageRequest.of(pageNum, 20, Sort.by("id"));
        Page<Diary> list = diaryService.list(filter, option, pageable);
        return list;
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
     *   PARAMETER : diary, MultipartHttpServletRequest
     *   SERVICE : write(Diary diary, MultipartFile file)
     *   FUNCTION : diary 객체(title, writer, content, imagePath) 저장
     * */
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String write(Diary diary, MultipartHttpServletRequest files) {
        MultipartFile file = files.getFile("imageFile");
        diaryService.write(diary, file);
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
    public String modify(Diary diary, MultipartHttpServletRequest files) {
        MultipartFile file = files.getFile("imageFile");
        diaryService.modify(diary, file);
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
