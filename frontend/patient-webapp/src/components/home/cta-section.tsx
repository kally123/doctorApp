import Link from 'next/link';
import { Button } from '@/components/ui/button';

export function CTASection() {
  return (
    <section className="container">
      <div className="relative overflow-hidden rounded-2xl bg-gradient-to-r from-primary to-primary/80 text-primary-foreground p-8 md:p-12">
        <div className="relative z-10 max-w-2xl">
          <h2 className="text-3xl md:text-4xl font-bold mb-4">
            Are you a Doctor?
          </h2>
          <p className="text-primary-foreground/90 mb-6 text-lg">
            Join thousands of doctors who are growing their practice with HealthApp. 
            Manage appointments, connect with patients, and expand your reach.
          </p>
          <div className="flex flex-wrap gap-4">
            <Button size="lg" variant="secondary" asChild>
              <Link href="/doctor/register">Join as Doctor</Link>
            </Button>
            <Button size="lg" variant="outline" className="bg-transparent border-primary-foreground/30 hover:bg-primary-foreground/10" asChild>
              <Link href="/about/for-doctors">Learn More</Link>
            </Button>
          </div>
        </div>

        {/* Decorative elements */}
        <div className="absolute top-0 right-0 w-64 h-64 bg-white/10 rounded-full -translate-y-1/2 translate-x-1/2" />
        <div className="absolute bottom-0 right-1/4 w-32 h-32 bg-white/5 rounded-full translate-y-1/2" />
      </div>
    </section>
  );
}
