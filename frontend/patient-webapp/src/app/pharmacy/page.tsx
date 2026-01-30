import { Metadata } from 'next';
import Link from 'next/link';
import { ShoppingCart, Search, Pill, TestTube, Truck, Clock, Shield } from 'lucide-react';

export const metadata: Metadata = {
  title: 'Pharmacy - Order Medicines Online | HealthApp',
  description: 'Order medicines online with fast delivery. Upload prescription and get genuine medicines delivered to your doorstep.',
};

const categories = [
  { name: 'Diabetes Care', icon: '💉', count: 245 },
  { name: 'Heart Care', icon: '❤️', count: 189 },
  { name: 'Vitamins & Supplements', icon: '💊', count: 456 },
  { name: 'Skin Care', icon: '✨', count: 312 },
  { name: 'Pain Relief', icon: '🩹', count: 167 },
  { name: 'Digestive Health', icon: '🫃', count: 203 },
  { name: 'Cold & Flu', icon: '🤧', count: 134 },
  { name: 'Eye Care', icon: '👁️', count: 89 },
];

const featuredProducts = [
  { id: '1', name: 'Dolo 650mg Tablet', manufacturer: 'Micro Labs', mrp: 30, price: 25, discount: 17, image: '/images/medicine-1.jpg' },
  { id: '2', name: 'Crocin Advance 500mg', manufacturer: 'GSK', mrp: 35, price: 30, discount: 14, image: '/images/medicine-2.jpg' },
  { id: '3', name: 'Shelcal 500mg Tablet', manufacturer: 'Torrent Pharma', mrp: 180, price: 155, discount: 14, image: '/images/medicine-3.jpg' },
  { id: '4', name: 'Becosules Capsule', manufacturer: 'Pfizer', mrp: 45, price: 38, discount: 16, image: '/images/medicine-4.jpg' },
];

export default function PharmacyPage() {
  return (
    <div className="min-h-screen bg-gray-50">
      {/* Hero Section */}
      <section className="bg-gradient-to-r from-green-600 to-teal-600 text-white py-16">
        <div className="container mx-auto px-4">
          <div className="max-w-3xl">
            <h1 className="text-4xl md:text-5xl font-bold mb-4">
              Order Medicines Online
            </h1>
            <p className="text-xl mb-8 text-green-100">
              Get genuine medicines delivered to your doorstep with up to 25% off
            </p>
            
            {/* Search Bar */}
            <div className="flex gap-2">
              <div className="relative flex-1">
                <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400 h-5 w-5" />
                <input
                  type="text"
                  placeholder="Search for medicines and health products..."
                  className="w-full pl-12 pr-4 py-4 rounded-lg text-gray-900 focus:outline-none focus:ring-2 focus:ring-green-300"
                />
              </div>
              <button className="bg-yellow-500 hover:bg-yellow-600 text-gray-900 font-semibold px-8 py-4 rounded-lg transition-colors">
                Search
              </button>
            </div>
            
            {/* Quick Actions */}
            <div className="flex flex-wrap gap-4 mt-6">
              <Link
                href="/pharmacy/upload-prescription"
                className="flex items-center gap-2 bg-white/20 hover:bg-white/30 px-4 py-2 rounded-lg transition-colors"
              >
                <Pill className="h-5 w-5" />
                Upload Prescription
              </Link>
              <Link
                href="/pharmacy/orders"
                className="flex items-center gap-2 bg-white/20 hover:bg-white/30 px-4 py-2 rounded-lg transition-colors"
              >
                <ShoppingCart className="h-5 w-5" />
                My Orders
              </Link>
            </div>
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="py-8 bg-white border-b">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-2 md:grid-cols-4 gap-6">
            <div className="flex items-center gap-3">
              <div className="p-3 bg-green-100 rounded-full">
                <Shield className="h-6 w-6 text-green-600" />
              </div>
              <div>
                <p className="font-semibold">100% Genuine</p>
                <p className="text-sm text-gray-500">Authentic medicines</p>
              </div>
            </div>
            <div className="flex items-center gap-3">
              <div className="p-3 bg-blue-100 rounded-full">
                <Truck className="h-6 w-6 text-blue-600" />
              </div>
              <div>
                <p className="font-semibold">Fast Delivery</p>
                <p className="text-sm text-gray-500">Within 2-4 hours</p>
              </div>
            </div>
            <div className="flex items-center gap-3">
              <div className="p-3 bg-purple-100 rounded-full">
                <Clock className="h-6 w-6 text-purple-600" />
              </div>
              <div>
                <p className="font-semibold">24/7 Service</p>
                <p className="text-sm text-gray-500">Round the clock</p>
              </div>
            </div>
            <div className="flex items-center gap-3">
              <div className="p-3 bg-orange-100 rounded-full">
                <TestTube className="h-6 w-6 text-orange-600" />
              </div>
              <div>
                <p className="font-semibold">Easy Returns</p>
                <p className="text-sm text-gray-500">Hassle-free returns</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Categories */}
      <section className="py-12">
        <div className="container mx-auto px-4">
          <h2 className="text-2xl font-bold mb-6">Shop by Category</h2>
          <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-8 gap-4">
            {categories.map((category) => (
              <Link
                key={category.name}
                href={`/pharmacy/category/${category.name.toLowerCase().replace(/\s+/g, '-')}`}
                className="bg-white rounded-lg p-4 text-center hover:shadow-lg transition-shadow border"
              >
                <div className="text-3xl mb-2">{category.icon}</div>
                <p className="font-medium text-sm">{category.name}</p>
                <p className="text-xs text-gray-500">{category.count} products</p>
              </Link>
            ))}
          </div>
        </div>
      </section>

      {/* Featured Products */}
      <section className="py-12 bg-white">
        <div className="container mx-auto px-4">
          <div className="flex justify-between items-center mb-6">
            <h2 className="text-2xl font-bold">Featured Products</h2>
            <Link href="/pharmacy/products" className="text-green-600 hover:underline">
              View All →
            </Link>
          </div>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-6">
            {featuredProducts.map((product) => (
              <Link
                key={product.id}
                href={`/pharmacy/product/${product.id}`}
                className="bg-white rounded-lg border hover:shadow-lg transition-shadow overflow-hidden"
              >
                <div className="aspect-square bg-gray-100 relative">
                  <div className="absolute top-2 left-2 bg-green-500 text-white text-xs px-2 py-1 rounded">
                    {product.discount}% OFF
                  </div>
                </div>
                <div className="p-4">
                  <h3 className="font-medium text-sm mb-1 line-clamp-2">{product.name}</h3>
                  <p className="text-xs text-gray-500 mb-2">{product.manufacturer}</p>
                  <div className="flex items-center gap-2">
                    <span className="font-bold text-green-600">₹{product.price}</span>
                    <span className="text-sm text-gray-400 line-through">₹{product.mrp}</span>
                  </div>
                  <button className="w-full mt-3 bg-green-600 hover:bg-green-700 text-white py-2 rounded-lg text-sm font-medium transition-colors">
                    Add to Cart
                  </button>
                </div>
              </Link>
            ))}
          </div>
        </div>
      </section>

      {/* Order from Prescription CTA */}
      <section className="py-12 bg-gradient-to-r from-blue-600 to-purple-600 text-white">
        <div className="container mx-auto px-4">
          <div className="max-w-2xl mx-auto text-center">
            <h2 className="text-3xl font-bold mb-4">Have a Prescription?</h2>
            <p className="text-xl mb-8 text-blue-100">
              Upload your prescription and we'll deliver all your medicines with the best prices
            </p>
            <Link
              href="/pharmacy/upload-prescription"
              className="inline-flex items-center gap-2 bg-white text-blue-600 font-semibold px-8 py-4 rounded-lg hover:bg-blue-50 transition-colors"
            >
              <Pill className="h-5 w-5" />
              Upload Prescription
            </Link>
          </div>
        </div>
      </section>
    </div>
  );
}
