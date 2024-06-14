package com.example.myweb.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myweb.board.domain.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}