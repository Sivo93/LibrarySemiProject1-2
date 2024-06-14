package com.example.myweb.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.myweb.board.domain.Notice;
import com.example.myweb.board.service.NoticeService;

import java.util.List;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/notices")
    public String getAllNotices(Model model) {
    	List<Notice> notices = noticeService.getAllNotices();
        model.addAttribute("notices", notices);
        return "noticeList"; // noticeList.html 템플릿을 반환합니다.
    }

    @GetMapping("/notices/{id}")
    public String getNoticeById(@PathVariable Long id, Model model) {
        model.addAttribute("notice", noticeService.getNoticeById(id));
        return "noticeDetail"; // noticeDetail.html 템플릿을 반환합니다.
    }

    @GetMapping("/notices/new")
    public String showNoticeForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "noticeForm"; // noticeForm.html 템플릿을 반환합니다.
    }
    
    @PostMapping("/notices/save")
    public String saveNotice(@ModelAttribute Notice notice) {
        noticeService.addNotice(notice);
        return "redirect:/notices";
    }

    @PostMapping("/notices")
    public String addNotice(@ModelAttribute Notice notice) {
        noticeService.addNotice(notice);
        return "redirect:/notices";
    }

    @GetMapping("/notices/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("notice", noticeService.getNoticeById(id));
        return "noticeEdit"; // noticeEdit.html 템플릿을 반환합니다.
    }

    @PostMapping("/notices/update")
    public String updateNotice(@ModelAttribute Notice notice) {
        notice.setId(notice.getId());
        noticeService.updateNotice(notice);
        return "redirect:/notices";
    }

    @GetMapping("/notices/delete/{id}")
    public String deleteNotice(@PathVariable Long id) {
        noticeService.deleteNoticeById(id);
        return "redirect:/notices";
    }
}
