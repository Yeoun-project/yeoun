import Circle from '../circle/Circle';

const OnBoardingStepOne = () => {
  return (
    <>
      <div className="w-full text-center">
        <p className="mb-2 text-2xl">여운은 어떤 공간인가요?</p>
        <p className="text-[#aaaaaa]">질문 하나, 마음속에 남는 여운</p>
      </div>
      <div className="my-12 flex justify-center overflow-hidden p-2">
        <Circle size={260} animate>
          <p className="text-blur p-4 text-lg break-keep text-black">
            다시 태어난다면, 당신은 어떻게 살고싶나요?
          </p>
        </Circle>
      </div>
      <p className="font-desc w-full text-center">
        매일 당신에게 질문이 도착해요 <br />
        의미 있는 질문을 남기고 답변을 들어보세요
      </p>
    </>
  );
};

export default OnBoardingStepOne;
