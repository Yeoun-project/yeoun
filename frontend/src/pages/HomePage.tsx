import { useNavigate } from 'react-router-dom';
import BackGroundCubeImage from '../assets/background-cube.svg?react';

const HomePage = () => {
  const navigate = useNavigate();
  const handleClick = () => {
    navigate('/login');
  };

  return (
    <main className="relative flex min-h-[100svh] flex-col justify-between pt-8 pb-5">
      <div className="relative space-y-6 px-6 after:absolute after:top-[calc(100%-93px)] after:left-0 after:h-[1px] after:w-[40%] after:bg-white/70 after:content-['']">
        <p className="text-gradient text-2xl/9">
          하루의 끝,
          <br />
          의미있는 질문에 답하며
          <br />
          당신만의 여운을 남기세요
        </p>
        <p className="font-desc text-sm">
          매일 주어지는 질문에 답하고
          <br />
          나만의 기록을 쌓아보세요
          <br />
          다른 사람에게 의미있는 질문을 던지고
          <br />
          그들의 답변을 들어보세요
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
