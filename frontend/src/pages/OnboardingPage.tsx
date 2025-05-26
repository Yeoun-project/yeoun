import { useState } from 'react';
import BasicButton from '../components/button/BasicButton';
import OnBoardingStepOne from '../components/onboarding/OnBoardingStepOne';
import OnBoardingStepTwo from '../components/onboarding/OnBoardingStepTwo';
import OnBoardingStepThree from '../components/onboarding/OnBoardingStepThree';
import OnBoardingStepFour from '../components/onboarding/OnBoardingStepFour';
import OnboardingStepFive from '../components/onboarding/OnboardingStepFive';

const OnboardingPage = () => {
  const [onBoardingStep, setOnBoardingStep] = useState(0);

  const ProcessBarStyle = () => {
    let processBar =
      'after:transition-all after:absolute after:top-1/2 after:left-0 after:z-10 after:h-[1.5px] after:-translate-y-1/2 after:rounded-full after:bg-white ';
    switch (onBoardingStep) {
      case 0:
        return (processBar += 'after:w-1/4');
      case 1:
        return (processBar += 'after:w-2/4');
      case 2:
        return (processBar += 'after:w-3/4');
      case 3:
        return (processBar += 'after:w-4/4');
    }
  };
  const onBoardingPage = () => {
    switch (onBoardingStep) {
      case 0:
        return <OnBoardingStepOne />;
      case 1:
        return <OnBoardingStepTwo />;
      case 2:
        return <OnBoardingStepThree />;
      case 3:
        return <OnBoardingStepFour />;
    }
  };

  const handleNext = () => {
    setOnBoardingStep((prev) => prev + 1);
  };

  const handlePrev = () => {
    if (onBoardingStep === 0) return;
    setOnBoardingStep((prev) => prev - 1);
  };

  const handleOnboardingSkip = () => {
    localStorage.setItem('onboarding', 'complete');
    window.location.reload();
  };
  return (
    <div className="flex h-[100svh] flex-col items-center justify-between overflow-hidden px-6 py-6">
      {onBoardingStep === 4 ? (
        <OnboardingStepFive />
      ) : (
        <>
          <header className="w-full">
            <div
              className={`mb-4 flex ${onBoardingStep !== 0 ? 'justify-between' : 'justify-end'}`}
            >
              {onBoardingStep !== 0 && (
                <button className="flex cursor-pointer gap-1" onClick={handlePrev}>
                  <img src={'/icons/left.svg'} />
                  <span>이전으로</span>
                </button>
              )}
              <button onClick={handleOnboardingSkip} className="cursor-pointer text-[#aaaaaa]">
                건너뛰기
              </button>
            </div>
            <div className={`relative h-[0.5px] w-full bg-[#aaaaaa] ${ProcessBarStyle()}`} />
          </header>
          <main className="flex flex-col items-center justify-center py-6">{onBoardingPage()}</main>
          <BasicButton onClick={handleNext}>다음</BasicButton>
        </>
      )}
    </div>
  );
};

export default OnboardingPage;
