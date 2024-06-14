package com.example.myweb.support.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {
	QuestionEntity findBySubject(String subject);

	QuestionEntity findBySubjectAndContent(String subject, String content);

	List<QuestionEntity> findBySubjectLike(String subject);

	Page<QuestionEntity> findAll(Pageable pageable);

	Page<QuestionEntity> findAll(Specification<QuestionEntity> spec, Pageable pageable);
	
}
