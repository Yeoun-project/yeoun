import QuestionCategory from './questionCategory';

export interface Question {
  id: number;
  content: string;
  heart: number;
  commentCount: number;
  categoryName: QuestionCategory;
  createTime: string;
}

export interface TodayQuestion {
  id: number;
  content: string;
}
