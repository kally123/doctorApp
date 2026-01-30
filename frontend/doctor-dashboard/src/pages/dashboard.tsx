import { useAuthStore } from '@/stores/auth-store';
import {
  Calendar,
  Users,
  Clock,
  TrendingUp,
  ArrowUpRight,
  ArrowDownRight,
} from 'lucide-react';

const stats = [
  {
    name: "Today's Appointments",
    value: '12',
    change: '+2',
    changeType: 'positive',
    icon: Calendar,
  },
  {
    name: 'Total Patients',
    value: '1,234',
    change: '+48',
    changeType: 'positive',
    icon: Users,
  },
  {
    name: 'Avg. Wait Time',
    value: '8 min',
    change: '-2 min',
    changeType: 'positive',
    icon: Clock,
  },
  {
    name: 'Monthly Revenue',
    value: '₹2.4L',
    change: '+12%',
    changeType: 'positive',
    icon: TrendingUp,
  },
];

const upcomingAppointments = [
  { id: 1, patient: 'Amit Sharma', time: '10:00 AM', type: 'Video', status: 'Confirmed' },
  { id: 2, patient: 'Priya Patel', time: '10:30 AM', type: 'In-Person', status: 'Confirmed' },
  { id: 3, patient: 'Rahul Kumar', time: '11:00 AM', type: 'Video', status: 'Pending' },
  { id: 4, patient: 'Sneha Gupta', time: '11:30 AM', type: 'In-Person', status: 'Confirmed' },
  { id: 5, patient: 'Vikram Singh', time: '12:00 PM', type: 'Follow-up', status: 'Confirmed' },
];

export function DashboardPage() {
  const { doctor } = useAuthStore();

  return (
    <div className="space-y-6">
      {/* Welcome header */}
      <div>
        <h1 className="text-2xl font-bold">
          Welcome back, Dr. {doctor?.firstName || 'Doctor'}!
        </h1>
        <p className="text-muted-foreground">
          Here's an overview of your practice today.
        </p>
      </div>

      {/* Stats grid */}
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        {stats.map((stat) => (
          <div
            key={stat.name}
            className="rounded-xl border bg-card p-6 shadow-sm"
          >
            <div className="flex items-center justify-between">
              <div className="p-2 rounded-lg bg-primary/10">
                <stat.icon className="h-5 w-5 text-primary" />
              </div>
              <div
                className={`flex items-center text-sm ${
                  stat.changeType === 'positive'
                    ? 'text-green-600'
                    : 'text-red-600'
                }`}
              >
                {stat.change}
                {stat.changeType === 'positive' ? (
                  <ArrowUpRight className="h-4 w-4" />
                ) : (
                  <ArrowDownRight className="h-4 w-4" />
                )}
              </div>
            </div>
            <div className="mt-4">
              <p className="text-2xl font-bold">{stat.value}</p>
              <p className="text-sm text-muted-foreground">{stat.name}</p>
            </div>
          </div>
        ))}
      </div>

      <div className="grid gap-6 lg:grid-cols-2">
        {/* Upcoming appointments */}
        <div className="rounded-xl border bg-card shadow-sm">
          <div className="flex items-center justify-between p-6 border-b">
            <h2 className="font-semibold">Upcoming Appointments</h2>
            <a href="/appointments" className="text-sm text-primary hover:underline">
              View all
            </a>
          </div>
          <div className="p-6">
            <div className="space-y-4">
              {upcomingAppointments.map((appointment) => (
                <div
                  key={appointment.id}
                  className="flex items-center justify-between py-2"
                >
                  <div className="flex items-center gap-3">
                    <div className="h-10 w-10 rounded-full bg-muted flex items-center justify-center">
                      <span className="text-sm font-medium">
                        {appointment.patient.split(' ').map(n => n[0]).join('')}
                      </span>
                    </div>
                    <div>
                      <p className="font-medium">{appointment.patient}</p>
                      <p className="text-sm text-muted-foreground">
                        {appointment.time} • {appointment.type}
                      </p>
                    </div>
                  </div>
                  <span
                    className={`px-2 py-1 text-xs rounded-full ${
                      appointment.status === 'Confirmed'
                        ? 'bg-green-100 text-green-700'
                        : 'bg-yellow-100 text-yellow-700'
                    }`}
                  >
                    {appointment.status}
                  </span>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Quick actions */}
        <div className="rounded-xl border bg-card shadow-sm">
          <div className="p-6 border-b">
            <h2 className="font-semibold">Quick Actions</h2>
          </div>
          <div className="p-6 grid grid-cols-2 gap-4">
            <button className="p-4 rounded-lg border hover:bg-muted transition-colors text-left">
              <Calendar className="h-6 w-6 text-primary mb-2" />
              <p className="font-medium">Manage Schedule</p>
              <p className="text-sm text-muted-foreground">Set availability</p>
            </button>
            <button className="p-4 rounded-lg border hover:bg-muted transition-colors text-left">
              <Users className="h-6 w-6 text-primary mb-2" />
              <p className="font-medium">View Patients</p>
              <p className="text-sm text-muted-foreground">Patient records</p>
            </button>
            <button className="p-4 rounded-lg border hover:bg-muted transition-colors text-left">
              <Clock className="h-6 w-6 text-primary mb-2" />
              <p className="font-medium">Set Break</p>
              <p className="text-sm text-muted-foreground">Block time slots</p>
            </button>
            <button className="p-4 rounded-lg border hover:bg-muted transition-colors text-left">
              <TrendingUp className="h-6 w-6 text-primary mb-2" />
              <p className="font-medium">Analytics</p>
              <p className="text-sm text-muted-foreground">View insights</p>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
