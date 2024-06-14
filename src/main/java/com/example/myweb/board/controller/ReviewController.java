package com.example.myweb.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.myweb.board.domain.Review;
import com.example.myweb.board.service.ReviewService;

import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public String getAllReviews(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "reviewList"; // reviewList.html 템플릿을 반환합니다.
    }

    @GetMapping("/reviews/{id}")
    public String getReviewById(@PathVariable Long id, Model model) {
        model.addAttribute("review", reviewService.getReviewById(id));
        return "reviewDetail"; // reviewDetail.html 템플릿을 반환합니다.
    }

    @GetMapping("/reviews/new")
    public String showReviewForm(Model model) {
        model.addAttribute("review", new Review());
        return "reviewForm"; // reviewForm.html 템플릿을 반환합니다.
    }

    @PostMapping("/reviews/save")
    public String saveReview(@ModelAttribute Review review) {
        reviewService.addReview(review);
        return "redirect:/reviews";
    }
    
    @PostMapping("/reviews")
    public String addReview(@ModelAttribute Review review) {
        reviewService.addReview(review);
        return "redirect:/reviews";
    }

    @GetMapping("/reviews/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("review", reviewService.getReviewById(id));
        return "reviewEdit"; // reviewEdit.html 템플릿을 반환합니다.
    }

    @PostMapping("/reviews/update")
    public String updateReview( @ModelAttribute Review review) {
        review.setId(review.getId());
        reviewService.updateReview(review);
        return "redirect:/reviews";
    }

    @GetMapping("/reviews/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
        return "redirect:/reviews";
    }
    
   
}