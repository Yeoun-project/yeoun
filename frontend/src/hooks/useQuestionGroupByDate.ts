import { useMemo } from 'react';
import { Question } from '../type/question';
import {
  questionGroupByYear,
  questionGroupByYearAndDate,
  sortedYearGroup,
} from '../utils/questionGroup';

const useQuestionGroupByDate = (questions: Question[], sortOrder: 'latest' | 'old') => {
  const QuestionDateGroup = useMemo(() => questionGroupByYearAndDate(questions), [questions]);
  const QuestionYearGroup = useMemo(() => questionGroupByYear(questions), [questions]);

  //   년도 정렬
  const sortedYearData = useMemo(() => {
    return sortedYearGroup(QuestionYearGroup, sortOrder);
  }, [QuestionYearGroup, sortOrder]);

  return {
    questionsDate: QuestionDateGroup,
    questionsYear: sortedYearData,
  };
};

export default useQuestionGroupByDate;
