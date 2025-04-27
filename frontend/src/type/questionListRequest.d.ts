import QuestionCategory from './questionCategory';

interface QuestionListReq {
  page?: number;
  categoryId?: string | undefined;
  category?: QuestionCategory;
}
