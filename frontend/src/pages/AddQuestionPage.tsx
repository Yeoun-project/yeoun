import axios from 'axios';

import { useSearchParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { addUserQuestion } from '../services/api/question/addQuestion';

import BackArrowButton from '../components/button/BackArrowButton';
import Dropdown from '../components/dropdown/Dropdown';

import useToastStore from '../store/useToastStore';

import useModalStore from '../store/useModalStore';
import Modal from '../components/modal/Modal';
import RegisterModal from '../components/modal/RegisterModal';

export interface Category {
  category: string;
  id: number;
  name: string;
  examples: string[];
  color: string;
}

const categories: Category[] = [
  {
    category: 'valuesAndBeliefs',
    id: 1,
    name: '가치관과 신념',
    examples: [
      '어떤 가치가 평생 변하지 않을 거라고 생각하나요?',
      '스스로 가장 중요하게 여기는 원칙이 있나요?',
    ],
    color: '#EA44C6',
  },
  {
    category: 'memories',
    id: 2,
    name: '추억과 기억',
    examples: ['잊을 수 없는 여행의 순간이 있나요?', '특별한 의미가 담긴 물건이 있나요?'],
    color: '#D88511',
  },
  {
    category: 'selfReflection',
    id: 3,
    name: '자기성찰',
    examples: ['요즘 가장 몰입하고 있는 일이 있나요?', '가장 큰 영향을 받은 경험은 무엇인가요?'],
    color: '#19B70B',
  },
  {
    category: 'relationships',
    id: 4,
    name: '사람과의 관계',
    examples: [
      '친구나 가족 중 꼭 닮고 싶은 사람은 누구인가요?',
      '사람을 볼 때 가장 중요하게 생각하는 기준이 있나요?',
    ],
    color: '#336CFB',
  },

  {
    category: 'mindAndEmotions',
    id: 5,
    name: '마음과 감정',
    examples: ['기분이 좋아지는 작은 습관이 있나요?', '요즘 가장 많이 느끼는 감정은 무엇인가요?'],
    color: '#E52323',
  },
  {
    category: 'challengesAndCourage',
    id: 6,
    name: '도전과 용기',
    examples: [
      '한 번쯤 해보고 싶지만 아직 용기가 나지 않는 일이 있나요?',
      '실패했지만 후회하지 않는 경험이 있나요?',
    ],
    color: '#CACA00',
  },
  {
    category: 'dreamsAndGoals',
    id: 7,
    name: '꿈과 목표',
    examples: [
      '요즘 가장 관심 있는 분야나 배우고 싶은 것이 있나요?',
      '언젠가 꼭 이루고 싶은 목표가 있나요?',
    ],
    color: '#B61ED4',
  },
];

const AddQuestionPage = () => {
  //#region State
  // query string
  const [searchParams, setSearchParams] = useSearchParams();
  const selectId = parseInt(searchParams.get('category') || '1');

  // post request
  const [content, setContent] = useState<string>('');
  const [categoryId, setCategoryId] = useState<number>(selectId);

  // 금지어 error state
  const [hasError, setHasError] = useState(false);
  const toast = useToastStore();

  // modal
  const modal = useModalStore();
  const [first, setFirst] = useState(true);
  const [second, setSecond] = useState(false);

  // dropdown
  const [isOpen, setIsOpen] = useState(false);
  const [selected, setSelected] = useState(
    categories.find((cat) => cat.id === categoryId) || categories[0]
  );
  //#endregion

  // categoryId가 바뀔 때마다 querystring도 업데이트
  useEffect(() => {
    setSearchParams({ category: String(categoryId) });
  }, [categoryId]);

  //#region function
  const onClick = () => setIsOpen((prev) => !prev);

  const handleSelect = (cat: Category) => {
    setSelected(cat);
    setCategoryId(cat.id);
    setIsOpen(false);
  };

  //#region 질문하기 버튼 클릭 시
  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      // 수정 필요
      const response = await addUserQuestion(content, selectId);
      console.log(response?.data);

      // 버튼 클릭 시 첫 번째 모달 출력
      modal.openModal();
      setFirst(true);
      setSecond(false);
    } catch (err) {
      if (axios.isAxiosError(err)) {
        const response = err.response?.data;
        setHasError(true);

        if (response.code === 'MISSING_PARAMETER') {
          toast.addToast.error({
            title: '질문 등록 실패',
            message: `질문을 등록해주세요!`,
          });
        }
        if (response.code === 'BAD_REQUEST') {
          toast.addToast.error({
            title: '질문 등록 실패',
            message: `질문에 제한된 표현이 포함되어 있어요.
            수정 후 다시 등록해 주세요!`,
          });
        }
      }
    }
  };

  // 두 번째 모달 등록 버튼
  const confirmModal = async () => {
    const response = await addUserQuestion(content, selectId);
    console.log(response?.data);

    // 등록 후 초기화
    setSecond(false);
    setContent('');
  };
  //#endregion

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (hasError) {
      setHasError(false);
    }
    setContent(e.target.value);
  };
  //#endregion

  return (
    <>
      <main className="flex min-h-[100svh] flex-col">
        <header className="relative flex justify-center p-6">
          <div className="absolute top-6 left-6">
            <BackArrowButton />
          </div>
          <h3 className="w-full text-center">답변목록</h3>
        </header>
        {/* Dropdown */}
        <div className="flex h-55 flex-col justify-between p-6">
          <h1>질문 카테고리 설정</h1>
          <Dropdown
            isOpen={isOpen}
            onClick={onClick}
            handleSelect={handleSelect}
            categories={categories}
            selected={selected}
            categoryId={categoryId}
          />
        </div>
        <div className="font-desc w-full p-6 text-white">
          <form
            id="add-question"
            onSubmit={(e) => {
              onSubmit(e);
            }}
            className="h-[192px]"
          >
            <textarea
              value={content}
              name="comment"
              id="comment"
              onChange={handleChange}
              maxLength={30}
              placeholder="사용자들의 생각을 듣고 싶은 의미있는 질문을 작성해주세요."
              className={`!h-[160px] w-full resize-none rounded-[4px] border p-5 placeholder:text-white focus:outline-none ${
                hasError
                  ? 'border-[#FF202080] bg-[#FF20200D] focus:border-[#FF202080]'
                  : 'border-[#99999999] bg-[#ffffff1a]'
              }`}
            />
            <div className="mt-1 text-right text-sm text-white">{content.length} / 30</div>
          </form>
        </div>
        {first && (
          <Modal>
            <Modal.Header>
              <Modal.Title>이대로 등록하시겠습니까?</Modal.Title>
              <Modal.SubTitle>
                작성한 질문은 한 번 등록하면{' '}
                <span className="text-[#FF2020]">수정이나 삭제가 불가능합니다!</span>
              </Modal.SubTitle>
            </Modal.Header>
            <Modal.Content>
              <div className="font-desc h-[200px] rounded-[8px] border border-[#919191] px-5 py-3 text-[#1A1A1A]">
                {content}
              </div>
              <div className="font-desc mt-2 text-right text-sm text-[#6D6D6D]">
                {content.length} / 30
              </div>
            </Modal.Content>
            <Modal.Footer>
              <Modal.CancleButton>수정</Modal.CancleButton>
              <Modal.ConfirmButton
                onConfirm={() => {
                  setFirst(false);
                  setSecond(true);
                }}
              >
                등록
              </Modal.ConfirmButton>
            </Modal.Footer>
          </Modal>
        )}
        {second && <RegisterModal value="질문" onSubmit={confirmModal} />}
        <div className="absolute bottom-0 w-full p-6">
          <button
            form="add-question"
            className="font-desc h-[60px] w-full cursor-pointer rounded-xl bg-white py-4 font-bold text-black"
          >
            질문 등록하기
          </button>
        </div>
      </main>
    </>
  );
};

export default AddQuestionPage;
