import { useState } from 'react';

import BackArrowButton from '../components/button/BackArrowButton';
import useAuthStore from '../store/useAuthStore';
import { useNavigate } from 'react-router-dom';
import { logout } from '../services/api/auth/logout';

const SettingPage = () => {
  const [activate, setActivate] = useState(true);
  const { userType, setUserType } = useAuthStore();
  const navigate = useNavigate();

  const onClickLogout = async () => {
    try {
      const response = await logout();
      if (response.status === 200) {
        setUserType(null);
        navigate('/login');
      }
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <>
      <main className="flex min-h-[100svh] flex-col gap-4">
        {/* Header */}
        <header className="relative flex justify-center p-6">
          <div className="absolute top-6 left-6">
            <BackArrowButton />
          </div>
          <h3 className="w-full text-center">설정</h3>
        </header>
        <ul className="font-desc w-full">
          {userType === 'User' && (
            <li className="flex flex-row items-center justify-between border-b border-[#AAAAAA]">
              <div className="px-6 py-4.5">알림</div>
              <div className="px-6 py-4">
                <button
                  className={`flex h-[24px] w-[40px] items-center rounded-full p-[4px] transition-colors duration-300 ${activate ? 'bg-[#FC90D1]' : 'border border-[#D4D4D4] bg-[#F5F5F5]'}`}
                  onClick={() => {
                    setActivate(!activate);
                  }}
                >
                  <div
                    className={`flex h-[16px] w-[16px] transform items-center rounded-full bg-white shadow-md transition-transform duration-300 ${activate ? 'translate-x-[16px] bg-[url(/icons/check.svg)] bg-center bg-no-repeat' : 'translate-x-0 bg-[url(/icons/Union.svg)] bg-center bg-no-repeat'}`}
                  ></div>
                </button>
              </div>
            </li>
          )}
          {userType === 'Guest' && (
            <li
              onClick={() => {
                if (userType === 'Guest') {
                  navigate('/login');
                }
              }}
              className="cursor-pointer overflow-hidden border-b border-[#AAAAAA]"
            >
              <div className="px-6 py-4.5 transition-transform duration-150 active:shadow-inner">
                로그인
              </div>
            </li>
          )}
          <li className="cursor-pointer border-b border-[#AAAAAA]">
            <div className="px-6 py-4.5 transition-transform duration-150 active:shadow-inner">
              문의하기
            </div>
          </li>
          <li className="flex flex-row justify-between border-b border-[#AAAAAA] px-6 py-4.5">
            <div>버전정보</div>
            <div>1.0.0</div>
          </li>
          {userType === 'User' && (
            <>
              <li onClick={onClickLogout} className="cursor-pointer border-b border-[#AAAAAA]">
                <div className="px-6 py-4.5 transition-transform duration-150 active:shadow-inner">
                  로그아웃
                </div>
              </li>
              <li
                onClick={() => {
                  if (userType === 'User') {
                    navigate('/user-delete');
                  }
                }}
                className="cursor-pointer border-b border-[#AAAAAA]"
              >
                <div className="px-6 py-4.5 text-[#777777] transition-transform duration-150 active:shadow-inner">
                  탈퇴하기
                </div>
              </li>
            </>
          )}
        </ul>
      </main>
    </>
  );
};

export default SettingPage;
