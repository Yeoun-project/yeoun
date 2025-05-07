import { useCallback, useEffect, useRef, useState } from 'react';

const useIndexCarousel = (MIN_MOVE: number, carouselLimit: number) => {
  const carouselRef = useRef<HTMLDivElement>(null);
  const isDraggingRef = useRef<boolean>(false);

  const touchStartRef = useRef<number>(0);
  const currentTransXRef = useRef<number>(0);
  const animationFrameIdRef = useRef<number | null>(null);

  const [currentCarousel, setCurrentCarousel] = useState(0);

  const getCarouselItemWidth = useCallback(() => {
    if (carouselRef.current) {
      return carouselRef.current.clientWidth;
    }
    return 0;
  }, []);

  const applyTransform = useCallback(() => {
    if (carouselRef.current) {
      const itemWidth = getCarouselItemWidth();
      const newTransform = -currentCarousel * itemWidth + currentTransXRef.current;
      carouselRef.current.style.transform = `translateX(${newTransform}px)`;
    }
  }, [currentCarousel, getCarouselItemWidth]);

  useEffect(() => {
    currentTransXRef.current = 0;
    if (carouselRef.current) {
      carouselRef.current.style.transition = 'transform 0.3s ease-out';
    }
    applyTransform();
  }, [currentCarousel, applyTransform]);

  const handleTouchStart = useCallback((e: React.PointerEvent<HTMLDivElement>) => {
    isDraggingRef.current = false;
    touchStartRef.current = e.clientX;
    currentTransXRef.current = 0;

    if (carouselRef.current) {
      carouselRef.current.style.transition = 'none';
    }

    if (animationFrameIdRef.current) {
      cancelAnimationFrame(animationFrameIdRef.current);
    }
  }, []);

  const handleTouchMove = useCallback(
    (e: React.PointerEvent<HTMLDivElement>) => {
      if (touchStartRef.current === 0 && !isDraggingRef.current) {
        return;
      }

      const deltaX = e.clientX - touchStartRef.current;
      currentTransXRef.current = deltaX;

      if (!isDraggingRef.current && Math.abs(deltaX) > 5) {
        isDraggingRef.current = true;
      }

      if (animationFrameIdRef.current) {
        cancelAnimationFrame(animationFrameIdRef.current);
      }

      animationFrameIdRef.current = requestAnimationFrame(applyTransform);
    },
    [applyTransform]
  );
  const handleTouchEnd = useCallback(() => {
    if (animationFrameIdRef.current) {
      cancelAnimationFrame(animationFrameIdRef.current);
      animationFrameIdRef.current = null;
    }

    if (carouselRef.current) {
      carouselRef.current.style.transition = 'transform 0.3s ease-out';
    }

    const movedDistance = currentTransXRef.current;
    let newCurrentCarousel = currentCarousel;

    if (movedDistance < -MIN_MOVE) {
      newCurrentCarousel = Math.min(currentCarousel + 1, carouselLimit);
    } else if (movedDistance > MIN_MOVE) {
      newCurrentCarousel = Math.max(currentCarousel - 1, 0);
    }

    touchStartRef.current = 0;

    if (newCurrentCarousel !== currentCarousel) {
      setCurrentCarousel(newCurrentCarousel);
    } else {
      currentTransXRef.current = 0;
      applyTransform();
    }
    isDraggingRef.current = false;
  }, [MIN_MOVE, carouselLimit, currentCarousel, applyTransform]);

  const getTranslateValue = useCallback(() => {
    return -currentCarousel * getCarouselItemWidth();
  }, [currentCarousel, getCarouselItemWidth]);

  const getScale = useCallback((): string => {
    return `scale(1)`;
  }, []);

  return {
    carouselRef,
    handleTouchStart,
    handleTouchMove,
    handleTouchEnd,
    getTranslateValue,
    getScale,
    setCurrentCarousel,
    currentCarousel,
    isDraggingRef,
  };
};

export default useIndexCarousel;
