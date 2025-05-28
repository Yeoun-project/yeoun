import { create } from 'zustand';
import { getNotification } from '../services/api/alarm/getNotification';

interface AlarmState {
  notification: boolean;
  hasAlarm: boolean;
  setNotification: (value: boolean) => void;
  setHasAlarm: (value: boolean) => void;
  fetchNotificationState: () => Promise<void>;
}

export const useAlarmStore = create<AlarmState>((set) => {
  return {
    notification: false,
    hasAlarm: false,
    setNotification: (value) => set({ notification: value }),
    setHasAlarm: (value) => set({ hasAlarm: value }),
    fetchNotificationState: async () => {
      try {
        const response = await getNotification();
        set({ notification: response.isNotification });
      } catch (error) {
        console.error('알림 상태 동기화 실패:', error);
      }
    },
  };
});
