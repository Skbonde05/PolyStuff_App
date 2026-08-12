create extension if not exists "uuid-ossp";

-- Users table (standalone - no FK to auth.users since app uses Firebase Auth)
create table if not exists public.users (
  id uuid default uuid_generate_v4() primary key,
  email text unique,
  name text,
  username text,
  role text default 'user',
  is_admin boolean default false,
  created_at timestamp with time zone default timezone('utc'::text, now()) not null
);

-- Ensure any old foreign key to auth.users is removed (since app uses Firebase Auth)
alter table public.users drop constraint if exists users_id_fkey;
alter table public.users alter column id set default uuid_generate_v4();

-- Enable RLS
alter table public.users enable row level security;

-- Policies for users table (with DROP POLICY IF EXISTS so script is re-runnable)
drop policy if exists "Users can view their own profile" on public.users;
drop policy if exists "Users can update their own profile" on public.users;
drop policy if exists "Only admins can insert users" on public.users;
drop policy if exists "Only admins can delete users" on public.users;
drop policy if exists "Allow public select on users" on public.users;
drop policy if exists "Allow public insert on users" on public.users;

-- Public select and insert for users (required because app uses Firebase Auth with anon key)
create policy "Allow public select on users" on public.users
  for select using (true);

create policy "Allow public insert on users" on public.users
  for insert with check (true);

-- Subjects table
create table if not exists public.subjects (
  id uuid default uuid_generate_v4() primary key,
  key text unique not null,
  title text not null,
  description text,
  category text,
  pdf_count integer default 0,
  is_active boolean default true,
  created_at timestamp with time zone default timezone('utc'::text, now()) not null,
  updated_at timestamp with time zone default timezone('utc'::text, now()) not null
);

alter table public.subjects enable row level security;

drop policy if exists "Subjects are viewable by everyone" on public.subjects;
drop policy if exists "Only admins can insert subjects" on public.subjects;
drop policy if exists "Only admins can update subjects" on public.subjects;
drop policy if exists "Only admins can delete subjects" on public.subjects;

-- Everyone can read subjects
create policy "Subjects are viewable by everyone" on public.subjects
  for select using (true);

-- PDFs table
create table if not exists public.pdfs (
  id uuid default uuid_generate_v4() primary key,
  subject_id uuid references public.subjects(id) on delete cascade not null,
  title text not null,
  url text not null,
  file_name text,
  file_size bigint,
  page_count integer,
  uploaded_by uuid,
  is_active boolean default true,
  created_at timestamp with time zone default timezone('utc'::text, now()) not null
);

alter table public.pdfs enable row level security;

drop policy if exists "PDFs are viewable by everyone" on public.pdfs;
drop policy if exists "Only admins can insert PDFs" on public.pdfs;
drop policy if exists "Only admins can update PDFs" on public.pdfs;
drop policy if exists "Only admins can delete PDFs" on public.pdfs;

-- Everyone can read PDFs
create policy "PDFs are viewable by everyone" on public.pdfs
  for select using (true);

-- Flashcards table
create table if not exists public.flashcards (
  id uuid default uuid_generate_v4() primary key,
  subject_id uuid references public.subjects(id) on delete cascade not null,
  question text not null,
  answer text not null,
  category text,
  difficulty integer default 1,
  created_at timestamp with time zone default timezone('utc'::text, now()) not null
);

alter table public.flashcards enable row level security;

drop policy if exists "Flashcards are viewable by everyone" on public.flashcards;
drop policy if exists "Only admins can insert flashcards" on public.flashcards;
drop policy if exists "Only admins can update flashcards" on public.flashcards;
drop policy if exists "Only admins can delete flashcards" on public.flashcards;

-- Everyone can read flashcards
create policy "Flashcards are viewable by everyone" on public.flashcards
  for select using (true);

-- Quizzes table
create table if not exists public.quizzes (
  id uuid default uuid_generate_v4() primary key,
  subject_id uuid references public.subjects(id) on delete cascade not null,
  title text not null,
  description text,
  question_count integer default 0,
  time_limit integer,
  is_active boolean default true,
  created_at timestamp with time zone default timezone('utc'::text, now()) not null
);

alter table public.quizzes enable row level security;

drop policy if exists "Quizzes are viewable by everyone" on public.quizzes;
drop policy if exists "Only admins can insert quizzes" on public.quizzes;
drop policy if exists "Only admins can update quizzes" on public.quizzes;
drop policy if exists "Only admins can delete quizzes" on public.quizzes;

-- Everyone can read quizzes
create policy "Quizzes are viewable by everyone" on public.quizzes
  for select using (true);
