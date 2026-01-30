import { Card, CardContent } from '@/components/ui/card';
import { Video, Calendar, Shield, Clock, CreditCard, MessageSquare } from 'lucide-react';

const features = [
  {
    icon: Video,
    title: 'Video Consultation',
    description: 'Connect with doctors from the comfort of your home through secure video calls.',
  },
  {
    icon: Calendar,
    title: 'Easy Booking',
    description: 'Book appointments in seconds with real-time availability and instant confirmation.',
  },
  {
    icon: Shield,
    title: 'Verified Doctors',
    description: 'All doctors are verified with their credentials and certifications.',
  },
  {
    icon: Clock,
    title: '24/7 Availability',
    description: 'Access healthcare services anytime with our round-the-clock support.',
  },
  {
    icon: CreditCard,
    title: 'Secure Payments',
    description: 'Safe and secure payment options with multiple payment methods.',
  },
  {
    icon: MessageSquare,
    title: 'Follow-up Care',
    description: 'Easy follow-up consultations and chat support with your doctor.',
  },
];

export function FeaturesSection() {
  return (
    <section className="bg-muted/30 py-16">
      <div className="container">
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold mb-3">Why Choose HealthApp?</h2>
          <p className="text-muted-foreground max-w-2xl mx-auto">
            We make healthcare accessible, convenient, and reliable for everyone
          </p>
        </div>

        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
          {features.map((feature) => (
            <Card key={feature.title} className="bg-background">
              <CardContent className="p-6">
                <div className="p-3 rounded-lg bg-primary/10 w-fit mb-4">
                  <feature.icon className="h-6 w-6 text-primary" />
                </div>
                <h3 className="font-semibold text-lg mb-2">{feature.title}</h3>
                <p className="text-muted-foreground text-sm">{feature.description}</p>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  );
}
