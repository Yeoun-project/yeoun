import client from '../client';

const addQuestionAnswer = async ({
  questionId,
  comment,
}: {
  questionId: number;
  comment: string;
}) => {
  const response = await client.post(`/api/comment/${questionId}`, {
    content: comment,
  });

  return response;
};

export default addQuestionAnswer;
