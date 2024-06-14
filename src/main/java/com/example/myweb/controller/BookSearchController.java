package com.example.myweb.controller;

import com.example.myweb.service.BookSearchService;
import com.example.myweb.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookSearchController {

    @Autowired
    private BookSearchService bookSearchService;

    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "query", required = false) String query,
                              @RequestParam(value = "queryType", required = false, defaultValue = "Title") String queryType,
                              @RequestParam(value = "sort", required = false, defaultValue = "Accuracy") String sort,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "9") int size,
                              Model model, HttpSession session) {
        if (query != null && !query.isEmpty()) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Book> bookPage = bookSearchService.searchBooks(query, queryType, sort, pageRequest);
            model.addAttribute("books", bookPage.getContent());
            model.addAttribute("totalResults", bookPage.getTotalElements());
            model.addAttribute("totalPages", bookPage.getTotalPages());
            model.addAttribute("currentPage", page);
            model.addAttribute("query", query);
            model.addAttribute("queryType", queryType);
            model.addAttribute("sort", sort);
            model.addAttribute("size", size);
        }

        return "bookSearch";
    }
}
