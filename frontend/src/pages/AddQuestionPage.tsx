import axios from 'axios';

import { useSearchParams } from 'react-router-dom';
import { useEffect, useState } from 'react';

import QuestionCategory from '../type/questionCategory';
import { addUserQuestion, verifiedQuestion } from '../services/api/question/addQuestion';

import BackArrowButton from '../components/button/BackArrowButton';
import Dropdown from '../components/dropdown/Dropdown';

import useToastStore from '../store/useToastStore';

import useModalStore from '../store/useModalStore';

import EditorForm from '../components/form/EditorForm';

import RegisterModal from '../components/modal/RegisterModal';
import ConfirmModal from '../components/modal/ConfirmModal';

import { useNavigate } from 'react-router-dom';

export interface Category {
  category: QuestionCategory;
  id: number;
  name: string;
  examples: string[];
}

const MAX_LENGTH = 30;

const categories: Category[] = [
  {
    category: 'valuesAndBeliefs',
    id: 1,
    name: '가치관과 신념',
    examples: [
      '어떤 가치가 평생 변하지 않을 거라고 생각하나요?',
      '스스로 가장 중요하게 여기는 원칙이 있나요?',
    ],
  },
  {
    category: 'memories',
    id: 2,
    name: '추억과 기억',
    examples: ['잊을 수 없는 여행의 순간이 있나요?', '특별한 의미가 담긴 물건이 있나요?'],
  },
  {
    category: 'selfReflection',
    id: 3,
    name: '자기성찰',
    examples: ['요즘 가장 몰입하고 있는 일이 있나요?', '가장 큰 영향을 받은 경험은 무엇인가요?'],
  },
  {
    category: 'relationships',
    id: 4,
    name: '사람과의 관계',
    examples: [
      '친구나 가족 중 꼭 닮고 싶은 사람은 누구인가요?',
      '사람을 볼 때 가장 중요하게 생각하는 기준이 있나요?',
    ],
  },
  {
    category: 'mindAndEmotions',
    id: 5,
    name: '마음과 감정',
    examples: ['기분이 좋아지는 작은 습관이 있나요?', '요즘 가장 많이 느끼는 감정은 무엇인가요?'],
  },
  {
    category: 'challengesAndCourage',
    id: 6,
    name: '도전과 용기',
    examples: [
      '한 번쯤 해보고 싶지만 아직 용기가 나지 않는 일이 있나요?',
      '실패했지만 후회하지 않는 경험이 있나요?',
    ],
  },
  {
    category: 'dreamsAndGoals',
    id: 7,
    name: '꿈과 목표',
    examples: [
      '요즘 가장 관심 있는 분야나 배우고 싶은 것이 있나요?',
      '언젠가 꼭 이루고 싶은 목표가 있나요?',
    ],
  },
];

const AddQuestionPage = () => {
  const nav = useNavigate();
  //#region State
  // query string
  const [searchParams, setSearchParams] = useSearchParams();

  // post request
  const [content, setContent] = useState<string>('');

  const [categoryId, setCategoryId] = useState<number>(
    parseInt(searchParams.get('category') || '1')
  );

  // 금지어 error state
  const [hasError, setHasError] = useState(false);
  const [forbidden, setForbidden] = useState<string[]>([]);
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
    if (categoryId !== 0) {
      setSelected(categories.find((cat) => cat.id === categoryId) || categories[0]);
    }
  }, [categoryId]);
  //#region EventHandler
  const onClick = () => setIsOpen((prev) => !prev);

  const handleSelect = (id: number) => {
    setCategoryId(id);
    setIsOpen(false);
  };
  // 질문하기 버튼 클릭 시
  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      // 금지어 탐색
      await verifiedQuestion(content, categoryId);

      // 금지어 X
      // 버튼 클릭 시 첫 번째 모달 출력
      modal.openModal();
      setFirst(true);
      setSecond(false);
    } catch (err) {
      // 금지어 O
      if (axios.isAxiosError(err)) {
        const response = err.response?.data;
        setForbidden(response.data);
        setHasError(true);

        if (response.code === 'MISSING_PARAMETER') {
          toast.addToast.error({
            title: '여운 등록 실패',
            message: `질문을 작성해주세요!`,
          });
        }
        if (response.code === 'BAD_REQUEST') {
          toast.addToast.error({
            title: '여운 등록 실패',
            message: `질문에 제한된 표현이 포함되어 있어요.
            수정 후 다시 등록해 주세요!`,
          });
        }
      }
    }
  };

  // 첫 번째 모달 등록 버튼
  const onRegister = () => {
    setFirst(false);
    setSecond(true);
  };
  // 두 번째 모달 등록 버튼
  const onConfirm = async () => {
    const response = await addUserQuestion(content, categoryId);
    console.log(response?.data);

    // 등록 후 초기화
    setSecond(false);
    setContent('');
    nav('/question');
  };

  const handleChange = (e: React.FormEvent<HTMLDivElement>) => {
    const selection = window.getSelection();
    if (!selection || !e.currentTarget) return;

    const { focusOffset } = selection;

    // 현재 커서 위치 저장
    const newFocusOffset = Math.min(focusOffset, e.currentTarget.innerText.length);

    if (e.currentTarget?.innerHTML === '<br>') {
      setContent('');
      e.currentTarget.innerHTML = '';
    } else {
      if (e.currentTarget?.innerText.length >= MAX_LENGTH) {
        e.currentTarget.innerText = e.currentTarget.innerText.slice(0, MAX_LENGTH);

        // 커서 복구
        const range = document.createRange();
        const sel = window.getSelection();
        if (e.currentTarget.childNodes.length > 0 && sel) {
          let node = e.currentTarget.childNodes[0];

          // span 같은 태그 때문에 childNodes[0]이 span일 수도 있음
          if (node.nodeType !== Node.TEXT_NODE) {
            node = node.firstChild || node;
          }

          range.setStart(node, Math.min(newFocusOffset, node.textContent?.length || 0));
          range.collapse(true);
          sel.removeAllRanges();
          sel.addRange(range);
        }
      }
      setContent(e.currentTarget?.innerText);
    }

    if (hasError) {
      setHasError(false);
      e.currentTarget.innerHTML = content;
    }
  };
  //#endregion

  return (
    <>
      <main className="flex min-h-[100svh] flex-col">
        <header className="relative flex justify-center p-8">
          <div className="absolute top-6 left-6">
            <BackArrowButton />
          </div>
          <h3 className="w-full text-center">질문작성</h3>
        </header>
        {/* <div className="flex h-60 flex-col justify-between p-6"> */}
        <div>
          <h1 className="mb-4 px-6">질문 카테고리 설정</h1>
          <Dropdown
            id={categoryId}
            isOpen={isOpen}
            all={false}
            onClick={onClick}
            handleSelect={handleSelect}
            categories={categories}
            selected={selected}
            location={'w-full px-6 text-white font-desc mb-4'}
          />
          <ul className="font-desc flex list-none flex-col gap-1 px-6 text-white">
            <span className="text-[#999999]">(예시)</span>
            {categories[categoryId - 1]?.examples.map((example, index) => (
              <li key={index}>"{example}"</li>
            ))}
          </ul>
        </div>
        <div className="font-desc w-full p-6 text-white">
          <EditorForm
            formId="add-question"
            onSubmit={(e) => onSubmit(e)}
            value={content}
            onChange={handleChange}
            hasError={hasError}
            maxLength={MAX_LENGTH}
            placeholder="사용자들의 생각을 듣고 싶은 의미있는 질문을 작성해주세요."
            forbidden={forbidden}
          />
        </div>
        {first && <RegisterModal content={content} onSubmit={onRegister} />}
        {second && <ConfirmModal value="질문" onSubmit={onConfirm} />}
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
