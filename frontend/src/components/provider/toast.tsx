import { createPortal } from 'react-dom';

import ToastItem from '../toast/ToastItem';
import useToastStore from '../../store/useToastStore';

const ToastProvider = () => {
  const { toastItem } = useToastStore();

  return createPortal(
    <>
      {toastItem && (
        <div className="fixed bottom-22 z-10 w-full space-y-2 px-6">
          <ToastItem title={toastItem.title} message={toastItem.message} />
        </div>
      )}
    </>,
    document.getElementById('toast') as HTMLDivElement
  );
};

export default ToastProvider;
