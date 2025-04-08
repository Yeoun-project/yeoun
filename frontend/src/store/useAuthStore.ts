import { create } from 'zustand';
import { persist } from 'zustand/middleware';

import UserType from '../type/auth/UserType';

import fetchToken from '../services/api/auth/fetchToken';

interface AuthState {
  userType: UserType;
  login: (identifier: string, code: string) => Promise<void>;
  setUserType: (type: UserType) => void;
}

const useAuthStore = create(
  persist<AuthState>( // zustand 미들웨어
    (set) => ({
      userType: null,
      login: async (identifier, code) => {
        await fetchToken(identifier, code);
      },
      setUserType: (type) => set((state) => ({ ...state, userType: type })),
    }),
    {
      name: 'auth-storage',
    }
  )
);

export default useAuthStore;
