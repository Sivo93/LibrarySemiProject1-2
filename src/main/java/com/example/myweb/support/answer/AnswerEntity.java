package com.example.myweb.support.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.myweb.member.entity.MemberEntity;
import com.example.myweb.support.question.QuestionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AnswerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createDate;

	@ManyToOne
	private QuestionEntity question;

	@ManyToOne
	private MemberEntity author;

	private LocalDateTime modifyDate;
	
	@ManyToMany
    Set<MemberEntity> voter;
}
