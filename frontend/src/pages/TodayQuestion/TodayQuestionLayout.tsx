/* eslint-disable react-refresh/only-export-components */

import { Outlet } from 'react-router-dom';
import { queryClient } from '../../utils/queryClient';
import { getTodayQuestion } from '../../services/api/question/todayQuestion';
import { TodayQuestion } from '../../type/question';

const TodayQuestionLayout = () => {
  //loader 사용 예정
  return (
    <>
      <Outlet />
    </>
  );
};

export default TodayQuestionLayout;

export const loader = async (): Promise<TodayQuestion> => {
  const todayQuestion = await queryClient.fetchQuery({
    queryKey: ['today-question'],
    queryFn: async () => await getTodayQuestion(),
  });

  return todayQuestion;
};
