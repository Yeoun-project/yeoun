import { useNavigate } from 'react-router-dom';
import { AlarmQuestion } from '../../type/auth/notification';
import Circle from '../circle/Circle';
import { getAlarmDetail, getAlarmList } from '../../services/api/alarm/getNotificationList';
import { queryClient } from '../../utils/queryClient';

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

const AlarmItem = ({ id, content, createTime, categoryName, isLast }: AlarmQuestion) => {
  const nav = useNavigate();
  const elapsedTimeText = getElapsedTimeText(createTime);

  const highlightPersonCount = (content: string) => {
    const regex = /(\d+명)/;
    const match = content.match(regex);

    if (!match) return content; // 매칭 없으면 그대로 반환

    const [fullMatch] = match;
    const splitParts = content.split(regex); // 정규식 기준으로 분할

    return (
      <>
        {splitParts.map((part, idx) =>
          part === fullMatch ? (
            <span key={idx} className="text-[#FC90D1]">
              {part}
            </span>
          ) : (
            <span key={idx}>{part}</span>
          )
        )}
      </>
    );
  };

  return (
    <div
      onClick={async () => {
        try {
          await getAlarmDetail(id);
          await getAlarmList({});
          queryClient.invalidateQueries({ queryKey: ['alarmList'] });

          nav(`/question/${id}`);
        } catch (err) {
          console.log(err);
        }
      }}
      className={`flex items-center ${!isLast ? 'border-b-1 border-[#AAAAAA]' : ''} px-3 py-3`}
    >
      <div className="px-2">
        <Circle size={30} category={categoryName}></Circle>
      </div>
      <div className="flex w-full flex-col px-3">
        <p className="text-sm">{highlightPersonCount(content)}</p>
        <span className="text-right text-xs text-[#AAAAAA]">{elapsedTimeText}</span>
      </div>
    </div>
  );
};

export default AlarmItem;
