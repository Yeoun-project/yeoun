import BackArrow from "../components/common/BackArrow";
import TabNav from "../components/TabNav";
const SettingPage = () => {
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
