import useIndexCarousel from '../hooks/queries/useIndexCarousel';

import QuestionCategory from '../type/questionCategory';

// Caategory Icon
import ValuesAndBeliefs from '../assets/Icons/category/valuesAndBeliefsIcon.svg?react';
import Memories from '../assets/Icons/category/memoriesIcon.svg?react';
import SelfReflection from '../assets/Icons/category/selfReflectionIcon.svg?react';
import Relationships from '../assets/Icons/category/relationshipsIcon.svg?react';
import MindAndEmotions from '../assets/Icons/category/mindAndEmotionsIcon.svg?react';
import ChallengesAndCourage from '../assets/Icons/category/challengesAndCourageIcon.svg?react';
import DreamsAndGoals from '../assets/Icons/category/dreamsAndGoalsIcon.svg?react';

// Components
import Circle from '../components/circle/Circle';
import Carousel from '../components/Carousel';
import BottomTabBar from '../components/nav/BottomTabBar';
import TopNavBar from '../components/nav/TopNavBar';

const QuestionCategoryList = [
  {
    category: 'valuesAndBeliefs',
    icon: (size?: number) => <ValuesAndBeliefs width={size || 48} height={size || 48} />,
    label: '가치관과 신념',
    color: '#EA44C6',
  },
  {
    category: 'memories',
    icon: (size?: number) => <Memories width={size || 48} height={size || 48} />,
    label: '추억과 기억',
    color: '#D88511',
  },
  {
    category: 'selfReflection',
    icon: (size?: number) => <SelfReflection width={size || 48} height={size || 48} />,
    label: '자기성찰',
    color: '#19B70B',
  },
  {
    category: 'relationships',
    icon: (size?: number) => <Relationships width={size || 48} height={size || 48} />,
    label: '사람과의 관계',
    color: '#336CFB',
  },
  {
    category: 'mindAndEmotions',
    icon: (size?: number) => <MindAndEmotions width={size || 48} height={size || 48} />,
    label: '마음과 감정',
    color: '#E52323',
  },
  {
    category: 'challengesAndCourage',
    icon: (size?: number) => <ChallengesAndCourage width={size || 48} height={size || 48} />,
    label: '도전과 용기',
    color: '#CACA00',
  },
  {
    category: 'dreamsAndGoals',
    icon: (size?: number) => <DreamsAndGoals width={size || 48} height={size || 48} />,
    label: '꿈과 목표',
    color: '#B61ED4',
  },
];

const QuestionPage = () => {
  const {
    carouselRef,
    currentCarousel,
    handleTouchStart,
    handleTouchMove,
    handleTouchEnd,
    getTranslate,
    setCurrentCarousel,
    isDraggingRef,
  } = useIndexCarousel(30, QuestionCategoryList.length - 1);

  return (
    <main className="h-[100svh]">
      <header className="p-6">
        <TopNavBar />
      </header>

      {/* 캐러세 네비게이션 */}
      <ul className="no-scrollbar mb-6 flex cursor-pointer justify-evenly overflow-x-scroll overflow-y-hidden">
        {QuestionCategoryList.map((category, idx) => (
          <li key={category.category} onClick={() => setCurrentCarousel(idx)}>
            <Circle category={category.category as QuestionCategory} size={42} animate />
          </li>
        ))}
      </ul>

      {/* 카테고리 캐러셀 */}
      <Carousel.Wrapper
        getTranslate={getTranslate}
        handleTouchEnd={handleTouchEnd}
        handleTouchMove={handleTouchMove}
        handleTouchStart={handleTouchStart}
        carouselRef={carouselRef}
      >
        {QuestionCategoryList.map((category, idx) => (
          <Carousel.Item
            currentItem={currentCarousel}
            idx={idx}
            key={`${idx}-${category.category}`}
          >
            <div
              className="cursor-pointer touch-none"
              onClick={() => {
                if (isDraggingRef.current) return;

                // 질문 여부 확인 후 모달 띄우기
              }}
            >
              <Circle size={230} animate category={category.category as QuestionCategory}>
                <div className="flex flex-col items-center justify-center">
                  {category.icon(64)}
                  <p className="text-blur text-xl" style={{ color: category.color }}>
                    {category.label}
                  </p>
                </div>
              </Circle>
            </div>
          </Carousel.Item>
        ))}
      </Carousel.Wrapper>

      <div className="w-full p-6">
        <div className="font-desc flex h-[150px] items-center justify-center rounded-lg border border-white/70 bg-white/10 text-center text-base/8 break-keep">
          <p>
            당신의 가치관과 신념을 적어보세요 EX. 질문은 이런게 있습니다 당신의 신념을 남겨 여운을
            남겨보세요 등의 카테고리 안내문구
          </p>
        </div>
      </div>
      <BottomTabBar />
    </main>
  );
};

export default QuestionPage;
