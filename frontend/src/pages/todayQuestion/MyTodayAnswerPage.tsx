/* eslint-disable react-refresh/only-export-components */
import { useEffect, useRef, useState } from 'react';

import useModalStore from '../../store/useModalStore';

import Circle from '../../components/circle/Circle';
import CommentForm from '../../components/form/CommentForm';
import BasicButton from '../../components/button/BasicButton';
import BackArrowButton from '../../components/button/BackArrowButton';

import Modal from '../../components/modal/Modal';
import { LoaderFunctionArgs, useLoaderData } from 'react-router-dom';
import { TodayQuestionComment } from '../../type/comment';
import { queryClient } from '../../utils/queryClient';
import { getTodayQuestionComment } from '../../services/api/question/todayQuestion';
import formatDate from '../../utils/formatDate';
import useUpdateCommentMutation from '../../hooks/mutation/useUpdateCommentMutation';
import useToastStore from '../../store/useToastStore';

const MyTodayAnswerPage = () => {
  const { addToast } = useToastStore();
  const [error, setError] = useState<boolean>(false);
  const todayQuestionComment = useLoaderData<TodayQuestionComment>();
  const { mutate } = useUpdateCommentMutation(todayQuestionComment.id);
  const formRef = useRef<HTMLTextAreaElement>(null);
  const { openModal, modal } = useModalStore();

  const [edit, setEdit] = useState(false);
  const [comment, setComment] = useState(todayQuestionComment.comment.content);

  useEffect(() => {
    // edit 상태로 변했을 때 폼 focus
    if (edit) {
      if (!formRef.current) return;
      const textLength = formRef.current.value.length;
      formRef.current.focus();
      formRef.current.setSelectionRange(textLength, textLength);
    }
  }, [edit]);

  const handleUpdateComment = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (comment.trim().length === 0) {
      setError(true);
      setTimeout(() => setError(false), 2300);
      addToast.error({ title: '여운 등록 실패', message: '답변을 작성해주세요!' });
      return;
    }

    try {
      mutate(comment);
      setEdit(false);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      {modal && (
        <Modal>
          <Modal.Header>
            <Modal.Title>등록하신 답변을 삭제하시겠습니까?</Modal.Title>
            <Modal.SubTitle>
              삭제 후 답변 <span className="text-error">복구</span>는 불가능합니다.
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
            <h3 className="text-center">{formatDate(todayQuestionComment.createTime)}</h3>
            <button className="cursor-pointer text-sm text-[#aaaaaa]" onClick={() => openModal()}>
              삭제
            </button>
          </header>

          {/* Content-Seiction */}
          <section className="z-1 pt-18">
            <h4
              className="text-blur-sm mb-6 text-center text-3xl/relaxed break-keep"
              aria-label={todayQuestionComment.content}
            >
              {todayQuestionComment.content}
            </h4>

            {!edit && (
              <p className="font-desc h-[250px] rounded-sm border border-white/50 bg-white/5 px-5 py-4 text-base/relaxed backdrop-blur-2xl">
                {todayQuestionComment.comment.content}
              </p>
            )}
            {edit && (
              <CommentForm
                error={error}
                formId="today-question-comment"
                ref={formRef}
                commentValue={comment}
                onChange={(e) => setComment(e.target.value)}
                onSubmit={handleUpdateComment}
              />
            )}
          </section>
        </div>

        {!edit && <BasicButton onClick={() => setEdit((prev) => !prev)}>수정하기</BasicButton>}
        {edit && <BasicButton form="today-question-comment">답변 등록하기</BasicButton>}
      </main>
    </>
  );
};

export default MyTodayAnswerPage;

export const loader = async ({
  params,
}: LoaderFunctionArgs<{ params: string }>): Promise<TodayQuestionComment | null> => {
  const { questionId } = params;
  const todayQuestionComment = await queryClient.fetchQuery({
    queryKey: ['today-question', 'comment', questionId],
    queryFn: async ({ queryKey }) => getTodayQuestionComment(queryKey[2] as string),
  });
  return todayQuestionComment;
};
