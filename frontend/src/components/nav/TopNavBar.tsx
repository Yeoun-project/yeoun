import { Link, useNavigate } from 'react-router-dom';
import useAuthStore from '../../store/useAuthStore';

const TopNavBar = ({ navTitle }: { navTitle?: string }) => {
  const { userType } = useAuthStore();
  const navigate = useNavigate();

  return (
    <nav className="flex items-center justify-between">
      <Link
        aria-label="설정 바로가기"
        to="/setting"
        className="size-6 bg-[url(/icons/settings.svg)] bg-no-repeat"
      />

      {navTitle && <h3>{navTitle}</h3>}

      {userType === 'Guest' && (
        <button
          onClick={() => navigate('/today-question/answers')}
          className="text-gradient cursor-pointer bg-clip-text"
        >
          답변목록
        </button>
      )}

      {userType === 'User' && (
        <div className="relative size-6">
          <Link
            aria-label="알림"
            to="/notification"
            className="block size-6 bg-[url(/icons/notification.svg)] bg-no-repeat"
          />
        </div>
      )}
    </nav>
  );
};

export default TopNavBar;
