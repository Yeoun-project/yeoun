package yeoun.question.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.question.domain.Question;
import yeoun.question.domain.QuestionHistory;
import yeoun.question.domain.repository.QuestionHistoryRepository;
import yeoun.question.domain.repository.QuestionRepository;
import yeoun.question.dto.response.TodayQuestionResponse;
import yeoun.user.domain.User;
import yeoun.user.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodayQuestionService {

    private final EntityManager entityManager;
    private final UserService userService;
    private final QuestionRepository questionRepository;
    private final QuestionHistoryRepository questionHistoryRepository;

    @Transactional
    public TodayQuestionResponse getTodayQuestionGuest(Long userId) {
        userService.validateUser(userId);

        // 1. 오늘 질문이 있는 경우
        Optional<Question> todayQuestionOpt = questionRepository.findTodayQuestion(userId);
        if (todayQuestionOpt.isPresent()) {
            return TodayQuestionResponse.of(todayQuestionOpt.get());
        }

        // 2. 오늘 질문이 없으면 인기 질문 중 랜덤
        Optional<Question> popularQuestionOpt = questionRepository.findRandomPopularityQuestionExcludingHistory(userId);
        if (popularQuestionOpt.isPresent()) {
            saveTodayQuestionHistory(userId, popularQuestionOpt.get());
            return TodayQuestionResponse.of(popularQuestionOpt.get());
        }

        // 3. 인기 질문도 없으면 고정 질문 중 랜덤
        Question fixedQuestion = findRandomFixedQuestionExcludingHistory(userId);
        return TodayQuestionResponse.of(fixedQuestion);
    }

    private void saveTodayQuestionHistory(Long userId, Question question) {
        questionHistoryRepository.save(
                QuestionHistory.builder()
                        .user(entityManager.getReference(User.class, userId))
                        .question(question)
                        .build()
        );
    }

    @Transactional
    public TodayQuestionResponse getTodayQuestionMember(Long userId) {
        Optional<Question> todayQuestionOpt = questionRepository.findTodayQuestion(userId);
        Question todayQuestion = todayQuestionOpt.orElseGet(() -> findRandomFixedQuestionExcludingHistory(userId));
        return TodayQuestionResponse.of(todayQuestion);
    }

    private Question findRandomFixedQuestionExcludingHistory(Long userId) {
        Question newTodayQuestion = questionRepository.findRandomFixedQuestionExcludingHistory(userId)
                // 오늘의 질문을 한개도 못 가져오는 경우는 is_fixed 질문이 한개도 없는 경우 밖에 없음
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "오늘의 질문을 가져오는데 실패했습니다."));

        questionHistoryRepository.save(
                QuestionHistory.builder()
                        .user(entityManager.getReference(User.class, userId))
                        .question(newTodayQuestion)
                        .build()
        );

        return newTodayQuestion;
    }

}
