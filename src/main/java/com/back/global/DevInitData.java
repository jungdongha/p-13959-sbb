// src/main/java/com/back/global/DevInitData.java
package com.back.global; // 패키지는 프로젝트 구조에 맞게 조정하세요.

import com.back.sbb.qusetion.entity.Question;
import com.back.sbb.qusetion.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Profile("dev") // 이 클래스는 "dev" 프로파일이 활성화될 때만 실행됩니다.
@Configuration // 스프링 설정 클래스임을 나타냅니다.
@RequiredArgsConstructor // Lombok을 사용하여 final 필드를 인자로 받는 생성자를 자동 생성합니다.
public class DevInitData {

    private final QuestionRepository questionRepository; // Question 엔티티의 Repository를 주입받습니다.

    // 애플리케이션 시작 시 자동으로 실행될 Bean을 정의합니다.
    @Bean
    ApplicationRunner devInitDataApplicationRunner() {
        return args -> {
            // 초기 데이터 생성 로직을 담당하는 work1() 메서드를 호출합니다.
            // self 주입 방식 대신 직접 메서드를 호출하도록 변경했습니다.
            work1();
        };
    }

    // 트랜잭션 내에서 데이터베이스 작업을 수행하도록 설정합니다.
    @Transactional
    void work1() {
        // 이미 데이터가 존재하면(Question 테이블에 하나라도 데이터가 있다면) 추가 생성을 건너뜁니다.
        // 이는 애플리케이션을 여러 번 재실행해도 데이터가 중복으로 쌓이는 것을 방지합니다.
        if (questionRepository.count() > 0) {
            System.out.println("[DevInitData] 이미 데이터가 존재하여 초기 데이터 생성을 건너뜁니다.");
            return;
        }

        System.out.println("[DevInitData] 'dev' 프로파일에서 초기 데이터 생성 시작...");

        // 첫 번째 질문 엔티티를 생성하고 데이터를 설정합니다.
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now()); // 현재 시간을 생성일로 설정합니다.
        questionRepository.save(q1); // QuestionRepository를 통해 데이터베이스에 저장합니다.

        // 두 번째 질문 엔티티를 생성하고 데이터를 설정합니다.
        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2); // 데이터베이스에 저장합니다.

        System.out.println("[DevInitData] 'dev' 프로파일 초기 데이터 생성 완료!");
    }
}