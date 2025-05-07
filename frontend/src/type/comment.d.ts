export interface Comment {
    id: number;
    content: string;
    likeCount: number;
    isLike: boolean;
    createdTime: string
}

export interface CommentList {
    mycomment: Comment;
    comment: Comment[];
    hasNext: boolean;
}