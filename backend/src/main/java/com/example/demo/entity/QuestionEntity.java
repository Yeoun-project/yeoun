package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int heart;

    @Column
    private final boolean isFixed = false;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private CategoryEntity category;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments;

    @CreatedDate
    private final Date createdDateTime = new Date();

    @Column
    private Date deletedDateTime;

    @Builder
    public QuestionEntity(String content, int heart, UserEntity user, CategoryEntity category) {
        this.content = content;
        this.heart = heart;
        this.user = user;
        this.category = category;
    }
}
