import { useEffect, useRef, useState } from 'react';

import ClickIcon from './assets/ClickIcon';

import TodayQuestionPage from './assets/step-two/TodayQuestionPage.svg?react';
import TodayAnswersList from './assets/step-two/TodayAnswersList.svg?react';
import AddTodayAnswer from './assets/step-two/AddTodayAnswer';

const OnBoardingStepTwo = () => {
  const animateWrapperRef = useRef<HTMLDivElement>(null);

  const clickIconRef = useRef<SVGSVGElement>(null);
  const secondClickIconRef = useRef<SVGSVGElement>(null);
  const todayQuestionRef = useRef<HTMLDivElement>(null);

  const addAnswerRef = useRef<HTMLDivElement>(null);
  const answerListRef = useRef<HTMLDivElement>(null);
  const ButtonRef = useRef<SVGRectElement>(null);
  const TextRef = useRef<SVGPathElement>(null);

  const [secondAnimate, setSecondAnimate] = useState(false);

  useEffect(() => {
    if (!clickIconRef.current || !animateWrapperRef.current) return;

    const timers: NodeJS.Timeout[] = [];

    timers.push(
      setTimeout(() => {
        clickIconRef.current!.classList.add('animate-click-icon-poisition');
        clickIconRef.current!.classList.remove('invisible');
      }, 2000)
    );

    timers.push(
      setTimeout(() => {
        animateWrapperRef.current!.style.transform = 'translateX(100%)';
      }, 4500)
    );

    timers.push(
      setTimeout(() => {
        setSecondAnimate(true);
      }, 6000)
    );

    return () => {
      timers.forEach((timerId) => clearTimeout(timerId));
    };
  }, [clickIconRef]);

  useEffect(() => {
    if (
      !secondClickIconRef.current ||
      !ButtonRef.current ||
      !TextRef.current ||
      !animateWrapperRef.current
    )
      return;

    const timers: NodeJS.Timeout[] = [];
    if (secondAnimate) {
      timers.push(
        setTimeout(() => {
          secondClickIconRef.current!.classList.add('animate-click-icon-poisition');
          secondClickIconRef.current!.classList.remove('invisible');
        }, 1500)
      );

      timers.push(
        setTimeout(() => {
          ButtonRef.current!.style.fill = '#FC90D1';
          TextRef.current!.style.fill = 'white';
        }, 2800)
      );

      timers.push(
        setTimeout(() => {
          animateWrapperRef.current!.style.transform = 'translateX(200%)';
        }, 3500)
      );

      timers.push(
        setTimeout(() => {
          answerListRef.current!.style.transform = 'translateX(100%)';
        }, 5000)
      );
    }

    return () => {
      timers.forEach((timerId) => clearTimeout(timerId));
    };
  }, [secondAnimate]);

  return (
    <>
      <div className="mb-4 max-w-[430px] overflow-clip">
        <p className="mb-2 text-2xl">여운은 이렇게 사용해요!</p>
        <p className="text-[#aaaaaa]">로그인 없이도 여운을 남길 수 있어요</p>
      </div>
      <div className="relative mb-5 h-[300px] w-[430px] overflow-hidden bg-white">
        <div
          className="flex h-full w-full -translate-x-[200%] flex-nowrap text-center transition-transform duration-1300 ease-in-out will-change-transform"
          ref={animateWrapperRef}
        >
          {/* 세번째 애니메이션 */}
          <div
            className="relative h-full w-full shrink-0 -translate-x-full transition-transform duration-1300 ease-in-out"
            ref={answerListRef}
          >
            <TodayAnswersList className="absolute left-1/2 mx-auto block w-[270px] -translate-x-1/2 translate-y-[10%] pb-7" />
          </div>

          {/* 두번째 애니메이션 */}
          <div ref={addAnswerRef} className="relative h-full w-full shrink-0">
            <AddTodayAnswer
              buttonRef={ButtonRef}
              textRef={TextRef}
              className={`absolute top-1/5 left-1/2 mx-auto block w-[270px] -translate-x-1/2 pb-7 transition-transform duration-1300 ease-in-out ${secondAnimate ? '-translate-y-[70%]' : ''}`}
            />
            <ClickIcon
              ref={secondClickIconRef}
              className="invisible absolute translate-x-65 translate-y-38"
            />
          </div>
          {/* 첫번째 애니메이션 */}
          <div ref={todayQuestionRef} className="relative h-full w-full shrink-0">
            <TodayQuestionPage className="animate-onboarding-position absolute -bottom-55 left-1/2 mx-auto block w-[270px] -translate-x-1/2 pb-7" />
            <ClickIcon
              ref={clickIconRef}
              className="invisible absolute translate-x-58 translate-y-43"
            />
          </div>
        </div>
      </div>
      <div>
        <p className="font-desc w-full text-center">
          오늘의 질문에 답하며 <br /> 당신만의 기록을 쌓을 수 있어요.
        </p>
      </div>
    </>
  );
};

export default OnBoardingStepTwo;
