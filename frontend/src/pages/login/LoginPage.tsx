import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import useAuthStore from '../../store/useAuthStore';

import Circle from '../../components/circle/Circle';
import AuthLogoIcon from '../../components/AuthLogoIcon';

const AUTH_PATH_LIST = [
  {
    label: '네이버로 시작하기',
    identifier: 'naver',
    path: import.meta.env.VITE_OAUTH_NAVER,
  },
  {
    label: '카카오로 시작하기',
    identifier: 'kakao',
    path: import.meta.env.VITE_OAUTH_KAKAO,
  },
  {
    label: 'Google로 시작하기',
    identifier: 'google',
    path: import.meta.env.VITE_OAUTH_GOOGLE,
  },
] as const;

const getAuthLinkStyle = (identifier: 'kakao' | 'naver' | 'google') => {
  switch (identifier) {
    case 'kakao':
      return 'bg-[#ffeb3b] text-[#1a1a1a]';
    case 'naver':
      return 'bg-[#03c75a]  text-white';
    case 'google':
      return 'bg-white text-[#1a1a1a]';
  }
};

const LoginPage = () => {
  const { setUserType, userType } = useAuthStore();
  const navigate = useNavigate();

  // 비회원 로그인
  const handleGuestLogin = () => {
    setUserType('Guest');
    navigate('/today-question');
  };

  useEffect(() => {
    // 로그인 상태 일 때 리다이렉트
    if (userType === 'User') {
      navigate(-1);
      return;
    }
  }, [navigate, userType]);

  return (
    <main className="flex h-[100svh] flex-col items-center justify-between px-6 pt-10 pb-5">
      <div className="">
        <div className="mb-4 flex items-center justify-center p-8">
          <Circle size={150} animate />
        </div>
        <p className="text-gradient text-center">
          질문 하나, 마음속에 남는
          <span className="text-blur-pink pl-0.5 text-white"> 여운</span>
        </p>
      </div>

      {/* Login Btn */}
      <ul className="font-desc flex w-full flex-col gap-4 text-center text-sm">
        <li>
          <button
            onClick={handleGuestLogin}
            className="block w-full cursor-pointer rounded-xl border bg-transparent py-4.5"
          >
            로그인 없이 이용하기
          </button>
        </li>
        {AUTH_PATH_LIST.map((el) => (
          <li>
            <Link
              to={el.path}
              className={`flex w-full items-center justify-center gap-2 rounded-xl py-4.5 ${getAuthLinkStyle(el.identifier)}`}
            >
              <AuthLogoIcon identifier={el.identifier} />
              <span>{el.label}</span>
            </Link>
          </li>
        ))}
      </ul>
    </main>
  );
};

export default LoginPage;
