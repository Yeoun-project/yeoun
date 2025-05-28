import { createPortal } from 'react-dom';

import ToastItem from '../toast/ToastItem';
import useToastStore from '../../store/useToastStore';

const ToastProvider = () => {
  const { toastItem } = useToastStore();

  return createPortal(
    <>
      {toastItem && (
        <div
          className={`toast-list fixed right-0 left-0 z-10 w-full max-w-[430px] space-y-2 px-6 ${toastItem.hasBottomTab ? 'bottom-24' : 'bottom-6'}`}
        >
          <ToastItem type={toastItem.type} title={toastItem.title} message={toastItem.message} />
        </div>
      )}
    </>,
    document.getElementById('toast') as HTMLDivElement
  );
};

export default ToastProvider;
