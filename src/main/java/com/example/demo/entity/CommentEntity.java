package com.example.demo.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private QuestionEntity question;

    @CreatedDate
    private Date createdDateTime;

    @Column
    private Date deletedDateTime;

}
