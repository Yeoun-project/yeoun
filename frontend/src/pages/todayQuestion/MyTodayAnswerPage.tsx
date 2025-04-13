import { useState } from 'react';
// import { useParams } from 'react-router-dom';

import useModalStore from '../../store/useModalStore';

import Circle from '../../components/circle/Circle';
import CommentForm from '../../components/form/CommentForm';
import BasicButton from '../../components/button/BasicButton';
import BackArrowButton from '../../components/button/BackArrow';

import Modal from '../../components/modal/Modal';

const MyTodayAnswerPage = () => {
  const [edit, setEdit] = useState(false);
  const [comment, setComment] = useState(
    `다시 태어나면 여행 다니면서 살고 싶어✈️! 지금의 삶도 만족스럽지만 다음 생엔 더 많은 곳을 돌아다니면서 새로운 경험도 많이 해보고 싶어ㅋㅋ 이것저것 고민하느라 미루기보다는 그냥 하고 싶은 대로 도전하면서 신나게 살거야. 한 번 사는 인생이니까~😆`
  );
  const { openModal, modal } = useModalStore();
  //   const { questionId } = useParams();
  return (
    <>
      {modal && (
        <Modal>
          <Modal.Header>
            <Modal.Title>등록하신 답변을 삭제하시겠습니까?</Modal.Title>
            <Modal.SubTitle>
              삭제 후 답변 <span className="text-alert">복구</span>는 불가능합니다.
            </Modal.SubTitle>
          </Modal.Header>

          <Modal.Footer>
            <Modal.CancleButton>취소</Modal.CancleButton>
            <Modal.ConfirmButton onConfirm={() => {}}>삭제</Modal.ConfirmButton>
          </Modal.Footer>
        </Modal>
      )}
      <main className="relative flex min-h-[100svh] flex-col justify-between gap-20 px-6 pt-3 pb-6">
        {/* Background-Circle */}
        <div className="absolute top-[10%] left-1/2 translate-x-[-50%] opacity-40" aria-hidden>
          <Circle size={280} animate category={'dreamsAndGoals'} />
        </div>

        <div>
          {/* Header */}
          <header className="relative flex items-center justify-between">
            <BackArrowButton />
            <h3 className="text-center">2025년 3월 26일</h3>
            <button className="cursor-pointer text-sm text-[#aaaaaa]" onClick={() => openModal()}>
              삭제
            </button>
          </header>

          {/* Content-Seiction */}
          <section className="z-1 pt-18">
            <h4
              className="text-blur-sm mb-6 text-center text-3xl/relaxed break-keep"
              aria-label="다시 태어난다면, 당신은 어떻게 살고싶나요?"
            >
              다시 태어난다면, 당신은 어떻게 살고싶나요?
            </h4>

            {!edit && (
              <p className="font-desc h-[250px] rounded-sm border border-white/50 bg-white/5 px-5 py-4 text-base/relaxed backdrop-blur-2xl">
                다시 태어나면 여행 다니면서 살고 싶어✈️! 지금의 삶도 만족스럽지만 다음 생엔 더 많은
                곳을 돌아다니면서 새로운 경험도 많이 해보고 싶어ㅋㅋ 이것저것 고민하느라
                미루기보다는 그냥 하고 싶은 대로 도전하면서 신나게 살거야. 한 번 사는 인생이니까~😆
              </p>
            )}
            {edit && (
              <CommentForm
                commentValue={comment}
                onChange={(e) => setComment(e.target.value)}
                onSubmit={(e) => console.log(e.currentTarget)}
              />
            )}
          </section>
        </div>

        <BasicButton onClick={() => setEdit((prev) => !prev)}>
          {edit ? '답변 등록하기' : '수정하기'}
        </BasicButton>
      </main>
    </>
  );
};

export default MyTodayAnswerPage;
