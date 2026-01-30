'use client';

import Link from 'next/link';
import { Button } from '@/components/ui/button';
import { useAuthStore } from '@/stores/auth-store';
import { Menu, User, LogOut, Calendar } from 'lucide-react';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';

export function Header() {
  const { user, isAuthenticated, logout } = useAuthStore();

  return (
    <header className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      <div className="container flex h-16 items-center justify-between">
        <div className="flex items-center gap-6">
          <Link href="/" className="flex items-center gap-2">
            <div className="flex h-8 w-8 items-center justify-center rounded-lg bg-primary text-primary-foreground">
              <span className="text-xl font-bold">H</span>
            </div>
            <span className="text-xl font-bold text-primary">HealthApp</span>
          </Link>

          <nav className="hidden md:flex items-center gap-6">
            <Link
              href="/doctors"
              className="text-sm font-medium text-muted-foreground transition-colors hover:text-foreground"
            >
              Find Doctors
            </Link>
            <Link
              href="/specializations"
              className="text-sm font-medium text-muted-foreground transition-colors hover:text-foreground"
            >
              Specializations
            </Link>
            <Link
              href="/video-consultation"
              className="text-sm font-medium text-muted-foreground transition-colors hover:text-foreground"
            >
              Video Consultation
            </Link>
          </nav>
        </div>

        <div className="flex items-center gap-4">
          {isAuthenticated ? (
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" className="gap-2">
                  <User className="h-4 w-4" />
                  <span className="hidden sm:inline">{user?.firstName}</span>
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end" className="w-48">
                <DropdownMenuItem asChild>
                  <Link href="/profile" className="flex items-center gap-2">
                    <User className="h-4 w-4" />
                    My Profile
                  </Link>
                </DropdownMenuItem>
                <DropdownMenuItem asChild>
                  <Link href="/appointments" className="flex items-center gap-2">
                    <Calendar className="h-4 w-4" />
                    My Appointments
                  </Link>
                </DropdownMenuItem>
                <DropdownMenuSeparator />
                <DropdownMenuItem onClick={logout} className="text-destructive">
                  <LogOut className="h-4 w-4 mr-2" />
                  Logout
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          ) : (
            <div className="flex items-center gap-2">
              <Button variant="ghost" asChild>
                <Link href="/login">Login</Link>
              </Button>
              <Button asChild>
                <Link href="/register">Sign Up</Link>
              </Button>
            </div>
          )}

          <Button variant="ghost" size="icon" className="md:hidden">
            <Menu className="h-5 w-5" />
          </Button>
        </div>
      </div>
    </header>
  );
}
