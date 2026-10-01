-- Supabase schema for PolyStuff Admin CMS
-- PDF delivery: GitHub + jsDelivr CDN only
-- Run this in the Supabase SQL Editor after creating your project.

-- Enable UUID extension
create extension if not exists "uuid-ossp";

-- Users table (extends auth.users)
create table if not exists public.users (
  id uuid references auth.users on delete cascade primary key,
  email text,
  name text,
  username text,
  role text default 'user',
  is_admin boolean default false,
  created_at timestamp with time zone default timezone('utc', now()) not null
);

-- Enable RLS
alter table public.users enable row level security;

-- Policies for users table
create policy "Users can view their own profile" on public.users
  for select using (auth.uid() = id);

create policy "Users can update their own profile" on public.users
  for update using (auth.uid() = id);

create policy "Only admins can insert users" on public.users
  for insert with check (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

create policy "Only admins can delete users" on public.users
  for delete using (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

-- Subjects table
create table if not exists public.subjects (
  id uuid default uuid_generate_v4() primary key,
  key text unique not null,
  title text not null,
  description text,
  category text,
  pdf_count integer default 0,
  is_active boolean default true,
  created_at timestamp with time zone default timezone('utc', now()) not null,
  updated_at timestamp with time zone default timezone('utc', now()) not null
);

alter table public.subjects enable row level security;

-- Everyone can read subjects
create policy "Subjects are viewable by everyone" on public.subjects
  for select using (true);

-- Only admins can modify subjects
create policy "Only admins can insert subjects" on public.subjects
  for insert with check (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

create policy "Only admins can update subjects" on public.subjects
  for update using (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

create policy "Only admins can delete subjects" on public.subjects
  for delete using (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

-- PDFs table - URLs point to GitHub/jsDelivr CDN
create table if not exists public.pdfs (
  id uuid default uuid_generate_v4() primary key,
  subject_id uuid references public.subjects(id) on delete cascade not null,
  title text not null,
  url text not null,
  file_name text,
  file_size bigint,
  page_count integer,
  uploaded_by uuid references auth.users(id),
  is_active boolean default true,
  created_at timestamp with time zone default timezone('utc', now()) not null
);

alter table public.pdfs enable row level security;

-- Everyone can read PDFs
create policy "PDFs are viewable by everyone" on public.pdfs
  for select using (true);

-- Only admins can modify PDFs
create policy "Only admins can insert PDFs" on public.pdfs
  for insert with check (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

create policy "Only admins can update PDFs" on public.pdfs
  for update using (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

create policy "Only admins can delete PDFs" on public.pdfs
  for delete using (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

-- Flashcards table
create table if not exists public.flashcards (
  id uuid default uuid_generate_v4() primary key,
  subject_id uuid references public.subjects(id) on delete cascade not null,
  question text not null,
  answer text not null,
  category text,
  difficulty integer default 1,
  created_at timestamp with time zone default timezone('utc', now()) not null
);

alter table public.flashcards enable row level security;

-- Everyone can read flashcards
create policy "Flashcards are viewable by everyone" on public.flashcards
  for select using (true);

-- Only admins can modify flashcards
create policy "Only admins can insert flashcards" on public.flashcards
  for insert with check (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

create policy "Only admins can update flashcards" on public.flashcards
  for update using (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

create policy "Only admins can delete flashcards" on public.flashcards
  for delete using (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

-- Quizzes table
create table if not exists public.quizzes (
  id uuid default uuid_generate_v4() primary key,
  subject_id uuid references public.subjects(id) on delete cascade not null,
  title text not null,
  description text,
  question_count integer default 0,
  time_limit integer,
  is_active boolean default true,
  created_at timestamp with time zone default timezone('utc', now()) not null
);

alter table public.quizzes enable row level security;

-- Everyone can read quizzes
create policy "Quizzes are viewable by everyone" on public.quizzes
  for select using (true);

-- Only admins can modify quizzes
create policy "Only admins can insert quizzes" on public.quizzes
  for insert with check (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

create policy "Only admins can update quizzes" on public.quizzes
  for update using (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

create policy "Only admins can delete quizzes" on public.quizzes
  for delete using (exists (select 1 from public.users where id = auth.uid() and is_admin = true));

-- Indexes for performance
create index if not exists idx_subjects_key on public.subjects(key);
create index if not exists idx_subjects_active on public.subjects(is_active);
create index if not exists idx_pdfs_subject on public.pdfs(subject_id);
create index if not exists idx_flashcards_subject on public.flashcards(subject_id);
create index if not exists idx_quizzes_subject on public.quizzes(subject_id);
