import { Question } from '../../type/question';

import QuestionListItem from './QuestionListItem';

interface QuestionListProps {
  questions: Question[];
  path: 'today' | 'question'; // 질문 path 경로
}

const QuestionList = ({ questions, path }: QuestionListProps) => {
  return (
    <ul className="flex w-full flex-col">
      {questions.map((question) => (
        <QuestionListItem
          key={question.id}
          content={question.content}
          category={question.categoryName}
          createTime={question.createTime}
          path={`/${path}/${question.id}`}
        />
      ))}
    </ul>
  );
};

export default QuestionList;
