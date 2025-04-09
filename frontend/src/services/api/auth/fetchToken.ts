import client from '../client';

const fetchToken = async (identifier: string, code: string) => {
  const response = await client.get(`/public/auth/login/${identifier}?code=${code}`);

  return response;
};

export default fetchToken;
