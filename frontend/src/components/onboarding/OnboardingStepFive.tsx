import OnboardingLightIcon from './assets/OnboardingLightIcon.svg?react';

const examples = [
  {
    desc: '개인적인 질문',
    example: '어디 사세요?',
  },
  {
    desc: '자극적·논란 유발 질문',
    example: '정치적 견해는?',
  },
  {
    desc: '의미 없는 질문',
    example: '오늘 뭐 먹었어요?',
  },
  {
    desc: '예/아니오로만 답할 수 있는 질문',
    example: '행복하세요?',
  },
];

const OnboardingStepFive = () => {
  const handleOnboardingComplete = () => {
    localStorage.setItem('onboarding', 'complete');
    window.location.reload();
  };

  return (
    <main className="relative -m-6 flex h-svh w-svw max-w-[430px] flex-col items-center justify-center bg-white p-6">
      <OnboardingLightIcon className="mx-auto mb-4" />
      <h3 className="mb-3 text-center text-2xl text-[#1a1a1a]">이런 질문 작성은 피해주세요</h3>
      <div className="font-desc mb-16 flex flex-col gap-2 rounded-3xl bg-[#eeeeee] px-4 py-3 text-[#717171]">
        {examples.map((examples) => (
          <p key={examples.desc}>
            <span className="text-error">{examples.desc}</span>
            {`("${examples.example}")`}
          </p>
        ))}
      </div>
      <p className="font-desc text-center text-[#1a1a1a]">
        약속을 지키지 않는 글은 신고될 수 있으며 <br /> 서비스 이용에 제한이 있을 수 있습니다.
      </p>
      <div className="absolute right-0 bottom-4 left-0 px-6">
        <button
          onClick={handleOnboardingComplete}
          className="font-desc w-full cursor-pointer rounded-xl bg-[#1a1a1a] py-4 text-sm font-bold text-white"
        >
          확인했습니다
        </button>
      </div>
    </main>
  );
};

export default OnboardingStepFive;
