/* eslint-disable react-refresh/only-export-components */

import { Outlet, redirect } from 'react-router-dom';
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

export const loader = async (): Promise<TodayQuestion | Response> => {
  // authStorage가 비어있다면 홈으로 redirect
  const authStorage = localStorage.getItem('auth-storage');

  if (!authStorage) {
    return redirect('/');
  }

  const { state } = JSON.parse(authStorage);

  const todayQuestion = await queryClient.fetchQuery({
    queryKey: ['today-question'],
    queryFn: async () => await getTodayQuestion(state.userType),
  });

  return todayQuestion;
};
