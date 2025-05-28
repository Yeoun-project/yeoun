package yeoun.survey.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yeoun.common.SuccessResponse;
import yeoun.survey.dto.SurveyRequest;
import yeoun.survey.service.SurveyService;

@RestController
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/public/survey")
    public ResponseEntity<SuccessResponse> appendSurvey(
            @RequestBody @Valid SurveyRequest surveyRequest,
            HttpServletRequest request
    ) {
        String userAgent = request.getHeader("User-Agent");
        surveyService.append(surveyRequest, userAgent);
        return ResponseEntity.ok(new SuccessResponse("설문 추가를 성공했습니다.",null));
    }

}
