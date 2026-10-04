# Online Healthcare Management System (CarePlus)

A frontend-only healthcare platform with three roles (Admin, Doctor, Patient), built with **HTML5, CSS3, Vanilla JavaScript and localStorage**. No npm, no server, no database.


## How to run
1. Copy the complete project folder and keep its structure unchanged.
2. Double-click `index.html`.
3. Log in with User ID and password, or use the "Continue as ..." buttons.

---

# How this website was built, brick by brick

## Brick 0: The idea and the constraints
Everything had to work by opening `index.html` directly (`file://`). That one rule shaped every decision:
- Plain `<script>` tags in dependency order.
- Relative paths only.
- `localStorage` as the "database", so separate HTML pages can share data.

## Brick 1: Folder structure
```
online-healthcare-system/
|-- index.html      landing page
|-- login.html      simulated login
|-- admin.html  doctor.html  patient.html   one page per role
|-- css/style.css   all styling (light + dark theme)
|-- js/
|   |-- core.js     the foundation: storage, session, UI helpers, shared logic
|   |-- auth.js     login page behaviour
|   |-- admin.js  doctor.js  patient.js    role-specific screens
`-- assets/
```
Each role page is almost empty HTML: it loads `core.js` then its own script, and JavaScript builds the screen.

## Brick 2: The data layer (`core.js`, storage)
A tiny wrapper around localStorage:
```js
const K=n=>'healthcare_'+n;
const db={ get(n,d){...JSON.parse(localStorage.getItem(K(n)))...},
           set(n,v){localStorage.setItem(K(n),JSON.stringify(v))} };
```
Keys used: `users`, `appointments`, `feedback`, `notifications`, `settings`, `schedules`, `records`, `session`, `theme`.

## Brick 3: Seed data
`seed()` runs once, only if `healthcare_users` does not exist, so refreshing never wipes your data. It creates 1 admin, 10 doctors, 20 patients, 30 appointments, feedback, notifications and default settings, all fictional.

## Brick 4: Simulated login and sessions
- `auth.js` only checks that the ID and password are **not empty**. No password comparison.
- `login(userId, role, remember)` stores `{userId, role, name}`: in localStorage if "Remember me" is ticked, otherwise in sessionStorage (gone when the tab closes).
- `displayName()` turns `adarsh@gmail.com` into **Adarsh** and `doctor123` into **Doctor123**.
- `guard(role)` runs at the top of each dashboard. No session sends you to the login page, and the wrong role is redirected to your own dashboard.
- `logout()` clears the session.

## Brick 5: The dashboard shell
`shell(role, menu, sections)` draws the sidebar, top bar, theme toggle, notification bell and content area for any role. Each role passes a menu and an object of section functions that return HTML. `go()` shows a loading skeleton and `redraw()` re-renders after every change. This is why three dashboards share one layout.

## Brick 6: Reusable UI components
All in `core.js`, used by every role:
- `toast()` for success, error, info and warning messages (no `alert()`).
- `modal()` and `confirmBox()` for forms and confirmations, with native browser validation.
- `badge()`, `stat()`, `empty()` (empty states), `bars()` (CSS charts).
- `notify()`, `myN()` and `readAll()` for role-based notifications.

## Brick 7: Appointment engine (the heart of the project)
Shared by all roles:
- `apptPanel()` and `drawT()` give real search, status/doctor/date filters and sorting.
- `apptTable()` shows buttons based on role and status (Confirm, Complete, Reschedule, Cancel, Feedback).
- `setStatus()` updates the appointment, saves it and notifies the other party.
- `slotsFor(doctor, date)` computes Available / Booked / Blocked / Completed slots, so a booked slot can't be double-booked.

## Brick 8: Admin screens (`admin.js`)
Dashboard stats and recent activity; user management (add, edit, delete, activate/deactivate with search and role filter); appointment management; analytics (CSS donut and bar charts plus completion/cancellation rates); system settings saved to localStorage.

## Brick 9: Doctor screens (`doctor.js`)
Dashboard; daily/weekly schedule with Add Availability and Block Time; appointments (confirm, reject, reschedule, complete with diagnosis and prescription); patient records with editable notes; feedback summary with rating distribution. A "Viewing as doctor" selector lets one demo login act as any seeded doctor.

## Brick 10: Patient screens (`patient.js`)
A booking wizard (specialty, doctor, date, time, details, confirmation, with an `.ics` calendar file); appointment tabs (Upcoming / Past / Cancelled); medical history with visit timeline and prescriptions; profile editing; star-rating feedback. On first login a new patient is added to `users`, so the admin sees them straight away.

## Brick 11: Cross-role magic
There is no server, yet the pages stay in sync because every page reads and writes the same localStorage keys:
```
Patient books -> localStorage -> Doctor confirms -> localStorage -> Patient sees "Confirmed" -> Admin sees the same record
```
Each status change also creates a notification for the affected role.

## Brick 12: Styling (`style.css`)
- CSS variables for colours; a `[data-theme=dark]` block overrides them, and the choice is saved in localStorage.
- Grid and flexbox layouts; tables scroll horizontally on small screens.
- Under 800px the sidebar becomes a slide-in drawer.
- Small transitions: hover lift, fade-in, skeleton loading.

---

## Why it stays light and fast
- No libraries or frameworks: just three file types, loaded locally.
- Charts are plain CSS, not a chart library.
- Only the screen you are viewing is rendered.
- Data lives in the browser, so there are no network requests.

## Honest limitations
- Login and security.
- Fictional data only; not suitable for real patients.
- Possible upgrades: a real backend and database, real authentication, email/SMS reminders, and more automated testing.

## script
1. Open `index.html` and click **Get Started**.
2. Log in as **Patient** (any ID/password) and book an appointment.
3. Logout, then log in as **Doctor**. Pick the same doctor under "Viewing as", and **Confirm**.
4. Log in as **Patient** again: the status is **Confirmed**.
5. Log in as **Admin**: the same appointment appears under Appointments.
