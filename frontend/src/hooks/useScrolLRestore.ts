import { useEffect, useRef, useState } from 'react';

export const useScrollRestore = () => {
  const scrollRef = useRef<HTMLDivElement>(null);
  const currentScroll = sessionStorage.getItem('scroll');

  const [scroll, setScroll] = useState(currentScroll);

  const handleScroll = (e: React.UIEvent<HTMLDivElement, UIEvent>) => {
    setScroll(e.currentTarget.scrollTop.toString());
  };

  useEffect(() => {
    const scrollItem = scrollRef.current;

    const timers = setTimeout(() => {
      if (scrollItem) {
        sessionStorage.setItem('scroll', scroll!.toString());
      }
    }, 100);
    return () => {
      clearTimeout(timers);
    };
  }, [scroll]);

  useEffect(() => {
    const scrollItem = scrollRef.current;

    if (scrollItem) {
      scrollItem.scrollTop = Number(sessionStorage.getItem('scroll'));

      // 스크롤이 이동 된 후에 세션 스토리지에 저장되어있는 scroll 값 삭제
      setTimeout(() => {
        sessionStorage.removeItem('scroll');
      }, 200);
    }
  }, []);

  return {
    scrollRef,
    handleScroll,
  };
};
