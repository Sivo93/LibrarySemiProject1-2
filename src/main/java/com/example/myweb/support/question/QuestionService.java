package com.example.myweb.support.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.myweb.member.entity.MemberEntity;
import com.example.myweb.support.answer.AnswerEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	@SuppressWarnings("unused")
	private Specification<QuestionEntity> search(String kw) {
		return new Specification<>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<QuestionEntity> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); // 중복을 제거
				Join<QuestionEntity, MemberEntity> u1 = q.join("author", JoinType.LEFT);
				Join<QuestionEntity, AnswerEntity> a = q.join("answerList", JoinType.LEFT);
				Join<AnswerEntity, MemberEntity> u2 = a.join("author", JoinType.LEFT);
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
						cb.like(q.get("content"), "%" + kw + "%"), // 내용
						cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자
						cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
						cb.like(u2.get("username"), "%" + kw + "%")); // 답변 작성자
			}
		};
	}

	public Page<QuestionEntity> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return null;
	}

	public QuestionEntity getQuestion(Integer id) {
		Optional<QuestionEntity> question = this.questionRepository.findById(id);
		question.isPresent();
			return question.get();
		}
	

	public void create(String subject, String content, MemberEntity user) {
		QuestionEntity q = new QuestionEntity();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		this.questionRepository.save(q);
	}

	public void modify(QuestionEntity question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}

	public void delete(QuestionEntity question) {
		this.questionRepository.delete(question);
	}

	public void vote(QuestionEntity question, MemberEntity siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
}
