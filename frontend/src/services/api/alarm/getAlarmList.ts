import client from '../client';

import Response from '../../../type/response';

interface AlarmType {
  id: number;
  content: string;
  createdTime: string;
}

export const postTestAlarm = async () => {
  await client.post('/api/notification/test');
};

export const getAlarmList = async (): Promise<AlarmType> => {
  const response = await client.get<Promise<Response<AlarmType>>>('/api/notification?page=0');

  return (await response.data).data;
};
