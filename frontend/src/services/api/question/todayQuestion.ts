import client from '../client';

import { TodayQuestion } from '../../../type/question';
import UserType from '../../../type/auth/UserType';
import Response from '../../../type/response';

const getTodayQuestionUrl = (type: UserType) => {
  if (type === 'User') {
    return '/api/question/today';
  } else {
    return '/public/question/today';
  }
};

export const getTodayQuestion = async (userType: UserType): Promise<TodayQuestion> => {
  const response = await client.get<Promise<Response<TodayQuestion>>>(
    getTodayQuestionUrl(userType)
  );

  return (await response.data).data;
};

export const addTodayQuestionComment = async (userType: UserType, content: string) => {
  const response = await client.post(getTodayQuestionUrl(userType), { content });

  return response;
};
