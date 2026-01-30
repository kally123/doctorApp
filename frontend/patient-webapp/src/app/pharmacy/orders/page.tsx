import { Metadata } from 'next';
import Link from 'next/link';
import { Package, ChevronRight, Clock, CheckCircle, Truck, MapPin } from 'lucide-react';

export const metadata: Metadata = {
  title: 'My Orders | HealthApp Pharmacy',
  description: 'Track your medicine orders and view order history.',
};

interface Order {
  id: string;
  orderNumber: string;
  status: 'PENDING' | 'CONFIRMED' | 'PROCESSING' | 'SHIPPED' | 'OUT_FOR_DELIVERY' | 'DELIVERED' | 'CANCELLED';
  itemCount: number;
  total: number;
  createdAt: string;
  estimatedDelivery?: string;
  items: { name: string; quantity: number }[];
}

const mockOrders: Order[] = [
  {
    id: '1',
    orderNumber: 'ORD-2024-001234',
    status: 'OUT_FOR_DELIVERY',
    itemCount: 3,
    total: 319,
    createdAt: '2024-01-15T10:30:00Z',
    estimatedDelivery: 'Today by 6 PM',
    items: [
      { name: 'Dolo 650mg Tablet', quantity: 2 },
      { name: 'Crocin Advance 500mg', quantity: 1 },
    ],
  },
  {
    id: '2',
    orderNumber: 'ORD-2024-001189',
    status: 'DELIVERED',
    itemCount: 5,
    total: 856,
    createdAt: '2024-01-12T14:20:00Z',
    items: [
      { name: 'Shelcal 500mg Tablet', quantity: 1 },
      { name: 'Becosules Capsule', quantity: 2 },
    ],
  },
  {
    id: '3',
    orderNumber: 'ORD-2024-001045',
    status: 'CANCELLED',
    itemCount: 2,
    total: 245,
    createdAt: '2024-01-08T09:15:00Z',
    items: [
      { name: 'Vitamin D3 Tablet', quantity: 1 },
    ],
  },
];

const getStatusColor = (status: Order['status']) => {
  switch (status) {
    case 'PENDING':
      return 'bg-yellow-100 text-yellow-700';
    case 'CONFIRMED':
    case 'PROCESSING':
      return 'bg-blue-100 text-blue-700';
    case 'SHIPPED':
    case 'OUT_FOR_DELIVERY':
      return 'bg-purple-100 text-purple-700';
    case 'DELIVERED':
      return 'bg-green-100 text-green-700';
    case 'CANCELLED':
      return 'bg-red-100 text-red-700';
    default:
      return 'bg-gray-100 text-gray-700';
  }
};

const getStatusLabel = (status: Order['status']) => {
  return status.replace(/_/g, ' ').replace(/\b\w/g, c => c.toUpperCase());
};

const getStatusIcon = (status: Order['status']) => {
  switch (status) {
    case 'PENDING':
      return <Clock className="h-4 w-4" />;
    case 'CONFIRMED':
    case 'PROCESSING':
      return <Package className="h-4 w-4" />;
    case 'SHIPPED':
    case 'OUT_FOR_DELIVERY':
      return <Truck className="h-4 w-4" />;
    case 'DELIVERED':
      return <CheckCircle className="h-4 w-4" />;
    default:
      return <Package className="h-4 w-4" />;
  }
};

export default function OrdersPage() {
  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="container mx-auto px-4 max-w-3xl">
        <h1 className="text-3xl font-bold mb-8">My Orders</h1>

        {/* Order Tabs */}
        <div className="flex gap-4 mb-6 overflow-x-auto">
          {['All', 'Active', 'Completed', 'Cancelled'].map(tab => (
            <button
              key={tab}
              className={`px-4 py-2 rounded-full text-sm font-medium whitespace-nowrap ${
                tab === 'All'
                  ? 'bg-green-600 text-white'
                  : 'bg-white text-gray-600 hover:bg-gray-100'
              }`}
            >
              {tab}
            </button>
          ))}
        </div>

        {/* Orders List */}
        <div className="space-y-4">
          {mockOrders.map(order => (
            <Link
              key={order.id}
              href={`/pharmacy/orders/${order.orderNumber}`}
              className="block bg-white rounded-xl shadow-sm p-6 hover:shadow-md transition-shadow"
            >
              <div className="flex justify-between items-start mb-4">
                <div>
                  <p className="font-semibold">{order.orderNumber}</p>
                  <p className="text-sm text-gray-500">
                    {new Date(order.createdAt).toLocaleDateString('en-IN', {
                      day: 'numeric',
                      month: 'short',
                      year: 'numeric',
                    })}
                  </p>
                </div>
                <span className={`flex items-center gap-1 px-3 py-1 rounded-full text-sm font-medium ${getStatusColor(order.status)}`}>
                  {getStatusIcon(order.status)}
                  {getStatusLabel(order.status)}
                </span>
              </div>

              <div className="flex items-center gap-4 mb-4">
                <div className="flex -space-x-2">
                  {[1, 2, 3].slice(0, Math.min(order.itemCount, 3)).map(i => (
                    <div key={i} className="w-12 h-12 bg-gray-100 rounded-lg border-2 border-white" />
                  ))}
                </div>
                <div>
                  <p className="text-sm text-gray-600">
                    {order.items.slice(0, 2).map(item => `${item.name}`).join(', ')}
                    {order.itemCount > 2 && ` +${order.itemCount - 2} more`}
                  </p>
                </div>
              </div>

              <div className="flex justify-between items-center pt-4 border-t">
                <div>
                  <span className="text-lg font-bold">₹{order.total}</span>
                  <span className="text-sm text-gray-500 ml-2">({order.itemCount} items)</span>
                </div>
                {order.estimatedDelivery && order.status !== 'DELIVERED' && order.status !== 'CANCELLED' && (
                  <div className="flex items-center gap-1 text-sm text-green-600">
                    <MapPin className="h-4 w-4" />
                    {order.estimatedDelivery}
                  </div>
                )}
                <ChevronRight className="h-5 w-5 text-gray-400" />
              </div>
            </Link>
          ))}
        </div>

        {mockOrders.length === 0 && (
          <div className="text-center py-16">
            <Package className="h-16 w-16 mx-auto text-gray-300 mb-4" />
            <h2 className="text-xl font-semibold mb-2">No orders yet</h2>
            <p className="text-gray-500 mb-6">Start shopping to see your orders here</p>
            <Link
              href="/pharmacy"
              className="inline-block bg-green-600 hover:bg-green-700 text-white font-semibold px-6 py-3 rounded-lg"
            >
              Browse Medicines
            </Link>
          </div>
        )}
      </div>
    </div>
  );
}
