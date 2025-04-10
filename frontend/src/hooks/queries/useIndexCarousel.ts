import { useCallback, useRef, useState } from 'react';

const useIndexCarousel = (MIN_MOVE: number, carouselLimit: number) => {
  const carouselRef = useRef<HTMLDivElement>(null);
  const isDraggingRef = useRef<boolean>(false);
  const getCarouselItemWidth = () => {
    if (carouselRef.current) {
      return carouselRef.current.clientWidth;
    }
    return 0;
  };

  const [currentCarousel, setCurrentCarousel] = useState(0);
  const [touchStartX, setTouchStartX] = useState(0);
  const [transX, setTransX] = useState(0);
  const [touchStart, setTouchStart] = useState(false);
  const handleTouchStart = useCallback((e: React.PointerEvent<HTMLDivElement>) => {
    isDraggingRef.current = false;
    setTouchStart(true);
    setTouchStartX(e.clientX);
  }, []);

  const handleTouchMove = useCallback(
    (e: React.PointerEvent<HTMLDivElement>) => {
      if (touchStart) {
        setTransX(e.clientX - touchStartX);

        if (!isDraggingRef.current && Math.abs(e.clientX - touchStartX) > 5) {
          isDraggingRef.current = true;
        }
      } else {
        return;
      }
    },
    [touchStart, touchStartX]
  );

  const handleTouchEnd = useCallback(() => {
    if (transX < -MIN_MOVE) {
      //next
      setCurrentCarousel((prev) => (prev < carouselLimit ? prev + 1 : prev));
    }
    if (transX > MIN_MOVE) {
      //prev
      setCurrentCarousel((prev) => (prev > 0 ? prev - 1 : prev));
    }
    setTransX(0);
    setTouchStartX(0);
    setTouchStart(false);
  }, [MIN_MOVE, carouselLimit, transX]);

  const getTranslate = () => {
    return `translateX(${-currentCarousel * getCarouselItemWidth() + transX}px)`;
  };

  const getScale = () => {
    return `scale(1)`;
  };

  return {
    carouselRef,
    handleTouchStart,
    handleTouchMove,
    handleTouchEnd,
    getTranslate,
    getScale,
    setCurrentCarousel,
    currentCarousel,
    isDraggingRef,
  };
};

export default useIndexCarousel;
