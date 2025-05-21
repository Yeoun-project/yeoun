import { useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';

const Alarm = () => {
  const [alarmData, setAlarmData] = useState(false);
  const eventSource = useRef<null | EventSource>(null);

  useEffect(() => {
    const fetchSSE = () => {
      eventSource.current = new EventSource('https://api.yeoun.kr/api/notification/connect', {
        withCredentials: true,
      });

      eventSource.current.addEventListener('notification', (event) => {
        console.log('SSE 수신됨: ', event.data);
        if (event.data === '0') {
          setAlarmData(false);
        } else {
          setAlarmData(true);
        }
      });

      eventSource.current.onerror = async () => {
        eventSource.current?.close();
        setTimeout(fetchSSE, 1000);
      };

      eventSource.current.onopen = (event) => {
        console.log('연결', event);
      };
    };

    fetchSSE();
    return () => {
      eventSource.current?.close();
    };
  }, []);

  return (
    <>
      <div className="relative size-6">
        <Link
          aria-label="알림"
          to="/notification"
          className={`block size-6 ${
            alarmData
              ? 'bg-[url(/icons/notificationBell.svg)]'
              : 'bg-[url(/icons/notification.svg)]'
          } bg-no-repeat`}
        />
      </div>
    </>
  );
};

export default Alarm;
