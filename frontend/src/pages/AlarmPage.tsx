import AlarmItem from '../components/alarmList/AlarmItem';

import FallBack from '../components/ui/FallBack';
import SubPageHeader from '../components/ui/SubPageHeader';

const list = [
  {
    questionId: 1,
    content: '[today question 1]에 새로운 답변이 달렸어요!',
    createTime: '2025-04-28T14:45:35.998',
  },
  {
    questionId: 1,
    content: '[today question 1]에 당신의 답변이 [남이권희]에게 여운을 남겼어요! ❤️',
    createTime: '2025-04-28T16:59:47.136',
  },
  {
    questionId: 2,
    content: '[today question 2]에 당신의 답변이 2명 에게 여운을 남겼어요! ❤️',
    createTime: '2025-04-28T17:00:01.725',
  },
];

const AlarmPage = () => {
  return (
    <>
      <main className="flex h-[100svh] flex-col">
        <SubPageHeader pageTitle={'알림'} />
        <div className="flex h-[calc(100%-140px)] flex-col">
          {list.length === 0 && <FallBack desc="" subDesc="오늘은 조용한 하루였어요" />}
          <ul className="font-desc w-full">
            {list.map((item) => (
              <AlarmItem id={item.questionId} content={item.content} createTime={item.createTime} />
            ))}
          </ul>
        </div>
      </main>
    </>
  );
};

export default AlarmPage;
