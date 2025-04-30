import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useModalStore from '../store/useModalStore';
import useIndexCarousel from '../hooks/queries/useIndexCarousel';

import QuestionCategory from '../type/questionCategory';

import extendsCategoryData from '../constant/category/extendsCategoryData';

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
import Modal from '../components/modal/Modal';

const extnedsCategoryIconAndColor = {
  valuesAndBeliefs: {
    icon: (size?: number) => <ValuesAndBeliefs width={size || 48} height={size || 48} />,
    color: '#EA44C6',
  },
  memories: {
    icon: (size?: number) => <Memories width={size || 48} height={size || 48} />,
    color: '#D88511',
  },
  selfReflection: {
    icon: (size?: number) => <SelfReflection width={size || 48} height={size || 48} />,
    color: '#19B70B',
  },
  relationships: {
    icon: (size?: number) => <Relationships width={size || 48} height={size || 48} />,
    color: '#336CFB',
  },
  mindAndEmotions: {
    icon: (size?: number) => <MindAndEmotions width={size || 48} height={size || 48} />,
    color: '#E52323',
  },
  challengesAndCourage: {
    icon: (size?: number) => <ChallengesAndCourage width={size || 48} height={size || 48} />,
    color: '#CACA00',
  },
  dreamsAndGoals: {
    icon: (size?: number) => <DreamsAndGoals width={size || 48} height={size || 48} />,
    color: '#B61ED4',
  },
};
const QuestionPage = () => {
  const navigate = useNavigate();

  const [currentCategory, setCurrentCategory] = useState<number | null>(null);
  const { modal, openModal, closeModal } = useModalStore();

  const QuestionCategoryList = extendsCategoryData(extnedsCategoryIconAndColor);

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

  const handleConfirm = () => {
    closeModal();
    navigate(`/question/add-question?category=${currentCategory}`);
  };

  return (
    <>
      <main className="h-[100svh]">
        <header className="p-6">
          <TopNavBar />
        </header>

        {/* 캐러세 네비게이션 */}
        <ul className="no-scrollbar mb-6 flex cursor-pointer justify-evenly overflow-x-scroll overflow-y-hidden">
          {QuestionCategoryList.map((category, idx) => (
            <li key={category.category} onClick={() => setCurrentCarousel(idx)}>
              <Circle category={category.category as QuestionCategory} size={42} />
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
                  openModal();
                  setCurrentCategory(category.id);
                  // 질문 여부 확인 후 모달 띄우기
                }}
              >
                <Circle size={250} animate category={category.category as QuestionCategory}>
                  <div className="flex flex-col items-center justify-center">
                    {category.icon && category.icon(64)}
                    <p className="text-blur text-xl" style={{ color: category.color }}>
                      {category.name}
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

      {modal && (
        <Modal>
          <Modal.Header>
            <Modal.Title>질문 작성은 하루에 1번만 가능합니다. 질문을 작성하시겠습니까?</Modal.Title>
            <Modal.SubTitle>
              작성 가능 횟수가 <span className="text-[#398DFA]">1번 남았습니다.</span>
            </Modal.SubTitle>
          </Modal.Header>
          <Modal.Footer>
            <Modal.CancleButton>아니요</Modal.CancleButton>
            <Modal.ConfirmButton onConfirm={handleConfirm}>네</Modal.ConfirmButton>
          </Modal.Footer>
        </Modal>
      )}
    </>
  );
};

export default QuestionPage;
