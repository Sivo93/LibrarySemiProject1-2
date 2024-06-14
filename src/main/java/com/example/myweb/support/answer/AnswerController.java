package com.example.myweb.support.answer;
import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.example.myweb.member.entity.MemberEntity;
import com.example.myweb.member.service.MemberService;
import com.example.myweb.support.question.QuestionEntity;
import com.example.myweb.support.question.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


public class AnswerController {
	
}