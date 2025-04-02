import { Link } from 'react-router-dom';
import Circle from '../components/circle/Circle';

const LoginPage = () => {
  return (
    <main className="flex h-[100svh] flex-col items-center justify-between px-6 pt-10 pb-5">
      <div className="">
        <div className="mb-4 flex items-center justify-center p-8">
          <Circle size={150} animate />
        </div>
        <p className="text-gradient text-center">
          하루 한마디, 내 안에 스며드는
          <span className="text-blur-pink pl-0.5 text-white"> 여운</span>
        </p>
      </div>

      {/* Login Btn */}
      <ul className="font-desc flex w-full flex-col gap-4 text-center text-sm">
        <li>
          <Link
            to={'/today-question'}
            className="block w-full rounded-xl border bg-transparent py-4.5"
          >
            로그인 없이 이용하기
          </Link>
        </li>
        <li>
          <Link
            to={import.meta.env.VITE_OAUTH_NAVER}
            className="block w-full rounded-xl bg-[#03C75A] py-4.5 text-white"
          >
            <img src="/icons/naverLogo.svg" alt="네이버로 시작하기" className="inline-block" />
            <span className="pl-2">네이버로 시작하기</span>
          </Link>
        </li>
        <li>
          <Link
            to={import.meta.env.VITE_OAUTH_KAKAO}
            className="block w-full rounded-xl bg-[#FFEB3B] py-4.5 text-[#1A1A1A]"
          >
            <img src="/icons/kakaoLogo.svg" alt="카카오로 시작하기" className="inline-block" />
            <span className="pl-2">카카오로 시작하기</span>
          </Link>
        </li>
        <li>
          <Link
            to={import.meta.env.VITE_OAUTH_GOOGLE}
            className="block w-full rounded-xl bg-white py-4.5 text-[#1A1A1A]"
          >
            <img src="/icons/googleLogo.svg" alt="구글로 시작하기" className="inline-block" />
            <span className="pl-2">구글로 시작하기</span>
          </Link>
        </li>
      </ul>
    </main>
  );
};

export default LoginPage;
