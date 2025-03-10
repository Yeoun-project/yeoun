import { Link } from 'react-router-dom';

const NavBar = () => {
  const links = [
    {
      endpoint: '/',
      name: '메인페이지',
    },
    {
      endpoint: '/login',
      name: '로그인',
    },
    {
      endpoint: '/question',
      name: '질문하기',
    },
    {
      endpoint: '/setting',
      name: '설정',
    },
    {
      endpoint: '/my-activity',
      name: '나의 활동',
    },
  ];
  return (
    <nav className="bg-blue-600 p-4 shadow-lg flex justify-between items-center">
      {links.map((link) => (
        <Link to={link.endpoint} className="text-white hover:text-gray-200">
          {link.name}
        </Link>
      ))}
    </nav>
  );
};

export default NavBar;
