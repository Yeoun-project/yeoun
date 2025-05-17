import client from '../client';

import CATEGORY from '../../../constant/category/Category';

import Response from '../../../type/response';
import { QuestionListReq } from '../../../type/questionListRequest';

import QuestionCategory from '../../../type/questionCategory';
import { QuestionList } from '../../../type/question';

const getQuestionListApiUrl = (url: string, { page = 1, category }: QuestionListReq) => {
  if (category) {
    return `${url}?page=${page}&category=${category}`;
  } else {
    return `${url}?page=${page}`;
  }
};

const getCategoryName = (categoryId: string | undefined) => {
  if (!categoryId) return undefined;

  const filteredCategory = CATEGORY.filter((category) => category.id === Number(categoryId))[0];

  return filteredCategory.category;
};

// 모든 질문 리스트
const getAllQuestions = async ({ page = 1, categoryId }: QuestionListReq) => {
  const response = await client.get<Response<QuestionList>>(
    getQuestionListApiUrl('/api/question/all', {
      page,
      category: getCategoryName(categoryId) as QuestionCategory,
    })
  );

  return response.data.data;
};

// 내가 [작성한] 질문 리스트
const getMyQuestions = async ({ page = 1, categoryId }: QuestionListReq): Promise<QuestionList> => {
  const response = await client.get<Response<QuestionList>>(
    getQuestionListApiUrl('/api/question/my', {
      page,
      category: getCategoryName(categoryId) as QuestionCategory,
    })
  );

  return response.data.data;
};

// 내가 [답변한] 질문 리스트
const getAnsweredQuestions = async ({
  page = 1,
  categoryId,
}: QuestionListReq): Promise<QuestionList> => {
  const response = await client.get<Response<QuestionList>>(
    getQuestionListApiUrl('/api/question/commented-by-me', {
      page,
      category: getCategoryName(categoryId) as QuestionCategory,
    })
  );

  return response.data.data;
};

// [오늘의 질문] 답변 리스트
const getTodayAnswersQuestions = async ({ page = 1 }: QuestionListReq): Promise<QuestionList> => {
  const response = await client.get<Response<QuestionList>>(
    getQuestionListApiUrl('/public/today-question/commented-by-me', {
      page,
    })
  );

  return response.data.data;
};
export { getAllQuestions, getMyQuestions, getAnsweredQuestions, getTodayAnswersQuestions };
