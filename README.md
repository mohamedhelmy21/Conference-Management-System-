# Conference Management System

Welcome to the **Conference Management System**!
This application streamlines the organization and management of conferences, offering tailored experiences for three distinct roles: **Managers**, **Attendees**, and **Speakers**.

---

## System Overview

The system enables:
- Conference managers to organize events, sessions, and participants.
- Attendees to register, build personalized schedules, give feedback, and receive certificates.
- Speakers to manage their profiles and review session feedback.

---

## System Roles & Features

### 1. Conference Manager

**Login Credentials**
- **Email:** `nancy.manager@example.com`
- **Password:** `password`

**Features**
- **Manage Conferences**
  - Create, update, and delete conferences.
  - View all managed conferences.
- **Manage Sessions**
  - Create, update, delete sessions.
  - Assign speakers, set session details (capacity, room, time).
- **Manage Speakers**
  - Create and delete speaker accounts.
  - View all speakers.
- **Manage Attendees**
  - Mark attendance for sessions.
  - Remove attendees from conferences.
- **Reports**
  - Generate/view session feedback reports.
  - Generate conference/session attendance reports.
- **Profile**
  - View manager details.
  - Logout.

---

### 2. Attendees

**Existing Users**
- **Mohamed:**
  - Email: `mohamed@gmail.com`
  - Password: `123`
- **Kareem:**
  - Email: `kareem@gmail.com`
  - Password: `123`

**Features**
- **Conference Selection**
  - View and register for available conferences.
- **Personalized Schedule**
  - Register/unregister for sessions.
  - View registered sessions and speaker bios.
- **Feedback**
  - Submit feedback (1-5 rating, optional comment) for attended sessions.
- **Certificate**
  - View/download certificate of attendance after conference ends.
- **Profile**
  - View attendee details.
  - Logout.

---

### 3. Speakers

**Existing User**
- **Email:** `john@gmail.com`
- **Password:** `123`

**Features**
- **My Sessions**
  - View assigned sessions.
  - View feedback received.
- **Profile**
  - Update profile details.
  - Logout.

---

## How to Use

### Login
1. Launch the application.
2. Enter your email and password.
3. Click **Login** to access your portal.

### Navigation
- Use the tabs to switch between features (e.g., Profile, Manage Sessions).
- Click **Logout** in the Profile tab to securely exit.

---

## Key Functionalities

- **Automated Certificate Generation**
  - Certificates are auto-generated for attendees who meet attendance criteria.
  - Managers can also generate certificates manually.
- **Feedback Submission**
  - Attendees can rate sessions and leave comments.
- **Reporting**
  - Managers generate feedback and attendance reports for sessions/conferences.
- **Session Scheduling**
  - Managers create/update sessions; attendees register/unregister and view schedules.
- **Attendee Registration**
  - Attendees register for conferences, unlocking session and feedback features.
- **Speaker Management**
  - Managers create/delete speakers; speakers update profiles and view feedback.

---

## Technical Details

- **Data Persistence:**
  File-based storage in the `data/` directory:
  - `users.json` – User accounts
  - `sessions.json` – Session details
  - `certificates.json` – Attendance certificates
  - `feedback.json` – Session feedback
  - `conferences.json` – Conference details
  - `reports.json` – Generated reports

- **Architecture:**
  - **MVC Pattern:** Separates logic, data handling, and UI.
  - **Service Layer:** Business logic (session management, certificate generation).
  - **Repository Layer:** Data persistence and retrieval.

---

## Summary

This system provides a comprehensive platform for conference management, ensuring a seamless experience for managers, attendees, and speakers.
For any issues or contributions, please refer to the respective documentation or contact the project maintainer.
