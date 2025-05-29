import React, { useState, useRef, useCallback } from 'react';
import EmptyRatingIcon from '../../assets/Icons/rating/emptyRating.svg?react';
import HalfRatingIcon from '../../assets/Icons/rating/halfRating.svg?react';
import FillRatingIcon from '../../assets/Icons/rating/fillRating.svg?react';
import { useIsMobile } from '../../hooks/useIsMobile';

interface RatingProps {
  maxRatingValue?: number;
  currentRating: number;
  setCurrentRating: (rating: number) => void;
  starSize?: number;
  readOnly?: boolean;
  handleSubmit?: () => void;
}

const StarDisplay = React.memo(
  ({ type, size }: { type: 'fill' | 'half' | 'empty'; size: number }) => {
    if (type === 'fill') return <FillRatingIcon width={size} height={size} />;
    if (type === 'half') return <HalfRatingIcon width={size} height={size} />;
    return <EmptyRatingIcon width={size} height={size} />;
  }
);

const Rating = ({
  maxRatingValue = 5,
  currentRating,
  setCurrentRating,
  starSize = 48,
  readOnly = false,
  handleSubmit = () => {},
}: RatingProps) => {
  const [hoverRating, setHoverRating] = useState<number | null>(null);
  const [isDragging, setIsDragging] = useState(false);
  const ratingContainerRef = useRef<HTMLDivElement>(null);
  const isMobile = useIsMobile();

  const getRatingToShow = readOnly
    ? currentRating
    : hoverRating !== null
      ? hoverRating
      : currentRating;

  const calculateRatingFromX = useCallback(
    (clientX: number): number => {
      if (!ratingContainerRef.current) return 0;

      const rect = ratingContainerRef.current.getBoundingClientRect();

      const x = clientX - rect.left;

      let newRating = (x / rect.width) * maxRatingValue;

      newRating = Math.round(newRating * 2) / 2;

      newRating = Math.max(0, Math.min(newRating, maxRatingValue));

      return newRating;
    },
    [maxRatingValue]
  );

  const handlePointerDown = (event: React.PointerEvent<HTMLDivElement>) => {
    if (readOnly) return;

    if (isMobile) {
      setIsDragging(true);

      event.currentTarget.setPointerCapture(event.pointerId);

      const newRating = calculateRatingFromX(event.clientX);
      setHoverRating(newRating);
      setCurrentRating(newRating);
    }
  };

  const handlePointerMove = (event: React.PointerEvent<HTMLDivElement>) => {
    if (readOnly) return;

    if (isMobile) {
      if (isDragging) {
        const newRating = calculateRatingFromX(event.clientX);
        setHoverRating(newRating);
        setCurrentRating(newRating);
      }
    } else {
      const newRating = calculateRatingFromX(event.clientX);
      setHoverRating(newRating);
    }
  };

  const handlePointerUp = (event: React.PointerEvent<HTMLDivElement>) => {
    if (readOnly) return;

    if (isMobile && isDragging) {
      event.currentTarget.releasePointerCapture(event.pointerId);
      setIsDragging(false);
    }

    if (isMobile) {
      handleSubmit();
    }
  };

  const handleClick = (event: React.MouseEvent<HTMLDivElement>) => {
    if (readOnly || isMobile) return;

    const ratingToSet = hoverRating !== null ? hoverRating : calculateRatingFromX(event.clientX);
    setCurrentRating(ratingToSet);
  };

  const handleMouseLeave = () => {
    if (readOnly || isMobile || isDragging) return;
    setHoverRating(null);
  };

  const starsToRender = [];
  for (let i = 0; i < maxRatingValue; i++) {
    const starPoint = i + 1;
    let starType: 'fill' | 'half' | 'empty' = 'empty';

    if (getRatingToShow >= starPoint) {
      starType = 'fill';
    } else if (getRatingToShow >= starPoint - 0.5) {
      starType = 'half';
    }
    starsToRender.push(<StarDisplay key={i} type={starType} size={starSize} />);
  }

  return (
    <div
      ref={ratingContainerRef}
      className={`inline-flex max-w-[240px] cursor-pointer items-center`}
      style={{ touchAction: isMobile && !readOnly ? 'none' : 'auto' }}
      onPointerDown={handlePointerDown}
      onPointerMove={handlePointerMove}
      onPointerUp={handlePointerUp}
      onClick={handleClick}
      onMouseLeave={handleMouseLeave}
      role="slider"
      aria-valuenow={currentRating}
      aria-valuemin={0}
      aria-valuemax={maxRatingValue}
      aria-readonly={readOnly}
      aria-label={`Rating: ${currentRating} out of ${maxRatingValue} stars`}
      tabIndex={readOnly ? -1 : 0}
    >
      {starsToRender}
    </div>
  );
};

export default Rating;
