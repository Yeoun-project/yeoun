import { useMemo, useState } from 'react';
import { useSearchParams } from 'react-router-dom';

import SubPageHeader from '../components/ui/SubPageHeader';
import Dropdown from '../components/dropdown/Dropdown';
import BottomTabBar from '../components/nav/BottomTabBar';
import FallBack from '../components/ui/FallBack';

import CATEGORY from '../constant/category/Category';

import { getAllQuestions } from '../services/api/question/getQuestions';

import useGetInfiniteQuestion from '../hooks/queries/useGetInfiniteQuestion';
import useQuestionGroupByYear from '../hooks/useQuestionGroupByYear';

import QuestionListYearSection from '../components/questionList/QuestionListYearSection';
import QuestionList from '../components/questionList/QuestionList';
import ListMoreButton from '../components/questionList/ListMoreButton';

const QuestionListPage = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const categoryId = searchParams.get('q');

  const [isOpen, setIsOpen] = useState(false);

  const { data, fetchNextPage, hasNextPage } = useGetInfiniteQuestion({
    queryKey: ['all', 'questions', categoryId],
    getQuestions: getAllQuestions,
    categoryId: categoryId as string,
  });

  const selectedCategoryName = useMemo(() => {
    const category = CATEGORY.find((c) => c.id === Number(categoryId));
    return category?.category;
  }, [categoryId]);

  const { questions, questionsYear } = useQuestionGroupByYear(data ? data : [], 'latest', selectedCategoryName);

  const handleSelect = (categoryId: number) => {
    setSearchParams({ q: categoryId.toString() });
    setIsOpen(false);
  };
  return (
    <div className="h-[100vh]">
      <SubPageHeader pageTitle="질문 모음" backButtonPath="/today-question" />

      <main className="flex h-[calc(100%-140px)] flex-col">
        <div className="mb-3 flex items-center justify-end gap-2 px-6">
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
        </div>
        {questionsYear.length > 0 && (
          <>
            <div className="font-desc gap-2.5 px-6 text-[14px]">
              <p>💬 같은 날 올라온 질문 중, 답변이 많이 달린 질문부터 보여드려요 :)</p>
            </div>
            <div className="no-scrollbar overflow-scroll pb-6">
              {questionsYear.map((year) => (
                <QuestionListYearSection key={year} year={year}>
                  <QuestionList questions={questions[year]} path="question" />
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
