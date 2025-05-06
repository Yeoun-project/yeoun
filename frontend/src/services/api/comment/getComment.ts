import client from '../client';

import Response from '../../../type/response';
import { CommentList } from '../../../type/comment';

type sortOrder = 'old' | 'latest' | 'like';

// 답변 정렬 api
const getCommentsUrl = (url: string, { id, sort = 'old' }: { id: number; sort: sortOrder }) => {
  if (sort === 'old') return `${url}/${id}?sort=createTime,DESC`;
  if (sort === 'latest') return `${url}/${id}?sort=createTime,ASC`;
  if (sort === 'like') return `${url}/${id}?sort=createTime,LIKE`;

  return `${url}/${id}?sort=createTime,DESC`;
};

// 해당 질문의 모든 댓글 리스트 불러오기
const getAllComments = async (id: number, sort: sortOrder) => {
  const response = await client.get<Response<CommentList>>(
    getCommentsUrl('/api/comment/all', { id, sort })
  );

  return response.data.data;
};

export { getAllComments };
