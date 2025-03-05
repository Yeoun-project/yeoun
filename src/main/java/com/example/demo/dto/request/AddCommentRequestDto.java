package com.example.demo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCommentRequestDto {

    public String content;

    public Long userId;

    public Long questionId;

}
