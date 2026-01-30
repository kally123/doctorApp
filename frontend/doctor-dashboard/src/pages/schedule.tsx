export function SchedulePage() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold">Schedule</h1>
        <p className="text-muted-foreground">Manage your availability and working hours</p>
      </div>

      <div className="grid gap-6 lg:grid-cols-3">
        <div className="lg:col-span-2 rounded-xl border bg-card shadow-sm">
          <div className="p-6 border-b">
            <h2 className="font-semibold">Weekly Schedule</h2>
          </div>
          <div className="p-6">
            {['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'].map((day) => (
              <div key={day} className="flex items-center justify-between py-4 border-b last:border-0">
                <div className="flex items-center gap-4">
                  <input type="checkbox" defaultChecked className="h-4 w-4 rounded border-input" />
                  <span className="font-medium w-24">{day}</span>
                </div>
                <div className="flex items-center gap-2">
                  <input
                    type="time"
                    defaultValue="09:00"
                    className="rounded border border-input bg-background px-2 py-1 text-sm"
                  />
                  <span>to</span>
                  <input
                    type="time"
                    defaultValue="17:00"
                    className="rounded border border-input bg-background px-2 py-1 text-sm"
                  />
                </div>
              </div>
            ))}
          </div>
        </div>

        <div className="rounded-xl border bg-card shadow-sm">
          <div className="p-6 border-b">
            <h2 className="font-semibold">Slot Settings</h2>
          </div>
          <div className="p-6 space-y-4">
            <div>
              <label className="block text-sm font-medium mb-2">Slot Duration</label>
              <select className="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm">
                <option>15 minutes</option>
                <option>30 minutes</option>
                <option>45 minutes</option>
                <option>60 minutes</option>
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium mb-2">Buffer Time</label>
              <select className="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm">
                <option>No buffer</option>
                <option>5 minutes</option>
                <option>10 minutes</option>
                <option>15 minutes</option>
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium mb-2">Max Bookings per Day</label>
              <input
                type="number"
                defaultValue={20}
                className="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm"
              />
            </div>
            <button className="w-full rounded-lg bg-primary py-2 text-sm font-medium text-primary-foreground hover:bg-primary/90">
              Save Changes
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
