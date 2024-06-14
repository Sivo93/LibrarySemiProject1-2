package com.example.myweb.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.myweb.board.domain.Application;
import com.example.myweb.board.service.ApplicationService;

import java.util.List;

@Controller
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/applications")
    public String getAllApplications(Model model) {
        List<Application> applications = applicationService.getAllApplications();
        model.addAttribute("applications", applications);
        return "applicationList"; // applicationList.html 템플릿을 반환합니다.
    }

    @GetMapping("/applications/{id}")
    public String getApplicationById(@PathVariable Long id, Model model) {
        model.addAttribute("application", applicationService.getApplicationById(id));
        return "applicationDetail"; // applicationDetail.html 템플릿을 반환합니다.
    }

    @GetMapping("/applications/new")
    public String showApplicationForm(Model model) {
        model.addAttribute("application", new Application());
        return "applicationForm"; // applicationForm.html 템플릿을 반환합니다.
    }

    @PostMapping("/applications")
    public String saveApplication(@ModelAttribute Application application) {
        applicationService.addApplication(application);
        return "redirect:/applications";
    }
    @PostMapping("/applications/save")
    public String addApplication(@ModelAttribute Application application) {
        applicationService.addApplication(application);
        return "redirect:/applications";
    }

    @GetMapping("/applications/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("application", applicationService.getApplicationById(id));
        return "applicationEdit"; // applicationEdit.html 템플릿을 반환합니다.
    }

    @PostMapping("/applications/{id}")
    public String updateApplication(@PathVariable("id") Long id, @ModelAttribute Application application) {
        application.setId(id);
        applicationService.updateApplication(application);
        return "redirect:/applications";
    }

    @GetMapping("/applications/delete/{id}")
    public String deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplicationById(id);
        return "redirect:/applications";
    }
    
}
