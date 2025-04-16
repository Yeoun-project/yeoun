/* eslint-disable react-refresh/only-export-components */

import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useAuthStore from '../../store/useAuthStore';

import { addTodayQuestionComment } from '../../services/api/question/todayQuestion';

import useGetTodayQuestion from '../../hooks/queries/useGetTodayQuestion';

import Squre from '../../assets/Squre';

import BackArrowButton from '../../components/button/BackArrowButton';
import CommentForm from '../../components/form/CommentForm';
import BasicButton from '../../components/button/BasicButton';

const TodayQuestionCommentPage = () => {
  const navigate = useNavigate();
  const [value, setValue] = useState<string>('');

  const { userType } = useAuthStore();
  const { data: todayQuestion } = useGetTodayQuestion(userType);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await addTodayQuestionComment(userType, value);
      return navigate('/today-question');
    } catch (err) {
      console.log(err);
    }
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
              onChange={(e) => setValue(e.target.value)}
              onSubmit={handleSubmit}
            />

            {/* Backgroun Squre */}
            <div aria-hidden className="animate-spin-second absolute top-[-15%] right-[-5%]">
              <Squre size={60} />
            </div>
            <div aria-hidden className="animate-spin-third absolute bottom-[-5%] left-[-5%]">
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
