'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { ShoppingCart, Minus, Plus, Trash2, MapPin, CreditCard, Truck, Tag } from 'lucide-react';

interface CartItem {
  id: string;
  productId: string;
  productName: string;
  manufacturer: string;
  quantity: number;
  unitPrice: number;
  mrp: number;
  requiresPrescription: boolean;
  imageUrl?: string;
}

interface CartSummary {
  itemCount: number;
  subtotal: number;
  discount: number;
  deliveryFee: number;
  packagingFee: number;
  total: number;
  couponCode?: string;
  couponDiscount?: number;
}

// Mock data - in real app, fetch from API
const mockCart: CartItem[] = [
  { id: '1', productId: 'med-1', productName: 'Dolo 650mg Tablet', manufacturer: 'Micro Labs', quantity: 2, unitPrice: 25, mrp: 30, requiresPrescription: false },
  { id: '2', productId: 'med-2', productName: 'Crocin Advance 500mg', manufacturer: 'GSK', quantity: 1, unitPrice: 30, mrp: 35, requiresPrescription: false },
  { id: '3', productId: 'med-3', productName: 'Augmentin 625mg Tablet', manufacturer: 'GSK', quantity: 1, unitPrice: 250, mrp: 290, requiresPrescription: true },
];

export default function CartPage() {
  const [items, setItems] = useState<CartItem[]>(mockCart);
  const [couponCode, setCouponCode] = useState('');
  const [appliedCoupon, setAppliedCoupon] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const calculateSummary = (): CartSummary => {
    const subtotal = items.reduce((sum, item) => sum + item.unitPrice * item.quantity, 0);
    const mrpTotal = items.reduce((sum, item) => sum + item.mrp * item.quantity, 0);
    const discount = mrpTotal - subtotal;
    const deliveryFee = subtotal >= 500 ? 0 : 49;
    const packagingFee = 10;
    const couponDiscount = appliedCoupon ? Math.floor(subtotal * 0.1) : 0;
    const total = subtotal - couponDiscount + deliveryFee + packagingFee;

    return {
      itemCount: items.reduce((sum, item) => sum + item.quantity, 0),
      subtotal,
      discount,
      deliveryFee,
      packagingFee,
      total,
      couponCode: appliedCoupon || undefined,
      couponDiscount,
    };
  };

  const updateQuantity = (id: string, delta: number) => {
    setItems(prev =>
      prev.map(item =>
        item.id === id
          ? { ...item, quantity: Math.max(1, item.quantity + delta) }
          : item
      )
    );
  };

  const removeItem = (id: string) => {
    setItems(prev => prev.filter(item => item.id !== id));
  };

  const applyCoupon = () => {
    if (couponCode.toUpperCase() === 'HEALTH10') {
      setAppliedCoupon(couponCode.toUpperCase());
    }
  };

  const summary = calculateSummary();
  const hasRxItems = items.some(item => item.requiresPrescription);

  if (items.length === 0) {
    return (
      <div className="min-h-screen bg-gray-50 py-16">
        <div className="container mx-auto px-4 text-center">
          <ShoppingCart className="h-20 w-20 mx-auto text-gray-300 mb-6" />
          <h1 className="text-2xl font-bold mb-4">Your cart is empty</h1>
          <p className="text-gray-600 mb-8">Add medicines to your cart to continue</p>
          <Link
            href="/pharmacy"
            className="inline-block bg-green-600 hover:bg-green-700 text-white font-semibold px-8 py-3 rounded-lg transition-colors"
          >
            Browse Medicines
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="container mx-auto px-4">
        <h1 className="text-3xl font-bold mb-8">Your Cart ({summary.itemCount} items)</h1>

        <div className="grid lg:grid-cols-3 gap-8">
          {/* Cart Items */}
          <div className="lg:col-span-2 space-y-4">
            {items.map(item => (
              <div key={item.id} className="bg-white rounded-xl shadow-sm p-4 flex gap-4">
                <div className="w-20 h-20 bg-gray-100 rounded-lg flex-shrink-0" />
                <div className="flex-1">
                  <div className="flex justify-between">
                    <div>
                      <h3 className="font-semibold">{item.productName}</h3>
                      <p className="text-sm text-gray-500">{item.manufacturer}</p>
                      {item.requiresPrescription && (
                        <span className="inline-block mt-1 text-xs bg-red-100 text-red-700 px-2 py-0.5 rounded">
                          Prescription Required
                        </span>
                      )}
                    </div>
                    <button
                      onClick={() => removeItem(item.id)}
                      className="text-gray-400 hover:text-red-500"
                    >
                      <Trash2 className="h-5 w-5" />
                    </button>
                  </div>
                  <div className="flex justify-between items-end mt-4">
                    <div className="flex items-center gap-2">
                      <button
                        onClick={() => updateQuantity(item.id, -1)}
                        className="p-1 border rounded hover:bg-gray-50"
                      >
                        <Minus className="h-4 w-4" />
                      </button>
                      <span className="w-8 text-center font-medium">{item.quantity}</span>
                      <button
                        onClick={() => updateQuantity(item.id, 1)}
                        className="p-1 border rounded hover:bg-gray-50"
                      >
                        <Plus className="h-4 w-4" />
                      </button>
                    </div>
                    <div className="text-right">
                      <p className="font-bold text-green-600">₹{item.unitPrice * item.quantity}</p>
                      <p className="text-sm text-gray-400 line-through">₹{item.mrp * item.quantity}</p>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>

          {/* Order Summary */}
          <div className="lg:col-span-1">
            <div className="bg-white rounded-xl shadow-sm p-6 sticky top-4">
              <h2 className="text-lg font-bold mb-4">Order Summary</h2>

              {/* Coupon Code */}
              <div className="mb-6">
                <div className="flex gap-2">
                  <div className="relative flex-1">
                    <Tag className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
                    <input
                      type="text"
                      placeholder="Enter coupon code"
                      value={couponCode}
                      onChange={(e) => setCouponCode(e.target.value)}
                      className="w-full pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                    />
                  </div>
                  <button
                    onClick={applyCoupon}
                    className="px-4 py-2 bg-gray-100 hover:bg-gray-200 rounded-lg font-medium transition-colors"
                  >
                    Apply
                  </button>
                </div>
                {appliedCoupon && (
                  <p className="text-green-600 text-sm mt-2">✓ Coupon {appliedCoupon} applied!</p>
                )}
              </div>

              {/* Price Breakdown */}
              <div className="space-y-3 text-sm mb-6">
                <div className="flex justify-between">
                  <span className="text-gray-600">Subtotal</span>
                  <span>₹{summary.subtotal}</span>
                </div>
                <div className="flex justify-between text-green-600">
                  <span>Discount</span>
                  <span>-₹{summary.discount}</span>
                </div>
                {summary.couponDiscount && summary.couponDiscount > 0 && (
                  <div className="flex justify-between text-green-600">
                    <span>Coupon Discount</span>
                    <span>-₹{summary.couponDiscount}</span>
                  </div>
                )}
                <div className="flex justify-between">
                  <span className="text-gray-600">Delivery Fee</span>
                  <span>{summary.deliveryFee === 0 ? 'FREE' : `₹${summary.deliveryFee}`}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-gray-600">Packaging Fee</span>
                  <span>₹{summary.packagingFee}</span>
                </div>
                <div className="border-t pt-3 flex justify-between font-bold text-lg">
                  <span>Total</span>
                  <span className="text-green-600">₹{summary.total}</span>
                </div>
              </div>

              {/* Savings Message */}
              {summary.discount > 0 && (
                <div className="bg-green-50 p-3 rounded-lg mb-6">
                  <p className="text-green-700 text-sm font-medium">
                    🎉 You save ₹{summary.discount + (summary.couponDiscount || 0)} on this order!
                  </p>
                </div>
              )}

              {/* Prescription Warning */}
              {hasRxItems && (
                <div className="bg-yellow-50 border border-yellow-200 p-3 rounded-lg mb-6">
                  <p className="text-yellow-800 text-sm">
                    ⚠️ Your cart contains prescription items. Please ensure you've uploaded a valid prescription.
                  </p>
                </div>
              )}

              {/* Checkout Button */}
              <Link
                href="/pharmacy/checkout"
                className="block w-full bg-green-600 hover:bg-green-700 text-white font-semibold py-4 rounded-lg text-center transition-colors"
              >
                Proceed to Checkout
              </Link>

              {/* Delivery Info */}
              <div className="mt-6 flex items-center gap-2 text-sm text-gray-500">
                <Truck className="h-4 w-4" />
                <span>Free delivery on orders above ₹500</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
