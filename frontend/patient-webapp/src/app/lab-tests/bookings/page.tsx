import { Metadata } from 'next';
import Link from 'next/link';
import { TestTube, ChevronRight, Clock, CheckCircle, Calendar, MapPin, FileText } from 'lucide-react';

export const metadata: Metadata = {
  title: 'My Lab Bookings | HealthApp',
  description: 'View and track your lab test bookings.',
};

interface LabBooking {
  id: string;
  bookingNumber: string;
  status: 'PENDING' | 'CONFIRMED' | 'SAMPLE_COLLECTED' | 'PROCESSING' | 'REPORT_READY' | 'COMPLETED' | 'CANCELLED';
  labName: string;
  testCount: number;
  total: number;
  createdAt: string;
  scheduledDate: string;
  scheduledSlot: string;
  bookingType: 'HOME_COLLECTION' | 'WALK_IN';
  reportUrl?: string;
  tests: string[];
}

const mockBookings: LabBooking[] = [
  {
    id: '1',
    bookingNumber: 'LB-2024-001234',
    status: 'REPORT_READY',
    labName: 'Apollo Diagnostics',
    testCount: 2,
    total: 899,
    createdAt: '2024-01-15T10:30:00Z',
    scheduledDate: '2024-01-16',
    scheduledSlot: '8:00 AM - 10:00 AM',
    bookingType: 'HOME_COLLECTION',
    reportUrl: '/reports/LB-2024-001234.pdf',
    tests: ['Complete Blood Count', 'Thyroid Profile'],
  },
  {
    id: '2',
    bookingNumber: 'LB-2024-001189',
    status: 'CONFIRMED',
    labName: 'Dr Lal PathLabs',
    testCount: 3,
    total: 1499,
    createdAt: '2024-01-14T14:20:00Z',
    scheduledDate: '2024-01-17',
    scheduledSlot: '6:00 AM - 8:00 AM',
    bookingType: 'HOME_COLLECTION',
    tests: ['Liver Function Test', 'Kidney Function Test', 'Lipid Profile'],
  },
  {
    id: '3',
    bookingNumber: 'LB-2024-001045',
    status: 'COMPLETED',
    labName: 'Thyrocare',
    testCount: 1,
    total: 499,
    createdAt: '2024-01-10T09:15:00Z',
    scheduledDate: '2024-01-11',
    scheduledSlot: '10:00 AM - 12:00 PM',
    bookingType: 'WALK_IN',
    reportUrl: '/reports/LB-2024-001045.pdf',
    tests: ['HbA1c'],
  },
];

const getStatusColor = (status: LabBooking['status']) => {
  switch (status) {
    case 'PENDING':
      return 'bg-yellow-100 text-yellow-700';
    case 'CONFIRMED':
      return 'bg-blue-100 text-blue-700';
    case 'SAMPLE_COLLECTED':
    case 'PROCESSING':
      return 'bg-purple-100 text-purple-700';
    case 'REPORT_READY':
      return 'bg-green-100 text-green-700';
    case 'COMPLETED':
      return 'bg-gray-100 text-gray-700';
    case 'CANCELLED':
      return 'bg-red-100 text-red-700';
    default:
      return 'bg-gray-100 text-gray-700';
  }
};

const getStatusLabel = (status: LabBooking['status']) => {
  switch (status) {
    case 'REPORT_READY':
      return 'Report Ready';
    case 'SAMPLE_COLLECTED':
      return 'Sample Collected';
    default:
      return status.replace(/_/g, ' ').replace(/\b\w/g, c => c.toUpperCase());
  }
};

export default function LabBookingsPage() {
  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="container mx-auto px-4 max-w-3xl">
        <h1 className="text-3xl font-bold mb-8">My Lab Bookings</h1>

        {/* Tabs */}
        <div className="flex gap-4 mb-6 overflow-x-auto">
          {['All', 'Upcoming', 'Reports Ready', 'Completed'].map(tab => (
            <button
              key={tab}
              className={`px-4 py-2 rounded-full text-sm font-medium whitespace-nowrap ${
                tab === 'All'
                  ? 'bg-purple-600 text-white'
                  : 'bg-white text-gray-600 hover:bg-gray-100'
              }`}
            >
              {tab}
            </button>
          ))}
        </div>

        {/* Bookings List */}
        <div className="space-y-4">
          {mockBookings.map(booking => (
            <div
              key={booking.id}
              className="bg-white rounded-xl shadow-sm p-6"
            >
              <div className="flex justify-between items-start mb-4">
                <div>
                  <p className="font-semibold">{booking.bookingNumber}</p>
                  <p className="text-sm text-gray-500">{booking.labName}</p>
                </div>
                <span className={`px-3 py-1 rounded-full text-sm font-medium ${getStatusColor(booking.status)}`}>
                  {getStatusLabel(booking.status)}
                </span>
              </div>

              <div className="flex flex-wrap gap-2 mb-4">
                {booking.tests.map((test, i) => (
                  <span key={i} className="text-xs bg-purple-50 text-purple-700 px-2 py-1 rounded">
                    {test}
                  </span>
                ))}
              </div>

              <div className="flex items-center gap-4 text-sm text-gray-500 mb-4">
                <div className="flex items-center gap-1">
                  <Calendar className="h-4 w-4" />
                  <span>{new Date(booking.scheduledDate).toLocaleDateString('en-IN', {
                    day: 'numeric',
                    month: 'short',
                  })}</span>
                </div>
                <div className="flex items-center gap-1">
                  <Clock className="h-4 w-4" />
                  <span>{booking.scheduledSlot}</span>
                </div>
                <div className="flex items-center gap-1">
                  <MapPin className="h-4 w-4" />
                  <span>{booking.bookingType === 'HOME_COLLECTION' ? 'Home' : 'Walk-in'}</span>
                </div>
              </div>

              <div className="flex justify-between items-center pt-4 border-t">
                <span className="font-bold text-lg">₹{booking.total}</span>
                
                <div className="flex gap-2">
                  {booking.reportUrl && (
                    <Link
                      href={booking.reportUrl}
                      className="flex items-center gap-1 bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded-lg text-sm font-medium"
                    >
                      <FileText className="h-4 w-4" />
                      View Report
                    </Link>
                  )}
                  <Link
                    href={`/lab-tests/bookings/${booking.bookingNumber}`}
                    className="flex items-center gap-1 bg-gray-100 hover:bg-gray-200 px-4 py-2 rounded-lg text-sm font-medium"
                  >
                    Details
                    <ChevronRight className="h-4 w-4" />
                  </Link>
                </div>
              </div>
            </div>
          ))}
        </div>

        {mockBookings.length === 0 && (
          <div className="text-center py-16">
            <TestTube className="h-16 w-16 mx-auto text-gray-300 mb-4" />
            <h2 className="text-xl font-semibold mb-2">No bookings yet</h2>
            <p className="text-gray-500 mb-6">Book lab tests to see them here</p>
            <Link
              href="/lab-tests"
              className="inline-block bg-purple-600 hover:bg-purple-700 text-white font-semibold px-6 py-3 rounded-lg"
            >
              Browse Lab Tests
            </Link>
          </div>
        )}
      </div>
    </div>
  );
}
