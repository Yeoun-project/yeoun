import { useState } from 'react';
import { useSearchParams } from 'react-router-dom';

import CATEGORY from '../../constant/category/Category';

import { getAnsweredQuestions } from '../../services/api/question/getQuestions';

import useGetInfiniteQuestion from '../../hooks/queries/useGetInfiniteQuestion';
import useQuestionGroupByYear from '../../hooks/useQuestionGroupByYear';

import BottomTabBar from '../../components/nav/BottomTabBar';

import FallBack from '../../components/ui/FallBack';
import SubPageHeader from '../../components/ui/SubPageHeader';

import Dropdown from '../../components/dropdown/Dropdown';

import QuestionListYearSection from '../../components/questionList/QuestionListYearSection';
import ListMoreButton from '../../components/questionList/ListMoreButton';
import QuestionList from '../../components/questionList/QuestionList';

const MyAnswersPage = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const categoryId = searchParams.get('q');

  const [dropdown, setDropdown] = useState(false);

  const { data, fetchNextPage, hasNextPage } = useGetInfiniteQuestion({
    queryKey: ['my', 'answers', categoryId],
    getQuestions: getAnsweredQuestions,
    categoryId: categoryId as string,
  });

  const { questions, questionsYear } = useQuestionGroupByYear(data ? data : [], 'latest');

  const handleSelectCategory = (categoryId: number) => {
    setSearchParams({ q: categoryId.toString() });
    setDropdown((prev) => !prev);
  };

  return (
    <div className="h-[100svh] overflow-hidden">
      <SubPageHeader pageTitle="내가 답변한 질문" backButtonPath="/my" />

      <main className="flex h-[calc(100%-140px)] flex-col">
        <Dropdown
          isOpen={dropdown}
          onClick={() => setDropdown((prev) => !prev)}
          all
          categories={CATEGORY}
          handleSelect={handleSelectCategory}
          selected={CATEGORY[Number(categoryId) - 1]}
          id={Number(categoryId) || 0}
          location="px-6"
        />

        {questionsYear.length === 0 && (
          <FallBack
            desc="아직 남긴 여운이 없어요"
            subDesc="누군가의 질문에 당신의 여운을 남겨주세요"
          />
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
      <BottomTabBar />
    </div>
  );
};

export default MyAnswersPage;
