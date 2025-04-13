import { create } from 'zustand';

interface ToastItem {
  title: string;
  message: string;
}

interface useToastStore {
  toastItem: ToastItem | null;
  addToast: ({ title, message }: { title: string; message: string }) => void;
  removeToast: () => void;
}

const useToastStore = create<useToastStore>((set) => ({
  toastItem: null,
  addToast: ({ title, message }: { title: string; message: string }) => {
    const newToastItem = { id: Math.random(), title, message };
    set(() => ({ toastItem: newToastItem }));
  },

  removeToast: () => {
    set({ toastItem: null });
  },
}));

export default useToastStore;
