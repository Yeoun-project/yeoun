import { Comment } from '../type/comment';

export const sortedCommentGroup = (comment: Comment[], sortOrder: 'old' | 'latest' | 'like') => {
  const sorted = [...comment]; // 원본 배열 변경 방지

  // 오래된 순
  if (sortOrder === 'old') {
    return sorted.sort(
      (a, b) => new Date(a.createTime).getTime() - new Date(b.createTime).getTime()
    );
  }

  // 최신순
  if (sortOrder === 'latest') {
    return sorted.sort(
      (a, b) => new Date(b.createTime).getTime() - new Date(a.createTime).getTime()
    );
  }

  // 여운순
  if (sortOrder === 'like') {
    return sorted.sort((a, b) => b.likeCount - a.likeCount);
  }

  return [];
};
