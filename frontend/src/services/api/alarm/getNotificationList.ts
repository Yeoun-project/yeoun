import client from '../client';

import Response from '../../../type/response';
import { AlarmList, AlarmQuestion } from '../../../type/auth/notification';

const getAlarmListUrl = (url: string, { page = 0 }: { page: number }) => {
  return `${url}?page=${page}`;
};

export const postTestAlarm = async () => {
  await client.post('/api/notification/test', {
    questionId: 1,
    receiver: 1,
    type: 'NEW_COMMENT',
  });
};

export const getAlarmList = async ({ page = 0 }: { page?: number }): Promise<AlarmList> => {
  const response = await client.get<Promise<Response<AlarmList>>>(
    getAlarmListUrl('/api/notification', { page })
  );

  return (await response.data).data;
};

export const getAlarmDetail = async (questionId: number) => {
  const response = await client.get<Promise<Response<AlarmQuestion>>>(
    `/api/notification/${questionId}`
  );

  return (await response.data).data;
};
