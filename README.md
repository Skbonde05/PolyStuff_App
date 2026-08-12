
<h1 align="center"> <img src="app/src/main/res/drawable/polystuff.png" width="40" alt="PolyStuff Logo"/> PolyStuff – Android Application</h1>

<p align="center">
  <a href="LICENSE"><img src="https://img.shields.io/badge/License-MIT-blue" alt="License"></a>
  <a href="https://developer.android.com/studio"><img src="https://img.shields.io/badge/IDE-Android%20Studio-green?logo=android" alt="Android Studio"></a>
  <a href="https://www.java.com/"><img src="https://img.shields.io/badge/Java-11-orange?logo=java" alt="Java"></a>
  <a href="https://firebase.google.com/"><img src="https://img.shields.io/badge/Backend-Firebase-yellow?logo=firebase" alt="Firebase"></a>
</p>

<p align="center">
  <a href="https://github.com/Skbonde05/PolyStuff" target="_blank">
    <img src="https://img.shields.io/badge/🌐 Live%20Demo-Check%20Now-brightgreen?style=for-the-badge" alt="Live Demo"/>
  </a>
</p>

---

## 📖 About the Project  

**PolyStuff** is an **Android application** designed to simplify **student and institutional activities**.  
It centralizes resources, announcements, and academic utilities into a single, user-friendly platform. 

---

## ✨ Features  

- 📚 Access academic resources in one place  
- 📰 View college announcements and circulars  
- 📅 Stay updated with events and schedules  
- 🛠️ Handy student tools (calculator, notes, etc.)  
- 📱 Modern and responsive UI for Android devices  
- 🔐 Admin CMS for owners to manage content  
- 📦 Free unlimited PDF storage via GitHub + jsDelivr CDN  

---

## 🛠 Tech Stack  

<p align="center">
  <img src="https://img.shields.io/badge/Java-11-orange?logo=java" alt="Java">
  <img src="https://img.shields.io/badge/Kotlin-1.9-purple?logo=kotlin" alt="Kotlin">
  <img src="https://img.shields.io/badge/Android%20Studio-IDE-green?logo=android" alt="Android Studio">
  <img src="https://img.shields.io/badge/Firebase-Auth%20%2B%20Database-yellow?logo=firebase" alt="Firebase">
  <img src="https://img.shields.io/badge/Supabase-Backend-green?logo=supabase" alt="Supabase">
  <img src="https://img.shields.io/badge/GitHub%20%2B%20jsDelivr-Storage-blue?logo=github" alt="CDN">
</p>

---

## 🔐 Admin CMS  

The app includes a role-based Admin Dashboard for owners only:
- **Normal users**: Browse notes, PDFs, videos, and profile.
- **Admin/Owner**: Access hidden Admin Dashboard to add/edit/delete content.

### How Admin Access Works
1. Admin status is stored in Firebase Realtime Database under `users/{uid}` with fields `role: "admin"` and `isAdmin: true`.
2. `AdminManager` checks these fields before granting access.
3. The Admin Dashboard UI is hidden for normal users and only visible to admins.

### Setting Up an Admin User
In Firebase Realtime Database, set the user node:
```json
"users": {
  "USER_UID": {
    "name": "Admin Name",
    "email": "admin@example.com",
    "username": "admin",
    "role": "admin",
    "isAdmin": true
  }
}
```

---

## 📦 Storage: GitHub + jsDelivr CDN  

PDFs are stored in the `pdf/` folder of this GitHub repository and served via jsDelivr CDN for fast, free delivery.
- **Repository**: `Shivam154CO/PolyStuff_App`
- **CDN Base**: `https://cdn.jsdelivr.net/gh/Shivam154CO/PolyStuff_App@main/pdf/`
- **Raw GitHub**: `https://raw.githubusercontent.com/Shivam154CO/PolyStuff_App/main/pdf/`

### Why PDFs Might Not Open
1. **Expired Firebase URLs**: Old `subjects_data.json` entries contain Firebase Storage URLs that may have expired download tokens. The app now automatically tries jsDelivr CDN first.
2. **Files not pushed to GitHub**: Ensure all PDFs in the local `pdf/` folder are committed and pushed to the remote repository.
3. **Network issues**: The app requires internet to load PDFs from CDN.

---

## ⚙️ Configuration  

### Firebase (`google-services.json`)
Place your `google-services.json` in `app/`.

### Supabase (Optional, for future use)
1. Create a Supabase project at [supabase.com](https://supabase.com).
2. Update `app/src/main/java/myapp/org/userapp/supabase/SupabaseConfig.java` with your project URL and anon key.
3. Configure Row Level Security (RLS) policies in Supabase.

---

## 🧪 Building the Project  

1. Open in Android Studio.
2. Sync Gradle.
3. Build and run on a device with internet access.
