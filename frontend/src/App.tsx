import { Routes, Route, Link } from 'react-router-dom';
import { useState } from 'react';
import HomePage from '@pages/HomePage.tsx';
import LoginPage from '@pages/LoginPage.tsx';
import QuestionPage from '@pages/QuestionPage.tsx';
import SettingPage from '@pages/SettingPage.tsx';
import MyActivityPage from '@pages/MyActivityPage.tsx';

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      {/*todo: 개발용 라우팅 설정 - 추후 삭제*/}
      <nav className="bg-blue-600 p-4 shadow-lg flex justify-between items-center">
        <Link to={'/'} className="text-white hover:text-gray-200">
          메인페이지
        </Link>
        <Link to={'/login'} className="text-white hover:text-gray-200">
          로그인
        </Link>
        <Link to={'/question'} className="text-white hover:text-gray-200">
          질문하기
        </Link>
        <Link to={'/setting'} className="text-white hover:text-gray-200">
          설정
        </Link>
        <Link to={'/my-activity'} className="text-white hover:text-gray-200">
          나의 활동
        </Link>
      </nav>

      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/question" element={<QuestionPage />} />
        <Route path="/setting" element={<SettingPage />} />
        <Route path="/my-activity" element={<MyActivityPage />} />
      </Routes>
    </>
  );
}

export default App;
