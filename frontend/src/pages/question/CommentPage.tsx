import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useQueryClient } from '@tanstack/react-query';

import BackArrowButton from '../../components/button/BackArrowButton';
import Squre from '../../assets/Squre';
import BasicButton from '../../components/button/BasicButton';
import CommentForm from '../../components/form/CommentForm';
import RegisterModal from '../../components/modal/RegisterModal';
import ConfirmModal from '../../components/modal/ConfirmModal';

import useModalStore from '../../store/useModalStore';
import useToastStore from '../../store/useToastStore';

import useGetQuestionDetail from '../../hooks/queries/useGetQuestionDetail';

import { addQuestionComment } from '../../services/api/comment/addQuestionComment';

const CommentPage = () => {
  const queryClient = useQueryClient();

  const navigate = useNavigate();
  const params = useParams();

  const questionId = Number(params.id);

  const [content, setContent] = useState<string>('');

  const { data: questionDetail } = useGetQuestionDetail(questionId);

  const toast = useToastStore();
  const modal = useModalStore();

  const [first, setFirst] = useState(true);
  const [second, setSecond] = useState(false);

  const [hasError, setHasError] = useState(false);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (content === '') {
      setHasError(true);
      toast.addToast.error({
        title: '여운 등록 실패!',
        message: '답변을 작성해주세요!',
      });
    } else {
      modal.openModal();
      setFirst(true);
      setSecond(false);
    }
  };

  const onChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);

    if (hasError) setHasError(false);
  };

  // 첫 번째 모달 등록 버튼
  const onRegister = () => {
    setFirst(false);
    setSecond(true);
  };
  // 두 번째 모달 등록 버튼
  const onConfirm = async () => {
    const response = await addQuestionComment(content, questionId);
    console.log(response?.data);

    // 등록 후 초기화
    setSecond(false);
    setContent('');

    queryClient.invalidateQueries({ queryKey: ['comment', questionId, 'old'] });
    modal.closeModal();
    navigate(`/question/${questionId}`);
  };

  return (
    <main className="-z-1 flex min-h-[100svh] flex-col items-center justify-between p-6">
      <div className="w-full">
        {/* Header */}
        <div className="mb-16">
          <BackArrowButton label="작성취소" />
        </div>

        <p className="text-gradient mb-6 block bg-clip-text text-center text-2xl/10 break-keep">
          {questionDetail?.content}
        </p>

        <div className="relative">
          <CommentForm
            formId="today-question"
            commentValue={content}
            onChange={onChange}
            onSubmit={handleSubmit}
            maxValue={50}
            error={hasError}
          />

          {/* Backgroun Squre */}
          <div aria-hidden className="animate-spin-cube absolute top-[-15%] right-[-5%]">
            <Squre size={60} />
          </div>
          <div aria-hidden className="animate-spin-cube-reverse absolute bottom-[-5%] left-[-5%]">
            <Squre size={80} />
          </div>
        </div>
      </div>
      {first && (
        <RegisterModal value="답변" content={content} maxLength={50} onSubmit={onRegister} />
      )}
      {second && <ConfirmModal value="답변" onSubmit={onConfirm} />}
      {/* Form Submit Button */}
      <BasicButton form="today-question">답변 등록하기</BasicButton>
    </main>
  );
};

export default CommentPage;
