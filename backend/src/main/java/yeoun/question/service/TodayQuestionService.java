package yeoun.question.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yeoun.comment.dto.request.SaveCommentRequest;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.question.domain.Question;
import yeoun.question.domain.QuestionHistory;
import yeoun.question.domain.repository.QuestionHistoryRepository;
import yeoun.question.domain.repository.QuestionRepository;
import yeoun.question.dto.request.AddTodayQuestionCommentRequest;
import yeoun.question.dto.response.TodayQuestionResponse;
import yeoun.user.domain.User;
import yeoun.user.service.UserService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodayQuestionService {

    private final EntityManager entityManager;
    private final UserService userService;
    private final QuestionRepository questionRepository;
    private final QuestionHistoryRepository questionHistoryRepository;

    @Transactional
<<<<<<< HEAD
    public QuestionHistoryEntity getTodayQuestionGuest(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "유저 정보가 잘못 되었습니다"));
        Optional<QuestionHistoryEntity> todayQuestion = questionHistoryRepository.findTodayQuestion(userId);
        // 오늘 조회했던 오늘의 질문이 있으면 그대로 반환
        if (todayQuestion.isPresent())
            return todayQuestion.get();

        Optional<QuestionEntity> randomPopularityQuestion = questionRepository.findRandomPopularityQuestionExcludingHistory(userId);
        if (randomPopularityQuestion.isPresent()) {
            return questionHistoryRepository.save(
                    QuestionHistoryEntity.builder()
                            .user(entityManager.getReference(UserEntity.class, userId))
                            .question(randomPopularityQuestion.get())
                            .build()
            );
=======
    public TodayQuestionResponse getTodayQuestionGuest(Long userId) {
        userService.validateUser(userId);

        // 1. 오늘 질문이 있는 경우
        Optional<Question> todayQuestionOpt = questionRepository.findTodayQuestion(userId);
        if (todayQuestionOpt.isPresent()) {
            return TodayQuestionResponse.of(todayQuestionOpt.get());
>>>>>>> d9428e8662699a05123a5a72f56aeffa81e9b6ca
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
<<<<<<< HEAD
    public QuestionHistoryEntity getTodayQuestionMember(Long userId) {
        Optional<QuestionHistoryEntity> todayQuestion = questionHistoryRepository.findTodayQuestion(userId);
        return todayQuestion.orElseGet(() -> findRandomFixedQuestionExcludingHistory(userId));
    }

    private QuestionHistoryEntity findRandomFixedQuestionExcludingHistory(Long userId) {
        QuestionEntity newTodayQuestion = questionRepository.findRandomFixedQuestionExcludingHistory(userId)
                // 오늘의 질문을 한개도 못 가져오는 경우는 is_fixed 질문이 한개도 없는 경우 밖에 없음
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "오늘의 질문을 가져오는데 실패했습니다."));

        return questionHistoryRepository.save(
                QuestionHistoryEntity.builder()
                        .user(entityManager.getReference(UserEntity.class, userId))
=======
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
>>>>>>> d9428e8662699a05123a5a72f56aeffa81e9b6ca
                        .question(newTodayQuestion)
                        .build()
        );
    }

    // 오늘자 questionHistory를 가져와서 해당 comment내용을 덮어쓰기로 저장함
    public void commentQuestion(SaveCommentRequest dto) {
        if(dto.getContent().length() > 500)
            throw new CustomException(ErrorCode.TOO_LONG_PARAMETER, "파라미터 값이 너무 깁니다");

        QuestionHistoryEntity questionHistory = questionHistoryRepository.findById(dto.getId())
            .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND, "해당 질문을 찾을수 없습니다"));

        if(questionHistory.getUser().getId() != dto.getUserId())
            throw new CustomException(ErrorCode.CONFLICT, "작성자가 아닙니다");

        questionHistoryRepository.save(QuestionHistoryEntity
                .builder()
                .id(questionHistory.getId())
                .user(entityManager.getReference(UserEntity.class, dto.getUserId()))
                .question(questionHistory.getQuestion())
                .comment(dto.getContent())
                .build()
        );
    }

    public List<QuestionHistoryEntity> getAllMyTodayQuestions(long userId) {
        return questionHistoryRepository.findAllByUserId(userId);
    }

    public QuestionHistoryEntity getMyTodayQuestion(long userId, Long questionHistoryId) {
        return questionHistoryRepository.findByIdWithQuestionAndUser(userId, questionHistoryId)
            .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND, "id값을 찾을 수 없습니다"));
    }

    @Transactional
    public void addTodayQuestionComment(Long userId, AddTodayQuestionCommentRequest request) {
        userService.validateUser(userId);

        QuestionHistory questionHistory = questionHistoryRepository.findTodayHistoryByQuestionIdAndUser(
                        userId, request.getQuestionId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid question ID"));

        if (questionHistory.getComment() != null) {
            throw new CustomException(ErrorCode.ALREADY_EXIST, "오늘은 이미 오늘의 질문에 답변했습니다.");
        }

        questionHistoryRepository.addCommentToTodayQuestion(request.getQuestionId(), request.getComment());
    }

}
