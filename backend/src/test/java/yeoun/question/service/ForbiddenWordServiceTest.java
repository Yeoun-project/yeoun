package yeoun.question.service;

import yeoun.question.domain.ForbiddenWord;
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
        String content = "당신은 시발인 경험이 있나요?";
        ForbiddenWord word1 = new ForbiddenWord(1L, "시발");
        ForbiddenWord word2 = new ForbiddenWord(2L, "좆");
        List<ForbiddenWord> allWords = List.of(word1, word2);
        given(forbiddenWordService.getAllForbiddenWords()).willReturn(allWords);

        // when & then
        assertThatThrownBy(() -> forbiddenWordService.validateForbiddenWord(content))
                .isInstanceOf(CustomException.class)
                .satisfies(e -> {
                    CustomException ex = (CustomException) e;
                    assertThat(ex.getMessage()).isEqualTo("질문 내용에 금지어가 포함되어 있습니다");
                    assertThat(ex.getData()).isEqualTo(List.of("시발"));
                });
    }

    @DisplayName("금지어가 포함되지 않은 경우 예외가 발생하지 않는다.")
    @Test
    void validateForbiddenWord_withoutForbiddenWord_shouldNotThrowException() {
        // given
        List<ForbiddenWord> forbiddenWords = List.of(
                new ForbiddenWord(1L, "시발"),
                new ForbiddenWord(2L, "좆")
        );
        given(forbiddenWordRepository.findAll()).willReturn(forbiddenWords);

        String content = "당신은 다시 태어나고 싶은가요?";

        // when & then
        forbiddenWordService.validateForbiddenWord(content);
    }

    @DisplayName("금지어 사이에 4글자 이상인 경우 예외가 발생하지 않는다.")
    @Test
    void validateForbiddenWord_3orMoreLettersBetweenForbiddenWord_shouldNotThrowException() {
        // given
        List<ForbiddenWord> forbiddenWords = List.of(
                new ForbiddenWord(1L, "시발"),
                new ForbiddenWord(2L, "좆")
        );
        given(forbiddenWordRepository.findAll()).willReturn(forbiddenWords);

        String content = "시&1@#발 짜증나네";

        // when & then
        forbiddenWordService.validateForbiddenWord(content);
    }

    @DisplayName("한글자 금지어가 단독으로 사용된 경우 CustomException을 던진다.")
    @Test
    void validateForbiddenWord_OneLettersForbiddenWord_shouldThrowException() {
        // given
        String content = "엿 먹어";
        ForbiddenWord word1 = new ForbiddenWord(1L, "엿");
        List<ForbiddenWord> allWords = List.of(word1);
        given(forbiddenWordService.getAllForbiddenWords()).willReturn(allWords);

        // when & then
        assertThatThrownBy(() -> forbiddenWordService.validateForbiddenWord(content))
                .isInstanceOf(CustomException.class)
                .satisfies(e -> {
                    CustomException ex = (CustomException) e;
                    assertThat(ex.getMessage()).isEqualTo("질문 내용에 금지어가 포함되어 있습니다");
                    assertThat(ex.getData()).isEqualTo(List.of("엿"));
                });
    }

}
