import { HeroSection } from '@/components/home/hero-section';
import { SpecializationsSection } from '@/components/home/specializations-section';
import { TopDoctorsSection } from '@/components/home/top-doctors-section';
import { FeaturesSection } from '@/components/home/features-section';
import { CTASection } from '@/components/home/cta-section';

export default function Home() {
  return (
    <div className="flex flex-col gap-16 pb-16">
      <HeroSection />
      <SpecializationsSection />
      <TopDoctorsSection />
      <FeaturesSection />
      <CTASection />
    </div>
  );
}
