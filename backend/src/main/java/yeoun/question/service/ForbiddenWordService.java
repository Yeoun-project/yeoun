package yeoun.question.service;

import yeoun.question.domain.ForbiddenWordEntity;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.question.domain.repository.ForbiddenWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeoun.util.FormattingUtil;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForbiddenWordService {

    private final ForbiddenWordRepository forbiddenWordRepository;

    public void validateForbiddenWord(String content) {
        List<String> matchedForbiddenWords = getMatchedForbiddenWords(content);
        if (!matchedForbiddenWords.isEmpty()) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "질문 내용에 금지어가 포함되어 있습니다", matchedForbiddenWords);
        }
    }

    public List<ForbiddenWordEntity> getAllForbiddenWords() {
        return forbiddenWordRepository.findAll();
    }

    public List<String> getMatchedForbiddenWords(String content) {
        String normalizedContent = FormattingUtil.cleanText(content);

        List<String> forbiddenWords = getAllForbiddenWords().stream()
                .map(f -> FormattingUtil.cleanText(f.getWord()))
                .toList();

        return forbiddenWords.stream()
                .filter(word -> {
                    String regex = wordToFlexibleRegex(word);
                    return Pattern.compile(regex).matcher(normalizedContent).find();
                })
                .collect(Collectors.toList());
    }

    private String wordToFlexibleRegex(String word) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            sb.append(Pattern.quote(String.valueOf(word.charAt(i))));
            if (i < word.length() - 1) {
                sb.append("[\\s\\d\\p{Punct}]*"); // 공백, 숫자, 특수문자
            }
        }
        return sb.toString();
    }
}