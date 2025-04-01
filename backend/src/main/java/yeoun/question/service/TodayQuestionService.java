package yeoun.question.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.question.domain.QuestionEntity;
import yeoun.question.domain.QuestionHistoryEntity;
import yeoun.question.domain.repository.QuestionHistoryRepository;
import yeoun.question.domain.repository.QuestionRepository;
import yeoun.user.domain.UserEntity;
import yeoun.user.domain.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodayQuestionService {

    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final QuestionHistoryRepository questionHistoryRepository;

    @Transactional
    public QuestionEntity getTodayQuestionGuest(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "유저 정보가 잘못 되었습니다"));
        Optional<QuestionEntity> todayQuestion = questionRepository.findTodayQuestion(userId);
        // 오늘 조회했던 오늘의 질문이 있으면 그대로 반환
        if (todayQuestion.isPresent()) return todayQuestion.get();
        Optional<QuestionEntity> randomPopularityQuestion = questionRepository.findRandomPopularityQuestionExcludingHistory(userId);
        if (randomPopularityQuestion.isPresent()) {
            questionHistoryRepository.save(
                    QuestionHistoryEntity.builder()
                            .user(entityManager.getReference(UserEntity.class, userId))
                            .question(randomPopularityQuestion.get())
                            .build()
            );

            return randomPopularityQuestion.get();
        }
        // 댓글이 10개 이상인 인기 질문이 없으면 고정 질문 중 1개 반환
        return findRandomFixedQuestionExcludingHistory(userId);
    }

    @Transactional
    public QuestionEntity getTodayQuestionMember(Long userId) {
        Optional<QuestionEntity> todayQuestion = questionRepository.findTodayQuestion(userId);
        return todayQuestion.orElseGet(() -> findRandomFixedQuestionExcludingHistory(userId));
    }

    private QuestionEntity findRandomFixedQuestionExcludingHistory(Long userId) {
        QuestionEntity newTodayQuestion = questionRepository.findRandomFixedQuestionExcludingHistory(userId)
                // 오늘의 질문을 한개도 못 가져오는 경우는 is_fixed 질문이 한개도 없는 경우 밖에 없음
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "오늘의 질문을 가져오는데 실패했습니다."));

        questionHistoryRepository.save(
                QuestionHistoryEntity.builder()
                        .user(entityManager.getReference(UserEntity.class, userId))
                        .question(newTodayQuestion)
                        .build()
        );

        return newTodayQuestion;
    }

}
