import { createBrowserRouter } from 'react-router-dom';

import App from '../App.tsx';

// 프라이빗 라우트
import PrivateRoute from './PrivateRoute.tsx';

import HomePage from '../pages/HomePage.tsx';
import QuestionPage from '../pages/QuestionPage.tsx';
import AddQuestionPage from '../pages/AddQuestionPage.tsx';
import SettingPage from '../pages/SettingPage.tsx';
import MyActivityPage from '../pages/MyActivityPage.tsx';

//로그인
import LoginPage from '../pages/login/LoginPage.tsx';
import LoginFallback from '../pages/login/LoginFallback.tsx';

// 오늘의 질문
import TodayQuestionLayout, {
  loader as TodayQestionLoader,
} from '../pages/todayQuestion/TodayQuestionLayout.tsx';
import TodayQuestionPage from '../pages/todayQuestion/TodayQuestionPage.tsx';
import TodayQuestionCommentPage from '../pages/todayQuestion/TodayQuestionCommentPage.tsx';
import MyTodayAnswersPage from '../pages/todayQuestion/MyTodayAnswersPage.tsx';
import MyTodayAnswerPage from '../pages/todayQuestion/MyTodayAnswerPage.tsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      { index: true, element: <HomePage /> },
      { path: '/setting', element: <SettingPage /> },
      {
        path: '/login',
        element: <LoginPage />,
      },
      {
        path: '/login/:identifier',
        element: <LoginFallback />,
      },
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
      {
        element: <PrivateRoute />,
        children: [
          {
            path: '/question',
            children: [
              {
                index: true,
                element: <QuestionPage />,
              },
            ], 
          },
          {
            path: '/add-question',
            element: <AddQuestionPage />,
          },
          { path: '/my-activity', element: <MyActivityPage /> },
        ],
      },
    ],
  },
]);

export default router;
