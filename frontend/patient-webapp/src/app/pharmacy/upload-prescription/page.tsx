'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { Upload, Camera, FileText, X, Check, AlertCircle } from 'lucide-react';

export default function UploadPrescriptionPage() {
  const router = useRouter();
  const [files, setFiles] = useState<File[]>([]);
  const [uploading, setUploading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const newFiles = Array.from(e.target.files);
      const validFiles = newFiles.filter(file => {
        const isValid = file.type.startsWith('image/') || file.type === 'application/pdf';
        const isSmall = file.size <= 10 * 1024 * 1024; // 10MB limit
        return isValid && isSmall;
      });
      
      if (validFiles.length !== newFiles.length) {
        setError('Some files were skipped. Only images and PDFs under 10MB are allowed.');
      }
      
      setFiles(prev => [...prev, ...validFiles]);
    }
  };

  const removeFile = (index: number) => {
    setFiles(prev => prev.filter((_, i) => i !== index));
  };

  const handleSubmit = async () => {
    if (files.length === 0) {
      setError('Please upload at least one prescription image');
      return;
    }

    setUploading(true);
    setError(null);

    try {
      // In real implementation, upload to API
      await new Promise(resolve => setTimeout(resolve, 2000));
      router.push('/pharmacy/cart?prescription=uploaded');
    } catch (err) {
      setError('Failed to upload prescription. Please try again.');
    } finally {
      setUploading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="container mx-auto px-4 max-w-2xl">
        <h1 className="text-3xl font-bold mb-2">Upload Prescription</h1>
        <p className="text-gray-600 mb-8">
          Upload a valid prescription to order prescription medicines
        </p>

        {/* Upload Area */}
        <div className="bg-white rounded-xl shadow-sm p-6 mb-6">
          <label className="flex flex-col items-center justify-center border-2 border-dashed border-gray-300 rounded-lg p-8 cursor-pointer hover:border-green-500 transition-colors">
            <Upload className="h-12 w-12 text-gray-400 mb-4" />
            <span className="text-lg font-medium text-gray-700">
              Drop your prescription here
            </span>
            <span className="text-sm text-gray-500 mt-1">
              or click to browse files
            </span>
            <span className="text-xs text-gray-400 mt-2">
              Supports JPG, PNG, PDF up to 10MB
            </span>
            <input
              type="file"
              className="hidden"
              accept="image/*,application/pdf"
              multiple
              onChange={handleFileChange}
            />
          </label>

          {/* Alternative Upload Options */}
          <div className="flex gap-4 mt-6">
            <label className="flex-1 flex items-center justify-center gap-2 bg-gray-100 hover:bg-gray-200 py-3 rounded-lg cursor-pointer transition-colors">
              <Camera className="h-5 w-5" />
              <span className="text-sm font-medium">Take Photo</span>
              <input
                type="file"
                accept="image/*"
                capture="environment"
                className="hidden"
                onChange={handleFileChange}
              />
            </label>
            <label className="flex-1 flex items-center justify-center gap-2 bg-gray-100 hover:bg-gray-200 py-3 rounded-lg cursor-pointer transition-colors">
              <FileText className="h-5 w-5" />
              <span className="text-sm font-medium">From Documents</span>
              <input
                type="file"
                accept="application/pdf"
                className="hidden"
                onChange={handleFileChange}
              />
            </label>
          </div>
        </div>

        {/* Uploaded Files */}
        {files.length > 0 && (
          <div className="bg-white rounded-xl shadow-sm p-6 mb-6">
            <h2 className="font-semibold mb-4">Uploaded Files ({files.length})</h2>
            <div className="space-y-3">
              {files.map((file, index) => (
                <div key={index} className="flex items-center gap-3 p-3 bg-gray-50 rounded-lg">
                  <FileText className="h-8 w-8 text-green-600" />
                  <div className="flex-1 min-w-0">
                    <p className="font-medium truncate">{file.name}</p>
                    <p className="text-sm text-gray-500">
                      {(file.size / 1024 / 1024).toFixed(2)} MB
                    </p>
                  </div>
                  <button
                    onClick={() => removeFile(index)}
                    className="p-1 hover:bg-gray-200 rounded"
                  >
                    <X className="h-5 w-5 text-gray-500" />
                  </button>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Error Message */}
        {error && (
          <div className="bg-red-50 border border-red-200 rounded-lg p-4 mb-6 flex items-start gap-3">
            <AlertCircle className="h-5 w-5 text-red-500 flex-shrink-0 mt-0.5" />
            <p className="text-red-700">{error}</p>
          </div>
        )}

        {/* Guidelines */}
        <div className="bg-blue-50 rounded-xl p-6 mb-6">
          <h2 className="font-semibold text-blue-900 mb-3">Prescription Guidelines</h2>
          <ul className="space-y-2 text-sm text-blue-800">
            <li className="flex items-start gap-2">
              <Check className="h-5 w-5 text-blue-600 flex-shrink-0" />
              <span>Prescription should be valid and not older than 6 months</span>
            </li>
            <li className="flex items-start gap-2">
              <Check className="h-5 w-5 text-blue-600 flex-shrink-0" />
              <span>Doctor's name, signature, and registration number should be visible</span>
            </li>
            <li className="flex items-start gap-2">
              <Check className="h-5 w-5 text-blue-600 flex-shrink-0" />
              <span>Medicine names and dosages should be clearly readable</span>
            </li>
            <li className="flex items-start gap-2">
              <Check className="h-5 w-5 text-blue-600 flex-shrink-0" />
              <span>Patient name and date should be mentioned on the prescription</span>
            </li>
          </ul>
        </div>

        {/* Submit Button */}
        <button
          onClick={handleSubmit}
          disabled={files.length === 0 || uploading}
          className="w-full bg-green-600 hover:bg-green-700 disabled:bg-gray-300 text-white font-semibold py-4 rounded-lg transition-colors flex items-center justify-center gap-2"
        >
          {uploading ? (
            <>
              <div className="h-5 w-5 border-2 border-white border-t-transparent rounded-full animate-spin" />
              Uploading...
            </>
          ) : (
            <>
              <Upload className="h-5 w-5" />
              Upload & Continue
            </>
          )}
        </button>
      </div>
    </div>
  );
}
