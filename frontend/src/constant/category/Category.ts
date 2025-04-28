import QuestionCategory from '../../type/questionCategory';

export interface CategoryList {
  id: number;
  name: string;
  category: QuestionCategory;
}

const CATEGORY: CategoryList[] = [
  {
    id: 1,
    category: 'valuesAndBeliefs',
    name: '가치관과 신념',
  },
  {
    id: 2,
    category: 'memories',
    name: '추억과 기억',
  },
  {
    id: 3,
    category: 'selfReflection',
    name: '자기성찰',
  },
  {
    id: 4,
    category: 'relationships',
    name: '사람과의 관계',
  },
  {
    id: 5,
    category: 'mindAndEmotions',
    name: '마음과 감정',
  },
  {
    id: 6,
    category: 'challengesAndCourage',
    name: '도전과 용기',
  },
  {
    id: 7,
    category: 'dreamsAndGoals',
    name: '꿈과 목표',
  },
];

export default CATEGORY;
