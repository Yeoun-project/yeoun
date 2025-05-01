import { useMemo } from 'react';

import { questionGroupByYear, sortedYearGroup } from '../utils/questionGroup';

import { Question } from '../type/question';

const useQuestionGroupByYear = (questions: Question[] | [], sortOrder: 'latest' | 'old') => {
  // Questions 데이터를 년도 별로 그룹화
  const QuestionYearGroup = useMemo(() => questionGroupByYear(questions), [questions]);

  //   년도 정렬
  const sortedYearData = useMemo(() => {
    return sortedYearGroup(QuestionYearGroup, sortOrder);
  }, [QuestionYearGroup, sortOrder]);

  return {
    questions: QuestionYearGroup,
    questionsYear: sortedYearData,
  };
};

export default useQuestionGroupByYear;
