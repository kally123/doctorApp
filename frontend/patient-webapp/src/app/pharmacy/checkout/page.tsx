'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { MapPin, CreditCard, Truck, Clock, Plus, Check } from 'lucide-react';

interface Address {
  id: string;
  label: string;
  recipientName: string;
  phone: string;
  addressLine1: string;
  addressLine2?: string;
  city: string;
  state: string;
  pincode: string;
  isDefault: boolean;
}

const mockAddresses: Address[] = [
  {
    id: '1',
    label: 'Home',
    recipientName: 'John Doe',
    phone: '+91 98765 43210',
    addressLine1: '123 Main Street, Apt 4B',
    addressLine2: 'Near City Mall',
    city: 'Mumbai',
    state: 'Maharashtra',
    pincode: '400001',
    isDefault: true,
  },
  {
    id: '2',
    label: 'Office',
    recipientName: 'John Doe',
    phone: '+91 98765 43210',
    addressLine1: 'Tech Park, Building A',
    city: 'Mumbai',
    state: 'Maharashtra',
    pincode: '400002',
    isDefault: false,
  },
];

const deliverySlots = [
  { id: '1', label: 'Express (2-4 hours)', time: 'Today, 2-4 PM', fee: 49 },
  { id: '2', label: 'Standard (Same day)', time: 'Today, 6-9 PM', fee: 29 },
  { id: '3', label: 'Scheduled', time: 'Tomorrow, 10 AM - 1 PM', fee: 0 },
];

const paymentMethods = [
  { id: 'upi', name: 'UPI', description: 'Pay using any UPI app', icon: '📱' },
  { id: 'card', name: 'Credit/Debit Card', description: 'Visa, Mastercard, RuPay', icon: '💳' },
  { id: 'netbanking', name: 'Net Banking', description: 'All major banks', icon: '🏦' },
  { id: 'cod', name: 'Cash on Delivery', description: 'Pay when delivered', icon: '💵' },
];

export default function CheckoutPage() {
  const router = useRouter();
  const [selectedAddress, setSelectedAddress] = useState(mockAddresses[0].id);
  const [selectedSlot, setSelectedSlot] = useState(deliverySlots[0].id);
  const [selectedPayment, setSelectedPayment] = useState('upi');
  const [loading, setLoading] = useState(false);

  const selectedDeliverySlot = deliverySlots.find(s => s.id === selectedSlot);

  const handlePlaceOrder = async () => {
    setLoading(true);
    try {
      // In real app, call API to create order
      await new Promise(resolve => setTimeout(resolve, 2000));
      router.push('/pharmacy/orders/ORD-123456');
    } catch (error) {
      console.error('Failed to place order', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="container mx-auto px-4 max-w-4xl">
        <h1 className="text-3xl font-bold mb-8">Checkout</h1>

        {/* Delivery Address */}
        <section className="bg-white rounded-xl shadow-sm p-6 mb-6">
          <div className="flex items-center gap-2 mb-4">
            <MapPin className="h-5 w-5 text-green-600" />
            <h2 className="text-lg font-bold">Delivery Address</h2>
          </div>
          <div className="grid gap-4">
            {mockAddresses.map(address => (
              <label
                key={address.id}
                className={`flex items-start gap-4 p-4 border rounded-lg cursor-pointer transition-colors ${
                  selectedAddress === address.id
                    ? 'border-green-500 bg-green-50'
                    : 'border-gray-200 hover:border-gray-300'
                }`}
              >
                <input
                  type="radio"
                  name="address"
                  value={address.id}
                  checked={selectedAddress === address.id}
                  onChange={() => setSelectedAddress(address.id)}
                  className="mt-1"
                />
                <div className="flex-1">
                  <div className="flex items-center gap-2 mb-1">
                    <span className="font-semibold">{address.label}</span>
                    {address.isDefault && (
                      <span className="text-xs bg-green-100 text-green-700 px-2 py-0.5 rounded">
                        Default
                      </span>
                    )}
                  </div>
                  <p className="text-sm">{address.recipientName}</p>
                  <p className="text-sm text-gray-600">
                    {address.addressLine1}
                    {address.addressLine2 && `, ${address.addressLine2}`}
                  </p>
                  <p className="text-sm text-gray-600">
                    {address.city}, {address.state} - {address.pincode}
                  </p>
                  <p className="text-sm text-gray-600">{address.phone}</p>
                </div>
                {selectedAddress === address.id && (
                  <Check className="h-5 w-5 text-green-600" />
                )}
              </label>
            ))}
            <button className="flex items-center gap-2 text-green-600 hover:text-green-700 font-medium">
              <Plus className="h-4 w-4" />
              Add New Address
            </button>
          </div>
        </section>

        {/* Delivery Slot */}
        <section className="bg-white rounded-xl shadow-sm p-6 mb-6">
          <div className="flex items-center gap-2 mb-4">
            <Clock className="h-5 w-5 text-green-600" />
            <h2 className="text-lg font-bold">Delivery Time</h2>
          </div>
          <div className="grid gap-3">
            {deliverySlots.map(slot => (
              <label
                key={slot.id}
                className={`flex items-center justify-between p-4 border rounded-lg cursor-pointer transition-colors ${
                  selectedSlot === slot.id
                    ? 'border-green-500 bg-green-50'
                    : 'border-gray-200 hover:border-gray-300'
                }`}
              >
                <div className="flex items-center gap-3">
                  <input
                    type="radio"
                    name="slot"
                    value={slot.id}
                    checked={selectedSlot === slot.id}
                    onChange={() => setSelectedSlot(slot.id)}
                  />
                  <div>
                    <p className="font-medium">{slot.label}</p>
                    <p className="text-sm text-gray-500">{slot.time}</p>
                  </div>
                </div>
                <span className={slot.fee === 0 ? 'text-green-600 font-medium' : 'text-gray-600'}>
                  {slot.fee === 0 ? 'FREE' : `₹${slot.fee}`}
                </span>
              </label>
            ))}
          </div>
        </section>

        {/* Payment Method */}
        <section className="bg-white rounded-xl shadow-sm p-6 mb-6">
          <div className="flex items-center gap-2 mb-4">
            <CreditCard className="h-5 w-5 text-green-600" />
            <h2 className="text-lg font-bold">Payment Method</h2>
          </div>
          <div className="grid gap-3">
            {paymentMethods.map(method => (
              <label
                key={method.id}
                className={`flex items-center justify-between p-4 border rounded-lg cursor-pointer transition-colors ${
                  selectedPayment === method.id
                    ? 'border-green-500 bg-green-50'
                    : 'border-gray-200 hover:border-gray-300'
                }`}
              >
                <div className="flex items-center gap-3">
                  <input
                    type="radio"
                    name="payment"
                    value={method.id}
                    checked={selectedPayment === method.id}
                    onChange={() => setSelectedPayment(method.id)}
                  />
                  <span className="text-2xl">{method.icon}</span>
                  <div>
                    <p className="font-medium">{method.name}</p>
                    <p className="text-sm text-gray-500">{method.description}</p>
                  </div>
                </div>
                {selectedPayment === method.id && (
                  <Check className="h-5 w-5 text-green-600" />
                )}
              </label>
            ))}
          </div>
        </section>

        {/* Order Summary */}
        <section className="bg-white rounded-xl shadow-sm p-6 mb-6">
          <h2 className="text-lg font-bold mb-4">Order Summary</h2>
          <div className="space-y-2 text-sm">
            <div className="flex justify-between">
              <span className="text-gray-600">Items Total</span>
              <span>₹305</span>
            </div>
            <div className="flex justify-between text-green-600">
              <span>Discount</span>
              <span>-₹45</span>
            </div>
            <div className="flex justify-between">
              <span className="text-gray-600">Delivery Fee</span>
              <span>{selectedDeliverySlot?.fee === 0 ? 'FREE' : `₹${selectedDeliverySlot?.fee}`}</span>
            </div>
            <div className="flex justify-between">
              <span className="text-gray-600">Packaging Fee</span>
              <span>₹10</span>
            </div>
            <div className="border-t pt-2 flex justify-between font-bold text-lg">
              <span>Total Payable</span>
              <span className="text-green-600">₹{270 + (selectedDeliverySlot?.fee || 0)}</span>
            </div>
          </div>
        </section>

        {/* Place Order Button */}
        <button
          onClick={handlePlaceOrder}
          disabled={loading}
          className="w-full bg-green-600 hover:bg-green-700 disabled:bg-gray-300 text-white font-bold py-4 rounded-xl text-lg transition-colors flex items-center justify-center gap-2"
        >
          {loading ? (
            <>
              <div className="h-5 w-5 border-2 border-white border-t-transparent rounded-full animate-spin" />
              Processing...
            </>
          ) : (
            <>
              <Truck className="h-5 w-5" />
              Place Order • ₹{270 + (selectedDeliverySlot?.fee || 0)}
            </>
          )}
        </button>
      </div>
    </div>
  );
}
