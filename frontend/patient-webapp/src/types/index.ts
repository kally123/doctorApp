export interface Doctor {
  id: string;
  userId: string;
  firstName: string;
  lastName: string;
  title: string;
  bio?: string;
  profilePhotoUrl?: string;
  experienceYears: number;
  registrationNumber: string;
  consultationFee: number;
  videoConsultationFee?: number;
  rating: number;
  totalReviews: number;
  totalConsultations: number;
  isVerified: boolean;
  isAcceptingPatients: boolean;
  offersVideoConsultation: boolean;
  offersInPersonConsultation: boolean;
  specializations: Specialization[];
  qualifications: Qualification[];
  languages: string[];
  clinics: Clinic[];
}

export interface Specialization {
  id: string;
  name: string;
  description?: string;
  iconUrl?: string;
  isPrimary?: boolean;
}

export interface Qualification {
  id: string;
  degree: string;
  institution: string;
  yearOfCompletion: number;
}

export interface Clinic {
  id: string;
  name: string;
  address: string;
  city: string;
  state: string;
  pincode: string;
  phone?: string;
  isPrimary?: boolean;
  consultationFee?: number;
}

export interface SearchFilters {
  query?: string;
  specialization?: string;
  city?: string;
  minRating?: number;
  maxFee?: number;
  verifiedOnly?: boolean;
  offersVideoConsultation?: boolean;
  sortBy?: 'RELEVANCE' | 'RATING' | 'EXPERIENCE' | 'CONSULTATION_FEE' | 'POPULARITY';
  page?: number;
  size?: number;
}

export interface SearchResponse {
  results: Doctor[];
  totalHits: number;
  page: number;
  size: number;
  totalPages: number;
  tookMs: number;
}

export interface ApiResponse<T> {
  success: boolean;
  data: T;
  message?: string;
  timestamp: string;
}
