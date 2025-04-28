import QuestionCategory from './questionCategory';
export interface TodayQuestion {
  id: number;
  content: string;
}

export interface MyTodayQuestionListItem extends TodayQuestion {
  created_at: string;
}

export interface QustionDetail extends Question {
  isAuthor: boolean;
}
export interface Question {
  id: number;
  content: string;
  commentCount: number;
  categoryName: QuestionCategory;
  createTime: string;
}

export interface QuestionList {
  question: Question[];
  hasNext: boolean;
}
