import { useEffect, useState } from 'react';
import useToastStore from '../../store/useToastStore';

interface ToastItemProps {
  title: string;
  message: string;
}

const ToastItem = ({ title, message }: ToastItemProps) => {
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

  return (
    <div
      className={`font-desc flex w-full items-center justify-between rounded-lg bg-gradient-to-b from-white/40 to-[#FC90D1]/40 px-4 py-3 text-white backdrop-blur-2xl transition-all ${visible ? 'opacity-100' : 'translate-y-4 opacity-0'}`}
    >
      <div>
        <h5>{title}</h5>
        <p className="text-sm">{message}</p>
      </div>
      <button
        onClick={() => removeToast()}
        className="block size-6 bg-[url(/icons/closeIcon.svg)]"
      />
    </div>
  );
};

export default ToastItem;
