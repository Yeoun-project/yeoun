import { Link } from 'react-router-dom';

import ProfileIcon from '../../assets/Icons/ProfileIcon.svg?react';

import BottomTabBar from '../../components/nav/BottomTabBar';
import TopNavBar from '../../components/nav/TopNavBar';
import AuthLogoIcon from '../../components/AuthLogoIcon';

const MY_PAGE_NAV_PATHS = [
  {
    label: '오늘의 질문 답변',
    path: '/today-question/answers',
  },
  {
    label: '내가 작성한 질문',
    path: '/my/questions?q=0',
  },
  {
    label: '내가 답변한 질문',
    path: '/my/answers?q=0',
  },
];

const MyActivityPage = () => {
  return (
    <>
      <div>
        <header className="mb-5 p-6">
          <TopNavBar navTitle="내 활동" />
        </header>
        <main>
          <div className="px-5">
            <div className="flex items-center gap-4 rounded-3xl border border-white/50 bg-gradient-to-b from-[#ffffff]/5 to-[#FC90D1]/5 px-5 py-6 bg-blend-difference">
              <ProfileIcon />
              <div className="font-desc font-bold">
                <p className="mb-2">email@naver.com</p>
                <p className="flex items-center gap-2">
                  <AuthLogoIcon identifier="naver" size={24} fill="#03C75A" />
                  <span>네이버 로그인</span>
                </p>
              </div>
            </div>
          </div>
          <ul>
            {MY_PAGE_NAV_PATHS.map((path) => (
              <li key={path.path} className="font-desc border-b-1 border-[#aaaaaa] px-6 py-5">
                <Link to={path.path}>{path.label}</Link>
              </li>
            ))}
          </ul>
        </main>
        <BottomTabBar />
      </div>
    </>
  );
};

export default MyActivityPage;
