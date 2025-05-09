import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useAuthStore from '../../store/useAuthStore';

import { addTodayQuestionComment } from '../../services/api/question/todayQuestion';

import useGetTodayQuestion from '../../hooks/queries/useGetTodayQuestion';

import Squre from '../../assets/Squre';

import BackArrowButton from '../../components/button/BackArrowButton';
import CommentForm from '../../components/form/CommentForm';
import BasicButton from '../../components/button/BasicButton';
import useToastStore from '../../store/useToastStore';
import { queryClient } from '../../utils/queryClient';

const TodayQuestionCommentPage = () => {
  const navigate = useNavigate();
  const [value, setValue] = useState<string>('');

  const { addToast } = useToastStore();
  const { userType } = useAuthStore();
  const { data: todayQuestion } = useGetTodayQuestion(userType);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await addTodayQuestionComment({ questionId: todayQuestion!.id, comment: value });
      queryClient.invalidateQueries({ queryKey: [userType, 'today-question'] });
      navigate('/today-question');
      addToast.notification({
        title: '여운 등록 완료',
        message: '오늘의 질문에 당신의 여운이 남겨졌어요.',
      });

      return;
    } catch (err) {
      console.log(err);
    }
  };

  const onChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setValue(e.target.value);
  };

  return (
    <>
      <main className="-z-1 flex min-h-[100svh] flex-col items-center justify-between p-6">
        <div className="w-full">
          {/* Header */}
          <div className="mb-16">
            <BackArrowButton label="작성취소" />
          </div>

          <p className="text-gradient mb-6 block bg-clip-text text-center text-2xl/10 break-keep">
            {todayQuestion?.content}
          </p>

          <div className="relative">
            <CommentForm
              formId="today-question"
              commentValue={value}
              onChange={onChange}
              onSubmit={handleSubmit}
              maxValue={500}
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
        {/* Form Submit Button */}
        <BasicButton form="today-question">답변 등록하기</BasicButton>
      </main>
    </>
  );
};

export default TodayQuestionCommentPage;
