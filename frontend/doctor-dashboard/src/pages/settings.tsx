export function SettingsPage() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold">Settings</h1>
        <p className="text-muted-foreground">Manage your account and preferences</p>
      </div>

      <div className="grid gap-6">
        <div className="rounded-xl border bg-card shadow-sm">
          <div className="p-6 border-b">
            <h2 className="font-semibold">Account Settings</h2>
          </div>
          <div className="p-6 space-y-4">
            <div>
              <label className="block text-sm font-medium mb-2">Email</label>
              <input
                type="email"
                className="w-full max-w-md rounded-lg border border-input bg-background px-3 py-2 text-sm"
                placeholder="doctor@example.com"
              />
            </div>
            <div>
              <label className="block text-sm font-medium mb-2">Phone</label>
              <input
                type="tel"
                className="w-full max-w-md rounded-lg border border-input bg-background px-3 py-2 text-sm"
                placeholder="+91 98765 43210"
              />
            </div>
            <button className="rounded-lg bg-primary px-4 py-2 text-sm font-medium text-primary-foreground hover:bg-primary/90">
              Update Account
            </button>
          </div>
        </div>

        <div className="rounded-xl border bg-card shadow-sm">
          <div className="p-6 border-b">
            <h2 className="font-semibold">Notification Preferences</h2>
          </div>
          <div className="p-6 space-y-4">
            {[
              { label: 'New appointment notifications', description: 'Get notified when a patient books an appointment' },
              { label: 'Appointment reminders', description: 'Receive reminders 30 minutes before appointments' },
              { label: 'Patient messages', description: 'Get notified when patients send you messages' },
              { label: 'Marketing emails', description: 'Receive updates about new features and promotions' },
            ].map((item) => (
              <div key={item.label} className="flex items-center justify-between">
                <div>
                  <p className="font-medium">{item.label}</p>
                  <p className="text-sm text-muted-foreground">{item.description}</p>
                </div>
                <input type="checkbox" defaultChecked className="h-5 w-5 rounded border-input" />
              </div>
            ))}
          </div>
        </div>

        <div className="rounded-xl border bg-card shadow-sm">
          <div className="p-6 border-b">
            <h2 className="font-semibold">Change Password</h2>
          </div>
          <div className="p-6 space-y-4">
            <div>
              <label className="block text-sm font-medium mb-2">Current Password</label>
              <input
                type="password"
                className="w-full max-w-md rounded-lg border border-input bg-background px-3 py-2 text-sm"
              />
            </div>
            <div>
              <label className="block text-sm font-medium mb-2">New Password</label>
              <input
                type="password"
                className="w-full max-w-md rounded-lg border border-input bg-background px-3 py-2 text-sm"
              />
            </div>
            <div>
              <label className="block text-sm font-medium mb-2">Confirm New Password</label>
              <input
                type="password"
                className="w-full max-w-md rounded-lg border border-input bg-background px-3 py-2 text-sm"
              />
            </div>
            <button className="rounded-lg bg-primary px-4 py-2 text-sm font-medium text-primary-foreground hover:bg-primary/90">
              Change Password
            </button>
          </div>
        </div>

        <div className="rounded-xl border border-destructive/50 bg-card shadow-sm">
          <div className="p-6 border-b border-destructive/50">
            <h2 className="font-semibold text-destructive">Danger Zone</h2>
          </div>
          <div className="p-6">
            <p className="text-sm text-muted-foreground mb-4">
              Once you delete your account, there is no going back. Please be certain.
            </p>
            <button className="rounded-lg bg-destructive px-4 py-2 text-sm font-medium text-destructive-foreground hover:bg-destructive/90">
              Delete Account
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
