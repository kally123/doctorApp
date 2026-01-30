export function PatientsPage() {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold">Patients</h1>
          <p className="text-muted-foreground">View and manage your patient records</p>
        </div>
      </div>

      <div className="rounded-xl border bg-card shadow-sm">
        <div className="p-6 border-b">
          <input
            type="text"
            placeholder="Search patients by name, email, or phone..."
            className="w-full max-w-md rounded-lg border border-input bg-background px-3 py-2 text-sm"
          />
        </div>

        <div className="grid gap-4 p-6 md:grid-cols-2 lg:grid-cols-3">
          {[1, 2, 3, 4, 5, 6].map((i) => (
            <div key={i} className="p-4 rounded-lg border hover:shadow-md transition-shadow cursor-pointer">
              <div className="flex items-center gap-3 mb-3">
                <div className="h-12 w-12 rounded-full bg-primary/10 flex items-center justify-center">
                  <span className="font-medium text-primary">P{i}</span>
                </div>
                <div>
                  <p className="font-medium">Patient Name {i}</p>
                  <p className="text-sm text-muted-foreground">patient{i}@example.com</p>
                </div>
              </div>
              <div className="text-sm text-muted-foreground space-y-1">
                <p>Last visit: 2 weeks ago</p>
                <p>Total visits: {i + 3}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
