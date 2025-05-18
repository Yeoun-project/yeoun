import client from '../client';

export const userDelete = async (checked: boolean) => {
  const response = await client.delete<Promise<Response>>(`/api/user/delete?hard=${checked}`);

  return response;
};
