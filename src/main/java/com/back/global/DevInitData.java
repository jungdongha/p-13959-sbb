package com.back.global;

import com.back.sbb.qusetion.entity.Question;
import com.back.sbb.qusetion.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class DevInitData {

    private final QuestionRepository questionRepository;

    @Bean
    ApplicationRunner devInitDataApplicationRunner() {
        return args -> {
            // work1()과 work2()의 로직을 통합하여 한 번에 처리하는 것이 더 좋다.
            // 여기서는 일단 기존 구조를 유지하며 work1()만 수정해 본다.
            work1();
            work2(); // work2()도 따로 실행한다면 이 부분은 그대로 둔다.
        };
    }

    @Transactional
    void work1() {
        if (questionRepository.count() > 0) {
            System.out.println("[DevInitData] 이미 데이터가 존재하여 초기 데이터 생성을 건너킵니다.");
            return;
        }

        System.out.println("[DevInitData] 'dev' 프로파일에서 초기 데이터 생성 시작...");

        // 첫 번째 질문 엔티티를 생성합니다.
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        // q1을 먼저 저장하면, 이 시점의 answerList는 비어있게 된다.

        // q1에 답변을 추가하고, 이 변경사항이 DB에 반영되도록 다시 저장한다.
        // Option 1: addAnswer 호출 후 명시적으로 저장
        q1.addAnswer("sbb는 스프링 부트 프로젝트입니다. 네, 자동으로 생성됩니다.");
        q1.addAnswer("스프링부트 게시판입니다.");
        questionRepository.save(q1); // <-- addAnswer 호출 후 다시 저장 (q1의 answerList 변경사항 반영)

        // 두 번째 질문 엔티티를 생성합니다.
        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());

        // q2에 답변 추가 후 다시 저장
        q2.addAnswer("네, id는 자동으로 생성됩니다. BaseEntity를 상속받기 때문입니다.");
        q2.addAnswer("JPA의 @GeneratedValue 어노테이션 덕분입니다.");
        questionRepository.save(q2); // <-- addAnswer 호출 후 다시 저장 (q2의 answerList 변경사항 반영)

        System.out.println("[DevInitData] 'dev' 프로파일 초기 데이터 생성 완료!");
    }

    // work2()는 이전 답변에서 제시한 100개 데이터 생성 로직으로 채운다.
    // work1()과 work2()를 별도로 두는 경우, work2() 안에서도 questionRepository.save(q); 호출 시 cascade가 잘 동작할 것이다.
    @Transactional
    void work2() {
        // 이미 100개 이상의 질문이 있다면 건너뛰도록 조건 추가 (work1()의 count()와는 별개)
        if (questionRepository.count() > 20) { // 20개는 work1에서 넣었으니 20보다 크면 이미 추가되었다고 가정
            System.out.println("[DevInitData] work2() 이미 데이터가 존재하여 100개 데이터 생성을 건너킵니다.");
            return;
        }

        System.out.println("[DevInitData] work2() 100개 데이터 생성 시작...");
        for (int i = 1; i <= 100; i++) {
            Question q = new Question();
            q.setSubject("제목%d".formatted(i));
            q.setContent("내용%d".formatted(i));
            q.setCreateDate(LocalDateTime.now());

            // 각 질문에 답변도 추가 (옵션)
            if (i % 2 != 0) {
                q.addAnswer("답변%d-1입니다.".formatted(i));
            } else {
                q.addAnswer("답변%d-1입니다.".formatted(i));
                q.addAnswer("답변%d-2입니다.".formatted(i));
            }

            questionRepository.save(q); // 답변 추가 후 Question 저장
        }
        System.out.println("[DevInitData] work2() 100개 데이터 생성 완료!");
    }
}