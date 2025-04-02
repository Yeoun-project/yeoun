import { Question } from '../../type/question';

import Circle from '../circle/Circle';
import TodayQuestionItem from './TodayQuestionItem';

interface QuestionListProps {
  questions: Question[];
}

const QuestionList = ({ questions }: QuestionListProps) => {
  return (
    <ul className="flex w-full flex-col gap-3">
      {questions.map((question) => (
        <li key={question.id} className="flex w-full items-center justify-between gap-6 px-6 py-3">
          <div>
            <Circle size={54} category={question.categoryName} animate>
              <span className="text-sm text-black">{`${new Date(question.createTime).getMonth() + 1}/${new Date(question.createTime).getDate()}`}</span>
            </Circle>
          </div>

          <TodayQuestionItem content={question.content} id={question.id} />
        </li>
      ))}
    </ul>
  );
};

export default QuestionList;
