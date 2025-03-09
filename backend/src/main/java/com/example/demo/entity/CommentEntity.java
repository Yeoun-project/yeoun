package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "questionId")
    private QuestionEntity question;

    @CreatedDate
    private final Date createdDateTime = new Date();

    @Column
    private Date deletedDateTime;

    @Builder
    public CommentEntity(Long id, String content, UserEntity user, QuestionEntity question) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.question = question;
    }
}
