package yeoun.user.service;

import lombok.extern.slf4j.Slf4j;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.user.domain.User;
import yeoun.auth.service.JwtService;
import yeoun.user.domain.repository.UserRepository;
import yeoun.user.domain.Role;
import yeoun.user.vo.UserRegisterInfoVo;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void validateUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "유저 정보가 잘못 되었습니다"));
    }

//    public void eraseAnonymous(long userId) {
//        userRepository.deleteById(userId);
//    }

}