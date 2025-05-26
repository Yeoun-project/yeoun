import { useCallback, useEffect, useRef, useState } from 'react';
import { NavigationType, useLocation, useNavigationType } from 'react-router-dom';

export const useScrollRestore = () => {
  const location = useLocation();
  const navigationType = useNavigationType();

  const scrollRef = useRef<HTMLDivElement>(null);
  const currentScroll = sessionStorage.getItem('scroll');

  // 페이지 별 고유 키
  const sesstionKey = `scroll_${location.pathname}`;

  const [scroll, setScroll] = useState(currentScroll);

  const handleScroll = useCallback((e: React.UIEvent<HTMLDivElement, UIEvent>) => {
    setScroll(e.currentTarget.scrollTop.toString());
  }, []);

  useEffect(() => {
    const scrollItem = scrollRef.current;

    const timers = setTimeout(() => {
      if (scrollItem && scroll) {
        sessionStorage.setItem(sesstionKey, scroll.toString());
      }
    }, 100);
    return () => {
      clearTimeout(timers);
    };
  }, [scroll, sesstionKey]);

  useEffect(() => {
    const scrollItem = scrollRef.current;

    if (!scrollItem) return;

    if (navigationType === NavigationType.Pop) {
      const savedScroll = sessionStorage.getItem(sesstionKey);
      scrollItem.scrollTop = Number(savedScroll);
    } else {
      sessionStorage.removeItem(sesstionKey);
      scrollItem.scrollTop = 0;
    }

    return () => {};
  }, [navigationType, sesstionKey]);

  return {
    scrollRef,
    handleScroll,
  };
};
