import { Link, useNavigate } from 'react-router-dom';

import Squre from '../../assets/Squre';

import Circle from '../../components/circle/Circle';
import TabNav from '../../components/TabNav';

const TodayQuestionPage = () => {
  const navigate = useNavigate();

  //useLoaderData

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
          {/* Top Nav */}
          <nav className="flex items-center justify-between">
            <Link
              aria-label="설정 바로가기"
              to="/settings"
              className="size-6 bg-[url(/icons/settings.svg)] bg-no-repeat"
            />

            <button
              onClick={() => navigate('/today-question/answers')}
              className="text-gradient cursor-pointer bg-clip-text"
            >
              내 여운 기록
            </button>
          </nav>
        </header>

        {/* Question */}
        <Link to={'/today-question/comment'}>
          <Circle size={300} animate>
            <p className="text-blur px-12 text-xl break-keep text-black">
              다시 태어난다면, 당신은 어떻게 살고 싶나요?
            </p>
          </Circle>
        </Link>
        <p>질문을 눌러 답변을 달아주세요!</p>
      </main>
      <TabNav />
    </>
  );
};
export default TodayQuestionPage;
