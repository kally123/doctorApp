'use client';

import Link from 'next/link';
import Image from 'next/image';
import { Card, CardContent } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Star, MapPin, BadgeCheck, Video } from 'lucide-react';
import { useQuery } from '@tanstack/react-query';
import apiClient from '@/lib/api-client';
import type { Doctor, ApiResponse } from '@/types';

export function TopDoctorsSection() {
  const { data, isLoading } = useQuery({
    queryKey: ['topDoctors'],
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<Doctor[]>>('/doctors/top?limit=6');
      return response.data.data;
    },
  });

  // Fallback mock data for development
  const doctors: Doctor[] = data || [
    {
      id: '1',
      userId: '1',
      firstName: 'Sarah',
      lastName: 'Johnson',
      title: 'MD',
      experienceYears: 15,
      registrationNumber: 'REG001',
      consultationFee: 500,
      rating: 4.9,
      totalReviews: 245,
      totalConsultations: 1520,
      isVerified: true,
      isAcceptingPatients: true,
      offersVideoConsultation: true,
      offersInPersonConsultation: true,
      specializations: [{ id: '1', name: 'Cardiologist', isPrimary: true }],
      qualifications: [{ id: '1', degree: 'MBBS, MD', institution: 'AIIMS Delhi', yearOfCompletion: 2008 }],
      languages: ['English', 'Hindi'],
      clinics: [{ id: '1', name: 'Heart Care Clinic', address: '123 Main St', city: 'Mumbai', state: 'Maharashtra', pincode: '400001', isPrimary: true }],
    },
    {
      id: '2',
      userId: '2',
      firstName: 'Michael',
      lastName: 'Chen',
      title: 'MD, PhD',
      experienceYears: 12,
      registrationNumber: 'REG002',
      consultationFee: 450,
      rating: 4.8,
      totalReviews: 189,
      totalConsultations: 980,
      isVerified: true,
      isAcceptingPatients: true,
      offersVideoConsultation: true,
      offersInPersonConsultation: true,
      specializations: [{ id: '2', name: 'Neurologist', isPrimary: true }],
      qualifications: [{ id: '2', degree: 'MBBS, MD, PhD', institution: 'Harvard Medical', yearOfCompletion: 2010 }],
      languages: ['English'],
      clinics: [{ id: '2', name: 'Neuro Health Center', address: '456 Oak Ave', city: 'Delhi', state: 'Delhi', pincode: '110001', isPrimary: true }],
    },
    {
      id: '3',
      userId: '3',
      firstName: 'Priya',
      lastName: 'Sharma',
      title: 'MBBS',
      experienceYears: 8,
      registrationNumber: 'REG003',
      consultationFee: 350,
      rating: 4.7,
      totalReviews: 156,
      totalConsultations: 720,
      isVerified: true,
      isAcceptingPatients: true,
      offersVideoConsultation: true,
      offersInPersonConsultation: true,
      specializations: [{ id: '3', name: 'Pediatrician', isPrimary: true }],
      qualifications: [{ id: '3', degree: 'MBBS, DCH', institution: 'JIPMER', yearOfCompletion: 2015 }],
      languages: ['English', 'Hindi', 'Tamil'],
      clinics: [{ id: '3', name: 'Child Care Clinic', address: '789 Park Rd', city: 'Bangalore', state: 'Karnataka', pincode: '560001', isPrimary: true }],
    },
  ];

  return (
    <section className="container">
      <div className="flex items-center justify-between mb-10">
        <div>
          <h2 className="text-3xl font-bold mb-2">Top Rated Doctors</h2>
          <p className="text-muted-foreground">
            Book appointments with our highest rated healthcare professionals
          </p>
        </div>
        <Button variant="outline" asChild>
          <Link href="/doctors">View All</Link>
        </Button>
      </div>

      <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        {doctors.map((doctor) => (
          <DoctorCard key={doctor.id} doctor={doctor} />
        ))}
      </div>
    </section>
  );
}

function DoctorCard({ doctor }: { doctor: Doctor }) {
  const primarySpecialization = doctor.specializations.find(s => s.isPrimary)?.name || doctor.specializations[0]?.name;
  const primaryClinic = doctor.clinics.find(c => c.isPrimary) || doctor.clinics[0];

  return (
    <Card className="overflow-hidden hover:shadow-lg transition-shadow">
      <CardContent className="p-0">
        <div className="flex p-4 gap-4">
          <div className="relative w-20 h-20 rounded-full overflow-hidden bg-muted flex-shrink-0">
            {doctor.profilePhotoUrl ? (
              <Image
                src={doctor.profilePhotoUrl}
                alt={`Dr. ${doctor.firstName} ${doctor.lastName}`}
                fill
                className="object-cover"
              />
            ) : (
              <div className="w-full h-full flex items-center justify-center text-2xl font-bold text-muted-foreground">
                {doctor.firstName[0]}{doctor.lastName[0]}
              </div>
            )}
          </div>
          
          <div className="flex-1 min-w-0">
            <div className="flex items-center gap-2">
              <h3 className="font-semibold truncate">
                Dr. {doctor.firstName} {doctor.lastName}
              </h3>
              {doctor.isVerified && (
                <BadgeCheck className="h-4 w-4 text-primary flex-shrink-0" />
              )}
            </div>
            <p className="text-sm text-muted-foreground">{primarySpecialization}</p>
            <p className="text-sm text-muted-foreground">{doctor.experienceYears} years exp.</p>
            
            <div className="flex items-center gap-3 mt-2">
              <div className="flex items-center gap-1">
                <Star className="h-4 w-4 fill-yellow-400 text-yellow-400" />
                <span className="text-sm font-medium">{doctor.rating}</span>
                <span className="text-xs text-muted-foreground">({doctor.totalReviews})</span>
              </div>
              {doctor.offersVideoConsultation && (
                <div className="flex items-center gap-1 text-primary">
                  <Video className="h-4 w-4" />
                  <span className="text-xs">Video</span>
                </div>
              )}
            </div>
          </div>
        </div>

        <div className="border-t px-4 py-3 bg-muted/30">
          <div className="flex items-center justify-between">
            <div>
              {primaryClinic && (
                <div className="flex items-center gap-1 text-sm text-muted-foreground">
                  <MapPin className="h-3 w-3" />
                  <span>{primaryClinic.city}</span>
                </div>
              )}
              <div className="text-sm font-medium">₹{doctor.consultationFee}</div>
            </div>
            <Button size="sm" asChild>
              <Link href={`/doctors/${doctor.id}`}>Book Now</Link>
            </Button>
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
