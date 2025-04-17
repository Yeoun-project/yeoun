import { useSearchParams } from 'react-router-dom';
import { useEffect, useState } from 'react';

import BackArrowButton from '../components/button/BackArrowButton';
import { addUserQuestion } from '../services/api/question/addQuestion';

import Dropdown from '../components/dropdown/Dropdown';

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
  // query string
  const [searchParams, setSearchParams] = useSearchParams();
  const selectId = parseInt(searchParams.get('category') || '0');

  // post request
  const [categoryId, setCategoryId] = useState<number>(selectId);
  const [content, setContent] = useState<string>('');

  // dropdown
  const [isOpen, setIsOpen] = useState(false);
  const [selected, setSelected] = useState(
    categories.find((cat) => cat.id === categoryId) || categories[0]
  );

  // categoryId가 바뀔 때마다 querystring도 업데이트
  useEffect(() => {
    setSearchParams({ category: String(categoryId) });
  }, [categoryId]);

  const onClick = () => setIsOpen((prev) => !prev);

  const handleSelect = (cat: Category) => {
    setSelected(cat);
    setCategoryId(cat.id);
    setIsOpen(false);
  };

  // post
  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await addUserQuestion(content, selectId);
    } catch (err) {
      console.error(err);
    }
  };

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
              onChange={(e) => setContent(e.target.value)}
              maxLength={30}
              placeholder="사용자들의 생각을 듣고 싶은 의미있는 질문을 작성해주세요."
              className="!h-[160px] w-full resize-none rounded-[4px] border border-[#99999999] bg-[#ffffff1a] p-5 placeholder:text-white focus:outline-none"
            />
            <div className="mt-1 text-right text-sm text-white">{content.length} / 30</div>
          </form>
        </div>
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
