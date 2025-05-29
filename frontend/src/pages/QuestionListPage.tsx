import { useState } from 'react';
import { useSearchParams } from 'react-router-dom';

import SubPageHeader from '../components/ui/SubPageHeader';
import Dropdown from '../components/dropdown/Dropdown';
import BottomTabBar from '../components/nav/BottomTabBar';
import FallBack from '../components/ui/FallBack';

import CATEGORY from '../constant/category/Category';

import { getAllQuestions } from '../services/api/question/getQuestions';

import useGetInfiniteQuestion from '../hooks/queries/useGetInfiniteQuestion';

import QuestionListYearSection from '../components/questionList/QuestionListYearSection';
import ListMoreButton from '../components/questionList/ListMoreButton';
import QuestionListByDate from '../components/questionList/QuestionListByDate';

import { useScrollRestore } from '../hooks/useScrolLRestore';
import useQuestionGroupByDate from '../hooks/useQuestionGroupByDate';

const QuestionListPage = () => {
  const { scrollRef, handleScroll } = useScrollRestore();
  const [searchParams, setSearchParams] = useSearchParams();
  const categoryId = searchParams.get('q');

  const [isOpen, setIsOpen] = useState(false);

  const { data, fetchNextPage, hasNextPage } = useGetInfiniteQuestion({
    queryKey: ['all', 'questions', categoryId],
    getQuestions: getAllQuestions,
    categoryId: categoryId as string,
  });

  const { questionsDate, questionsYear } = useQuestionGroupByDate(data ? data : [], 'latest');

  const handleSelect = (categoryId: number) => {
    setSearchParams({ q: categoryId.toString() });
    setIsOpen(false);
  };

  return (
    <div className="h-[calc(100svh-140px)]">
      <SubPageHeader pageTitle="질문 모음" backButtonPath="/today-question" />
      <main className="flex h-full flex-col">
        <Dropdown
          id={Number(categoryId) || 0}
          isOpen={isOpen}
          all={true}
          onClick={() => setIsOpen((prev) => !prev)}
          handleSelect={handleSelect}
          categories={CATEGORY}
          selected={CATEGORY[Number(categoryId) - 1]}
          location={'w-full text-white font-desc'}
        />
        {questionsYear.length > 0 && (
          <>
            <div className="font-desc gap-2.5 px-6 py-3 text-[14px]">
              <p>💬 같은 날 올라온 질문 중, 답변이 많이 달린 질문부터 보여드려요 :)</p>
            </div>
            <div
              className="no-scrollbar overflow-scroll pb-6"
              ref={scrollRef}
              onScroll={(e) => handleScroll(e)}
            >
              {questionsYear.map((year) => (
                <QuestionListYearSection key={year} year={year}>
                  {questionsDate[year].map((date) => (
                    <QuestionListByDate questions={date.items} />
                  ))}
                </QuestionListYearSection>
              ))}

              {/* 더보기 버튼 */}
              {hasNextPage && <ListMoreButton fetchNextPage={fetchNextPage} />}
            </div>
          </>
        )}
        {questionsYear.length === 0 && (
          <FallBack
            desc={`아직 ${Number(categoryId) ? `'${CATEGORY[Number(categoryId) - 1].name}'엔` : ''} 남겨진 질문이 없어요`}
            subDesc="당신의 질문이 첫 여운이 되어주세요"
          />
        )}
      </main>
      <BottomTabBar />
    </div>
  );
};

export default QuestionListPage;
