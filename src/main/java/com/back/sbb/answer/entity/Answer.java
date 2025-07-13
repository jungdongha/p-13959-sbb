package com.back.sbb.answer.entity;


import com.back.sbb.qusetion.entity.Question;
import com.back.sbb.user.entity.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Answer {
//    id,content,createDate,question

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @ManyToOne //필수.
    private Question question;

    @ManyToOne
    private SiteUser author;

}
