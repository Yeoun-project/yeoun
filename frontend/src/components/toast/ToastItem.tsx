import { useEffect, useState } from 'react';
import useToastStore from '../../store/useToastStore';

interface ToastItemProps {
  type: 'notification' | 'error';
  title: string;
  message: string;
}

const ToastItem = ({ type, title, message }: ToastItemProps) => {
  const { removeToast } = useToastStore();
  const [visible, setVisible] = useState<boolean>(false);

  useEffect(() => {
    setVisible(true);
    const visibleTimer = setTimeout(() => {
      setVisible(false);
    }, 2300);
    const timer = setTimeout(() => {
      removeToast();
    }, 2500);

    return () => {
      clearTimeout(timer);
      clearTimeout(visibleTimer);
    };
  }, [removeToast]);

  const getToastStyle = (type: 'notification' | 'error') => {
    if (type === 'notification') {
      return `bg-gradient-to-b from-white/40 to-[#FC90D1]/40 backdrop-blur-2xl justify-between`;
    }
    if (type === 'error') {
      return `bg-error/30 border border-white/50`;
    }
  };

  return (
    <div
      className={`${getToastStyle(type)} font-desc flex w-full items-center rounded-lg px-4 py-3 text-white transition-all ${visible ? 'opacity-100' : 'translate-y-4 opacity-0'}`}
    >
      {type === 'error' && (
        <div className="mr-3 size-8 bg-[url(/icons/error.svg)] bg-center bg-no-repeat"></div>
      )}
      <div>
        <h5 className="text-base/relaxed">{title}</h5>
        <p className="text-sm whitespace-pre-line">{message}</p>
      </div>

      {type === 'notification' && (
        <button
          onClick={() => removeToast()}
          className="block size-6 cursor-pointer bg-[url(/icons/closeIcon.svg)]"
        />
      )}
    </div>
  );
};

export default ToastItem;
