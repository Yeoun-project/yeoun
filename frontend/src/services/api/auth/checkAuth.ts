import client from '../client';

const checkAuth = async () => {
  const response = await client.post('/public/auth/me');
  return response;
};

export default checkAuth;
