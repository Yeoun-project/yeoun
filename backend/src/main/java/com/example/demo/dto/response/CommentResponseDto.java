package com.example.demo.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {

    @NotNull
    private Long id;

    private String content;

    private Date createTime;

    @Builder
    public CommentResponseDto(Long id, String content, Date createTime) {
        this.id = id;
        this.content = content;
        this.createTime = createTime;
    }
}
