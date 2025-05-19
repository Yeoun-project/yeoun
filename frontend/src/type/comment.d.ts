export interface TodayQuestionComment {
  id: number;
  content: string;
  createTime: string;
  comment: {
    id: number;
    content: string;
    createTime: string;
    updateTime: string;
  };
}

export interface Comment {
  id: number;
  content: string;
  likeCount: number;
  isLike: boolean;
  createTime: string;
}

export interface CommentList {
  myComment: Comment;
  comments: Comment[];
  hasNext: boolean;
}
