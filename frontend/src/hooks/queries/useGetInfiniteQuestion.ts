import { useInfiniteQuery } from '@tanstack/react-query';
import { QuestionListReq } from '../../type/questionListRequest';
import { QuestionList } from '../../type/question';

interface useGetInfiniteQuestionProps<T> {
  queryKey: T[]; // 쿼리키
  getQuestions: ({ page, categoryId }: QuestionListReq) => Promise<QuestionList>; // fetch Questions Fn
  categoryId: string; // categoryId
}

// 질문 목록 Infinite Query Hook
const useGetInfiniteQuestion = <T>({
  queryKey,
  getQuestions,
  categoryId,
}: useGetInfiniteQuestionProps<T>) => {
  return useInfiniteQuery({
    queryKey: queryKey,
    queryFn: async ({ pageParam }) =>
      await getQuestions({
        page: pageParam as number,
        categoryId: categoryId === '0' ? undefined : (categoryId as string),
      }),
    initialPageParam: 0,
    getNextPageParam: (lastPage, allPages) => (lastPage.hasNext ? allPages.length : undefined),
    select: (data) => {
      // 새로 불러온 데이터들이 있다면 기존 데이터들과 매핑 후 반환
      return data.pages.flatMap((page) => page.questions || []);
    },
  });
};

export default useGetInfiniteQuestion;
