import { useRef, useEffect } from 'react';
import { useAlarmStore } from '../store/useAlarmStore';
import { getAlarmList } from '../services/api/alarm/getNotificationList';
import { queryClient } from '../utils/queryClient';

export default function AlarmManager() {
  const eventSource = useRef<EventSource | null>(null);
  const setHasAlarm = useAlarmStore((state) => state.setHasAlarm);

  useEffect(() => {
    eventSource.current = new EventSource('https://api.yeoun.kr/api/notification/connect', {
      withCredentials: true,
    });

    eventSource.current.addEventListener('notification', (event) => {
      console.log('SSE 수신됨:', event.data);
      setHasAlarm(event.data !== '0');

      // 실시간 알림 오면 반영
      getAlarmList({});
      queryClient.invalidateQueries({ queryKey: ['alarmList'] });
    });

    eventSource.current.onopen = (event) => {
      console.log('SSE 연결됨: ', event);
    };

    return () => {
      eventSource.current?.close();
      eventSource.current = null;
      console.log('SSE 연결 해제됨');
    };
  }, []);

  return null; // 렌더링할 UI가 없다면
}
