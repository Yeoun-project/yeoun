package yeoun.question.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import yeoun.question.domain.Category;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponse {

    private final Long id;
    private final String name;
    private final Long totalQuestions;

    public static CategoryResponse of(
            Category category,
            Long totalQuestions
    ) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .totalQuestions(totalQuestions)
                .build();
    }

}
