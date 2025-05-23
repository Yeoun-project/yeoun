import Circle from '../circle/Circle';

interface AlarmItemProps {
  id: number;
  content: string;
  createTime: string;
}

const getElapsedTimeText = (createTime: string) => {
  const now = new Date();
  const created = new Date(createTime);

  const diffMs = now.getTime() - created.getTime();
  const diffMinutes = Math.floor(diffMs / 60000); // 1분 = 60000ms
  const diffHours = Math.floor(diffMinutes / 60);
  const diffDays = Math.floor(diffHours / 24);

  if (diffMinutes <= 3) return '방금 전';
  if (diffMinutes <= 9) return '조금 전';
  if (diffMinutes <= 19) return '10분 전';
  if (diffMinutes <= 29) return '20분 전';
  if (diffMinutes <= 39) return '30분 전';
  if (diffMinutes <= 49) return '40분 전';
  if (diffMinutes <= 59) return '50분 전';
  if (diffHours <= 1) return '1시간 전';
  if (diffHours <= 2) return '2시간 전';
  if (diffHours <= 5) return `${diffHours}시간 전`;
  if (diffHours <= 23) return '오늘';
  if (diffHours <= 47) return '어제';
  if (diffDays <= 6) return '며칠 전';

  // 7일 이상은 "4월 3일" 형태로 출력
  return `${created.getMonth() + 1}월 ${created.getDate()}일`;
};

const AlarmItem = ({ id, content, createTime }: AlarmItemProps) => {
  const elapsedTimeText = getElapsedTimeText(createTime);

  return (
    <div
      onClick={() => {
        console.log(id);
      }}
      className="flex items-center border-b-1 border-[#AAAAAA] px-3 py-3"
    >
      <div className="px-2">
        <Circle size={30}></Circle>
      </div>
      <div className="flex w-full flex-col px-3">
        <p className="text-[14px]">{content}</p>
        <span className="text-right text-xs text-[#AAAAAA]">{elapsedTimeText}</span>
      </div>
    </div>
  );
};

export default AlarmItem;
