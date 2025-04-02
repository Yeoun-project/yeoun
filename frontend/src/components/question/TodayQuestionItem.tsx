import { Link } from 'react-router-dom';

interface TodayQuestionItemProps {
  content: string;
  id: number;
}

const TodayQuestionItem = ({ content, id }: TodayQuestionItemProps) => {
  return (
    <Link
      to={`/today-question/${id}`}
      className="rounded-sm border border-white/50 bg-white/10 px-3 py-2"
    >
      <p className="font-desc text-sm text-white">{content}</p>
    </Link>
  );
};

export default TodayQuestionItem;
