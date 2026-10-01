-- Sample data for testing the dynamic app
-- Run this AFTER the main schema.sql
-- All PDF URLs point to GitHub/jsDelivr CDN

-- Insert sample subjects
INSERT INTO public.subjects (key, title, description, category, pdf_count, is_active) VALUES
  ('python_cs', 'Python Programming', 'Python programming fundamentals and practice', 'CS', 2, true),
  ('ds_cs', 'Data Structures', 'Data structures and algorithms fundamentals', 'CS', 2, true),
  ('oop_cs', 'Object Oriented Programming', 'OOP concepts with C++ programming', 'CS', 2, true),
  ('acn_cs', 'Advanced Computer Networks', 'Computer networking fundamentals and protocols', 'CS', 2, true),
  ('m1_cs', 'Mathematics I', 'Engineering mathematics - calculus and algebra', 'CS', 1, true),
  ('m2_cs', 'Mathematics II', 'Advanced engineering mathematics', 'CS', 2, true),
  ('m3_cs', 'Mathematics III', 'Probability, statistics and numerical methods', 'CS', 2, true),
  ('cms1_cs', 'Communication Skills I', 'English communication and soft skills', 'CS', 1, true),
  ('cms2_cs', 'Communication Skills II', 'Advanced communication skills', 'CS', 1, true),
  ('physics_cs', 'Physics', 'Engineering physics fundamentals', 'CS', 2, true),
  ('ee_cs', 'Electrical Engineering', 'Electrical engineering fundamentals', 'CS', 2, true),
  ('ict_cs', 'Fundamentals of ICT', 'ICT basics and computer commands', 'CS', 1, true),
  ('html_cs', 'HTML Programming', 'Web development with HTML', 'CS', 1, true),
  ('linux_cs', 'Linux Commands', 'Linux operating system commands', 'CS', 1, true),
  ('os_cs', 'Operating Systems', 'OS concepts and implementation', 'CS', 2, true),
  ('im_cs', 'Information Management', 'Information management systems', 'CS', 1, true),
  ('set_cs', 'Software Engineering & Testing', 'SE principles and testing methodologies', 'CS', 2, true),
  ('jp1_cs', 'Java Programming I', 'Java fundamentals and OOP', 'CS', 2, true),
  ('jp2_cs', 'Java Programming II', 'Advanced Java programming', 'CS', 2, true),
  ('st_it', 'Software Testing', 'Software testing methodologies', 'IT', 1, true),
  ('rdbms_cs', 'RDBMS', 'Relational database management systems', 'CS', 2, true),
  ('aap_cs', 'Android Programming', 'Android app development', 'CS', 1, true),
  ('cc_cs', 'Cloud Computing', 'Cloud computing fundamentals', 'CS', 2, true),
  ('foe_cs', 'Fundamentals of Electronics', 'Electronics basics and components', 'CS', 2, true),
  ('cg_cs', 'Computer Graphics', 'Computer graphics principles', 'CS', 2, true),
  ('cn_cs', 'Computer Networks', 'Networking fundamentals', 'CS', 2, true),
  ('cphm_cs', 'Computer Peripherals & Hardware', 'Hardware maintenance and peripherals', 'CS', 2, true),
  ('cs_cs', 'Computer Security', 'Information security principles', 'CS', 2, true),
  ('dmi_cs', 'Data Mining', 'Data mining techniques and applications', 'CS', 2, true),
  ('dtmp_cs', 'Digital Techniques & Microprocessor', 'Microprocessor architecture and digital logic', 'CS', 2, true),
  ('edp_cs', 'Entrepreneurship Development', 'Business entrepreneurship fundamentals', 'CS', 1, true),
  ('ce_cs', 'Computing Essentials', 'Basic computing concepts', 'CS', 2, true);

-- Insert sample PDFs for Data Structures
INSERT INTO public.pdfs (subject_id, title, url, file_name, file_size, page_count) VALUES
  (
    (SELECT id FROM public.subjects WHERE key = 'ds_cs'),
    'DS Unit 1 - Introduction to Data Structures',
    'https://cdn.jsdelivr.net/gh/Shivam154CO/PolyStuff_App@main/pdf/DS%20UNIT%201.pdf',
    'DS UNIT 1.pdf',
    2500000,
    45
  ),
  (
    (SELECT id FROM public.subjects WHERE key = 'ds_cs'),
    'DS Unit 2 - Arrays and Linked Lists',
    'https://cdn.jsdelivr.net/gh/Shivam154CO/PolyStuff_App@main/pdf/DS%20UNIT%202.pdf',
    'DS UNIT 2.pdf',
    2800000,
    52
  );

-- Insert sample PDFs for OOP
INSERT INTO public.pdfs (subject_id, title, url, file_name, file_size, page_count) VALUES
  (
    (SELECT id FROM public.subjects WHERE key = 'oop_cs'),
    'OOP Unit 1 - Introduction to C++',
    'https://cdn.jsdelivr.net/gh/Shivam154CO/PolyStuff_App@main/pdf/OOP%20UNIT%201.pdf',
    'OOP UNIT 1.pdf',
    3000000,
    55
  ),
  (
    (SELECT id FROM public.subjects WHERE key = 'oop_cs'),
    'OOP Unit 2 - Classes and Objects',
    'https://cdn.jsdelivr.net/gh/Shivam154CO/PolyStuff_App@main/pdf/OOP%20UNIT%202.pdf',
    'OOP UNIT 2.pdf',
    3200000,
    60
  );

-- Insert sample PDFs for Computer Networks
INSERT INTO public.pdfs (subject_id, title, url, file_name, file_size, page_count) VALUES
  (
    (SELECT id FROM public.subjects WHERE key = 'acn_cs'),
    'ACN Unit 1 - Network Fundamentals',
    'https://cdn.jsdelivr.net/gh/Shivam154CO/PolyStuff_App@main/pdf/ACN%20UNIT%201.pdf',
    'ACN UNIT 1.pdf',
    2700000,
    48
  ),
  (
    (SELECT id FROM public.subjects WHERE key = 'acn_cs'),
    'ACN Unit 2 - TCP/IP Protocol',
    'https://cdn.jsdelivr.net/gh/Shivam154CO/PolyStuff_App@main/pdf/ACN%20UNIT%202.pdf',
    'ACN UNIT 2.pdf',
    2900000,
    53
  );

-- Insert sample PDFs for Mathematics I
INSERT INTO public.pdfs (subject_id, title, url, file_name, file_size, page_count) VALUES
  (
    (SELECT id FROM public.subjects WHERE key = 'm1_cs'),
    'M1 Unit 1 - Calculus',
    'https://cdn.jsdelivr.net/gh/Shivam154CO/PolyStuff_App@main/pdf/M1%20UNIT%201.pdf',
    'M1 UNIT 1.pdf',
    2000000,
    40
  );

-- Insert sample PDFs for Physics
INSERT INTO public.pdfs (subject_id, title, url, file_name, file_size, page_count) VALUES
  (
    (SELECT id FROM public.subjects WHERE key = 'physics_cs'),
    'Physics Unit 1 - Mechanics',
    'https://cdn.jsdelivr.net/gh/Shivam154CO/PolyStuff_App@main/pdf/PHY%20UNIT%201.pdf',
    'PHY UNIT 1.pdf',
    2600000,
    50
  ),
  (
    (SELECT id FROM public.subjects WHERE key = 'physics_cs'),
    'Physics Unit 2 - Thermodynamics',
    'https://cdn.jsdelivr.net/gh/Shivam154CO/PolyStuff_App@main/pdf/PHY%20UNIT%202.pdf',
    'PHY UNIT 2.pdf',
    2400000,
    46
  );
