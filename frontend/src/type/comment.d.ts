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
  createdTime: string;
}

export interface CommentList {
  mycomment: Comment;
  comment: Comment[];
  hasNext: boolean;
}
