/* eslint-disable react-refresh/only-export-components */

import { useState } from 'react';
import BackArrow from '../../components/common/BackArrow';
import Squre from '../../assets/Squre';
import useGetTodayQuestion from '../../hooks/queries/useGetTodayQuestion';
import { ActionFunctionArgs, Form } from 'react-router-dom';

import { addTodayQuestionComment } from '../../services/api/question/todayQuestion';

const TodayQuestionCommentPage = () => {
  const [value, setValue] = useState<string>('');

  const { data: todayQuestion } = useGetTodayQuestion();

  return (
    <>
      <main className="-z-1 flex min-h-[100svh] flex-col items-center justify-between p-6">
        <div className="w-full">
          {/* Header */}
          <div className="mb-16">
            <BackArrow label="작성취소" />
          </div>

          {/* Question */}
          <p className="text-gradient mb-6 bg-clip-text text-center text-2xl/10 break-keep">
            {todayQuestion?.content}
          </p>

          {/* Form */}
          <Form method="post" className="relative z-10 w-full" id="today-question">
            {/* Answer Input */}
            <textarea
              value={value}
              onChange={(e) => setValue(e.target.value)}
              name="comment"
              id="comment"
              maxLength={500}
              className="font-desc z-30 h-[270px] w-full resize-none rounded-sm border border-white/50 bg-white/10 p-4 backdrop-blur-xs outline-none placeholder:text-white"
              placeholder="답변을 작성해주세요"
            />
            <div className="font-desc w-full text-right text-sm">{`${value.length || 0} / 500`}</div>

            {/* Background Squre */}
            <div aria-hidden className="animate-spin-second absolute top-[-15%] right-[-5%] -z-10">
              <Squre size={60} />
            </div>
            <div aria-hidden className="animate-spin-third absolute bottom-[-5%] left-[-5%] -z-10">
              <Squre size={100} />
            </div>
          </Form>
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

export const action = async ({ request }: ActionFunctionArgs) => {
  const comment = (await request.formData()).get('comment') as string;
  try {
    const response = await addTodayQuestionComment(comment);
    console.log(response);
  } catch (error) {
    console.log(error);
  }

  return undefined;
};
