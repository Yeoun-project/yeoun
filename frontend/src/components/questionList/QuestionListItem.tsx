import { Link } from 'react-router-dom';

import QuestionCategory from '../../type/questionCategory';

import Circle from '../circle/Circle';

interface QuestionListItemProps {
  content: string;
  category: QuestionCategory;
  createTime: string;
  path: string;
}

const QuestionListItem = ({ content, category, createTime, path }: QuestionListItemProps) => {
  return (
    <li>
      <Link to={path} className="flex w-full items-center justify-between gap-6 px-6 py-3">
        <div>
          <Circle size={48} category={category}>
            <span className="text-sm text-black">{`${new Date(createTime).getMonth() + 1}/${new Date(createTime).getDate()}`}</span>
          </Circle>
        </div>

        <div className="block w-full rounded-sm border border-white/50 bg-white/10 p-3">
          <p className="font-desc text-sm text-white">{content}</p>
        </div>
      </Link>
    </li>
  );
};

export default QuestionListItem;
