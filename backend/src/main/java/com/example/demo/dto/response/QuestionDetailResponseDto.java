package com.example.demo.dto.response;

import lombok.Builder;
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

    @Builder
    public QuestionDetailResponseDto(Long id, String content, int heart, String categoryName,
        Date createTime, List<CommentResponseDto> comments) {
        this.id = id;
        this.content = content;
        this.heart = heart;
        this.categoryName = categoryName;
        this.createTime = createTime;
        this.comments = comments;
    }
}
