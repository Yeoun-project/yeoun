import QuestionCategory from '../questionCategory';

export interface AlarmQuestion {
  id: number;
  content: string;
  categoryName?: QuestionCategory;
  createTime: string;
}

export interface Alarm {
  questionId: number;
  content: string;
  createTime: string;
  category: QuestionCategory;
}

export interface AlarmList {
  details: Alarm[];
  hasNext: boolean;
}
