import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import fetchToken from '../services/api/auth/fetchToken';

interface AuthState {
  isLoggedIn: boolean;
  login: (identifier: string, code: string) => Promise<void>;
}

const useAuthStore = create(
  persist<AuthState>(
    (set) => ({
      isLoggedIn: false,
      login: async (identifier, code) => {
        await fetchToken(identifier, code);
        set((state) => ({ ...state, isLoggedIn: true }));
      },
    }),
    {
      name: 'auth-storage',
    }
  )
);

export default useAuthStore;
