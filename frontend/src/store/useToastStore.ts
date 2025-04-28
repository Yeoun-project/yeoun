import { create } from 'zustand';

type ToastType = 'notification' | 'error';
interface ToastItem {
  type: ToastType;
  title: string;
  message: string;
}

interface useToastStore {
  toastItem: ToastItem | null;
  addToast: {
    notification: ({ title, message }: { title: string; message: string }) => void;
    error: ({ title, message }: { title: string; message: string }) => void;
  };
  removeToast: () => void;
}

const useToastStore = create<useToastStore>((set) => ({
  toastItem: null,
  addToast: {
    // notification Toast
    notification: ({ title, message }) => {
      const newToastItem: ToastItem = { type: 'notification' as const, title, message };
      set(() => ({ toastItem: newToastItem }));
    },
    // error Toast
    error: ({ title, message }) => {
      const newToastItem: ToastItem = { type: 'error' as const, title, message };
      set(() => ({ toastItem: newToastItem }));
    },
  },

  removeToast: () => {
    set({ toastItem: null });
  },
}));

export default useToastStore;
