'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { useParams } from 'next/navigation';
import { 
  Package, 
  MapPin, 
  Phone, 
  CheckCircle, 
  Clock, 
  Truck, 
  Home,
  ArrowLeft,
  Download,
  HelpCircle
} from 'lucide-react';

interface OrderStep {
  status: string;
  label: string;
  description: string;
  time?: string;
  completed: boolean;
  current: boolean;
}

interface OrderDetail {
  orderNumber: string;
  status: string;
  paymentStatus: string;
  createdAt: string;
  confirmedAt?: string;
  shippedAt?: string;
  deliveredAt?: string;
  estimatedDelivery: string;
  deliveryAddress: {
    recipientName: string;
    phone: string;
    address: string;
    city: string;
    pincode: string;
  };
  items: {
    id: string;
    name: string;
    manufacturer: string;
    quantity: number;
    unitPrice: number;
    total: number;
  }[];
  subtotal: number;
  discount: number;
  deliveryFee: number;
  packagingFee: number;
  total: number;
  deliveryAgent?: {
    name: string;
    phone: string;
  };
}

const mockOrder: OrderDetail = {
  orderNumber: 'ORD-2024-001234',
  status: 'OUT_FOR_DELIVERY',
  paymentStatus: 'COMPLETED',
  createdAt: '2024-01-15T10:30:00Z',
  confirmedAt: '2024-01-15T10:35:00Z',
  shippedAt: '2024-01-15T14:00:00Z',
  estimatedDelivery: 'Today by 6 PM',
  deliveryAddress: {
    recipientName: 'John Doe',
    phone: '+91 98765 43210',
    address: '123 Main Street, Apt 4B, Near City Mall',
    city: 'Mumbai',
    pincode: '400001',
  },
  items: [
    { id: '1', name: 'Dolo 650mg Tablet', manufacturer: 'Micro Labs', quantity: 2, unitPrice: 25, total: 50 },
    { id: '2', name: 'Crocin Advance 500mg', manufacturer: 'GSK', quantity: 1, unitPrice: 30, total: 30 },
    { id: '3', name: 'Augmentin 625mg Tablet', manufacturer: 'GSK', quantity: 1, unitPrice: 250, total: 250 },
  ],
  subtotal: 330,
  discount: 45,
  deliveryFee: 49,
  packagingFee: 10,
  total: 344,
  deliveryAgent: {
    name: 'Rajesh Kumar',
    phone: '+91 98765 00000',
  },
};

export default function OrderDetailPage() {
  const params = useParams();
  const [order, setOrder] = useState<OrderDetail | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Simulate API call
    setTimeout(() => {
      setOrder(mockOrder);
      setLoading(false);
    }, 500);
  }, [params.orderNumber]);

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50 py-8 flex items-center justify-center">
        <div className="h-8 w-8 border-4 border-green-600 border-t-transparent rounded-full animate-spin" />
      </div>
    );
  }

  if (!order) {
    return (
      <div className="min-h-screen bg-gray-50 py-8">
        <div className="container mx-auto px-4 text-center">
          <h1 className="text-2xl font-bold mb-4">Order not found</h1>
          <Link href="/pharmacy/orders" className="text-green-600 hover:underline">
            Back to Orders
          </Link>
        </div>
      </div>
    );
  }

  const steps: OrderStep[] = [
    { status: 'CONFIRMED', label: 'Order Confirmed', description: 'Your order has been placed', time: order.confirmedAt, completed: true, current: false },
    { status: 'PROCESSING', label: 'Processing', description: 'Pharmacy is preparing your order', completed: true, current: false },
    { status: 'SHIPPED', label: 'Shipped', description: 'Order picked up for delivery', time: order.shippedAt, completed: true, current: false },
    { status: 'OUT_FOR_DELIVERY', label: 'Out for Delivery', description: 'Order is on the way', completed: true, current: true },
    { status: 'DELIVERED', label: 'Delivered', description: 'Order delivered successfully', completed: false, current: false },
  ];

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="container mx-auto px-4 max-w-3xl">
        {/* Header */}
        <div className="flex items-center gap-4 mb-6">
          <Link href="/pharmacy/orders" className="p-2 hover:bg-gray-100 rounded-lg">
            <ArrowLeft className="h-5 w-5" />
          </Link>
          <div>
            <h1 className="text-2xl font-bold">{order.orderNumber}</h1>
            <p className="text-sm text-gray-500">
              Placed on {new Date(order.createdAt).toLocaleDateString('en-IN', {
                day: 'numeric',
                month: 'long',
                year: 'numeric',
                hour: '2-digit',
                minute: '2-digit',
              })}
            </p>
          </div>
        </div>

        {/* Delivery Progress */}
        <section className="bg-white rounded-xl shadow-sm p-6 mb-6">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-lg font-bold">Delivery Status</h2>
            <span className="text-green-600 font-medium">{order.estimatedDelivery}</span>
          </div>

          <div className="relative">
            {steps.map((step, index) => (
              <div key={step.status} className="flex gap-4 pb-6 last:pb-0">
                <div className="flex flex-col items-center">
                  <div className={`w-8 h-8 rounded-full flex items-center justify-center ${
                    step.completed 
                      ? 'bg-green-600 text-white' 
                      : 'bg-gray-200 text-gray-400'
                  }`}>
                    {step.completed ? (
                      <CheckCircle className="h-5 w-5" />
                    ) : (
                      <Clock className="h-5 w-5" />
                    )}
                  </div>
                  {index < steps.length - 1 && (
                    <div className={`w-0.5 flex-1 ${
                      step.completed ? 'bg-green-600' : 'bg-gray-200'
                    }`} />
                  )}
                </div>
                <div className="flex-1 pb-4">
                  <p className={`font-medium ${step.current ? 'text-green-600' : ''}`}>
                    {step.label}
                  </p>
                  <p className="text-sm text-gray-500">{step.description}</p>
                  {step.time && (
                    <p className="text-xs text-gray-400 mt-1">
                      {new Date(step.time).toLocaleTimeString('en-IN', {
                        hour: '2-digit',
                        minute: '2-digit',
                      })}
                    </p>
                  )}
                </div>
              </div>
            ))}
          </div>

          {/* Delivery Agent */}
          {order.deliveryAgent && (
            <div className="mt-4 pt-4 border-t flex items-center justify-between">
              <div className="flex items-center gap-3">
                <div className="w-10 h-10 bg-gray-200 rounded-full flex items-center justify-center">
                  <Truck className="h-5 w-5 text-gray-500" />
                </div>
                <div>
                  <p className="font-medium">{order.deliveryAgent.name}</p>
                  <p className="text-sm text-gray-500">Delivery Partner</p>
                </div>
              </div>
              <a
                href={`tel:${order.deliveryAgent.phone}`}
                className="flex items-center gap-2 bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg"
              >
                <Phone className="h-4 w-4" />
                Call
              </a>
            </div>
          )}
        </section>

        {/* Delivery Address */}
        <section className="bg-white rounded-xl shadow-sm p-6 mb-6">
          <div className="flex items-center gap-2 mb-4">
            <Home className="h-5 w-5 text-green-600" />
            <h2 className="text-lg font-bold">Delivery Address</h2>
          </div>
          <div className="text-sm">
            <p className="font-medium">{order.deliveryAddress.recipientName}</p>
            <p className="text-gray-600">{order.deliveryAddress.address}</p>
            <p className="text-gray-600">
              {order.deliveryAddress.city} - {order.deliveryAddress.pincode}
            </p>
            <p className="text-gray-600">{order.deliveryAddress.phone}</p>
          </div>
        </section>

        {/* Order Items */}
        <section className="bg-white rounded-xl shadow-sm p-6 mb-6">
          <div className="flex items-center gap-2 mb-4">
            <Package className="h-5 w-5 text-green-600" />
            <h2 className="text-lg font-bold">Order Items ({order.items.length})</h2>
          </div>
          <div className="space-y-4">
            {order.items.map(item => (
              <div key={item.id} className="flex gap-4">
                <div className="w-16 h-16 bg-gray-100 rounded-lg" />
                <div className="flex-1">
                  <p className="font-medium">{item.name}</p>
                  <p className="text-sm text-gray-500">{item.manufacturer}</p>
                  <p className="text-sm text-gray-500">Qty: {item.quantity}</p>
                </div>
                <div className="text-right">
                  <p className="font-bold">₹{item.total}</p>
                </div>
              </div>
            ))}
          </div>
        </section>

        {/* Payment Summary */}
        <section className="bg-white rounded-xl shadow-sm p-6 mb-6">
          <h2 className="text-lg font-bold mb-4">Payment Summary</h2>
          <div className="space-y-2 text-sm">
            <div className="flex justify-between">
              <span className="text-gray-600">Subtotal</span>
              <span>₹{order.subtotal}</span>
            </div>
            <div className="flex justify-between text-green-600">
              <span>Discount</span>
              <span>-₹{order.discount}</span>
            </div>
            <div className="flex justify-between">
              <span className="text-gray-600">Delivery Fee</span>
              <span>₹{order.deliveryFee}</span>
            </div>
            <div className="flex justify-between">
              <span className="text-gray-600">Packaging Fee</span>
              <span>₹{order.packagingFee}</span>
            </div>
            <div className="border-t pt-2 flex justify-between font-bold text-lg">
              <span>Total</span>
              <span className="text-green-600">₹{order.total}</span>
            </div>
          </div>
          <div className="mt-4 pt-4 border-t flex items-center justify-between text-sm">
            <span className="text-gray-600">Payment Status</span>
            <span className="bg-green-100 text-green-700 px-3 py-1 rounded-full font-medium">
              {order.paymentStatus === 'COMPLETED' ? 'Paid' : 'Pending'}
            </span>
          </div>
        </section>

        {/* Actions */}
        <div className="flex gap-4">
          <button className="flex-1 flex items-center justify-center gap-2 bg-white border py-3 rounded-lg hover:bg-gray-50">
            <Download className="h-5 w-5" />
            Download Invoice
          </button>
          <button className="flex-1 flex items-center justify-center gap-2 bg-white border py-3 rounded-lg hover:bg-gray-50">
            <HelpCircle className="h-5 w-5" />
            Need Help?
          </button>
        </div>
      </div>
    </div>
  );
}
