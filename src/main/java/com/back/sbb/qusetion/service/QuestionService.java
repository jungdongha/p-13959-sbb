package com.back.sbb.qusetion.service;

import com.back.global.DataNotFoundException;
import com.back.sbb.qusetion.entity.Question;
import com.back.sbb.qusetion.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;


    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question findByQuestionId(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        }else
            throw new DataNotFoundException("question not found");
    }

    public void create(String subject, String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        questionRepository.save(q);
    }
}
