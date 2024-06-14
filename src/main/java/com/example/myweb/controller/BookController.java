package com.example.myweb.controller;

import com.example.myweb.service.BookSearchService;
import com.example.myweb.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {

    @Autowired
    private BookSearchService bookSearchService;

    @GetMapping("/books/detail")
    public String bookDetail(@RequestParam("title") String title,
                             @RequestParam("author") String author,
                             Model model) {
        Book book = bookSearchService.getBookDetail(title, author);
        model.addAttribute("book", book);
        return "bookDetail";
    }
}
