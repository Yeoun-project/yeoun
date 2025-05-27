import { useEffect, useState } from 'react';
import Circle from '../circle/Circle';
import CATEGORY from '../../constant/category/Category';

const QuestionPageFallback = () => {
  const [category, setCategory] = useState(CATEGORY[0].category);
  const [currentCategory, setCurrentCategory] = useState(1);

  useEffect(() => {
    const timer = setTimeout(() => {
      if (currentCategory === 7) {
        setCurrentCategory(0);
        return;
      }
      setCategory(CATEGORY[currentCategory].category);
      setCurrentCategory((prev) => prev + 1);
    }, 300);

    return () => {
      clearTimeout(timer);
    };
  }, [currentCategory]);

  return (
    <div className="flex h-svh w-full flex-col items-center justify-center gap-3">
      <Circle size={64} category={category} animate />
      <p>
        여운이 로딩중입니다 <br /> 잠시만 기다려주세요...
      </p>
    </div>
  );
};

export default QuestionPageFallback;
