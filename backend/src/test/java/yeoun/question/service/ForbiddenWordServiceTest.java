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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        String content = "당신은 좆같은 경험이 있나요?";
        ForbiddenWordEntity word1 = new ForbiddenWordEntity(1L, "시발");
        ForbiddenWordEntity word2 = new ForbiddenWordEntity(2L, "좆");
        List<ForbiddenWordEntity> allWords = List.of(word1, word2);
        given(forbiddenWordService.getAllForbiddenWords()).willReturn(allWords);

        // when & then
        assertThatThrownBy(() -> forbiddenWordService.validateForbiddenWord(content))
                .isInstanceOf(CustomException.class)
                .satisfies(e -> {
                    CustomException ex = (CustomException) e;
                    assertThat(ex.getMessage()).isEqualTo("질문 내용에 금지어가 포함되어 있습니다");
                    assertThat(ex.getData()).isEqualTo(List.of("좆"));
                });
    }

    @DisplayName("금지어가 포함되지 않은 경우 예외가 발생하지 않는다.")
    @Test
    void validateForbiddenWord_withoutForbiddenWord_shouldNotThrowException() {
        // given
        List<ForbiddenWordEntity> forbiddenWords = List.of(
                new ForbiddenWordEntity(1L, "시발"),
                new ForbiddenWordEntity(2L, "좆")
        );
        given(forbiddenWordRepository.findAll()).willReturn(forbiddenWords);

        String content = "당신은 다시 태어나고 싶은가요?";

        // when & then
        forbiddenWordService.validateForbiddenWord(content);
    }

}
