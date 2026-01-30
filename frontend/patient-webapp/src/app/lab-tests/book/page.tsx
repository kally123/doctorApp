'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import { 
  ArrowLeft, 
  TestTube, 
  Clock, 
  Home, 
  MapPin, 
  Calendar,
  Check,
  AlertCircle,
  User,
  Phone
} from 'lucide-react';

interface SelectedTest {
  id: string;
  name: string;
  price: number;
  sampleType: string;
  fastingRequired: boolean;
}

const mockSelectedTests: SelectedTest[] = [
  { id: '1', name: 'Complete Blood Count (CBC)', price: 350, sampleType: 'Blood', fastingRequired: false },
  { id: '2', name: 'Thyroid Profile', price: 499, sampleType: 'Blood', fastingRequired: true },
];

const labPartners = [
  { id: '1', name: 'Apollo Diagnostics', rating: 4.8, reviews: 2450, distance: '2.5 km', price: 849 },
  { id: '2', name: 'Dr Lal PathLabs', rating: 4.7, reviews: 3200, distance: '3.1 km', price: 899 },
  { id: '3', name: 'Thyrocare', rating: 4.6, reviews: 1890, distance: '4.2 km', price: 799 },
];

const timeSlots = [
  { id: '1', label: '6:00 AM - 8:00 AM', available: true },
  { id: '2', label: '8:00 AM - 10:00 AM', available: true },
  { id: '3', label: '10:00 AM - 12:00 PM', available: true },
  { id: '4', label: '4:00 PM - 6:00 PM', available: false },
  { id: '5', label: '6:00 PM - 8:00 PM', available: true },
];

export default function LabBookingPage() {
  const router = useRouter();
  const [step, setStep] = useState(1);
  const [bookingType, setBookingType] = useState<'home' | 'walkin'>('home');
  const [selectedLab, setSelectedLab] = useState(labPartners[0].id);
  const [selectedDate, setSelectedDate] = useState<string | null>(null);
  const [selectedSlot, setSelectedSlot] = useState<string | null>(null);
  const [patientDetails, setPatientDetails] = useState({
    name: '',
    phone: '',
    age: '',
    gender: 'male',
  });
  const [loading, setLoading] = useState(false);

  const selectedLabPartner = labPartners.find(l => l.id === selectedLab);
  const totalPrice = mockSelectedTests.reduce((sum, t) => sum + t.price, 0);
  const homeCollectionFee = bookingType === 'home' ? 50 : 0;
  const finalPrice = totalPrice + homeCollectionFee;

  // Generate next 7 days
  const dates = Array.from({ length: 7 }, (_, i) => {
    const date = new Date();
    date.setDate(date.getDate() + i);
    return {
      value: date.toISOString().split('T')[0],
      day: date.toLocaleDateString('en-IN', { weekday: 'short' }),
      date: date.getDate(),
      month: date.toLocaleDateString('en-IN', { month: 'short' }),
    };
  });

  const handleSubmit = async () => {
    setLoading(true);
    try {
      await new Promise(resolve => setTimeout(resolve, 2000));
      router.push('/lab-tests/bookings/LB-123456');
    } catch (error) {
      console.error('Booking failed', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="container mx-auto px-4 max-w-3xl">
        {/* Header */}
        <div className="flex items-center gap-4 mb-6">
          <Link href="/lab-tests" className="p-2 hover:bg-gray-100 rounded-lg">
            <ArrowLeft className="h-5 w-5" />
          </Link>
          <h1 className="text-2xl font-bold">Book Lab Tests</h1>
        </div>

        {/* Progress Steps */}
        <div className="flex items-center gap-2 mb-8">
          {[1, 2, 3].map((s) => (
            <div key={s} className="flex items-center flex-1">
              <div className={`w-8 h-8 rounded-full flex items-center justify-center text-sm font-bold ${
                s <= step ? 'bg-purple-600 text-white' : 'bg-gray-200 text-gray-500'
              }`}>
                {s < step ? <Check className="h-4 w-4" /> : s}
              </div>
              <span className={`ml-2 text-sm ${s === step ? 'font-medium' : 'text-gray-500'}`}>
                {s === 1 ? 'Schedule' : s === 2 ? 'Details' : 'Confirm'}
              </span>
              {s < 3 && <div className={`flex-1 h-1 mx-4 rounded ${s < step ? 'bg-purple-600' : 'bg-gray-200'}`} />}
            </div>
          ))}
        </div>

        {/* Selected Tests Summary */}
        <div className="bg-white rounded-xl shadow-sm p-4 mb-6">
          <h2 className="font-semibold mb-3">Selected Tests ({mockSelectedTests.length})</h2>
          <div className="space-y-2">
            {mockSelectedTests.map(test => (
              <div key={test.id} className="flex items-center justify-between text-sm">
                <div className="flex items-center gap-2">
                  <TestTube className="h-4 w-4 text-purple-600" />
                  <span>{test.name}</span>
                  {test.fastingRequired && (
                    <span className="text-xs bg-yellow-100 text-yellow-700 px-2 py-0.5 rounded">
                      Fasting
                    </span>
                  )}
                </div>
                <span className="font-medium">₹{test.price}</span>
              </div>
            ))}
          </div>
        </div>

        {/* Step 1: Schedule */}
        {step === 1 && (
          <div className="space-y-6">
            {/* Collection Type */}
            <section className="bg-white rounded-xl shadow-sm p-6">
              <h2 className="font-semibold mb-4">Collection Type</h2>
              <div className="grid grid-cols-2 gap-4">
                <button
                  onClick={() => setBookingType('home')}
                  className={`p-4 rounded-lg border-2 text-left transition-colors ${
                    bookingType === 'home'
                      ? 'border-purple-600 bg-purple-50'
                      : 'border-gray-200 hover:border-gray-300'
                  }`}
                >
                  <Home className="h-6 w-6 text-purple-600 mb-2" />
                  <p className="font-medium">Home Collection</p>
                  <p className="text-sm text-gray-500">Sample collected at home</p>
                  <p className="text-sm text-purple-600 mt-1">+₹50</p>
                </button>
                <button
                  onClick={() => setBookingType('walkin')}
                  className={`p-4 rounded-lg border-2 text-left transition-colors ${
                    bookingType === 'walkin'
                      ? 'border-purple-600 bg-purple-50'
                      : 'border-gray-200 hover:border-gray-300'
                  }`}
                >
                  <MapPin className="h-6 w-6 text-purple-600 mb-2" />
                  <p className="font-medium">Walk-in</p>
                  <p className="text-sm text-gray-500">Visit the lab center</p>
                  <p className="text-sm text-green-600 mt-1">Free</p>
                </button>
              </div>
            </section>

            {/* Lab Partner */}
            <section className="bg-white rounded-xl shadow-sm p-6">
              <h2 className="font-semibold mb-4">Select Lab</h2>
              <div className="space-y-3">
                {labPartners.map(lab => (
                  <label
                    key={lab.id}
                    className={`flex items-center justify-between p-4 rounded-lg border cursor-pointer transition-colors ${
                      selectedLab === lab.id
                        ? 'border-purple-600 bg-purple-50'
                        : 'border-gray-200 hover:border-gray-300'
                    }`}
                  >
                    <div className="flex items-center gap-3">
                      <input
                        type="radio"
                        name="lab"
                        checked={selectedLab === lab.id}
                        onChange={() => setSelectedLab(lab.id)}
                      />
                      <div>
                        <p className="font-medium">{lab.name}</p>
                        <div className="flex items-center gap-2 text-sm text-gray-500">
                          <span>⭐ {lab.rating}</span>
                          <span>({lab.reviews} reviews)</span>
                          <span>•</span>
                          <span>{lab.distance}</span>
                        </div>
                      </div>
                    </div>
                    <span className="font-bold text-purple-600">₹{lab.price}</span>
                  </label>
                ))}
              </div>
            </section>

            {/* Date Selection */}
            <section className="bg-white rounded-xl shadow-sm p-6">
              <h2 className="font-semibold mb-4">Select Date</h2>
              <div className="flex gap-3 overflow-x-auto pb-2">
                {dates.map(date => (
                  <button
                    key={date.value}
                    onClick={() => setSelectedDate(date.value)}
                    className={`flex-shrink-0 w-16 py-3 rounded-lg border text-center transition-colors ${
                      selectedDate === date.value
                        ? 'border-purple-600 bg-purple-600 text-white'
                        : 'border-gray-200 hover:border-gray-300'
                    }`}
                  >
                    <p className="text-xs">{date.day}</p>
                    <p className="text-lg font-bold">{date.date}</p>
                    <p className="text-xs">{date.month}</p>
                  </button>
                ))}
              </div>
            </section>

            {/* Time Slot */}
            {selectedDate && (
              <section className="bg-white rounded-xl shadow-sm p-6">
                <h2 className="font-semibold mb-4">Select Time Slot</h2>
                <div className="grid grid-cols-2 gap-3">
                  {timeSlots.map(slot => (
                    <button
                      key={slot.id}
                      onClick={() => slot.available && setSelectedSlot(slot.id)}
                      disabled={!slot.available}
                      className={`p-3 rounded-lg border text-center transition-colors ${
                        selectedSlot === slot.id
                          ? 'border-purple-600 bg-purple-50 text-purple-600'
                          : slot.available
                          ? 'border-gray-200 hover:border-gray-300'
                          : 'border-gray-100 bg-gray-50 text-gray-400 cursor-not-allowed'
                      }`}
                    >
                      <Clock className="h-4 w-4 mx-auto mb-1" />
                      <p className="text-sm font-medium">{slot.label}</p>
                    </button>
                  ))}
                </div>
              </section>
            )}

            <button
              onClick={() => setStep(2)}
              disabled={!selectedDate || !selectedSlot}
              className="w-full bg-purple-600 hover:bg-purple-700 disabled:bg-gray-300 text-white font-bold py-4 rounded-xl transition-colors"
            >
              Continue
            </button>
          </div>
        )}

        {/* Step 2: Patient Details */}
        {step === 2 && (
          <div className="space-y-6">
            <section className="bg-white rounded-xl shadow-sm p-6">
              <h2 className="font-semibold mb-4">Patient Details</h2>
              <div className="space-y-4">
                <div>
                  <label className="block text-sm font-medium mb-1">Full Name</label>
                  <div className="relative">
                    <User className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400" />
                    <input
                      type="text"
                      value={patientDetails.name}
                      onChange={(e) => setPatientDetails({ ...patientDetails, name: e.target.value })}
                      placeholder="Enter patient name"
                      className="w-full pl-10 pr-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500"
                    />
                  </div>
                </div>
                <div>
                  <label className="block text-sm font-medium mb-1">Phone Number</label>
                  <div className="relative">
                    <Phone className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400" />
                    <input
                      type="tel"
                      value={patientDetails.phone}
                      onChange={(e) => setPatientDetails({ ...patientDetails, phone: e.target.value })}
                      placeholder="Enter phone number"
                      className="w-full pl-10 pr-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500"
                    />
                  </div>
                </div>
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="block text-sm font-medium mb-1">Age</label>
                    <input
                      type="number"
                      value={patientDetails.age}
                      onChange={(e) => setPatientDetails({ ...patientDetails, age: e.target.value })}
                      placeholder="Age"
                      className="w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500"
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium mb-1">Gender</label>
                    <select
                      value={patientDetails.gender}
                      onChange={(e) => setPatientDetails({ ...patientDetails, gender: e.target.value })}
                      className="w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500"
                    >
                      <option value="male">Male</option>
                      <option value="female">Female</option>
                      <option value="other">Other</option>
                    </select>
                  </div>
                </div>
              </div>
            </section>

            {/* Fasting Notice */}
            {mockSelectedTests.some(t => t.fastingRequired) && (
              <div className="bg-yellow-50 border border-yellow-200 rounded-xl p-4 flex gap-3">
                <AlertCircle className="h-5 w-5 text-yellow-600 flex-shrink-0 mt-0.5" />
                <div>
                  <p className="font-medium text-yellow-800">Fasting Required</p>
                  <p className="text-sm text-yellow-700">
                    Some tests require 8-12 hours of fasting. Drink only water before sample collection.
                  </p>
                </div>
              </div>
            )}

            <div className="flex gap-4">
              <button
                onClick={() => setStep(1)}
                className="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 font-bold py-4 rounded-xl transition-colors"
              >
                Back
              </button>
              <button
                onClick={() => setStep(3)}
                disabled={!patientDetails.name || !patientDetails.phone}
                className="flex-1 bg-purple-600 hover:bg-purple-700 disabled:bg-gray-300 text-white font-bold py-4 rounded-xl transition-colors"
              >
                Continue
              </button>
            </div>
          </div>
        )}

        {/* Step 3: Confirm & Pay */}
        {step === 3 && (
          <div className="space-y-6">
            <section className="bg-white rounded-xl shadow-sm p-6">
              <h2 className="font-semibold mb-4">Booking Summary</h2>
              
              <div className="space-y-4 text-sm">
                <div className="flex justify-between">
                  <span className="text-gray-600">Lab Partner</span>
                  <span className="font-medium">{selectedLabPartner?.name}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-gray-600">Collection Type</span>
                  <span className="font-medium">{bookingType === 'home' ? 'Home Collection' : 'Walk-in'}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-gray-600">Date & Time</span>
                  <span className="font-medium">
                    {dates.find(d => d.value === selectedDate)?.date} {dates.find(d => d.value === selectedDate)?.month}, 
                    {timeSlots.find(s => s.id === selectedSlot)?.label}
                  </span>
                </div>
                <div className="flex justify-between">
                  <span className="text-gray-600">Patient</span>
                  <span className="font-medium">{patientDetails.name}</span>
                </div>
              </div>
            </section>

            <section className="bg-white rounded-xl shadow-sm p-6">
              <h2 className="font-semibold mb-4">Payment Summary</h2>
              <div className="space-y-2 text-sm">
                <div className="flex justify-between">
                  <span className="text-gray-600">Tests Total</span>
                  <span>₹{totalPrice}</span>
                </div>
                {homeCollectionFee > 0 && (
                  <div className="flex justify-between">
                    <span className="text-gray-600">Home Collection Fee</span>
                    <span>₹{homeCollectionFee}</span>
                  </div>
                )}
                <div className="border-t pt-2 flex justify-between font-bold text-lg">
                  <span>Total</span>
                  <span className="text-purple-600">₹{finalPrice}</span>
                </div>
              </div>
            </section>

            <div className="flex gap-4">
              <button
                onClick={() => setStep(2)}
                className="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-700 font-bold py-4 rounded-xl transition-colors"
              >
                Back
              </button>
              <button
                onClick={handleSubmit}
                disabled={loading}
                className="flex-1 bg-purple-600 hover:bg-purple-700 disabled:bg-gray-300 text-white font-bold py-4 rounded-xl transition-colors flex items-center justify-center gap-2"
              >
                {loading ? (
                  <>
                    <div className="h-5 w-5 border-2 border-white border-t-transparent rounded-full animate-spin" />
                    Processing...
                  </>
                ) : (
                  `Pay ₹${finalPrice}`
                )}
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
