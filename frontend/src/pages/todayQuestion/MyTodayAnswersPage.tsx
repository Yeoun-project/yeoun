import { useState } from 'react';
import { useInfiniteQuery } from '@tanstack/react-query';

import { getTodayAnswersQuestions } from '../../services/api/question/getQuestions';

import useQuestionGroupByYear from '../../hooks/useQuestionGroupByYear';

import BottomTabBar from '../../components/nav/BottomTabBar';

import CheckBox from '../../components/common/CheckBox';

import FallBack from '../../components/ui/FallBack';
import SubPageHeader from '../../components/ui/SubPageHeader';

import QuestionListYearSection from '../../components/questionList/QuestionListYearSection';
import ListMoreButton from '../../components/questionList/ListMoreButton';
import QuestionList from '../../components/questionList/QuestionList';
import useAuthStore from '../../store/useAuthStore';

type sortOrder = 'latest' | 'old';

const SORTORDER_CHECKBOXS = [
  {
    label: '최신순',
    id: 'latest',
  },
  {
    label: '오래된순',
    id: 'old',
  },
];

const MyTodayAnswersPage = () => {
  const { userType } = useAuthStore();
  const [sortOrder, setSortOrder] = useState<sortOrder>('latest');

  const { data, fetchNextPage, hasNextPage } = useInfiniteQuery({
    queryKey: ['my', 'today-question', 'answers'],
    queryFn: async ({ pageParam }) =>
      await getTodayAnswersQuestions({
        page: pageParam as number,
      }),
    initialPageParam: 1,
    getNextPageParam: (lastPage, allPages) => (lastPage.hasNext ? allPages.length + 1 : undefined),
    select: (data) => {
      // 새로 불러온 데이터들이 있다면 기존 데이터들과 매핑 후 반환
      return data.pages.flatMap((page) => page.questions || []);
    },
  });

  const { questions, questionsYear } = useQuestionGroupByYear(data ? data : [], sortOrder);

  const handleSelectSortOrder = (sortOrder: sortOrder) => {
    setSortOrder(sortOrder);
  };
  return (
    <div className="h-[100svh] overflow-hidden">
      <SubPageHeader
        pageTitle="답변목록"
        backButtonPath={userType === 'Guest' ? '/today-question' : '/my'}
      />

      <main className="flex h-[calc(100%-140px)] flex-col">
        {questionsYear.length > 0 && (
          <div className="mb-3 flex items-center justify-end gap-2 px-6">
            {SORTORDER_CHECKBOXS.map((option) => (
              <CheckBox
                key={option.id}
                isChecked={sortOrder === option.id}
                id="sortOrder"
                name={option.id}
                label={option.label}
                value={option.id}
                onChange={(e) => handleSelectSortOrder(e.target.value as sortOrder)}
              />
            ))}
          </div>
        )}

        {questionsYear.length === 0 && (
          <FallBack desc="아직 남겨진 여운이 없어요" subDesc="오늘, 당신의 여운을 남겨볼까요?" />
        )}

        {questionsYear.length > 0 && (
          <div className="no-scrollbar overflow-scroll pb-6">
            {questionsYear.map((year) => (
              <QuestionListYearSection key={year} year={year}>
                <QuestionList questions={questions[year]} path="question" />
              </QuestionListYearSection>
            ))}

            {/* 더보기 버튼 */}
            {hasNextPage && <ListMoreButton fetchNextPage={fetchNextPage} />}
          </div>
        )}
      </main>
      {userType !== 'Guest' && <BottomTabBar />}
    </div>
  );
};

export default MyTodayAnswersPage;
