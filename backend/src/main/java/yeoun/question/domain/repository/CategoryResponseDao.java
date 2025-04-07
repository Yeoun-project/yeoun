package yeoun.question.domain.repository;

import yeoun.question.domain.CategoryEntity;

public interface CategoryResponseDao {
    CategoryEntity getCategory();
    Long getCount();
}

