import { useState } from "react";

import BackArrow from "../components/common/BackArrow";
import TabNav from "../components/TabNav";

const SettingPage = () => {
  const [activate, setActivate] = useState(true);

  return (
    <>
      <main className="flex min-h-[100svh] flex-col gap-4">
        {/* Header */}
        <header className="relative flex justify-center p-6">
          <div className="absolute top-6 left-6">
            <BackArrow />
          </div>
          <h3 className="w-full text-center">답변목록</h3>
        </header>
        <ul className="w-full font-desc">
          <li
            onClick={() => {}}
            className="border-b border-[#AAAAAA] cursor-pointer overflow-hidden"
          >
            <div className="px-6 py-4.5 transition-transform duration-150 active:shadow-inner">
              로그아웃
            </div>
          </li>

          <li className="flex flex-row justify-between items-center border-b border-[#AAAAAA]">
            <div className="px-6 py-4.5">
              알림
            </div>
            <div className="px-6 py-4">
              <button 
                className={`w-[40px] h-[24px] p-[4px] flex items-center rounded-full transition-colors duration-300 
                ${activate ? "bg-[#FC90D1]" : "bg-[#F5F5F5] border border-[#D4D4D4]"}`}
                onClick={() => { setActivate(!activate) }}
              >
                <div
                  className={`w-[16px] h-[16px]  flex items-center rounded-full bg-white shadow-md transform transition-transform duration-300 
                    ${activate ? "translate-x-[16px] bg-[url(/icons/check.svg)] bg-no-repeat bg-center" : "translate-x-0 bg-[url(/icons/Union.svg)] bg-no-repeat bg-center"}`}
                >
                </div>
              </button>
            </div>
          </li>
          <li className="flex flex-row justify-between px-6 py-4.5 border-b border-[#AAAAAA]">
            <div>
              버전정보
            </div>
            <div>
              1.0.0
            </div>
          </li>
          <li className="border-b border-[#AAAAAA] cursor-pointer">
            <div className="px-6 py-4.5 transition-transform duration-150 active:shadow-inner">
              문의하기
            </div>
          </li>
        </ul>
      </main>
      <TabNav />
    </>
  );
};

export default SettingPage;
