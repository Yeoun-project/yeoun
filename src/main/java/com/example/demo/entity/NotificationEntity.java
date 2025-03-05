package com.example.demo.entity;

import com.example.demo.type.NotificationType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity(name = "notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isRead;

    @Column(nullable = false)
    private NotificationType notificationType;

    @ManyToOne
    private UserEntity receiver;

    @CreatedDate
    private Date sentDateTime;

}
