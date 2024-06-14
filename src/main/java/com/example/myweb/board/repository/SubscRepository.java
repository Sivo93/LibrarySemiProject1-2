package com.example.myweb.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myweb.board.domain.Subsc;

public interface SubscRepository extends JpaRepository<Subsc, Long> {
}