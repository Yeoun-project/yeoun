import client from '../client';

export const addQuestionComment = async (content: string, questionId: number) => {
  const response = await client.post(`/api/comment/${questionId}`, {
    content: content,
  });

  return response;
};
