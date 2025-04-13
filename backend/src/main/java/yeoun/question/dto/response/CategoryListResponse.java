package yeoun.question.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CategoryListResponse {

    final List<CategoryResponse> categoryResponses;

}
