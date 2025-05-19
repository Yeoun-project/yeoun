import React from 'react';

interface CarouselWrapperProps {
  children: React.ReactNode;
  handleTouchStart: (e: React.PointerEvent<HTMLDivElement>) => void;
  handleTouchMove: (e: React.PointerEvent<HTMLDivElement>) => void;
  handleTouchEnd: () => void;
  carouselRef: React.RefObject<HTMLDivElement | null>;
}

const Wrapper = ({
  children,
  carouselRef,
  handleTouchEnd,
  handleTouchMove,
  handleTouchStart,
}: CarouselWrapperProps) => {
  return (
    <div className="overflow-hidden">
      <div
        className="flex touch-pan-y py-3"
        onPointerDown={handleTouchStart}
        onPointerMove={handleTouchMove}
        onPointerUp={handleTouchEnd}
        onPointerLeave={handleTouchEnd}
        ref={carouselRef}
      >
        {children}
      </div>
    </div>
  );
};

export default Wrapper;
