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

export const questionGroupByYearAndDate = (questionList: Question[]) => {
  const YEAR_DATE_GROUP = questionList.reduce<{
    [year: string]: { [monthDay: string]: Question[] };
  }>((acc, question) => {
    const date = new Date(question.createTime);
    const year = date.getFullYear().toString();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const monthDay = `${month}-${day}`;

    if (!acc[year]) {
      acc[year] = {};
    }

    if (!acc[year][monthDay]) {
      acc[year][monthDay] = [];
    }

    acc[year][monthDay].push(question);

    return acc;
  }, {});

  const result: {
    [year: string]: { date: string; items: Question[] }[];
  } = {};

  Object.entries(YEAR_DATE_GROUP).forEach(([year, dateGroup]) => {
    const sorted = Object.entries(dateGroup)
      .sort((a, b) => {
        const dateA = new Date(`${year}-${a[0]}`);
        const dateB = new Date(`${year}-${b[0]}`);
        return dateB.getTime() - dateA.getTime(); // 최신순
      })
      .map(([date, items]) => ({ date, items }));

    result[year] = sorted;
  });

  return result;
};
