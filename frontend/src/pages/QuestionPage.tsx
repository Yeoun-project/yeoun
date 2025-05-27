import { useEffect, useMemo, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

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
import checkQuestionToday from '../services/api/question/checkQuestionToday';
import useToastStore from '../store/useToastStore';

const extnedsCategoryIconAndColor = {
  valuesAndBeliefs: {
    icon: (size?: number) => <ValuesAndBeliefs width={size || 48} height={size || 48} />,
    color: '#EA44C6',
    desc: '중요한 가치나 신념에 대한 질문을 적어보세요. 그 사람의 선택을 이끄는 믿음을 알 수 있어요.',
    example: '스스로 가장 중요하게 여기는 원칙이 있나요?',
  },
  memories: {
    icon: (size?: number) => <Memories width={size || 48} height={size || 48} />,
    color: '#D88511',
    desc: '과거의 기억에 대한 질문을 적어보세요. 특별한 의미로 남은 순간을 알 수 있어요.',
    example: '잊을 수 없는 여행의 순간이 있나요?',
  },
  selfReflection: {
    icon: (size?: number) => <SelfReflection width={size || 48} height={size || 48} />,
    color: '#19B70B',
    desc: '자신을 돌아보는 질문을 적어보세요. 지금의 자신이 된 과정을 알 수 있어요.',
    example: '가장 큰 영향을 받은 경험은 무엇인가요?',
  },
  relationships: {
    icon: (size?: number) => <Relationships width={size || 48} height={size || 48} />,
    color: '#336CFB',
    desc: '관계에 대한 질문을 적어보세요. 소통 방식과 관계의 가치를 알 수 있어요.',
    example: '친구나 가족 중 꼭 닮고 싶은 사람은 누구인가요?',
  },
  mindAndEmotions: {
    icon: (size?: number) => <MindAndEmotions width={size || 48} height={size || 48} />,
    color: '#E52323',
    desc: '감정에 대한 질문을 적어보세요. 감정이 생각과 행동에 준 영향을 알 수 있어요.',
    example: '요즘 가장 많이 느끼는 감정은 무엇인가요?',
  },
  challengesAndCourage: {
    icon: (size?: number) => <ChallengesAndCourage width={size || 48} height={size || 48} />,
    color: '#CACA00',
    desc: '도전에 대한 질문을 적어보세요. 용기를 준 계기와 시도를 알 수 있어요',
    example: '한 번쯤 해보고 싶지만 아직 용기가 나지 않는 일이 있나요?',
  },
  dreamsAndGoals: {
    icon: (size?: number) => <DreamsAndGoals width={size || 48} height={size || 48} />,
    color: '#B61ED4',
    desc: '꿈이나 목표에 대한 질문을 적어보세요. 삶을 이끄는 방향을 알 수 있어요.',
    example: '언젠가 꼭 이루고 싶은 목표가 있나요?',
  },
};

const QuestionPage = () => {
  const { state } = useLocation();
  const navigate = useNavigate();

  const [checkQuestion, setCheckQuestion] = useState<boolean>();
  const [currentCategory, setCurrentCategory] = useState<number | null>(null);

  const { modal, openModal, closeModal } = useModalStore();
  const toast = useToastStore();

  useEffect(() => {
    if (state?.showToast) {
      toast.addToast.notification({
        title: '여운 등록 완료',
        message: '당신의 질문이 누군가의 마음에 여운을 남길 거예요.',
      });
    }
  }, []);

  const QuestionCategoryList = useMemo(() => extendsCategoryData(extnedsCategoryIconAndColor), []);

  const {
    carouselRef,
    currentCarousel,
    handleTouchStart,
    handleTouchMove,
    handleTouchEnd,
    setCurrentCarousel,
    isDraggingRef,
  } = useIndexCarousel(30, QuestionCategoryList.length - 1);

  const handleConfirm = () => {
    closeModal();
    navigate(`/question/add-question?category=${currentCategory}`);
  };

  const handleCheckQuestionToday = async (categoryId: number) => {
    if (isDraggingRef.current) return;

    try {
      if (await checkQuestionToday()) {
        setCheckQuestion(true);
      } else {
        setCheckQuestion(false);
        setCurrentCategory(categoryId);
      }
      openModal();
    } catch (error) {
      console.log(error);
    }
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
                onClick={async () => await handleCheckQuestionToday(category.id)}
              >
                <Circle size={250} category={category.category as QuestionCategory} animate>
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
        <div className="w-full px-6 pt-2">
          <div className="font-desc flex h-[150px] flex-col items-center justify-center gap-1 rounded-lg border border-white/70 bg-white/10 p-6 text-center text-base/6">
            <p>{QuestionCategoryList[currentCarousel].desc}</p>
            <p className="text-[#aaaaaa]">{`EX. "${QuestionCategoryList[currentCarousel].example}"`}</p>
          </div>
        </div>
        <BottomTabBar />
      </main>

      {modal && (
        <Modal>
          <Modal.Header>
            <Modal.Title>
              <>
                질문 작성은 하루에 1번만 가능합니다.{!checkQuestion && '질문을 작성하시겠습니까?'}
              </>
            </Modal.Title>
            <Modal.SubTitle>
              {!checkQuestion && (
                <>
                  작성 가능 횟수가 <span className="text-[#398DFA]">1번 남았습니다.</span>
                </>
              )}
              {checkQuestion && (
                <span className="text-error">오늘 이미 질문을 작성하셨습니다.</span>
              )}
            </Modal.SubTitle>
          </Modal.Header>
          <Modal.Footer>
            <Modal.CancleButton>{!checkQuestion ? '아니요' : '확인'}</Modal.CancleButton>
            {!checkQuestion && (
              <Modal.ConfirmButton onConfirm={handleConfirm}>네</Modal.ConfirmButton>
            )}
          </Modal.Footer>
        </Modal>
      )}
    </>
  );
};

export default QuestionPage;
