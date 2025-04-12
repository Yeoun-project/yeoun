import useAuthStore from '../store/useAuthStore';
import { Navigate, Outlet } from 'react-router-dom';

const PrivateRoute = () => {
  const { userType } = useAuthStore();
  if (userType !== 'User') {
    return <Navigate to="/login" replace />;
  }

  return <Outlet />;
};

export default PrivateRoute;
