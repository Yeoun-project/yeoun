package com.example.demo.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;

    private String content;

    private Date createTime;

}
