import client from '../client';

import { TodayQuestion } from '../../../type/question';
import UserType from '../../../type/auth/UserType';
import Response from '../../../type/response';

const getTodayQuestionUrl = (type: UserType) => {
  if (type === 'User') {
    return '/api/today-question';
  } else {
    return '/public/today-question';
  }
};

export const getTodayQuestion = async (userType: UserType): Promise<TodayQuestion> => {
  const response = await client.get<Promise<Response<TodayQuestion>>>(
    getTodayQuestionUrl(userType)
  );

  return (await response.data).data;
};

export const addTodayQuestionComment = async ({
  questionId,
  comment,
}: {
  questionId: number;
  comment: string;
}) => {
  const response = await client.post(`/public/today-question/comment`, {
    questionId,
    comment,
  });

  return response;
};
