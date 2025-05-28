package yeoun.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import yeoun.survey.dto.SurveyRequest;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double starRate;

    @Column(nullable = false)
    private String message;

    @Column
    private String platform;

    @CreatedDate
    private LocalDateTime createdTime;

    public static Survey of(SurveyRequest surveyRequest, String platform) {
        return new Survey(
                surveyRequest.getStarRate(),
                surveyRequest.getMessage(),
                platform,
                LocalDateTime.now()
        );
    }

    public Survey(double starRate, String message, String platform, LocalDateTime createdTime) {
        this.starRate = starRate;
        this.message = message;
        this.platform = platform;
        this.createdTime = createdTime;
    }
}
