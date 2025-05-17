import client from '../client';

import { TodayQuestion } from '../../../type/question';
import UserType from '../../../type/auth/UserType';
import Response from '../../../type/response';
import { TodayQuestionComment } from '../../../type/comment';

const getTodayQuestionUrl = (type: UserType) => {
  if (type === 'User') {
    return '/api/today-question';
  } else {
    return '/public/today-question';
  }
};

// 오늘의 질문 조회
export const getTodayQuestion = async (userType: UserType): Promise<TodayQuestion> => {
  const response = await client.get<Promise<Response<TodayQuestion>>>(
    getTodayQuestionUrl(userType)
  );

  return (await response.data).data;
};

// 오늘의 질문 답변 조회
export const getTodayQuestionComment = async (
  questionId: string
): Promise<TodayQuestionComment> => {
  const response = await client.get<Promise<Response<TodayQuestionComment>>>(
    `${getTodayQuestionUrl(null)}/${questionId}`
  );

  return (await response.data).data;
};

// 오늘의 질문 답변 추가
export const addTodayQuestionComment = async ({
  questionId,
  comment,
}: {
  questionId: number;
  comment: string;
}) => {
  const response = await client.post(`${getTodayQuestionUrl(null)}/comment`, {
    questionId,
    comment,
  });

  return response;
};

// 오늘의 질문 답변 수정
export const updateTodayQuestionComment = async ({
  comment,
  questionId,
}: {
  comment: string;
  questionId: number;
}) => {
  const response = await client.patch(`${getTodayQuestionUrl(null)}/comment`, {
    comment,
    questionId,
  });

  return response;
};
