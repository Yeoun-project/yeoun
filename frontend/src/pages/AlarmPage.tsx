import { useInView } from 'react-intersection-observer';
import AlarmItem from '../components/alarm/AlarmItem';

import FallBack from '../components/ui/FallBack';
import SubPageHeader from '../components/ui/SubPageHeader';
import useGetInfiniteNotification from '../hooks/queries/useGetInfiniteNotification';

import { getAlarmList } from '../services/api/alarm/getNotificationList';
import { useEffect } from 'react';

const AlarmPage = () => {
  const [ref, inView] = useInView();

  const { data, fetchNextPage, hasNextPage } = useGetInfiniteNotification({
    queryKey: ['alarmList'],
    getAlarm: getAlarmList,
  });

  const alarmList = data ? data : [];

  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage]);

  return (
    <>
      <main className="flex h-[100svh] flex-col">
        <SubPageHeader pageTitle={'알림'} />
        <div className="flex h-[calc(100%-140px)] flex-col">
          {alarmList.length === 0 && <FallBack desc="" subDesc="오늘은 조용한 하루였어요" />}
          <ul className="font-desc w-full">
            {alarmList.map((item, index) => (
              <AlarmItem
                id={item.questionId}
                content={item.content}
                createTime={item.createTime}
                categoryName={item.category}
                isLast={index === alarmList.length - 1}
              />
            ))}
            <div ref={ref} style={{ visibility: 'hidden' }}>
              로딩
            </div>
          </ul>
        </div>
      </main>
    </>
  );
};

export default AlarmPage;
