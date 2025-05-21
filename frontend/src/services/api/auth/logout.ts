import client from '../client';
import Response from '../../../type/response';

export const logout = async () => {
  const response = await client.get<Promise<Response<null>>>('/api/user/logout');

  return response;
};
