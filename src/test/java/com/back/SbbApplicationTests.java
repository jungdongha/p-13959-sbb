package com.back;

import com.back.sbb.answer.repository.AnswerRepository;
import com.back.sbb.qusetion.entity.Question;
import com.back.sbb.qusetion.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class SbbApplicationTests {


    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("findAll")
    void t1(){
        List<Question> all = questionRepository.findAll();
        //실제결과, 예상값
        assertEquals(2, all.size());

        Question question = all.get(0);
        assertEquals("sbb가 무엇인가요?", question.getSubject());
        }

    @Test
    @DisplayName("findBySubject")
    void t2(){
        Question question = questionRepository.findBySubject("sbb가 무엇인가요?").get();
        assertThat(question.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findBySubjectAndContent")
    void t3(){
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.").get();
        assertThat(q.getId()).isEqualTo(1);

    }
//
    @Test
    @DisplayName("findBySubjectLike")
    void t4(){
        List<Question> questions = questionRepository.findBySubjectLike("sbb%");
        Question q = questions.get(0);
        assertThat(q.getId()).isEqualTo(1);

    }
//
//    @Test
//    @DisplayName("수정")
//    @Transactional
//
//
//    @Test
//    @DisplayName("삭제")
//    @Transactional
//
//    @Test
//    @DisplayName("답변 생성")
//    @Transactional
//
//    @Test
//    @DisplayName("답변 생성 v2")
//    @Transactional
//    @Rollback(value = false)
}
