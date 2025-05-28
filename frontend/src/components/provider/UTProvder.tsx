import { useEffect, useState } from 'react';
import { createPortal } from 'react-dom';
import Rating from '../backgroundBanner/Rating';
import writeUtReview from '../../services/api/writeUtReview';

const UT_TIMER_DURATION_MS = 10 * 60;

const LOCAL_STORAGE_KEY_POPUP_DISMISSED_DATE = 'appPopupDismissedDate';

const getTodayDateString = () => {
  const today = new Date();
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
  const day = String(today.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

const UTProvder = () => {
  const dismissedDate = localStorage.getItem(LOCAL_STORAGE_KEY_POPUP_DISMISSED_DATE);
  const todayStr = getTodayDateString();

  const [showForm, setShowForm] = useState<boolean>(false);
  const [close, setClose] = useState<boolean>(false);
  const [currentRating, setCurrentRating] = useState<number>(0);
  useEffect(() => {
    if (dismissedDate === todayStr) {
      if (showForm) {
        setShowForm(false);
        return;
      }
      return;
    } else {
      // 처음 접속 후 2분간 유지 시 토스트 메시지 띄우기
      const timer = setTimeout(() => {
        setShowForm(true);
      }, 200);

      return () => {
        clearTimeout(timer);
      };
    }
  }, []);

  useEffect(() => {
    // close 버튼을 눌렀을 시 5분 간격으로 토스트 메세지 띄우기
    if (close) {
      setClose(false);
      setTimeout(() => {
        setShowForm(true);
      }, UT_TIMER_DURATION_MS * 500);
    }
  }, [close]);

  const handleChangeCurrentRating = (rating: number) => {
    setCurrentRating(rating);
  };

  const handleSubmit = async () => {
    try {
      await writeUtReview({ starRate: currentRating, message: 'mobile review' });
      localStorage.setItem('utReview', 'complete');
      setShowForm(false);
    } catch (error) {
      console.log(error);
    }
  };

  const handleDismissToday = () => {
    const todayStr = getTodayDateString();
    localStorage.setItem(LOCAL_STORAGE_KEY_POPUP_DISMISSED_DATE, todayStr);
    setShowForm(false); // 즉시 팝업을 닫음
  };

  return createPortal(
    <>
      {showForm && (
        <div className="font-desc fixed right-0 bottom-4 left-0 z-50 mx-auto flex w-[90%] flex-col items-center rounded-lg border-[1.5px] border-[#FC90D1]/50 bg-gradient-to-b from-white/20 to-[#96567C]/50 p-4 text-white backdrop-blur-md">
          <div className="relative w-full self-start">
            <p className="font-semibold">여운, 어떠셨나요?</p>
            <p className="text-sm">솔직한 평가를 들려주세요!</p>

            <div
              onClick={() => {
                setClose(true);
                setShowForm(false);
              }}
              className="absolute top-0 -right-4 mr-3 size-10 bg-[url(/icons/closeIcon.svg)] bg-center bg-no-repeat"
            />
          </div>
          <Rating
            setCurrentRating={handleChangeCurrentRating}
            currentRating={currentRating}
            maxRatingValue={5}
            handleSubmit={handleSubmit}
          />
          <button onClick={handleDismissToday}>오늘 하루 보지않기</button>
        </div>
      )}
    </>,
    document.getElementById('pop-up') as HTMLDivElement
  );
};

export default UTProvder;
