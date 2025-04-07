import BackArrow from "../components/common/BackArrow";
import TabNav from "../components/TabNav";
const SettingPage = () => {
  return (
    <>
      <header className="-z-1 flex flex-col items-center justify-between p-6">
          <div className="w-full flex items-center justify-center relative">
            {/* Header */}
            <div className="absolute left-0">
              <BackArrow />
            </div>
            <div className="text-center">
              <p>설정</p>
            </div>
          </div>
      </header>
      <main className="-z-1 flex min-h-[90svh] flex-col items-center justify-between pb-[70px]">
        <ul className="w-full font-desc">
          <li className="px-6 py-4.5 border-b border-gray-400">로그아웃</li>
          <li className="px-6 py-4.5 border-b border-gray-400">알림</li>
          <li className="px-6 py-4.5 border-b border-gray-400">버전정보</li>
          <li className="px-6 py-4.5 border-b border-gray-400">문의하기</li>
        </ul>
      </main>
      <TabNav />
    </>
  );
};

export default SettingPage;
