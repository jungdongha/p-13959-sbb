package com.back.sbb.answer.service;


import com.back.global.DataNotFoundException;
import com.back.sbb.answer.entity.Answer;
import com.back.sbb.answer.repository.AnswerRepository;
import com.back.sbb.qusetion.entity.Question;
import com.back.sbb.user.entity.SiteUser;
import com.back.sbb.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    public Answer create(Question question, String content,SiteUser siteUser) {
        Answer answer = new Answer();
        answer.setContent(answer.getContent());
        answer.setQuestion(answer.getQuestion());
        answer.setCreateDate(LocalDateTime.now());
        answer.setAuthor(author);
        return answerRepository.save(answer);
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = userRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("SiteUser not found");
        }
    }



}
