import { Routes, Route, Navigate } from 'react-router-dom';
import { useAuthStore } from '@/stores/auth-store';
import { DashboardLayout } from '@/components/layout/dashboard-layout';
import { LoginPage } from '@/pages/login';
import { RegisterPage } from '@/pages/register';
import { DashboardPage } from '@/pages/dashboard';
import { AppointmentsPage } from '@/pages/appointments';
import { PatientsPage } from '@/pages/patients';
import { ProfilePage } from '@/pages/profile';
import { SchedulePage } from '@/pages/schedule';
import { SettingsPage } from '@/pages/settings';

function PrivateRoute({ children }: { children: React.ReactNode }) {
  const { isAuthenticated } = useAuthStore();
  return isAuthenticated ? <>{children}</> : <Navigate to="/login" replace />;
}

function App() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      
      <Route
        path="/"
        element={
          <PrivateRoute>
            <DashboardLayout />
          </PrivateRoute>
        }
      >
        <Route index element={<DashboardPage />} />
        <Route path="appointments" element={<AppointmentsPage />} />
        <Route path="patients" element={<PatientsPage />} />
        <Route path="schedule" element={<SchedulePage />} />
        <Route path="profile" element={<ProfilePage />} />
        <Route path="settings" element={<SettingsPage />} />
      </Route>

      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}

export default App;
