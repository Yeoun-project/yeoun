import { Link } from 'react-router-dom';

import Squre from '../../assets/Squre';

import useGetTodayQuestion from '../../hooks/queries/useGetTodayQuestion';

import Circle from '../../components/circle/Circle';
import useAuthStore from '../../store/useAuthStore';
import BottomTabBar from '../../components/nav/BottomTabBar';
import TopNavBar from '../../components/nav/TopNavBar';

const TodayQuestionPage = () => {
  const { userType } = useAuthStore();
  const { data: todayQuestion } = useGetTodayQuestion(userType);

  if (!todayQuestion) return null;
  return (
    <>
      <main className="flex min-h-[100svh] flex-col items-center justify-between p-6 pb-[100px]">
        <div aria-hidden className="animate-spin-second absolute top-[15%] right-[10%]">
          <Squre size={50} />
        </div>
        <div aria-hidden className="animate-spin-third absolute bottom-[23%] left-[8%]">
          <Squre size={70} />
        </div>
        <header className="w-full">
          <TopNavBar />
        </header>

        {/* Question */}
        <Link to={'/today-question/comment'}>
          <Circle size={300} animate>
            <p className="text-blur px-8 text-xl break-keep text-black">{todayQuestion.content}</p>
          </Circle>
        </Link>
        <p>질문을 눌러 답변을 달아주세요!</p>
      </main>

      {userType === 'User' && <BottomTabBar />}
    </>
  );
};
export default TodayQuestionPage;
