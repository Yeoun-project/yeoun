import { Link } from 'react-router-dom';
import { useAlarmStore } from '../../store/useAlarmStore';

const Alarm = () => {
  const { notification } = useAlarmStore();
  const hasAlarm = useAlarmStore((state) => state.hasAlarm);

  return (
    <>
      <div className="relative size-6">
        <Link
          aria-label="알림"
          to="/notification"
          className={`block size-6 ${
            notification
              ? hasAlarm
                ? 'bg-[url(/icons/notificationBell.svg)]'
                : 'bg-[url(/icons/notification.svg)]'
              : 'bg-[url(/icons/notification.svg)]'
          } bg-no-repeat`}
        />
      </div>
    </>
  );
};

export default Alarm;
