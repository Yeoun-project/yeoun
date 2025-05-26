import { useEffect, useRef } from 'react';

import ClickIcon from './assets/ClickIcon';

import AddQuestionFormPage from './assets/step-four/AddQuestionFormPage';
import AddQuestionAlertModal from './assets/step-four/AddQuestionAlertModal';

const OnBoardingStepFour = () => {
  const AddQuestionFormPageRef = useRef<SVGSVGElement>(null);
  const initialTextRef = useRef<SVGPathElement>(null);
  const generateTextRef = useRef<SVGPathElement>(null);
  const buttonRef = useRef<SVGRectElement>(null);
  const buttonTextRef = useRef<SVGPathElement>(null);
  const clickIconRef = useRef<SVGSVGElement>(null);
  const formBackgroundRef = useRef<SVGPathElement>(null);
  const formBorderRef = useRef<SVGPathElement>(null);
  const alertModalRef = useRef<SVGSVGElement>(null);

  useEffect(() => {
    if (
      !AddQuestionFormPageRef.current ||
      !initialTextRef.current ||
      !generateTextRef.current ||
      !buttonRef.current ||
      !buttonTextRef.current ||
      !formBackgroundRef.current ||
      !formBorderRef.current ||
      !alertModalRef.current
    )
      return;
    const timers: NodeJS.Timeout[] = [];
    timers.push(
      setTimeout(() => {
        AddQuestionFormPageRef.current!.style.transform = 'translateY(-117%)';
      }, 300),
      setTimeout(() => {
        initialTextRef.current!.style.opacity = '0';
      }, 1800),
      setTimeout(() => {
        generateTextRef.current!.style.opacity = '100';
      }, 2500),
      setTimeout(() => {
        AddQuestionFormPageRef.current!.style.transition = 'opacity';
        AddQuestionFormPageRef.current!.style.transitionDuration = '1000ms';
        AddQuestionFormPageRef.current!.style.opacity = '0';
      }, 4000),
      setTimeout(() => {
        AddQuestionFormPageRef.current!.style.transform = 'translateY(-160%)';
      }, 5000),
      setTimeout(() => {
        AddQuestionFormPageRef.current!.style.transform = 'translateY(-160%)';
      }, 5500),
      setTimeout(() => {
        AddQuestionFormPageRef.current!.style.opacity = '100';
        clickIconRef.current!.classList.remove('invisible');
        clickIconRef.current!.classList.add('animate-click-icon-poisition');
      }, 6000),
      setTimeout(() => {
        buttonRef.current!.style.fill = '#FC90D1';
        buttonTextRef.current!.style.fill = 'white';
      }, 7400),
      setTimeout(() => {
        clickIconRef.current!.classList.add('invisible');
        AddQuestionFormPageRef.current!.style.opacity = '0';
      }, 8000),
      setTimeout(() => {
        AddQuestionFormPageRef.current!.style.transform = 'translateY(-130%)';
      }, 9000),
      setTimeout(() => {
        AddQuestionFormPageRef.current!.style.opacity = '100';

        formBackgroundRef.current!.style.fill = 'rgba(255,32,32,0.5)';
        formBorderRef.current!.style.fill = 'rgba(255,32,32,0.6)';
        generateTextRef.current!.style.fill = '#FF2020';

        alertModalRef.current!.style.opacity = '100';
        alertModalRef.current!.style.transform = 'translateY(-20%)';
      }, 9800)
    );

    return () => {
      timers.forEach((timer) => clearTimeout(timer));
    };
  }, []);
  return (
    <>
      <div className="mb-4">
        <p className="mb-2 text-center text-2xl">여운을 지키는 약속</p>
        <p className="text-[#aaaaaa]">작고 단단한 규칙을 지켜주세요</p>
      </div>
      <div className="relative mb-5 h-[300px] w-[430px] overflow-hidden bg-white">
        <AddQuestionFormPage
          AddQuestionFormPageRef={AddQuestionFormPageRef}
          formBackgroundRef={formBackgroundRef}
          formBorderRef={formBorderRef}
          initialTextRef={initialTextRef}
          generateTextRef={generateTextRef}
          buttonRef={buttonRef}
          buttonTextRef={buttonTextRef}
          className="absolute left-1/2 -translate-x-1/2 translate-y-[100%] transition-all duration-1500 ease-in-out"
        />
        <ClickIcon ref={clickIconRef} className="invisible absolute bottom-[15%] left-[60%]" />
        <AddQuestionAlertModal
          className="absolute left-1/2 -translate-x-1/2 translate-y-20 opacity-0 transition-all duration-1000"
          alertModalRef={alertModalRef}
        />
      </div>
      <div className="mb-4">
        <p className="font-desc w-full text-center">
          '의미 있는 질문'만 작성할 수 있어요. <br />
          질문과 답변은 등록 후엔 <span className="text-error">수정·삭제할 수 없어요.</span>
        </p>
      </div>
      <p className="font-desc text-[#ffe867] underline">💡 이런 질문 작성은 피해주세요</p>
    </>
  );
};

export default OnBoardingStepFour;
