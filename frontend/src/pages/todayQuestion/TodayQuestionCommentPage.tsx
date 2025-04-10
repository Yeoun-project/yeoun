/* eslint-disable react-refresh/only-export-components */

import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useAuthStore from '../../store/useAuthStore';

import { addTodayQuestionComment } from '../../services/api/question/todayQuestion';

import useGetTodayQuestion from '../../hooks/queries/useGetTodayQuestion';

import BackArrow from '../../components/common/BackArrow';
import CommentForm from '../../components/form/CommentForm';

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
            <BackArrow label="작성취소" />
          </div>

          <CommentForm
            formId="today-question"
            question={todayQuestion!.content}
            commentValue={value}
            onChange={(e) => setValue(e.target.value)}
            onSubmit={handleSubmit}
          />
        </div>

        {/* Form Submit Button */}
        <button
          form="today-question"
          className="font-desc w-full cursor-pointer rounded-xl bg-white py-4 text-sm font-bold text-black"
        >
          답글 작성완료
        </button>
      </main>
    </>
  );
};

export default TodayQuestionCommentPage;
