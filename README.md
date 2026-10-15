# CarePlus — Online Healthcare Management System

**CarePlus** is a role-based healthcare management application designed to bring patient appointment booking, doctor scheduling, medical records, notifications, and administrative operations into one organized platform.

**Live website:** https://adarsh-guptaa.github.io/Online-Healthcare-Management-System/

**Project presentation:** [Healthcare_System_Presentation.pptx](https://github.com/user-attachments/files/33507979/Healthcare_System_Presentation.pptx)

---

## Contents

- [Overview](#overview)
- [Core Objectives](#core-objectives)
- [User Roles](#user-roles)
- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Application Workflow](#application-workflow)
- [Appointment Lifecycle](#appointment-lifecycle)
- [Data Model](#data-model)
- [Current Data Storage and Java Backend Architecture](#current-data-storage-and-java-backend-architecture)
- [Run the Application](#run-the-application)
- [Demonstration Workflow](#demonstration-workflow)
- [Security and Privacy Considerations](#security-and-privacy-considerations)
- [Deployment](#deployment)
- [Future Integration Roadmap](#future-integration-roadmap)

---

## Overview

CarePlus is designed to simplify day-to-day healthcare administration through a shared application for patients, doctors, and administrators. It brings appointment management, schedule coordination, patient information, notifications, and operational analytics into a consistent interface.

The system separates functionality by role so that each user can focus on the tasks relevant to them:

- **Patients** can explore doctors, book appointments, review appointment status, maintain profile information, and view available medical-history sections.
- **Doctors** can manage availability, review appointments, update appointment status, and maintain visit-related information.
- **Administrators** can manage user accounts, oversee appointments, review analytics, and configure system settings.

The interface is designed to be responsive, with reusable dashboard components, status indicators, form validation, notifications, and light/dark themes.

## Core Objectives

1. Centralize the main workflows used by patients, doctors, and administrators.
2. Make appointment booking and status tracking straightforward.
3. Help doctors organize availability and appointment schedules.
4. Provide structured areas for patient profiles, visit history, prescriptions, and medical documents.
5. Give administrators tools for user management, appointment oversight, and operational reporting.
6. Maintain a consistent, accessible interface across desktop and mobile screen sizes.
7. Establish a clear separation between the user interface, application logic, and persistent data layer as the system is connected to its Java backend.

---

## User Roles

### 1. Patient

The patient area is designed for self-service healthcare tasks:

- Browse doctors and specialties.
- Select a doctor, date, and available time slot.
- Submit appointment details and review confirmation.
- View upcoming, completed, and cancelled appointments.
- Review medical-history, prescription, and document sections.
- Update profile details.
- Submit feedback using a star rating.
- Receive relevant appointment notifications.

### 2. Doctor

The doctor area supports appointment and schedule management:

- View dashboard information and upcoming appointments.
- Add availability and block unavailable time periods.
- Review appointment requests.
- Confirm, reject, reschedule, or complete appointments where permitted.
- Record visit notes, diagnosis, and prescription details in the relevant workflow.
- Review patient-related information available to the account.
- View feedback summaries and notification updates.

### 3. Administrator

The administrator area supports operational oversight:

- View system summary statistics and recent activity.
- Add, edit, activate, deactivate, and remove user accounts.
- Search and filter users by role.
- Review and manage appointments.
- View appointment completion and cancellation metrics.
- Review department and doctor statistics.
- Configure clinic information, booking limits, notification preferences, and other system settings.
- Review system notifications.

---

## Key Features

### Appointment Management

The appointment workflow is organized around doctor, date, time, patient details, and appointment status. Appointment lists can be searched, filtered, sorted, and viewed according to the active role. Status changes are reflected in the relevant dashboard workflow.

### Doctor Scheduling

Doctors can manage availability and block time periods. Slot calculations are intended to distinguish available, booked, blocked, and completed periods and to prevent conflicting bookings.

### User Management

Administrative user tools include account creation and editing, role-based filtering, status changes, and confirmation prompts for sensitive actions.

### Patient Profile and Medical History

Patient-facing sections organize health summaries, visit history, prescriptions, and medical documents. Profile forms include validation to reduce incomplete or incorrectly formatted input.

### Notifications

The interface supports role-relevant notifications, unread indicators, and a mark-all-as-read interaction. Appointment actions can create notifications for affected users.

### Analytics

Dashboard summaries include appointment status, completion and cancellation rates, user distribution, department statistics, and doctor activity. The current interface uses lightweight CSS-based visualizations.

### Interface and Usability

- Shared dashboard layout and navigation.
- Light and dark themes.
- Theme preference retained in browser storage.
- Toast messages for success, errors, warnings, and information.
- Modal forms and confirmation dialogs.
- Loading skeletons and empty states.
- Responsive tables and mobile navigation drawer.
- Calendar export for appointment details where available.

---

## Technology Stack

| Layer | Technology | Responsibility |
|---|---|---|
| User interface | HTML5 | Page structure and forms |
| Styling | CSS3 | Responsive layout, themes, dashboard components, charts |
| Existing client-side behavior | Vanilla JavaScript | Navigation, interface interactions, validation, and browser-side state |
| Planned server-side application layer | Java, Jakarta Servlets | Request handling, business rules, role checks, and application services |
| Database connectivity | JDBC | Connecting Java application code to a relational database |
| Relational data layer | MariaDB/MySQL-compatible SQL | Structured storage for users, doctors, patients, appointments, schedules, and related records |
| Java build | Maven | Dependency management and packaging the Java application |
| Static frontend hosting | GitHub Pages | Publishing the HTML, CSS, and JavaScript website |

**Implementation note:** The currently published website is a static frontend. Its existing client-side workflows use browser storage for application data. The Java Servlet/JDBC/MariaDB architecture is the server-side direction for persistent, multi-user operation; it must be deployed and connected to the frontend before the website can claim live server-backed storage. GitHub Pages itself does not execute Java Servlets.

---

## Project Structure

The current frontend is organized around role-specific pages and shared styling and scripts. The intended Java backend can be maintained separately so that the current website files remain unchanged during backend development.

```text
Online-Healthcare-Management-System/
├── index.html
├── login.html
├── admin.html
├── doctor.html
├── patient.html
├── css/
│   └── style.css
├── js/
│   ├── core.js
│   ├── auth.js
│   ├── admin.js
│   ├── doctor.js
│   └── patient.js
├── backend/
│   ├── pom.xml
│   └── src/main/java/com/careplus/
│       ├── config/
│       │   └── Database.java
│       └── servlet/
│           ├── HealthCheckServlet.java
│           └── AppointmentServlet.java
├── database/
│   └── schema.sql
└── README.md
```

The `backend/` and `database/` paths represent the separate Java/SQL backend structure to add to the repository if they are not already present. Keep the frontend and backend concerns separate: the browser displays the interface, the Java application processes server requests, and the relational database stores persistent records.

---

## Application Workflow

A fully integrated deployment is intended to follow this request flow:

```text
Patient / Doctor / Admin
          |
          v
HTML, CSS and JavaScript interface
          |
          v
Java Servlet endpoints
          |
          v
Validation and role-based authorization
          |
          v
Service / business logic
          |
          v
JDBC PreparedStatement
          |
          v
MariaDB / MySQL database
          |
          v
JSON or HTTP response to the interface
```

The backend should validate every request on the server. Hiding a button in the interface is not sufficient authorization because a client can be modified.

### Example: Booking an Appointment

1. The patient selects a doctor, date, time, and enters the appointment details.
2. The interface sends the booking request to the Java backend.
3. The backend validates the patient identity, requested doctor, date, time, and booking rules.
4. The backend checks that the selected slot is still available.
5. JDBC writes the appointment to the database inside a transaction.
6. The application returns the saved appointment and its status.
7. The patient, doctor, and administrator dashboards retrieve the appropriate current data from the backend.
8. Relevant notifications are created and displayed.

For reliable booking, the availability check and insert should be protected against concurrent requests, using transactions and suitable database constraints or locking.

---

## Appointment Lifecycle

A typical appointment can move through the following states:

```text
PENDING
   |
   +----> CONFIRMED ----> COMPLETED
   |           |
   |           +--------> CANCELLED
   |
   +----> CANCELLED
```

The exact allowed transitions should be enforced in Java business logic, not only in the frontend. Each change should record who made the change and when it occurred. Rescheduling should also validate the new time slot before saving it.

---

## Data Model

The relational database is intended to use separate tables for the main entities rather than storing all application data in one record.

| Table | Purpose |
|---|---|
| `users` | Login identity, role, account status, and account metadata |
| `patients` | Patient profile information |
| `doctors` | Doctor profile, specialization, and contact information |
| `appointments` | Patient, doctor, appointment date/time, reason, and status |
| `schedules` | Doctor availability and blocked time periods |
| `medical_records` | Visit notes and other authorized medical-record details |
| `prescriptions` | Prescription information linked to a visit or appointment |
| `notifications` | Recipient, message, read status, and creation time |
| `feedback` | Rating, comments, appointment reference, and submission time |
| `settings` | Clinic and application configuration |

The exact table names and columns must match the SQL schema used by the deployed backend. Foreign keys should connect related records, indexes should support common searches, and transactions should protect multi-step operations. Access to medical records must be restricted to authorized users.

---

## Current Data Storage and Java Backend Architecture

### Browser-side storage

The currently published static frontend uses browser storage for its client-side workflows. This is useful for demonstrating interactions in the browser, but browser storage is not a shared server database:

- Data is associated with the browser and origin.
- Different devices and browsers do not automatically share records.
- Clearing browser data can remove stored application information.
- Client-side role checks do not provide server-grade security.
- It is not suitable for storing real patient medical information.

### Java and JDBC data layer

For persistent multi-user operation, the Java backend should own data access and business rules. Servlets receive HTTP requests, validate input, call application logic, and return appropriate responses. JDBC connects that logic to MariaDB/MySQL.

Database access should use `PreparedStatement` for user-provided values, try-with-resources for database resources, transactions for multi-step changes, and environment variables or a secret manager for credentials. Database passwords must never be committed to the repository or embedded in frontend JavaScript.

### Example JDBC connection configuration

Configure the deployed Java server with environment variables rather than hard-coding credentials:

```text
DB_URL=jdbc:mariadb://HOST:3306/careplus_db
DB_USER=CAREPLUS_APP_USER
DB_PASSWORD=SET_THIS_OUTSIDE_SOURCE_CONTROL
```

The actual host, database user, and password must be supplied by the deployment environment. These values are examples, not working credentials.

---

## Run the Application

### Run the existing frontend

1. Clone or download the repository.
2. Keep the folder structure unchanged.
3. Open `index.html` in a browser, or serve the project using a local static web server.
4. Select **Get Started** and use the login screen or the available quick-demo role buttons.
5. Explore the patient, doctor, and administrator workflows.

The current demo login behavior accepts non-empty credentials and is not production authentication. Use fictional data only.

### Run the Java backend

The Java backend requires a compatible Java runtime, Maven, a Jakarta Servlet-compatible server, and a reachable MariaDB/MySQL database.

1. Install a supported JDK and Maven.
2. Create the database and tables using the project's `database/schema.sql`.
3. Create a dedicated database account with only the permissions the application requires.
4. Configure `DB_URL`, `DB_USER`, and `DB_PASSWORD` in the backend server environment.
5. From the `backend/` directory, build the application:

   ```bash
   mvn clean package
   ```

6. Deploy the generated WAR file to a compatible Jakarta Servlet container, such as Tomcat 10.1.
7. Check the backend health endpoint:

   ```text
   GET /healthcare-backend/api/health
   ```

8. Verify database connectivity and server logs before connecting frontend actions to the API.

The backend endpoints must be deployed separately from GitHub Pages. A successful frontend deployment does not mean the Java server or database is online.

---

## Demonstration Workflow

Use fictional records when demonstrating the application:

1. Open the CarePlus website and enter the patient area.
2. Create an appointment using an available doctor and time.
3. Review the appointment in the patient appointment list.
4. Open the doctor area and review the appointment request.
5. Change the appointment status using the available doctor action.
6. Review the updated status in the relevant dashboards.
7. Open the administrator area to review users, appointments, and analytics.
8. Change a display preference, such as the theme, and verify the interface behavior.

When running the static frontend, the workflow demonstrates client-side behavior and browser-local data. In an integrated deployment, repeat the workflow across separate browser sessions or devices to verify that records are truly being saved and retrieved by the server.

---

## Security and Privacy Considerations

Healthcare applications handle sensitive information and must be designed accordingly. Before using real patient data, implement and test the following:

- Server-side authentication with securely hashed passwords.
- Server-side role-based authorization for every protected operation.
- Secure session handling, session expiration, and logout invalidation.
- HTTPS for all deployed traffic.
- Input validation and parameterized database queries.
- Least-privilege database credentials and protected secrets.
- Audit logging for access to and modification of medical records.
- Appropriate access restrictions for patient, doctor, and administrator roles.
- Backups, recovery procedures, and database migration practices.
- Privacy, retention, consent, and applicable legal/regulatory requirements.
- Protection against common web risks, including injection, cross-site scripting, cross-site request forgery, and insecure direct object references.

Do not use real patient information in the demo environment. The published static frontend and client-side demo login are not a secure medical-record system.

---

## Deployment

### Frontend

The static website is published through GitHub Pages:

https://adarsh-guptaa.github.io/Online-Healthcare-Management-System/

GitHub Pages can host the frontend files, but it cannot run a Java Servlet application or host a relational database process.

### Backend and database

To make the system available across devices with shared persistent data:

1. Deploy the Java WAR to a Java-compatible hosting service.
2. Provision a separately hosted MariaDB/MySQL database.
3. Configure the backend using environment variables or a secret manager.
4. Expose the API through HTTPS.
5. Configure the frontend API base URL and connect each relevant action to the corresponding endpoint.
6. Apply authentication, authorization, CORS and CSRF protections as appropriate to the deployment.
7. Test the full patient-to-doctor-to-admin workflow using separate accounts and sessions.

The backend URL, database host, credentials, and production deployment details should be documented only after they have been configured and verified.

---

## Future Integration Roadmap

- Connect login and session management to server-side Java authentication.
- Implement persistent user and role management through JDBC.
- Connect appointment booking, rescheduling, cancellation, and status changes to transactional database operations.
- Add persistent doctor schedules and conflict prevention.
- Store patient profiles, medical records, prescriptions, notifications, and feedback in the database.
- Load dashboard analytics from database queries.
- Add audit trails, secure password reset, and optional email/SMS notifications.
- Add automated unit, integration, and end-to-end tests.
- Add monitoring, backups, recovery, and deployment documentation.

---

## Project Summary

CarePlus brings the key workflows of a healthcare management system into one role-based interface. Its design covers appointment booking, doctor scheduling, patient information, administrative management, notifications, analytics, and responsive presentation. The intended Java/JDBC/SQL backend provides the path toward secure, persistent, multi-user operation, while GitHub Pages serves the static frontend.

**Repository:** https://github.com/adarsh-guptaa/Online-Healthcare-Management-System  
**Live website:** https://adarsh-guptaa.github.io/Online-Healthcare-Management-System/

*CarePlus is intended for educational demonstration and development. Do not use it to make clinical decisions or store real patient data unless the necessary backend, security, privacy, and operational safeguards have been implemented and independently tested.*
