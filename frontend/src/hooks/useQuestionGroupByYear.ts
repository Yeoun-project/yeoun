import { useMemo } from 'react';

import { questionGroupByYear, sortedYearGroup } from '../utils/questionGroup';

import { Question } from '../type/question';

const useQuestionGroupByYear = (
  questions: Question[] | [],
  sortOrder: 'latest' | 'old',
  selectedCategory?: string
) => {
  // 선택한 카테고리만 필터링
  const selectedQuestions = useMemo(() => {
    if (!selectedCategory || selectedCategory === '0') return questions;
    return questions.filter((q) => q.categoryName === selectedCategory);
  }, [questions, selectedCategory]);

  // Questions 데이터를 년도 별로 그룹화
  const QuestionYearGroup = useMemo(
    () => questionGroupByYear(selectedQuestions),
    [selectedQuestions]
  );

  // 년도 정렬
  const sortedYearData = useMemo(() => {
    return sortedYearGroup(QuestionYearGroup, sortOrder);
  }, [QuestionYearGroup, sortOrder]);

  return {
    questions: QuestionYearGroup,
    questionsYear: sortedYearData,
  };
};

export default useQuestionGroupByYear;
