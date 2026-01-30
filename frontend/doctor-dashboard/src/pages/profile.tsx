import { useAuthStore } from '@/stores/auth-store';

export function ProfilePage() {
  const { doctor } = useAuthStore();

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold">Profile</h1>
        <p className="text-muted-foreground">Manage your public profile information</p>
      </div>

      <div className="grid gap-6 lg:grid-cols-3">
        <div className="rounded-xl border bg-card shadow-sm p-6">
          <div className="text-center">
            <div className="h-24 w-24 rounded-full bg-primary/10 mx-auto flex items-center justify-center mb-4">
              <span className="text-3xl font-bold text-primary">
                {doctor?.firstName?.[0]}{doctor?.lastName?.[0]}
              </span>
            </div>
            <h2 className="text-xl font-bold">Dr. {doctor?.firstName} {doctor?.lastName}</h2>
            <p className="text-muted-foreground">{doctor?.specialization}</p>
            <div className="mt-2 flex items-center justify-center gap-1">
              {doctor?.isVerified ? (
                <span className="px-2 py-1 text-xs rounded-full bg-green-100 text-green-700">
                  Verified
                </span>
              ) : (
                <span className="px-2 py-1 text-xs rounded-full bg-yellow-100 text-yellow-700">
                  Pending Verification
                </span>
              )}
            </div>
          </div>
          <div className="mt-6 space-y-3">
            <button className="w-full rounded-lg border py-2 text-sm hover:bg-muted transition-colors">
              Change Photo
            </button>
            <button className="w-full rounded-lg border py-2 text-sm hover:bg-muted transition-colors">
              Preview Public Profile
            </button>
          </div>
        </div>

        <div className="lg:col-span-2 rounded-xl border bg-card shadow-sm">
          <div className="p-6 border-b">
            <h2 className="font-semibold">Professional Information</h2>
          </div>
          <div className="p-6 space-y-4">
            <div className="grid gap-4 md:grid-cols-2">
              <div>
                <label className="block text-sm font-medium mb-2">First Name</label>
                <input
                  type="text"
                  defaultValue={doctor?.firstName}
                  className="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm"
                />
              </div>
              <div>
                <label className="block text-sm font-medium mb-2">Last Name</label>
                <input
                  type="text"
                  defaultValue={doctor?.lastName}
                  className="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm"
                />
              </div>
            </div>
            <div>
              <label className="block text-sm font-medium mb-2">Title</label>
              <input
                type="text"
                defaultValue={doctor?.title}
                className="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm"
                placeholder="MBBS, MD"
              />
            </div>
            <div>
              <label className="block text-sm font-medium mb-2">Bio</label>
              <textarea
                rows={4}
                className="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm"
                placeholder="Write a brief description about yourself..."
              />
            </div>
            <div className="grid gap-4 md:grid-cols-2">
              <div>
                <label className="block text-sm font-medium mb-2">Consultation Fee (₹)</label>
                <input
                  type="number"
                  className="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm"
                  placeholder="500"
                />
              </div>
              <div>
                <label className="block text-sm font-medium mb-2">Experience (Years)</label>
                <input
                  type="number"
                  className="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm"
                  placeholder="10"
                />
              </div>
            </div>
            <button className="rounded-lg bg-primary px-4 py-2 text-sm font-medium text-primary-foreground hover:bg-primary/90">
              Save Changes
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
