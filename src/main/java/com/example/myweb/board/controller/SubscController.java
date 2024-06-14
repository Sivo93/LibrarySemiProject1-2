package com.example.myweb.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.myweb.board.domain.Subsc;
import com.example.myweb.board.service.SubscService;

import java.util.List;

@Controller
public class SubscController {

    @Autowired
    private SubscService subscService;

    @GetMapping("/subscs")
    public String getSubscs(Model model) {
        List<Subsc> subscs = subscService.getAllSubscs();
        model.addAttribute("subscs", subscs);
        return "subscList";
    }

    @GetMapping("/subscs/{id}")
    public String getSubscById(@PathVariable Long id, Model model) {
        model.addAttribute("subsc", subscService.getSubscById(id));
        return "subscDetail"; 
    }

    @GetMapping("/subscs/new")
    public String showSubscForm(Model model) {
        model.addAttribute("subsc", new Subsc());
        return "subscForm"; // applicationForm.html 템플릿을 반환합니다.
    }

    @PostMapping("/subscs")
    public String saveSubsc(@ModelAttribute Subsc subsc) {
    	subscService.addSubsc(subsc);
        return "redirect:/subscs";
    }
    @PostMapping("/subscs/save")
    public String addSubsc(@ModelAttribute Subsc subsc) {
    	subscService.addSubsc(subsc);
        return "redirect:/subscs";
    }

    @GetMapping("/subscs/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("subsc", subscService.getSubscById(id));
        return "subscEdit"; 
    }

    @PostMapping("/subscs/update")
    public String updateSubsc( @ModelAttribute Subsc subsc) {
        subsc.setId(subsc.getId());
        subscService.updateSubsc(subsc);
        return "redirect:/subscs";
    }

    @GetMapping("/subscs/delete/{id}")
    public String deleteSubsc(@PathVariable Long id) {
    	subscService.deleteSubscById(id);
        return "redirect:/subscs";
    }
    
}
