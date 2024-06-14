package com.example.myweb.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myweb.board.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}