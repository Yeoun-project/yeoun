import QuestionListYearSection from './QuestionListYearSection';
import QuestionListByDate from './QuestionListByDate';
import { Question } from '../../type/question';

interface RenderedQuestionsProps {
  questionsYear: string[];
  questionsDate: {
    [key: string]: {
      date: string;
      items: Question[];
    }[];
  };
}

const RenderedQuestions = ({ questionsYear, questionsDate }: RenderedQuestionsProps) => {
  return (
    <>
      {questionsYear.map((year, yearIndex) => {
        const isLastYear = yearIndex === questionsYear.length - 1;
        const dateList = questionsDate[year];

        return (
          <QuestionListYearSection key={year} year={year}>
            {dateList.map((date, dateIndex) => {
              const isLastDate = dateIndex === dateList.length - 1;
              const isLast = isLastYear && isLastDate;

              return <QuestionListByDate key={date.date} questions={date.items} isLast={isLast} />;
            })}
          </QuestionListYearSection>
        );
      })}
    </>
  );
};

export default RenderedQuestions;
