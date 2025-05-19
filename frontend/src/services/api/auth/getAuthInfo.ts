import UserAuthInfo from '../../../type/auth/UserAuthInfo';
import Response from '../../../type/response';
import client from '../client';

const getAuthInfo = async (): Promise<UserAuthInfo> => {
  const response = await client.get<Response<UserAuthInfo>>('/api/user/oAuth');

  return response.data.data;
};

export default getAuthInfo;
