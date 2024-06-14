package com.example.myweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class BookDetailController {
	
	// 책 상세 정보 페이지
    @GetMapping("/bookDetail")
    public String bookDetail(@RequestParam String title,
                             @RequestParam String thumbnail,
                             @RequestParam String authors,
                             @RequestParam String publisher,
                             @RequestParam String datetime,
                             @RequestParam String contents,
                             @RequestParam String url,
                             @RequestParam String isbn,
                             @RequestParam String stockstatus,
                             @RequestParam String status,
                             @RequestParam String translators,
                             Model model) {
        // 날짜 포맷
        LocalDate date = LocalDate.parse(datetime);
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        model.addAttribute("title", title);
        model.addAttribute("thumbnail", thumbnail);
        model.addAttribute("authors", authors);
        model.addAttribute("publisher", publisher);
        model.addAttribute("datetime", formattedDate);
        model.addAttribute("contents", contents);
        model.addAttribute("url", url);
        model.addAttribute("isbn", isbn);
        model.addAttribute("stockstatus", stockstatus);
        model.addAttribute("status", status);
        model.addAttribute("translators", translators);
        return "bookDetail";
    }
}
