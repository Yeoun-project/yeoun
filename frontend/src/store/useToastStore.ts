import { create } from 'zustand';

type ToastType = 'notification' | 'error';
interface ToastItem {
  type: ToastType;
  title: string;
  message: string;
  hasBottomTab?: boolean;
}

interface useToastStore {
  toastItem: ToastItem | null;
  addToast: {
    notification: ({
      title,
      message,
      hasBottomTab,
    }: {
      title: string;
      message: string;
      hasBottomTab?: boolean;
    }) => void;
    error: ({
      title,
      message,
      hasBottomTab,
    }: {
      title: string;
      message: string;
      hasBottomTab?: boolean;
    }) => void;
  };
  removeToast: () => void;
}

const useToastStore = create<useToastStore>((set) => ({
  toastItem: null,
  addToast: {
    // notification Toast
    notification: ({ title, message, hasBottomTab = true }) => {
      const newToastItem: ToastItem = {
        type: 'notification' as const,
        title,
        message,
        hasBottomTab,
      };
      set(() => ({ toastItem: newToastItem }));
    },
    // error Toast
    error: ({ title, message, hasBottomTab = true }) => {
      const newToastItem: ToastItem = { type: 'error' as const, title, message, hasBottomTab };
      set(() => ({ toastItem: newToastItem }));
    },
  },

  removeToast: () => {
    set({ toastItem: null });
  },
}));

export default useToastStore;
