import { createBrowserRouter } from 'react-router-dom';

import App from '../App.tsx';

import HomePage from '../pages/HomePage.tsx';
import LoginPage from '../pages/LoginPage.tsx';
import QuestionPage from '../pages/QuestionPage.tsx';
import SettingPage from '../pages/SettingPage.tsx';
import MyActivityPage from '../pages/MyActivityPage.tsx';

// 오늘의 질문
import TodayQuestionPage from '../pages/TodayQuestion/TodayQuestionPage.tsx';
import TodayQuestionCommentPage, {
  action as TodayQuestionAction,
} from '../pages/TodayQuestion/TodayQuestionCommentPage.tsx';
import TodayQuestionLayout, {
  loader as TodayQestionLoader,
} from '../pages/TodayQuestion/TodayQuestionLayout.tsx';
import MyTodayAnswersPage from '../pages/TodayQuestion/MyTodayAnswersPage.tsx';
import MyTodayAnswerPage from '../pages/TodayQuestion/MyTodayAnswerPage.tsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      { index: true, element: <HomePage /> },
      { path: '/login', element: <LoginPage /> },
      {
        path: '/today-question',
        element: <TodayQuestionLayout />,
        loader: TodayQestionLoader,
        id: 'today-question',
        hydrateFallbackElement: <></>,
        children: [
          {
            index: true,
            element: <TodayQuestionPage />,
          },
          {
            path: '/today-question/comment',
            element: <TodayQuestionCommentPage />,
            action: TodayQuestionAction,
          },
          {
            path: '/today-question/answers',
            element: <MyTodayAnswersPage />,
          },
          {
            path: '/today-question/:questionId',
            element: <MyTodayAnswerPage />,
          },
        ],
      },
      { path: '/question', element: <QuestionPage /> },
      { path: '/setting', element: <SettingPage /> },
      { path: '/my-activity', element: <MyActivityPage /> },
    ],
  },
]);

export default router;
