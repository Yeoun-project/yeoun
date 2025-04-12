// import { useParams } from 'react-router-dom';
import BackArrow from '../../components/common/BackArrow';
import Circle from '../../components/circle/Circle';

const MyTodayAnswerPage = () => {
  //   const { questionId } = useParams();

  return (
    <main className="relative flex min-h-[100svh] flex-col gap-20">
      {/* Background-Circle */}
      <div className="absolute top-[20%] left-1/2 translate-x-[-50%] opacity-40" aria-hidden>
        <Circle size={300} animate category={'dreamsAndGoals'} />
      </div>

      {/* Header */}
      <header className="relative flex items-center justify-between px-6 py-3">
        <BackArrow />
        <h3 className="text-center">2025년 3월 26일</h3>
        <button className="cursor-pointer text-sm text-[#aaaaaa]">삭제</button>
      </header>

      {/* Content-Seiction */}
      <section className="z-1 px-6 py-18">
        <h4
          className="text-blur-sm mb-6 text-center text-3xl/relaxed break-keep"
          aria-label="다시 태어난다면, 당신은 어떻게 살고싶나요?"
        >
          다시 태어난다면, 당신은 어떻게 살고싶나요?
        </h4>

        <p className="font-desc rounded-sm border border-white/50 bg-white/5 px-5 py-4 text-base/relaxed backdrop-blur-2xl">
          다시 태어나면 여행 다니면서 살고 싶어✈️! 지금의 삶도 만족스럽지만 다음 생엔 더 많은 곳을
          돌아다니면서 새로운 경험도 많이 해보고 싶어ㅋㅋ 이것저것 고민하느라 미루기보다는 그냥 하고
          싶은 대로 도전하면서 신나게 살거야. 한 번 사는 인생이니까~😆
        </p>
      </section>
    </main>
  );
};

export default MyTodayAnswerPage;
