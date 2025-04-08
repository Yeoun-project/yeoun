package yeoun.question.presentation;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yeoun.auth.infrastructure.SecurityUtil;
import yeoun.auth.service.JwtService;
import yeoun.common.SuccessResponse;
import yeoun.question.domain.QuestionEntity;
import yeoun.question.dto.response.TodayQuestionResponse;
import yeoun.question.service.TodayQuestionService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class TodayQuestionController {

    private final TodayQuestionService todayQuestionService;

    @GetMapping("/public/question/today")
    public ResponseEntity<?> getTodayQuestionGuest(HttpServletResponse response) throws IOException {
        if (SecurityUtil.isLoggedIn()) response.sendRedirect("/api/question/today");

        Long userId = JwtService.getUserIdFromAuthentication();

        QuestionEntity todayQuestion = todayQuestionService.getTodayQuestionGuest(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("Get today's question for guest success", TodayQuestionResponse.builder()
                        .id(todayQuestion.getId())
                        .content(todayQuestion.getContent())
                        .build())
        );
    }

    @GetMapping("/api/question/today")
    public ResponseEntity<?> getTodayQuestionMember() {
        Long userId = JwtService.getUserIdFromAuthentication();
        QuestionEntity todayQuestion = todayQuestionService.getTodayQuestionMember(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("Get today's question for member success", TodayQuestionResponse.builder()
                        .id(todayQuestion.getId())
                        .content(todayQuestion.getContent())
                        .build())
        );
    }

}
