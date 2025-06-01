import { useState } from 'react';
import Rating from './Rating';
import writeUtReview from '../../services/api/writeUtReview';

const ReviewForm = () => {
  const [currentRating, setCurrentRating] = useState<number>(0);
  const [review, setReview] = useState<string>('');
  const [submitComplete, setSubmitComplete] = useState(false);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await writeUtReview({ starRate: currentRating, message: review });
      setSubmitComplete(true);
      setCurrentRating(0);
      setReview('소중한 의견을 남겨주셔서 감사합니다!');
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <form className="flex w-full flex-col gap-4" onSubmit={handleSubmit}>
      <Rating
        currentRating={currentRating}
        setCurrentRating={(rating) => {
          setCurrentRating(rating);
        }}
      />

      <div>
        <textarea
          name="review"
          id="review"
          className={`font-desc mb-2 h-[180px] w-full resize-none rounded-2xl border border-[#717171]/30 bg-white px-8 py-6 transition-colors outline-none ${submitComplete ? 'text-[#EC69B8]' : 'text-[#1a1a1a]'}`}
          placeholder="서비스를 사용하면서 불편했던 점이나 개선이 필요한 부분을 작성해주세요"
          value={review}
          onChange={(e) => {
            setReview(e.target.value);
          }}
          onFocus={() => {
            if (submitComplete) {
              setSubmitComplete(false);
              setReview('');
            }
          }}
        />

        <button className="font-desc w-full cursor-pointer rounded-lg bg-white py-3 transition-colors hover:bg-[#FC90D1] hover:text-white active:bg-[#EC69B8] active:text-white disabled:bg-[#DADADA] disabled:text-[#717171]">
          리뷰 등록하기
        </button>
      </div>
    </form>
  );
};

export default ReviewForm;
