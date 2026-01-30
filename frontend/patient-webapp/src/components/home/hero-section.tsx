'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Search, MapPin, Video } from 'lucide-react';

export function HeroSection() {
  const router = useRouter();
  const [searchQuery, setSearchQuery] = useState('');
  const [location, setLocation] = useState('');

  const handleSearch = () => {
    const params = new URLSearchParams();
    if (searchQuery) params.set('q', searchQuery);
    if (location) params.set('city', location);
    router.push(`/doctors?${params.toString()}`);
  };

  return (
    <section className="relative bg-gradient-to-br from-primary/5 via-background to-primary/10 pt-16 pb-24">
      <div className="container">
        <div className="max-w-3xl mx-auto text-center">
          <h1 className="text-4xl md:text-5xl lg:text-6xl font-bold tracking-tight mb-6">
            Find and Book the Best{' '}
            <span className="text-primary">Doctors</span> Near You
          </h1>
          <p className="text-lg text-muted-foreground mb-8">
            Book appointments with verified doctors, get video consultations, and manage your healthcare journey - all in one place.
          </p>

          <div className="bg-white rounded-xl shadow-lg p-4 md:p-6">
            <div className="flex flex-col md:flex-row gap-4">
              <div className="relative flex-1">
                <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-muted-foreground" />
                <Input
                  type="text"
                  placeholder="Search doctors, specializations..."
                  className="pl-10 h-12"
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                  onKeyDown={(e) => e.key === 'Enter' && handleSearch()}
                />
              </div>
              <div className="relative flex-1 md:max-w-[200px]">
                <MapPin className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-muted-foreground" />
                <Input
                  type="text"
                  placeholder="Location"
                  className="pl-10 h-12"
                  value={location}
                  onChange={(e) => setLocation(e.target.value)}
                  onKeyDown={(e) => e.key === 'Enter' && handleSearch()}
                />
              </div>
              <Button size="lg" className="h-12 px-8" onClick={handleSearch}>
                Search
              </Button>
            </div>
          </div>

          <div className="flex flex-wrap justify-center gap-4 mt-8">
            <Button variant="outline" size="sm" className="gap-2" onClick={() => router.push('/video-consultation')}>
              <Video className="h-4 w-4" />
              Video Consultation
            </Button>
            <Button variant="ghost" size="sm" onClick={() => router.push('/doctors?specialization=General Physician')}>
              General Physician
            </Button>
            <Button variant="ghost" size="sm" onClick={() => router.push('/doctors?specialization=Dermatologist')}>
              Dermatologist
            </Button>
            <Button variant="ghost" size="sm" onClick={() => router.push('/doctors?specialization=Pediatrician')}>
              Pediatrician
            </Button>
            <Button variant="ghost" size="sm" onClick={() => router.push('/doctors?specialization=Cardiologist')}>
              Cardiologist
            </Button>
          </div>
        </div>

        <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mt-16 max-w-4xl mx-auto">
          <div className="text-center p-4">
            <div className="text-3xl font-bold text-primary">5000+</div>
            <div className="text-sm text-muted-foreground">Verified Doctors</div>
          </div>
          <div className="text-center p-4">
            <div className="text-3xl font-bold text-primary">50K+</div>
            <div className="text-sm text-muted-foreground">Happy Patients</div>
          </div>
          <div className="text-center p-4">
            <div className="text-3xl font-bold text-primary">100+</div>
            <div className="text-sm text-muted-foreground">Specializations</div>
          </div>
          <div className="text-center p-4">
            <div className="text-3xl font-bold text-primary">4.8</div>
            <div className="text-sm text-muted-foreground">Average Rating</div>
          </div>
        </div>
      </div>
    </section>
  );
}
