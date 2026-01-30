import { Metadata } from 'next';
import Link from 'next/link';
import { Search, TestTube, Home, Clock, Shield, Award, ChevronRight } from 'lucide-react';

export const metadata: Metadata = {
  title: 'Lab Tests - Book Tests Online | HealthApp',
  description: 'Book lab tests online with home sample collection. Get accurate reports from certified labs delivered to your phone.',
};

const categories = [
  { id: '1', name: 'Full Body Checkup', icon: '🏥', testCount: 45 },
  { id: '2', name: 'Diabetes', icon: '💉', testCount: 28 },
  { id: '3', name: 'Thyroid', icon: '🦋', testCount: 15 },
  { id: '4', name: 'Heart Health', icon: '❤️', testCount: 32 },
  { id: '5', name: 'Liver Function', icon: '🫀', testCount: 18 },
  { id: '6', name: 'Kidney Function', icon: '🫘', testCount: 22 },
  { id: '7', name: 'Vitamins', icon: '💊', testCount: 35 },
  { id: '8', name: 'Infection', icon: '🦠', testCount: 40 },
];

const popularTests = [
  { id: '1', name: 'Complete Blood Count (CBC)', price: 350, mrp: 500, parameters: 24, reportTime: '6 hours' },
  { id: '2', name: 'Thyroid Profile (T3, T4, TSH)', price: 499, mrp: 800, parameters: 3, reportTime: '12 hours' },
  { id: '3', name: 'Lipid Profile', price: 450, mrp: 650, parameters: 8, reportTime: '8 hours' },
  { id: '4', name: 'HbA1c (Glycated Hemoglobin)', price: 399, mrp: 600, parameters: 1, reportTime: '6 hours' },
  { id: '5', name: 'Liver Function Test (LFT)', price: 599, mrp: 850, parameters: 12, reportTime: '12 hours' },
  { id: '6', name: 'Kidney Function Test (KFT)', price: 549, mrp: 750, parameters: 10, reportTime: '12 hours' },
];

const healthPackages = [
  { 
    id: '1', 
    name: 'Basic Health Checkup', 
    tests: 45, 
    parameters: 65, 
    price: 999, 
    mrp: 2500, 
    discount: 60,
    highlights: ['CBC', 'Diabetes', 'Lipid Profile', 'Liver', 'Kidney'],
  },
  { 
    id: '2', 
    name: 'Comprehensive Health Checkup', 
    tests: 78, 
    parameters: 120, 
    price: 1999, 
    mrp: 5500, 
    discount: 64,
    highlights: ['Full Body', 'Thyroid', 'Vitamin D', 'B12', 'Iron'],
  },
  { 
    id: '3', 
    name: 'Executive Health Checkup', 
    tests: 95, 
    parameters: 150, 
    price: 2999, 
    mrp: 8000, 
    discount: 63,
    highlights: ['Advanced Lipid', 'Cardiac Risk', 'Cancer Markers', 'Hormones'],
  },
];

export default function LabTestsPage() {
  return (
    <div className="min-h-screen bg-gray-50">
      {/* Hero Section */}
      <section className="bg-gradient-to-r from-purple-600 to-indigo-600 text-white py-16">
        <div className="container mx-auto px-4">
          <div className="max-w-3xl">
            <h1 className="text-4xl md:text-5xl font-bold mb-4">
              Book Lab Tests Online
            </h1>
            <p className="text-xl mb-8 text-purple-100">
              Home sample collection • Certified labs • Reports in 24 hours
            </p>
            
            {/* Search Bar */}
            <div className="flex gap-2">
              <div className="relative flex-1">
                <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400 h-5 w-5" />
                <input
                  type="text"
                  placeholder="Search for tests, packages, or health conditions..."
                  className="w-full pl-12 pr-4 py-4 rounded-lg text-gray-900 focus:outline-none focus:ring-2 focus:ring-purple-300"
                />
              </div>
              <button className="bg-yellow-500 hover:bg-yellow-600 text-gray-900 font-semibold px-8 py-4 rounded-lg transition-colors">
                Search
              </button>
            </div>
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="py-8 bg-white border-b">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-2 md:grid-cols-4 gap-6">
            <div className="flex items-center gap-3">
              <div className="p-3 bg-purple-100 rounded-full">
                <Home className="h-6 w-6 text-purple-600" />
              </div>
              <div>
                <p className="font-semibold">Home Collection</p>
                <p className="text-sm text-gray-500">Free for orders ₹500+</p>
              </div>
            </div>
            <div className="flex items-center gap-3">
              <div className="p-3 bg-blue-100 rounded-full">
                <Clock className="h-6 w-6 text-blue-600" />
              </div>
              <div>
                <p className="font-semibold">Quick Reports</p>
                <p className="text-sm text-gray-500">Within 24-48 hours</p>
              </div>
            </div>
            <div className="flex items-center gap-3">
              <div className="p-3 bg-green-100 rounded-full">
                <Shield className="h-6 w-6 text-green-600" />
              </div>
              <div>
                <p className="font-semibold">NABL Certified</p>
                <p className="text-sm text-gray-500">Accurate results</p>
              </div>
            </div>
            <div className="flex items-center gap-3">
              <div className="p-3 bg-orange-100 rounded-full">
                <Award className="h-6 w-6 text-orange-600" />
              </div>
              <div>
                <p className="font-semibold">Best Prices</p>
                <p className="text-sm text-gray-500">Up to 70% off</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Categories */}
      <section className="py-12">
        <div className="container mx-auto px-4">
          <h2 className="text-2xl font-bold mb-6">Browse by Category</h2>
          <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-8 gap-4">
            {categories.map((category) => (
              <Link
                key={category.id}
                href={`/lab-tests/category/${category.id}`}
                className="bg-white rounded-lg p-4 text-center hover:shadow-lg transition-shadow border"
              >
                <div className="text-3xl mb-2">{category.icon}</div>
                <p className="font-medium text-sm">{category.name}</p>
                <p className="text-xs text-gray-500">{category.testCount} tests</p>
              </Link>
            ))}
          </div>
        </div>
      </section>

      {/* Health Packages */}
      <section className="py-12 bg-white">
        <div className="container mx-auto px-4">
          <div className="flex justify-between items-center mb-6">
            <h2 className="text-2xl font-bold">Health Packages</h2>
            <Link href="/lab-tests/packages" className="text-purple-600 hover:underline">
              View All →
            </Link>
          </div>
          <div className="grid md:grid-cols-3 gap-6">
            {healthPackages.map((pkg) => (
              <Link
                key={pkg.id}
                href={`/lab-tests/package/${pkg.id}`}
                className="bg-white rounded-xl border hover:shadow-lg transition-shadow overflow-hidden"
              >
                <div className="bg-gradient-to-r from-purple-500 to-indigo-500 p-4 text-white">
                  <div className="flex justify-between items-start">
                    <h3 className="font-bold text-lg">{pkg.name}</h3>
                    <span className="bg-yellow-400 text-gray-900 text-xs font-bold px-2 py-1 rounded">
                      {pkg.discount}% OFF
                    </span>
                  </div>
                  <div className="flex gap-4 mt-2 text-sm text-purple-100">
                    <span>{pkg.tests} Tests</span>
                    <span>{pkg.parameters} Parameters</span>
                  </div>
                </div>
                <div className="p-4">
                  <div className="flex flex-wrap gap-2 mb-4">
                    {pkg.highlights.map((h, i) => (
                      <span key={i} className="text-xs bg-purple-50 text-purple-700 px-2 py-1 rounded">
                        {h}
                      </span>
                    ))}
                  </div>
                  <div className="flex items-center justify-between">
                    <div>
                      <span className="text-2xl font-bold text-purple-600">₹{pkg.price}</span>
                      <span className="text-sm text-gray-400 line-through ml-2">₹{pkg.mrp}</span>
                    </div>
                    <button className="bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded-lg text-sm font-medium transition-colors">
                      Book Now
                    </button>
                  </div>
                </div>
              </Link>
            ))}
          </div>
        </div>
      </section>

      {/* Popular Tests */}
      <section className="py-12">
        <div className="container mx-auto px-4">
          <div className="flex justify-between items-center mb-6">
            <h2 className="text-2xl font-bold">Popular Tests</h2>
            <Link href="/lab-tests/all" className="text-purple-600 hover:underline">
              View All →
            </Link>
          </div>
          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-4">
            {popularTests.map((test) => (
              <Link
                key={test.id}
                href={`/lab-tests/test/${test.id}`}
                className="bg-white rounded-lg border p-4 hover:shadow-md transition-shadow flex items-center justify-between"
              >
                <div className="flex items-center gap-3">
                  <div className="p-2 bg-purple-100 rounded-lg">
                    <TestTube className="h-6 w-6 text-purple-600" />
                  </div>
                  <div>
                    <h3 className="font-medium">{test.name}</h3>
                    <div className="flex gap-3 text-xs text-gray-500 mt-1">
                      <span>{test.parameters} Parameters</span>
                      <span>•</span>
                      <span>Report: {test.reportTime}</span>
                    </div>
                  </div>
                </div>
                <div className="text-right flex-shrink-0 ml-4">
                  <p className="font-bold text-purple-600">₹{test.price}</p>
                  <p className="text-xs text-gray-400 line-through">₹{test.mrp}</p>
                </div>
              </Link>
            ))}
          </div>
        </div>
      </section>

      {/* How It Works */}
      <section className="py-12 bg-purple-50">
        <div className="container mx-auto px-4">
          <h2 className="text-2xl font-bold text-center mb-8">How It Works</h2>
          <div className="grid md:grid-cols-4 gap-8">
            {[
              { step: 1, title: 'Book Test', desc: 'Search and select tests or packages' },
              { step: 2, title: 'Schedule', desc: 'Choose home collection or visit lab' },
              { step: 3, title: 'Sample Collection', desc: 'Our phlebotomist collects samples' },
              { step: 4, title: 'Get Reports', desc: 'Receive digital reports on app' },
            ].map((item) => (
              <div key={item.step} className="text-center">
                <div className="w-12 h-12 bg-purple-600 text-white rounded-full flex items-center justify-center text-xl font-bold mx-auto mb-4">
                  {item.step}
                </div>
                <h3 className="font-semibold mb-2">{item.title}</h3>
                <p className="text-sm text-gray-600">{item.desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
}
