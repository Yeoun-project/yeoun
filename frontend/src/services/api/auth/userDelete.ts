import client from '../client';

interface DeleteProps {
  checked: boolean;
  reasonCategory: string;
  reason: string;
}

export const userDelete = async ({ checked, reasonCategory, reason }: DeleteProps) => {
  const response = await client.post<Promise<Response>>(`/api/user/delete?isHard=${!checked}`, {
    reasonCategory: reasonCategory,
    reasonDetail: reason,
  });

  return response;
};
