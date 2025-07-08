package com.back.sbb.answer.service;


import com.back.sbb.answer.entity.Answer;
import com.back.sbb.answer.repository.AnswerRepository;
import com.back.sbb.qusetion.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(answer.getContent());
        answer.setQuestion(answer.getQuestion());
        answer.setCreateDate(LocalDateTime.now());

        return answerRepository.save(answer);
    }



}
