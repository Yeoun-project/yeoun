package yeoun.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import yeoun.question.domain.repository.QuestionRepository;
import yeoun.user.domain.repository.UserDeleteRepository;
import yeoun.user.domain.repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserWithdrawer {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final UserDeleteRepository userDeleteRepository;

    public void withdraw(final Boolean isHard, final Long userId) {
        if (isHard) {
            hardDeleteAll(userId);
        } else {
            softDeleteUser(userId);
        }
    }

    private void softDeleteUser(final Long userId) {
        userRepository.deleteById(userId);
    }

    private void hardDeleteAll(final Long userId) {
        List<Long> questionIdList = questionRepository.findAllIdsByUserId(userId);
        userDeleteRepository.deleteLike(userId);
        userDeleteRepository.deleteNotification(userId, questionIdList);
        userDeleteRepository.deleteUserHistory(userId);
        userDeleteRepository.deleteComment(userId, questionIdList);
        userDeleteRepository.deleteQuestionHistory(userId, questionIdList);
        userDeleteRepository.deleteQuestion(userId);
        userDeleteRepository.updateComment(userId);
        userDeleteRepository.hardDeleteUser(userId);
    }

}
