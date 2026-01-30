import Link from 'next/link';

export function Footer() {
  return (
    <footer className="border-t bg-muted/50">
      <div className="container py-12">
        <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
          <div className="col-span-2 md:col-span-1">
            <Link href="/" className="flex items-center gap-2 mb-4">
              <div className="flex h-8 w-8 items-center justify-center rounded-lg bg-primary text-primary-foreground">
                <span className="text-xl font-bold">H</span>
              </div>
              <span className="text-xl font-bold text-primary">HealthApp</span>
            </Link>
            <p className="text-sm text-muted-foreground">
              Your trusted partner for healthcare. Book appointments with the best doctors in your city.
            </p>
          </div>

          <div>
            <h3 className="font-semibold mb-4">For Patients</h3>
            <ul className="space-y-2">
              <li>
                <Link href="/doctors" className="text-sm text-muted-foreground hover:text-foreground">
                  Find Doctors
                </Link>
              </li>
              <li>
                <Link href="/video-consultation" className="text-sm text-muted-foreground hover:text-foreground">
                  Video Consultation
                </Link>
              </li>
              <li>
                <Link href="/specializations" className="text-sm text-muted-foreground hover:text-foreground">
                  Specializations
                </Link>
              </li>
              <li>
                <Link href="/health-articles" className="text-sm text-muted-foreground hover:text-foreground">
                  Health Articles
                </Link>
              </li>
            </ul>
          </div>

          <div>
            <h3 className="font-semibold mb-4">For Doctors</h3>
            <ul className="space-y-2">
              <li>
                <Link href="/doctor/register" className="text-sm text-muted-foreground hover:text-foreground">
                  Join as Doctor
                </Link>
              </li>
              <li>
                <Link href="/doctor/login" className="text-sm text-muted-foreground hover:text-foreground">
                  Doctor Login
                </Link>
              </li>
              <li>
                <Link href="/about/for-doctors" className="text-sm text-muted-foreground hover:text-foreground">
                  Benefits
                </Link>
              </li>
            </ul>
          </div>

          <div>
            <h3 className="font-semibold mb-4">Company</h3>
            <ul className="space-y-2">
              <li>
                <Link href="/about" className="text-sm text-muted-foreground hover:text-foreground">
                  About Us
                </Link>
              </li>
              <li>
                <Link href="/contact" className="text-sm text-muted-foreground hover:text-foreground">
                  Contact
                </Link>
              </li>
              <li>
                <Link href="/privacy" className="text-sm text-muted-foreground hover:text-foreground">
                  Privacy Policy
                </Link>
              </li>
              <li>
                <Link href="/terms" className="text-sm text-muted-foreground hover:text-foreground">
                  Terms of Service
                </Link>
              </li>
            </ul>
          </div>
        </div>

        <div className="border-t mt-8 pt-8 text-center text-sm text-muted-foreground">
          <p>© {new Date().getFullYear()} HealthApp. All rights reserved.</p>
        </div>
      </div>
    </footer>
  );
}
