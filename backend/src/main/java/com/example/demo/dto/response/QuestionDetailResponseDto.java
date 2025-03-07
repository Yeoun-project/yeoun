package com.example.demo.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDetailResponseDto {

    private Long id;

    private String content;

    private int heart;

    private String categoryName;

    private Date createTime;

    private List<CommentResponseDto> comments;

}
