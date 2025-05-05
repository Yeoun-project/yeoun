import { createBrowserRouter } from 'react-router-dom';

import App from '../App.tsx';

// 프라이빗 라우트
import PrivateRoute from './PrivateRoute.tsx';

import HomePage from '../pages/HomePage.tsx';
import QuestionPage from '../pages/QuestionPage.tsx';
import AddQuestionPage from '../pages/AddQuestionPage.tsx';
import SettingPage from '../pages/SettingPage.tsx';

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
import AlarmPage from '../pages/AlarmPage.tsx';

import MyActivityPage from '../pages/my/MyActivityPage.tsx';
import MyQuestionsPage from '../pages/my/MyQuestionsPage.tsx';
import QuestionListPage from '../pages/QuestionListPage.tsx';
import MyAnswersPage from '../pages/my/MyAnswersPage.tsx';
import QuestionCommentPage from '../pages/question/QuestionCommentPage.tsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      { index: true, element: <HomePage /> },
      { path: '/setting', element: <SettingPage /> },
      { path: '/notification', element: <AlarmPage /> },
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
              {
                path: '/question/add-question',
                element: <AddQuestionPage />,
              },
              {
                path: '/question/:id',
                element: <QuestionCommentPage />,
              }
            ],
          },
          {
            path: '/question-list',
            element: <QuestionListPage />
          },
          {
            path: '/my',
            children: [
              {
                index: true,
                element: <MyActivityPage />,
              },
              {
                path: '/my/questions',
                element: <MyQuestionsPage />,
              },
              {
                path: '/my/answers',
                element: <MyAnswersPage />,
              },
            ],
          },
        ],
      },
    ],
  },
]);

export default router;
