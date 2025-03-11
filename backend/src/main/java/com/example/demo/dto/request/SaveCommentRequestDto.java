package com.example.demo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveCommentRequestDto {

    public Long id;

    public String content;

    public Long userId;

    public Long questionId;

}
