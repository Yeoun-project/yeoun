package yeoun.question.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yeoun.auth.service.JwtService;
import yeoun.common.SuccessResponse;
import yeoun.question.dto.response.TodayQuestionResponse;
import yeoun.question.service.TodayQuestionService;

@RestController
@RequiredArgsConstructor
public class TodayQuestionController {

    private final TodayQuestionService todayQuestionService;

    @GetMapping("/public/question/today")
    public ResponseEntity<?> getTodayQuestionGuest() {
        Long userId = JwtService.getUserIdFromAuthentication();
        TodayQuestionResponse todayQuestionResponse = todayQuestionService.getTodayQuestionGuest(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("Get today's question for guest success", todayQuestionResponse));
    }

    @GetMapping("/api/question/today")
    public ResponseEntity<?> getTodayQuestionMember() {
        Long userId = JwtService.getUserIdFromAuthentication();
        TodayQuestionResponse todayQuestionResponse = todayQuestionService.getTodayQuestionMember(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("Get today's question for member success", todayQuestionResponse));
    }

}
