import { useNavigate } from 'react-router-dom';

interface BackArrowProps {
  label?: string;
  path?: string;
}

const BackArrowButton = ({ label, path }: BackArrowProps) => {
  const navigate = useNavigate();

  const handleClick = () => {
    if (path) return navigate(path);
    if (!path) return navigate(-1);
  };

  return (
    <button
      onClick={handleClick}
      className="block min-h-6 min-w-6 cursor-pointer bg-[url(/icons/left.svg)] bg-no-repeat"
    >
      {label && <div className="pl-[28px]">{label}</div>}
    </button>
  );
};

export default BackArrowButton;
