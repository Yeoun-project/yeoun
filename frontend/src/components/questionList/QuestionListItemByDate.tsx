import { Link } from 'react-router-dom';

import QuestionCategory from '../../type/questionCategory';

import Circle from '../circle/Circle';

interface QuestionListItemProps {
  content: string;
  commentCount: number;
  category: QuestionCategory;
  createTime: string;
  path: string;
}

const QuestionListItemByDate = ({
  content,
  commentCount,
  category,
  createTime,
  path,
}: QuestionListItemProps) => {
  return (
    <li>
      <Link to={path} className="flex w-full items-center justify-between gap-6 px-6 pt-3 pb-2">
        <div>
          <Circle size={48} category={category}>
            <span className="text-sm text-black">{`${new Date(createTime).getMonth() + 1}/${new Date(createTime).getDate()}`}</span>
          </Circle>
        </div>

        <div className="flex min-h-[66px] w-full items-center rounded-sm border border-white/50 bg-white/10 px-3">
          <p className="font-desc text-sm text-white">{content}</p>
        </div>
      </Link>
      <div className="font-desc gap-1 px-6 pb-3 text-right text-[12px]">
        <span className="text-[#AAAAAA]">이 질문에 대한 답변 갯수 </span>
        {`${commentCount > 99 ? '99+' : commentCount}`}
      </div>
    </li>
  );
};

export default QuestionListItemByDate;
