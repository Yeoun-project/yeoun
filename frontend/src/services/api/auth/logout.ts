import client from '../client';

export const logout = async () => {
  const response = await client.get<Promise<Response>>('/api/user/logout');

  return response;
};
