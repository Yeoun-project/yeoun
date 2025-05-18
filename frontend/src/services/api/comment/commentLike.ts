import client from '../client';

export const changeCommentLike = async (commentId: number, isLike: boolean) => {
  const response = await client.post(`/api/comment/${commentId}/like`, { isLike: !isLike });

  return response;
};
