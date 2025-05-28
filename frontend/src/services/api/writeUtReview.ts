import client from './client';

const writeUtReview = async ({ starRate, message }: { starRate: number; message: string }) => {
  const response = await client.post('/public/survey', { starRate, message });

  return response;
};

export default writeUtReview;
