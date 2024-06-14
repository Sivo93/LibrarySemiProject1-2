package com.example.myweb.support.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.example.myweb.member.entity.MemberEntity;
import com.example.myweb.support.answer.AnswerEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class QuestionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 200)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createDate;

	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<AnswerEntity> answerList;

	@ManyToOne
	private MemberEntity author;

	private LocalDateTime modifyDate;
	
	@ManyToMany
    Set<MemberEntity> voter;
}
