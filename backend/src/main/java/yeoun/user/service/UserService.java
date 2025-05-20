package yeoun.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.question.domain.repository.QuestionRepository;
import yeoun.user.domain.User;
import yeoun.auth.service.JwtService;
import yeoun.user.domain.repository.UserDeleteRepository;
import yeoun.user.domain.repository.UserRepository;
import yeoun.user.domain.Role;
import yeoun.user.dto.request.IsNotificationRequest;
import yeoun.user.dto.request.UserRegisterInfoVo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import yeoun.user.dto.request.UserWithdrawRequest;
import yeoun.withdrawHistory.domain.WithdrawHistoryService;
import yeoun.withdrawHistory.domain.WithdrawHistoryWriter;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Value("${user.default-question-count}")
    private int DEFAULT_QUESTION_COUNT;

    private final UserWithdrawer userWithdrawer;
    private final WithdrawHistoryService withdrawHistoryService;
    private final UserRepository userRepository;

    @Transactional
    public User registerByUserInfo(UserRegisterInfoVo vo) {
        Long userId = null;
        Optional<Long> tokenId = JwtService.getAnonymousTokenAuthentication();

        if (tokenId.isPresent()) {
            log.info(tokenId.get().toString());
            userId = tokenId.get();
        }

        String uuid = UUID.randomUUID().toString();
        User newUser = User.builder()
                .id(userId)
                .oAuthPlatform(vo.getOAuthPlatform())
                .oAuthId(vo.getOAuthId())
                .name(vo.getName())
                .email(vo.getEmail())
                .phone(vo.getPhone())
                .role(Role.USER.name())
                .uuid(uuid)
                .build();

        return userRepository.save(newUser);
    }

    @Transactional
    public User registerAnonymousUser() {
        return userRepository.save(
                User.builder()
                        .role(Role.ANONYMOUS.name())
                        .name("비회원")
                        .build()
        );
    }

    public User getUserInfo(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND)
        );
    }

    @Transactional
    public void setIsNotification(IsNotificationRequest dto, long userId) {
        userRepository.setIsNotification(dto.getIsNotification(), userId);
    }

    @Transactional
    public void withdraw(final UserWithdrawRequest userWithdrawRequest, final Boolean isHard, final Long userId) {
//        userWithdrawer.withdraw(isHard, userId);
        withdrawHistoryService.save(userWithdrawRequest);
    }

    public void validateUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "유저 정보가 잘못 되었습니다"));
    }


    // scheduler set user question count = 1, every day
    @Transactional
    @Scheduled(cron = "${scheduler.every-day}")
    public void deleteOldUserHistory() {
        userRepository.setAllUserQuestionCount(DEFAULT_QUESTION_COUNT);
    }

}
