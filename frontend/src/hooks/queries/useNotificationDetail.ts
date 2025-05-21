import { useEffect, useState } from 'react';
import { getAlarmDetail } from '../../services/api/alarm/getNotificationList';
import { Alarm, AlarmQuestion } from '../../type/auth/notification';

const useNotificationDetail = (Alarm: Alarm[]) => {
  const [alarmList, setAlarmList] = useState<AlarmQuestion[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      const data = await Promise.all(Alarm.map((alarm) => getAlarmDetail(alarm.questionId)));
      setAlarmList(data);
    };

    if (Alarm.length > 0) {
      fetchData();
    }
  }, [Alarm]);

  return alarmList;
};
export default useNotificationDetail;
