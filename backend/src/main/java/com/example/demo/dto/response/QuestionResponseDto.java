package com.example.demo.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDto {

    private Long id;

    private String content;

    private int heart;

    private int commentCount;

    private String categoryName;

    private Date createTime;
}
