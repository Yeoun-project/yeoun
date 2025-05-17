import { useState } from 'react';
import { useSearchParams } from 'react-router-dom';

import CATEGORY from '../../constant/category/Category';

import { getMyQuestions } from '../../services/api/question/getQuestions';

import useGetInfiniteQuestion from '../../hooks/queries/useGetInfiniteQuestion';
import useQuestionGroupByYear from '../../hooks/useQuestionGroupByYear';

import BottomTabBar from '../../components/nav/BottomTabBar';

import FallBack from '../../components/ui/FallBack';
import SubPageHeader from '../../components/ui/SubPageHeader';

import Dropdown from '../../components/dropdown/Dropdown';

import QuestionListYearSection from '../../components/questionList/QuestionListYearSection';
import ListMoreButton from '../../components/questionList/ListMoreButton';
import QuestionList from '../../components/questionList/QuestionList';

const MyQuestionsPage = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const categoryId = searchParams.get('q');

  const [dropdown, setDropdown] = useState(false);

  const { data, fetchNextPage, hasNextPage } = useGetInfiniteQuestion({
    queryKey: ['my', 'questions', categoryId],
    getQuestions: getMyQuestions,
    categoryId: categoryId as string,
  });

  const { questions, questionsYear } = useQuestionGroupByYear(data ? data : [], 'latest');

  const handleSelectCategory = (categoryId: number) => {
    setSearchParams({ q: categoryId.toString() });
    setDropdown((prev) => !prev);
  };

  return (
    <div className="h-[100svh] overflow-hidden">
      <SubPageHeader pageTitle="내가 작성한 질문" backButtonPath="/my" />

      <main className="flex h-[calc(100%-140px)] flex-col">
        <Dropdown
          isOpen={dropdown}
          onClick={() => setDropdown((prev) => !prev)}
          all
          categories={CATEGORY}
          handleSelect={handleSelectCategory}
          selected={CATEGORY[Number(categoryId) - 1]}
          id={Number(categoryId) || 0}
          location="w-full"
        />

        {questionsYear.length === 0 && (
          <FallBack
            desc="아직 꺼내놓은 질문이 없어요"
            subDesc="마음속에 남아있던 질문, 꺼내볼까요?"
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

export default MyQuestionsPage;
