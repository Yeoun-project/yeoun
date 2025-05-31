import { useRef, useEffect } from 'react';
import { useAlarmStore } from '../store/useAlarmStore';
import useAuthStore from '../store/useAuthStore';
import { queryClient } from '../utils/queryClient';

export default function AlarmManager() {
  const eventSource = useRef<EventSource | null>(null);
  const setHasAlarm = useAlarmStore((state) => state.setHasAlarm);

  const { userType } = useAuthStore();
  const { fetchNotificationState } = useAlarmStore();

  const baseURL = import.meta.env.PROD
    ? import.meta.env.VITE_API_BASE_URL
    : 'https://localhost:5173';

  useEffect(() => {
    const fetchSSE = () => {
      eventSource.current = new EventSource(`${baseURL}/api/notification/connect`, {
        withCredentials: true,
      });

      eventSource.current.addEventListener('notification', (event) => {
        queryClient.invalidateQueries({ queryKey: ['alarmList'] });
        setHasAlarm(event.data !== '0');
      });

      eventSource.current.onmessage = (event) => {
        queryClient.invalidateQueries({ queryKey: ['alarmList'] });
        setHasAlarm(event.data !== '0');
      };

      eventSource.current.onopen = (event) => {
        console.log('SSE 연결됨: ', event);
      };

      eventSource.current.onerror = () => {
        eventSource.current?.close();
        eventSource.current = null;
        fetchSSE();
      };
    };

    fetchSSE();
    if (userType === 'User') fetchNotificationState();

    return () => {
      eventSource.current?.close();
      eventSource.current = null;
      console.log('SSE 연결 해제됨');
    };
  }, []);

  return null; // 렌더링할 UI가 없다면
}
