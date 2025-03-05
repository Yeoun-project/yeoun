package com.example.demo.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity(name = "question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int heart;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private CategoryEntity category;

    @CreatedDate
    private Date createdDateTime;

    @Column
    private Date deletedDateTime;

}
