import QuestionCategory from '../questionCategory';

export interface AlarmQuestion {
  id: number;
  content: string;
  commentCount: number;
  categoryName: QuestionCategory;
  createTime: string;
  isAuthor: boolean;
}

export interface Alarm {
  questionId: number;
  content: string;
  createTime: string;
}

export interface AlarmList {
  details: Alarm[];
  hasNext: boolean;
}
