import { Question } from '../type/question';

// questionList 년도별 매핑 함수
// ex) { { 2025 : Question[] }, { 2024 : Question[] }, { 2023 : Question[] } } 형식의 데이터 반환
export const questionGroupByYear = (questionList: Question[]) => {
  const YEAR_GROUP = questionList.reduce<{ [year: string]: Question[] }>((acc, question) => {
    const year = new Date(question.createTime).getFullYear();

    if (!acc[year]) {
      // 해당 year의 배열이 없다면 새로운 year arr를 만듬
      acc[year] = [];
    }

    acc[year].push(question);

    return acc;
  }, {});

  return YEAR_GROUP;
};

// 년도 정렬 함수
// ex) [2025, 2024, 2023] | [2023, 2024, 2025]
export const sortedYearGroup = (
  YearGroup: { [year: string]: Question[] },
  sortOrder: 'latest' | 'old'
): string[] => {
  if (sortOrder === 'latest') {
    // 최신순
    return Object.keys(YearGroup).sort((a, b) => Number(b) - Number(a));
  }

  if (sortOrder === 'old') {
    // 오래된 순
    return Object.keys(YearGroup).sort((a, b) => Number(a) - Number(b));
  }

  return [];
};
