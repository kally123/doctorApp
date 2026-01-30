'use client';

import Link from 'next/link';
import { Card, CardContent } from '@/components/ui/card';
import { 
  Heart, Brain, Eye, Bone, Baby, Stethoscope, 
  Smile, Ear, Pill, Scissors, Activity, Shield 
} from 'lucide-react';

const specializations = [
  { name: 'Cardiologist', icon: Heart, color: 'text-red-500', bg: 'bg-red-50' },
  { name: 'Neurologist', icon: Brain, color: 'text-purple-500', bg: 'bg-purple-50' },
  { name: 'Ophthalmologist', icon: Eye, color: 'text-blue-500', bg: 'bg-blue-50' },
  { name: 'Orthopedist', icon: Bone, color: 'text-orange-500', bg: 'bg-orange-50' },
  { name: 'Pediatrician', icon: Baby, color: 'text-pink-500', bg: 'bg-pink-50' },
  { name: 'General Physician', icon: Stethoscope, color: 'text-primary', bg: 'bg-primary/10' },
  { name: 'Dentist', icon: Smile, color: 'text-cyan-500', bg: 'bg-cyan-50' },
  { name: 'ENT Specialist', icon: Ear, color: 'text-yellow-600', bg: 'bg-yellow-50' },
  { name: 'Psychiatrist', icon: Pill, color: 'text-indigo-500', bg: 'bg-indigo-50' },
  { name: 'Dermatologist', icon: Scissors, color: 'text-rose-500', bg: 'bg-rose-50' },
  { name: 'Gynecologist', icon: Activity, color: 'text-fuchsia-500', bg: 'bg-fuchsia-50' },
  { name: 'Urologist', icon: Shield, color: 'text-teal-500', bg: 'bg-teal-50' },
];

export function SpecializationsSection() {
  return (
    <section className="container">
      <div className="text-center mb-10">
        <h2 className="text-3xl font-bold mb-3">Find Doctors by Specialization</h2>
        <p className="text-muted-foreground max-w-2xl mx-auto">
          Choose from our wide range of medical specialists to find the right care for your needs
        </p>
      </div>

      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-4">
        {specializations.map((spec) => (
          <Link key={spec.name} href={`/doctors?specialization=${encodeURIComponent(spec.name)}`}>
            <Card className="h-full hover:shadow-md transition-shadow cursor-pointer">
              <CardContent className="flex flex-col items-center justify-center p-6 text-center">
                <div className={`p-3 rounded-full ${spec.bg} mb-3`}>
                  <spec.icon className={`h-6 w-6 ${spec.color}`} />
                </div>
                <span className="text-sm font-medium">{spec.name}</span>
              </CardContent>
            </Card>
          </Link>
        ))}
      </div>

      <div className="text-center mt-8">
        <Link 
          href="/specializations" 
          className="text-primary hover:underline font-medium"
        >
          View All Specializations →
        </Link>
      </div>
    </section>
  );
}
