import { useState } from 'react';

import EmptyRating from '../../assets/Icons/rating/emptyRating.svg?react';
import HalfRating from '../../assets/Icons/rating/halfRating.svg?react';
import FillRating from '../../assets/Icons/rating/fillRating.svg?react';

interface RatingProps {
  maxRatingValue?: number;
  currentRating: number;
  setCurrentRating: (rating: number) => void;
}

const getStar = (index: number, ratingValue: number) => {
  const fullStars = Math.floor(ratingValue);
  const hasHalfStar = ratingValue % 1 !== 0 && index === fullStars;

  if (index < fullStars) {
    return <FillRating width={48} height={48} />;
  }
  if (hasHalfStar) {
    return <HalfRating width={48} height={48} />;
  }
  return <EmptyRating width={48} height={48} />;
};

const Rating = ({ maxRatingValue = 5, setCurrentRating, currentRating }: RatingProps) => {
  const [hoverRating, setHoverRating] = useState<number | null>(null);

  const handleMouseMove = (index: number, event: React.MouseEvent<HTMLSpanElement>) => {
    const starElement = event.currentTarget;
    const rect = starElement.getBoundingClientRect();
    const mouseX = event.clientX - rect.left;
    const starWidth = rect.width;

    if (mouseX <= starWidth / 2) {
      setHoverRating(index + 0.5);
    } else {
      setHoverRating(index + 1);
    }
  };

  const handleMouseLeave = () => {
    setHoverRating(null);
  };

  const handleClick = (index: number) => {
    const ratingToSet = hoverRating !== null ? hoverRating : index + 0.5;
    setCurrentRating(ratingToSet);
  };

  return (
    <div className="inline-flex items-center" onMouseLeave={handleMouseLeave}>
      {[...Array(maxRatingValue)].map((_, index) => {
        const ratingValue = hoverRating !== null ? hoverRating : currentRating;
        return (
          <span
            className="cursor-pointer"
            key={index}
            onMouseMove={(e) => handleMouseMove(index, e)}
            onClick={() => handleClick(index)}
          >
            {getStar(index, ratingValue)}
          </span>
        );
      })}
    </div>
  );
};

export default Rating;
