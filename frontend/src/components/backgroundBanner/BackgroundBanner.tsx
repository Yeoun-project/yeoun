import ReviewForm from './ReviewForm';
import Logo from '/logo.svg';

const BackgroundBanner = () => {
  return (
    <div className="fixed top-0 left-0 hidden h-full w-[100vw] overflow-y-scroll bg-[url(/gradient-background.svg)] bg-cover bg-center bg-no-repeat xl:block">
      <div className="absolute left-[5%] z-50 p-20">
        <img src={Logo} alt="여운" className="mb-4" />
        <p className="mb-2 text-[32px]">
          안녕하세요 :) <br />
          <span className="relative after:absolute after:bottom-0 after:left-0 after:-z-10 after:h-[15px] after:w-full after:bg-[#FC90D1]/45">
            자기성찰 Q&A 서비스, 여운
          </span>
          입니다.
        </p>
        <p className="font-desc mb-4 text-xl text-[#717171]">
          웹앱 형태로 제공되며, 모바일 기기에서의 사용을 권장 드립니다.
        </p>

        <ReviewForm />
      </div>
    </div>
  );
};

export default BackgroundBanner;
