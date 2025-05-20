import client from '../client';
import Response from '../../../type/response';

interface AlarmType {
  isNotification: boolean;
}

export const getNotification = async () => {
  const response = await client.get<Promise<Response<AlarmType>>>('/api/user/notification');

  return (await response.data).data;
};

export const postNotification = async (isNotification: boolean) => {
  const response = await client.post('/api/user/notification', {
    isNotification: !isNotification,
  });

  return response;
};
