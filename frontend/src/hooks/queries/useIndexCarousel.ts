import { useCallback, useEffect, useRef, useState } from 'react';

const useIndexCarousel = (MIN_MOVE: number, carouselLimit: number) => {
  const carouselRef = useRef<HTMLDivElement>(null);
  const isDraggingRef = useRef<boolean>(false);

  const touchStartClientXRef = useRef<number>(0);
  const dragDeltaXRef = useRef<number>(0);

  const animationFrameIdRef = useRef<number | null>(null);
  const [currentCarousel, setCurrentCarousel] = useState(0);
  const itemWidthRef = useRef<number>(0);

  const updateItemWidth = useCallback(() => {
    if (carouselRef.current) {
      itemWidthRef.current = carouselRef.current.clientWidth;
    }
  }, []);

  useEffect(() => {
    updateItemWidth();
    if (carouselRef.current && itemWidthRef.current > 0) {
      const initialTransform = -currentCarousel * itemWidthRef.current;
      carouselRef.current.style.transition = 'none';
      carouselRef.current.style.transform = `translateX(${initialTransform}px)`;

      carouselRef.current.style.transition = 'transform 0.3s ease-out';
    }

    window.addEventListener('resize', updateItemWidth);
    return () => {
      window.removeEventListener('resize', updateItemWidth);
      if (animationFrameIdRef.current) {
        cancelAnimationFrame(animationFrameIdRef.current);
      }
    };
  }, [updateItemWidth, currentCarousel]);

  const applyDragTransform = useCallback(() => {
    if (carouselRef.current && itemWidthRef.current > 0) {
      const baseTransform = -currentCarousel * itemWidthRef.current;
      const currentActualTransform = baseTransform + dragDeltaXRef.current;
      carouselRef.current.style.transform = `translateX(${currentActualTransform}px)`;
    }
  }, [currentCarousel]);

  useEffect(() => {
    if (carouselRef.current && itemWidthRef.current > 0) {
      carouselRef.current.style.transition = 'transform 0.3s ease-out';
      const targetTransform = -currentCarousel * itemWidthRef.current;
      carouselRef.current.style.transform = `translateX(${targetTransform}px)`;
    }

    dragDeltaXRef.current = 0;
  }, [currentCarousel]);

  const handleTouchStart = useCallback((e: React.PointerEvent<HTMLDivElement>) => {
    touchStartClientXRef.current = e.clientX;
    dragDeltaXRef.current = 0;

    if (carouselRef.current) {
      carouselRef.current.style.transition = 'none';
    }

    if (animationFrameIdRef.current) {
      cancelAnimationFrame(animationFrameIdRef.current);
      animationFrameIdRef.current = null;
    }
  }, []);

  const handleTouchMove = useCallback(
    (e: React.PointerEvent<HTMLDivElement>) => {
      if (touchStartClientXRef.current === 0 && e.buttons !== 1) {
        return;
      }

      const currentX = e.clientX;
      const deltaX = currentX - touchStartClientXRef.current;

      if (!isDraggingRef.current && Math.abs(deltaX) > 5) {
        isDraggingRef.current = true;
      }

      if (isDraggingRef.current) {
        dragDeltaXRef.current = deltaX;
        if (animationFrameIdRef.current) {
          cancelAnimationFrame(animationFrameIdRef.current);
        }
        animationFrameIdRef.current = requestAnimationFrame(applyDragTransform);
      }
    },
    [applyDragTransform]
  );

  const handleTouchEnd = useCallback(() => {
    if (animationFrameIdRef.current) {
      cancelAnimationFrame(animationFrameIdRef.current);
      animationFrameIdRef.current = null;
    }

    let newCurrentCarousel = currentCarousel;
    const dragged = isDraggingRef.current;

    if (dragged) {
      const movedDistance = dragDeltaXRef.current;
      if (movedDistance < -MIN_MOVE) {
        newCurrentCarousel = Math.min(currentCarousel + 1, carouselLimit);
      } else if (movedDistance > MIN_MOVE) {
        newCurrentCarousel = Math.max(currentCarousel - 1, 0);
      }
    }

    touchStartClientXRef.current = 0;

    if (carouselRef.current) {
      carouselRef.current.style.transition = 'transform 0.3s ease-out';
    }

    if (newCurrentCarousel !== currentCarousel) {
      setCurrentCarousel(newCurrentCarousel);
    } else if (dragged) {
      dragDeltaXRef.current = 0;
      if (carouselRef.current && itemWidthRef.current > 0) {
        const targetTransform = -currentCarousel * itemWidthRef.current;
        carouselRef.current.style.transform = `translateX(${targetTransform}px)`;
      }
    }

    if (dragged) {
      setTimeout(() => {
        isDraggingRef.current = false;
      }, 0);
    }
  }, [MIN_MOVE, carouselLimit, currentCarousel]);

  const getTranslateValue = useCallback(() => {
    if (itemWidthRef.current === 0) updateItemWidth();
    return -currentCarousel * itemWidthRef.current;
  }, [currentCarousel, updateItemWidth]);

  return {
    carouselRef,
    handleTouchStart,
    handleTouchMove,
    handleTouchEnd,
    getTranslateValue,
    setCurrentCarousel,
    currentCarousel,
    isDraggingRef,
  };
};

export default useIndexCarousel;
