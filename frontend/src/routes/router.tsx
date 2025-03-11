import { createBrowserRouter } from 'react-router-dom';
import App from '../App.tsx';
import HomePage from "../pages/HomePage.tsx";
import LoginPage from "../pages/LoginPage.tsx";
import QuestionPage from "../pages/QuestionPage.tsx";
import SettingPage from "../pages/SettingPage.tsx";
import MyActivityPage from "../pages/MyActivityPage.tsx";

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      { index: true, element: <HomePage /> },
      { path: '/login', element: <LoginPage /> },
      { path: '/question', element: <QuestionPage /> },
      { path: '/setting', element: <SettingPage /> },
      { path: '/my-activity', element: <MyActivityPage /> },
    ],
  },
]);

export default router;
