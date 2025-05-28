package yeoun.survey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeoun.survey.domain.Survey;
import yeoun.survey.domain.repository.SurveyRepository;
import yeoun.survey.dto.SurveyRequest;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public void append(SurveyRequest surveyRequest, String userAgent) {
        String platform = detectPlatform(userAgent);
        Survey survey = Survey.of(surveyRequest, platform);
        surveyRepository.save(survey);
    }

    private String detectPlatform(String userAgent) {
        if (userAgent.toLowerCase().contains("mobile")) {
            return "모바일";
        } else if (userAgent.toLowerCase().contains("windows") || userAgent.toLowerCase().contains("macintosh")) {
            return "PC";
        } else {
            return "기타";
        }
    }

}
