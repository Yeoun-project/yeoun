import { useNavigate } from 'react-router-dom';

import Squre from '../../assets/Squre';

import useGetTodayQuestion from '../../hooks/queries/useGetTodayQuestion';

import Circle from '../../components/circle/Circle';
import useAuthStore from '../../store/useAuthStore';
import BottomTabBar from '../../components/nav/BottomTabBar';
import TopNavBar from '../../components/nav/TopNavBar';

const TodayQuestionPage = () => {
  const navigate = useNavigate();
  const { userType } = useAuthStore();
  const { data: todayQuestion } = useGetTodayQuestion(userType);

  if (!todayQuestion) return null;

  const handleClick = () => {
    if (todayQuestion.hasComment) {
      return navigate(`/today-question/${todayQuestion.id}`);
    } else {
      return navigate('/today-question/comment');
    }
  };
  return (
    <>
      <main className="flex min-h-[100svh] flex-col items-center justify-between p-6 pb-[100px]">
        <div aria-hidden className="animate-spin-cube absolute top-[10%] right-[13%]">
          <Squre size={50} />
        </div>
        <div aria-hidden className="animate-spin-cube-reverse absolute bottom-[20%] left-[8%]">
          <Squre size={70} />
        </div>
        <header className="w-full">
          <TopNavBar />
        </header>

        {/* Question */}
        <button onClick={handleClick}>
          <Circle size={300} animate>
            <p className="text-blur text-black-primary px-8 text-xl break-keep">
              {todayQuestion.content}
            </p>
          </Circle>
        </button>
        <p>질문을 눌러 답변을 달아주세요!</p>
      </main>

      {userType === 'User' && <BottomTabBar />}
    </>
  );
};
export default TodayQuestionPage;
