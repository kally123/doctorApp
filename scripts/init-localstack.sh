#!/bin/bash
set -e

echo "Creating S3 buckets for prescription and EHR services..."

# Create prescription bucket
awslocal s3 mb s3://healthapp-prescriptions

# Create EHR documents bucket
awslocal s3 mb s3://healthapp-ehr-documents

# Set bucket policies for public read (for development only)
echo "Buckets created successfully!"

# List buckets
awslocal s3 ls

echo "LocalStack S3 initialization complete!"
