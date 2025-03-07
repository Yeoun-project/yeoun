package com.example.demo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddQuestionRequestDto {

    public String content;

    public Long userId;

    public Long categoryId;

}
