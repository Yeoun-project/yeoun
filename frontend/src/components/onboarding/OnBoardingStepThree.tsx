/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable prefer-const */

import { useEffect, useRef } from 'react';

import ClickIcon from './assets/ClickIcon';

import AddQuestionPage from './assets/step-three/AddQuestionPage.png';
import AddQuestionpageModal from './assets/step-three/AddQuestionPageModal.svg?react';

import CommentPageModal from './assets/step-three/CommentPageModal.svg?react';
import CommentPage from './assets/step-three/CommentPage';

const OnBoardingStepThree = () => {
  const animateWrapperRef = useRef<HTMLDivElement>(null);

  // clickIcon
  const clickIconRef = useRef<SVGSVGElement>(null);
  const secondClickIconRef = useRef<SVGSVGElement>(null);

  // addQuestion
  const addQuestionpageRef = useRef<HTMLDivElement>(null);
  const addQuestionPageImageRef = useRef<HTMLImageElement>(null);
  const addQuestionModalRef = useRef<HTMLDivElement>(null);

  // commentPage
  const commentPageWrapRef = useRef<HTMLDivElement>(null);
  const commentPageRef = useRef<SVGSVGElement>(null);
  const buttonRef = useRef<SVGRectElement>(null);
  const textRef = useRef<SVGPathElement>(null);
  const commentPageModalRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (
      !addQuestionModalRef.current ||
      !addQuestionPageImageRef.current ||
      !clickIconRef.current ||
      !secondClickIconRef.current ||
      !buttonRef.current ||
      !textRef.current ||
      !commentPageRef.current ||
      !commentPageWrapRef.current ||
      !animateWrapperRef.current ||
      !commentPageModalRef.current ||
      !addQuestionpageRef.current
    )
      return;

    const timers: NodeJS.Timeout[] = [];

    timers.push(
      setTimeout(
        () => (addQuestionPageImageRef.current!.style.transform = 'translateY(-35%)'),
        300
      ),
      setTimeout(() => {
        clickIconRef.current!.classList.add('animate-click-icon-poisition');
        clickIconRef.current!.classList.remove('invisible');
      }, 2000),
      setTimeout(() => {
        clickIconRef.current!.style.opacity = '0';
        addQuestionModalRef.current!.style.opacity = '100';
      }, 4500),
      setTimeout(() => {
        animateWrapperRef.current!.style.transform = 'translateX(100%)';
      }, 6000),
      setTimeout(() => {
        commentPageRef.current!.style.transform = 'translateY(-80%)';
      }, 7500),
      setTimeout(() => {
        secondClickIconRef.current!.classList.add('animate-click-icon-poisition');
        secondClickIconRef.current!.classList.remove('invisible');
      }, 8500),
      setTimeout(() => {
        buttonRef.current!.style.fill = '#FC90D1';
        textRef.current!.style.fill = 'white';
      }, 10000),
      setTimeout(() => {
        secondClickIconRef.current!.classList.add('invisible');
        commentPageRef.current!.style.transform = 'translateY(-27%)';
      }, 11000),
      setTimeout(() => {
        commentPageModalRef.current!.style.opacity = '100';
      }, 12500)
    );

    return () => {
      timers.forEach((timer) => clearTimeout(timer));
    };
  }, []);

  return (
    <>
      <div className="mb-8">
        <p className="mb-2 text-center text-2xl">여운은 이렇게 사용해요!</p>
        <p className="text-gradient-linear">로그인하면 더 넓은 여운을 나눌 수 있어요!</p>
      </div>
      <div className="relative mb-5 h-[300px] w-[430px] overflow-hidden bg-white">
        <div
          className="flex h-full w-full -translate-x-[100%] flex-nowrap text-center transition-transform duration-1300 ease-in-out"
          ref={animateWrapperRef}
        >
          <div ref={commentPageWrapRef} className="relative h-full w-full shrink-0">
            <CommentPage
              commentPageRef={commentPageRef}
              buttonRef={buttonRef}
              textRef={textRef}
              className="absolute left-1/2 -translate-x-1/2 translate-y-[15%] transition-transform duration-1500 ease-in-out"
            />
            <ClickIcon
              ref={secondClickIconRef}
              className="invisible absolute translate-x-65 translate-y-50 transition-opacity duration-1000"
            />

            <div
              ref={commentPageModalRef}
              className="absolute top-0 left-1/2 h-full w-[270px] -translate-x-1/2 bg-black/20 opacity-0 transition-opacity duration-1000"
            >
              <CommentPageModal className="absolute top-1/2 left-1/2 -translate-1/2" />
            </div>
          </div>

          <div className="relative h-full w-full shrink-0" ref={addQuestionpageRef}>
            <img
              src={AddQuestionPage}
              ref={addQuestionPageImageRef}
              className="absolute left-1/2 block -translate-x-1/2 translate-y-[15%] transition-transform duration-1500 ease-in-out"
            />
            <ClickIcon
              ref={clickIconRef}
              className="invisible absolute translate-x-55 translate-y-39 transition-opacity duration-1000"
            />

            <div
              ref={addQuestionModalRef}
              className="absolute top-0 left-1/2 h-full w-[270px] -translate-x-1/2 bg-black/20 opacity-0 transition-opacity duration-1000"
            >
              <AddQuestionpageModal className="absolute top-1/2 left-1/2 -translate-1/2" />
            </div>
          </div>
        </div>
      </div>

      <div>
        <p className="font-desc w-full text-center">
          하루에 하나, 질문을 남길 수 있어요 <br />
          하나의 질문엔 단 한 번만 답할 수 있어요 <br />
        </p>
      </div>
    </>
  );
};

export default OnBoardingStepThree;
