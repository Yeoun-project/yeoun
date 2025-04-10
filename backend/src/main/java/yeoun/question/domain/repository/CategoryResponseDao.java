package yeoun.question.domain.repository;

import yeoun.question.domain.Category;

public interface CategoryResponseDao {
    Category getCategory();
    Long getCount();
}

