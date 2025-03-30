package yeoun.question.service;

import yeoun.question.domain.ForbiddenWordEntity;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.question.domain.repository.ForbiddenWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForbiddenWordService {

    private final ForbiddenWordRepository forbiddenWordRepository;

    public void validateForbiddenWord(String content) {
        String forbiddenWordsPattern = getForbiddenWordsPattern();
        if (forbiddenWordsPattern.isEmpty()) return;
        if (Pattern.compile(forbiddenWordsPattern).matcher(content).find()) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "질문 내용에 금지어가 포함되어 있습니다");
        }
    }

    @Cacheable("forbiddenWords")
    public List<ForbiddenWordEntity> getAllForbiddenWords() {
        return forbiddenWordRepository.findAll();
    }

    private String getForbiddenWordsPattern() {
        List<ForbiddenWordEntity> forbiddenWordList = getAllForbiddenWords();
        return forbiddenWordList.stream()
                .map(ForbiddenWordEntity::getWord)
                .map(Pattern::quote) // 특수문자 처리
                .collect(Collectors.joining("|"));
    }
}