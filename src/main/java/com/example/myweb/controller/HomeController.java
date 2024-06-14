package com.example.myweb.controller;

import com.example.myweb.service.BookSearchService;
import com.example.myweb.model.Book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private BookSearchService bookSearchService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        List<Book> bestsellers = bookSearchService.getBestsellers();
        List<Book> editorChoices = bookSearchService.getEditorChoices();
        List<Book> newSpecials = bookSearchService.getNewSpecials();

        model.addAttribute("bestsellers", bestsellers);
        model.addAttribute("editorChoices", editorChoices);
        model.addAttribute("newSpecials", newSpecials);


        return "index";
    }
}
