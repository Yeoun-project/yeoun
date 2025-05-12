package yeoun.question.service;

import yeoun.question.domain.ForbiddenWord;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.question.domain.repository.ForbiddenWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeoun.util.FormattingUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public List<ForbiddenWord> getAllForbiddenWords() {
        return forbiddenWordRepository.findAll();
    }

    public List<String> getMatchedForbiddenWords(String content) {
        List<ForbiddenWord> forbiddenWords = getAllForbiddenWords();

        List<String> matched = new ArrayList<>();

        for (ForbiddenWord forbiddenWord : forbiddenWords) {
            String word = FormattingUtil.cleanText(forbiddenWord.getWord());
            String regex = wordToFlexibleRegex(word);

            Matcher matcher = Pattern.compile(regex).matcher(content);
            while (matcher.find()) {
                matched.add(matcher.group());
            }
        }

        return matched;
    }

    private String wordToFlexibleRegex(String word) {
        if (word.length() == 1) {
            // 앞뒤가 한글이 아닌 문자일 때만 매칭 (엿 먹어 -> O, 엿장수 -> X)
            return "(?<![\\p{IsHangul}])" + Pattern.quote(word) + "(?![\\p{IsHangul}])";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            sb.append(Pattern.quote(String.valueOf(word.charAt(i))));
            if (i < word.length() - 1) {
                sb.append("[\\s\\d\\p{Punct}]{0,3}");
            }
        }
        return sb.toString();
    }
}