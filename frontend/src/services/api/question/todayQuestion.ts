import { TodayQuestion } from '../../../type/question';
import Response from '../../../type/response';
import client from '../client';

const todayQuestionUrl = '/public/question/today';

export const getTodayQuestion = async (): Promise<TodayQuestion> => {
  const response = await client.get<Promise<Response<TodayQuestion>>>(todayQuestionUrl);

  return (await response.data).data;
};

export const addTodayQuestionComment = async (content: string) => {
  const response = await client.post(todayQuestionUrl, { content });

  return response;
};
