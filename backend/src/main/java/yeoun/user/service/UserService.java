package yeoun.user.service;

import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.auth.service.JwtService;
import yeoun.user.domain.User;
import yeoun.user.domain.repository.UserRepository;
import yeoun.user.domain.Role;
import yeoun.user.dto.request.IsNotificationRequest;
import yeoun.user.dto.request.UserRegisterInfoVo;
import java.util.Optional;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User registerByUserInfo(UserRegisterInfoVo vo) {
        Long userId = null;
        Optional<Long> tokenId = JwtService.getAnonymousTokenAuthentication();

        if(tokenId.isPresent()) {
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
            () -> {throw new CustomException(ErrorCode.NOT_FOUND);}
        );
    }

    @Transactional
    public void setIsNotification(IsNotificationRequest dto, long userId) {
        userRepository.setIsNotification(dto.getIsNotification(), userId);
    }

    public void validateUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "유저 정보가 잘못 되었습니다"));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}