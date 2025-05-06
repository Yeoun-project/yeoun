import client from '../client';

import Response from '../../../type/response';
import { QustionDetail } from '../../../type/question';

export const getQuestionDetail = async (questionId: number) => {
  const response = await client.get<Response<QustionDetail>>(`/api/question/${questionId}`);

  return (response.data).data;
};
