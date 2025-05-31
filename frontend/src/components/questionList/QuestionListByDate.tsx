import { Question } from '../../type/question';
import QuestionListItemByDate from './QuestionListItemByDate';

interface QuestionsProps {
  questions: Question[];
  isLast: boolean;
}

const QuestionListByDate = ({ questions, isLast }: QuestionsProps) => {
  return (
    <ul className={`my-2 flex w-full flex-col ${!isLast ? 'border-b border-[#AAAAAA]' : ''}`}>
      {questions.map((question) => (
        <QuestionListItemByDate
          key={question.id}
          commentCount={question.commentCount}
          content={question.content}
          category={question.categoryName}
          createTime={question.createTime}
          path={`/question/${question.id}`}
        />
      ))}
    </ul>
  );
};

export default QuestionListByDate;
