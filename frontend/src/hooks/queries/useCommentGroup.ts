import { useMemo } from 'react';

import { Comment } from '../../type/comment';
import { sortedCommentGroup } from '../../utils/commentGroup';

const useCommentGroup = (comment: Comment[] | [], sortOrder: 'old' | 'latest' | 'like') => {
  const sortedCommentData = useMemo(() => {
    return sortedCommentGroup(comment, sortOrder);
  }, [comment, sortOrder]);

  return { comments: sortedCommentData };
};

export default useCommentGroup;
