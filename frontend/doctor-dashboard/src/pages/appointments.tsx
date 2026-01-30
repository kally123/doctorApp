export function AppointmentsPage() {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold">Appointments</h1>
          <p className="text-muted-foreground">Manage your patient appointments</p>
        </div>
        <button className="px-4 py-2 bg-primary text-primary-foreground rounded-lg hover:bg-primary/90 transition-colors">
          + New Appointment
        </button>
      </div>

      <div className="rounded-xl border bg-card shadow-sm">
        <div className="p-6 border-b">
          <div className="flex items-center gap-4">
            <input
              type="text"
              placeholder="Search appointments..."
              className="flex-1 max-w-sm rounded-lg border border-input bg-background px-3 py-2 text-sm"
            />
            <select className="rounded-lg border border-input bg-background px-3 py-2 text-sm">
              <option>All Status</option>
              <option>Confirmed</option>
              <option>Pending</option>
              <option>Completed</option>
              <option>Cancelled</option>
            </select>
            <select className="rounded-lg border border-input bg-background px-3 py-2 text-sm">
              <option>Today</option>
              <option>This Week</option>
              <option>This Month</option>
            </select>
          </div>
        </div>

        <div className="overflow-x-auto">
          <table className="w-full">
            <thead className="bg-muted/50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                  Patient
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                  Date & Time
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                  Type
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                  Status
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody className="divide-y">
              <tr className="hover:bg-muted/30">
                <td className="px-6 py-4 whitespace-nowrap">
                  <div className="flex items-center gap-3">
                    <div className="h-10 w-10 rounded-full bg-primary/10 flex items-center justify-center">
                      <span className="text-sm font-medium text-primary">AS</span>
                    </div>
                    <div>
                      <p className="font-medium">Amit Sharma</p>
                      <p className="text-sm text-muted-foreground">amit@example.com</p>
                    </div>
                  </div>
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <p className="font-medium">Today, 10:00 AM</p>
                  <p className="text-sm text-muted-foreground">30 min</p>
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <span className="px-2 py-1 text-xs rounded-full bg-blue-100 text-blue-700">
                    Video
                  </span>
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <span className="px-2 py-1 text-xs rounded-full bg-green-100 text-green-700">
                    Confirmed
                  </span>
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <button className="text-primary hover:underline text-sm">Join</button>
                </td>
              </tr>
              {/* More rows... */}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
