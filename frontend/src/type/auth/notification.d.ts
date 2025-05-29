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
}

export interface AlarmList {
  details: Alarm[];
  hasNext: boolean;
}
