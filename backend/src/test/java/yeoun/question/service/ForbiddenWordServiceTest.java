package yeoun.question.service;

import yeoun.question.domain.ForbiddenWordEntity;
import yeoun.exception.CustomException;
import yeoun.question.domain.repository.ForbiddenWordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ForbiddenWordServiceTest {

    @Mock
    private ForbiddenWordRepository forbiddenWordRepository;

    @InjectMocks
    private ForbiddenWordService forbiddenWordService;

    @DisplayName("금지어가 포함된 경우 CustomException을 던진다.")
    @Test
    void validateForbiddenWord() {
        // given
        List<ForbiddenWordEntity> forbiddenWords = List.of(
                new ForbiddenWordEntity(1L, "forbiddenWord1"),
                new ForbiddenWordEntity(2L, "forbiddenWord2")
        );
        given(forbiddenWordRepository.findAll()).willReturn(forbiddenWords);

        String content = "This content contains forbiddenWord1";

        // when & then
        assertThatThrownBy(() -> forbiddenWordService.validateForbiddenWord(content))
                .isInstanceOf(CustomException.class)
                .hasMessage("질문 내용에 금지어가 포함되어 있습니다");
    }

    @DisplayName("금지어가 포함되지 않은 경우 예외가 발생하지 않는다.")
    @Test
    void validateForbiddenWord_withoutForbiddenWord_shouldNotThrowException() {
        // given
        List<ForbiddenWordEntity> forbiddenWords = List.of(
                new ForbiddenWordEntity(1L, "forbiddenWord1"),
                new ForbiddenWordEntity(2L, "forbiddenWord2")
        );
        given(forbiddenWordRepository.findAll()).willReturn(forbiddenWords);

        String content = "This content is clean.";

        // when & then
        forbiddenWordService.validateForbiddenWord(content);
    }

}
