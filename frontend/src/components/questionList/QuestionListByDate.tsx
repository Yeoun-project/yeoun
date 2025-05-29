import { Question } from '../../type/question';
import QuestionListItemByDate from './QuestionListItemByDate';

interface QuestionsProps {
  questions: Question[];
}

const QuestionListByDate = ({ questions }: QuestionsProps) => {
  return (
    <ul className="my-2 flex w-full flex-col border-b border-[#AAAAAA]">
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
