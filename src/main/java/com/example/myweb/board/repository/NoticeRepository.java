package com.example.myweb.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myweb.board.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
