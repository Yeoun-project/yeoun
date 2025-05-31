import { useState } from 'react';
import { useSearchParams } from 'react-router-dom';

import SubPageHeader from '../components/ui/SubPageHeader';
import Dropdown from '../components/dropdown/Dropdown';
import BottomTabBar from '../components/nav/BottomTabBar';
import FallBack from '../components/ui/FallBack';

import CATEGORY from '../constant/category/Category';

import { getAllQuestions } from '../services/api/question/getQuestions';

import { useScrollRestore } from '../hooks/useScrolLRestore';
import useQuestionGroupByDate from '../hooks/useQuestionGroupByDate';

import ListMoreButton from '../components/questionList/ListMoreButton';
import RenderedQuestions from '../components/questionList/renderQuestions';
import CheckBox from '../components/common/CheckBox';
import { useInfiniteQuery } from '@tanstack/react-query';

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

const QuestionListPage = () => {
  const { scrollRef, handleScroll } = useScrollRestore();
  const [searchParams, setSearchParams] = useSearchParams();
  const categoryId = searchParams.get('q');

  const [isOpen, setIsOpen] = useState(false);

  const [sortOrder, setSortOrder] = useState<sortOrder>(
    (searchParams.get('sort') as sortOrder) || 'latest'
  );

  const handleSelectSortOrder = (sortOrder: sortOrder) => {
    setSortOrder(sortOrder);
  };

  const { data, fetchNextPage, hasNextPage } = useInfiniteQuery({
    queryKey: ['all', 'questions', categoryId, sortOrder],
    queryFn: async ({ pageParam }) =>
      await getAllQuestions({
        page: pageParam as number,
        categoryId: categoryId === '0' ? undefined : (categoryId as string),
        sort: sortOrder,
      }),
    initialPageParam: 0,
    getNextPageParam: (lastPage, allPages) => (lastPage.hasNext ? allPages.length : undefined),
    select: (data) => {
      // 새로 불러온 데이터들이 있다면 기존 데이터들과 매핑 후 반환
      return data.pages.flatMap((page) => page.questions || []);
    },
  });

  const { questionsDate, questionsYear } = useQuestionGroupByDate(data ? data : [], sortOrder);

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
            <div className="flex items-center justify-start gap-2.5 px-6 py-4">
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
            <div
              className="no-scrollbar overflow-scroll pb-6"
              ref={scrollRef}
              onScroll={(e) => handleScroll(e)}
            >
              <RenderedQuestions questionsYear={questionsYear} questionsDate={questionsDate} />
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
