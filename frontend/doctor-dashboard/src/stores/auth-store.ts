import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface Doctor {
  id: string;
  userId: string;
  email: string;
  firstName: string;
  lastName: string;
  title: string;
  specialization: string;
  avatarUrl?: string;
  isVerified: boolean;
}

interface AuthState {
  doctor: Doctor | null;
  accessToken: string | null;
  refreshToken: string | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  setDoctor: (doctor: Doctor | null) => void;
  setTokens: (accessToken: string, refreshToken: string) => void;
  logout: () => void;
  setLoading: (loading: boolean) => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      doctor: null,
      accessToken: null,
      refreshToken: null,
      isAuthenticated: false,
      isLoading: false,
      setDoctor: (doctor) => set({ doctor, isAuthenticated: !!doctor }),
      setTokens: (accessToken, refreshToken) => set({ accessToken, refreshToken }),
      logout: () =>
        set({
          doctor: null,
          accessToken: null,
          refreshToken: null,
          isAuthenticated: false,
        }),
      setLoading: (isLoading) => set({ isLoading }),
    }),
    {
      name: 'doctor-auth-storage',
      partialize: (state) => ({
        doctor: state.doctor,
        accessToken: state.accessToken,
        refreshToken: state.refreshToken,
        isAuthenticated: state.isAuthenticated,
      }),
    }
  )
);
