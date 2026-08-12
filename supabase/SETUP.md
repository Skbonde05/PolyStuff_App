# Supabase Setup Guide

## 1. Create Supabase Project
1. Go to [supabase.com](https://supabase.com) and sign up
2. Create a new project
3. Wait for project to be ready

## 2. Get Credentials
1. Go to Project Settings → API
2. Copy the **Project URL** (e.g. `https://abc123.supabase.co`)
3. Copy the **anon/public** key

## 3. Update App Configuration
Replace the values in these files:
- `app/src/main/java/myapp/org/userapp/supabase/SupabaseConfig.java`
- `app/src/main/java/myapp/org/userapp/supabase/SupabaseClient.kt`

## 4. Run Database Schema
1. Go to Supabase Dashboard → SQL Editor
2. Copy contents of `supabase/schema.sql`
3. Paste and run the SQL

## 5. Enable Auth Providers
1. Go to Authentication → Providers
2. Enable **Email** provider
3. Configure as needed

## 6. Create Storage Bucket (Optional)
1. Go to Storage → Create bucket
2. Name: `pdfs`
3. Set as public
4. Configure RLS policies

## 7. Test Connection
Run the app and check logcat for:
```
SupabaseClientWrapper: Supabase URL: https://your-project.supabase.co
SupabaseClientWrapper: Supabase configured: true
```
