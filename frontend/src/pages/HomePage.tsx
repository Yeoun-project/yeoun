import { useNavigate } from 'react-router-dom';

import BackGroundCubeImage from '../assets/background-cube.svg?react';

import useAuthStore from '../store/useAuthStore';

import checkAuth from '../services/api/auth/checkAuth';

const HomePage = () => {
  const { setUserType, userType } = useAuthStore();

  const navigate = useNavigate();

  const handleClick = async () => {
    // 비회원
    if (userType === 'Guest') {
      return navigate('/today-question');
    }

    // 회원
    if (userType === 'User') {
      try {
        await checkAuth();
        setUserType('User');
        return navigate('/today-question');
      } catch {
        setUserType(null);
        return navigate('/login');
      }
    }

    // 로그인하지 않았을 때
    if (!userType) {
      return navigate('/login');
    }
  };

  return (
    <main className="relative flex min-h-[100svh] flex-col justify-between pt-8 pb-5">
      <div className="relative space-y-6 px-6 after:absolute after:top-[calc(100%-67px)] after:left-0 after:h-[1px] after:w-[40%] after:bg-white/70 after:content-['']">
        <p className="text-gradient text-2xl/9">
          하루의 끝,
          <br />
          의미있는 질문에 답하며
          <br />
          당신만의 여운을 남기세요
        </p>
        <p className="font-desc text-sm/loose">
          매일 답하고 기록을 쌓아보세요.
          <br />
          질문을 남기고 답변을 받아보세요.
        </p>
      </div>

      <div className="absolute bottom-[10%] left-0 overflow-hidden" aria-hidden>
        <BackGroundCubeImage width={400} height={330} />
      </div>

      <div className="px-6">
        <button
          onClick={handleClick}
          className="font-desc w-full rounded-xl bg-white py-4 font-extrabold"
        >
          <span className="text-gradient-linear">여운 시작하기</span>
        </button>
      </div>
    </main>
  );
};

export default HomePage;
