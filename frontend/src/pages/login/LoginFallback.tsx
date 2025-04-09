import { useEffect } from 'react';
import { useNavigate, useParams, useSearchParams } from 'react-router-dom';
import useAuthStore from '../../store/useAuthStore';

const LoginFallback = () => {
  const { login, setUserType } = useAuthStore();
  const { identifier } = useParams();
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');
  const navigate = useNavigate();

  useEffect(() => {
    if (!identifier || !code) {
      navigate('/');
      return;
    }
    login(identifier, code)
      .then(() => {
        setUserType('User');
        return navigate('/today-question');
      })
      .catch((error) => console.log(error));
  }, [code, identifier, login, navigate, setUserType]);

  return null;
};

export default LoginFallback;
