package yeoun.question.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.question.domain.Question;
import yeoun.question.domain.QuestionHistory;
import yeoun.question.domain.repository.QuestionHistoryRepository;
import yeoun.question.domain.repository.QuestionRepository;
import yeoun.question.domain.repository.TodayQuestionRepository;
import yeoun.question.dto.request.AddTodayQuestionCommentRequest;
import yeoun.question.dto.response.TodayQuestionDetailResponse;
import yeoun.question.dto.response.TodayQuestionListResponse;
import yeoun.question.dto.response.TodayQuestionResponse;
import yeoun.user.domain.User;
import yeoun.user.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodayQuestionService {

    private final EntityManager entityManager;
    private final UserService userService;
    private final QuestionRepository questionRepository;
    private final TodayQuestionRepository todayQuestionRepository;
    private final QuestionHistoryRepository questionHistoryRepository;

    @Transactional
    public TodayQuestionResponse getTodayQuestionGuest(Long userId) {
        userService.validateUser(userId);

        // 1. 오늘 질문 확인
        Optional<QuestionHistory> questionHistoryOpt = todayQuestionRepository.findQuestionHistoryWithQuestionByUserId(userId);
        if (questionHistoryOpt.isPresent()) {
            return buildTodayQuestionResponse(questionHistoryOpt.get());
        }

        // 2. 오늘 질문 없으면 인기 질문 중 랜덤
        Optional<Question> popularQuestion = questionRepository.findRandomPopularityQuestionExcludingHistory(userId);
        if (popularQuestion.isPresent()) {
            QuestionHistory questionHistory = saveTodayQuestionHistory(userId, popularQuestion.get());
            return buildTodayQuestionResponse(questionHistory);
        }

        // 3. 인기 질문도 없으면 고정 질문 중 랜덤
        QuestionHistory questionHistory = findRandomFixedQuestionExcludingHistory(userId);
        return buildTodayQuestionResponse(questionHistory);
    }

    private TodayQuestionResponse buildTodayQuestionResponse(QuestionHistory questionHistory) {
        return TodayQuestionResponse.of(questionHistory, questionHistory.getComment() != null);
    }

    @Transactional
    public TodayQuestionResponse getTodayQuestionMember(Long userId) {
        Optional<QuestionHistory> questionHistoryOpt = todayQuestionRepository.findQuestionHistoryWithQuestionByUserId(userId);
        QuestionHistory questionHistory = questionHistoryOpt.orElseGet(() -> findRandomFixedQuestionExcludingHistory(userId));
        return buildTodayQuestionResponse(questionHistory);
    }

    private QuestionHistory saveTodayQuestionHistory(Long userId, Question question) {
        return questionHistoryRepository.save(
                QuestionHistory.builder()
                        .user(entityManager.getReference(User.class, userId))
                        .question(question)
                        .build()
        );
    }

    private QuestionHistory findRandomFixedQuestionExcludingHistory(Long userId) {
        Question newTodayQuestion = todayQuestionRepository.findRandomFixedQuestionExcludingHistory(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "오늘의 질문을 가져오는데 실패했습니다."));

        return questionHistoryRepository.save(
                QuestionHistory.builder()
                        .user(entityManager.getReference(User.class, userId))
                        .question(newTodayQuestion)
                        .build()
        );
    }

    @Transactional
    public void addTodayQuestionComment(Long userId, AddTodayQuestionCommentRequest request) {
        userService.validateUser(userId);

        QuestionHistory questionHistory = questionHistoryRepository.findTodayHistoryByQuestionIdAndUser(
                        userId, request.getQuestionId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "질문을 찾을 수 없습니다."));

        if (questionHistory.getComment() != null) {
            throw new CustomException(ErrorCode.ALREADY_EXIST, "오늘은 이미 오늘의 질문에 답변했습니다.");
        }

        questionHistoryRepository.addCommentToTodayQuestion(userId, request.getQuestionId(), request.getComment());
    }

    @Transactional
    public void updateTodayQuestionComment(Long userId, AddTodayQuestionCommentRequest request) {
        userService.validateUser(userId);

        QuestionHistory questionHistory = questionHistoryRepository.findTodayHistoryByQuestionIdAndUser(
                        userId, request.getQuestionId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "질문을 찾을 수 없습니다."));
        if (questionHistory.getComment() == null) throw new CustomException(ErrorCode.CONFLICT, "수정할 댓글이 없습니다.");

        questionHistory.setComment(request.getComment());
    }

    @Transactional
    public void deleteTodayQuestion(Long userId, Long questionId) {
        userService.validateUser(userId);
        QuestionHistory questionHistory = questionHistoryRepository.findTodayHistoryByQuestionIdAndUser(
                        userId, questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "질문을 찾을 수 없습니다."));
        questionHistory.setComment(null);
    }

    @Transactional(readOnly = true)
    public TodayQuestionListResponse getAllCommentedMyTodayQuestions(final Long userId) {
        List<QuestionHistory> questionHistories = questionHistoryRepository.findAllCommentedWithQuestionByUserId(userId);
        List<TodayQuestionResponse> todayQuestionResponses = questionHistories.stream()
                .map(questionHistory -> TodayQuestionResponse.of(
                        questionHistory,
                        questionHistory.getComment() != null
                )).toList();
        return new TodayQuestionListResponse(todayQuestionResponses);
    }

    @Transactional(readOnly = true)
    public TodayQuestionDetailResponse getTodayQuestionDetail(final Long questionId, final Long userId) {
        QuestionHistory questionHistory = todayQuestionRepository.findByQuestionIdAndUserId(questionId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "오늘의 질문 ID가 잘못 되었습니다."));
        return TodayQuestionDetailResponse.of(questionHistory);
    }

}
