package com.example.myweb.support.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.myweb.member.entity.MemberEntity;
import com.example.myweb.support.question.QuestionEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

	private final AnswerRepository answerRepository;

	public AnswerEntity create(QuestionEntity question, String content, MemberEntity author) {
		AnswerEntity answer = new AnswerEntity();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		this.answerRepository.save(answer);
		return answer;
	}

	public AnswerEntity getAnswer(Integer id) {
		Optional<AnswerEntity> answer = this.answerRepository.findById(id);
		if (answer.isPresent()) {
			return answer.get();
		} else {
			return null;
		}
	}

	public void modify(AnswerEntity answer, String content) {
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}

	public void delete(AnswerEntity answer) {
		this.answerRepository.delete(answer);
	}

	public void vote(AnswerEntity answer, MemberEntity siteUser) {
		answer.getVoter().add(siteUser);
		this.answerRepository.save(answer);
	}
}