Conference Management System
Welcome to the Conference Management System! This system facilitates the management of conferences by providing distinct roles and features for Managers, Attendees, and Speakers. It allows conference managers to organize sessions, register attendees, manage feedback, and generate reports. Attendees and speakers can access conference details and interact with the system based on their roles.
________________________________________
System Roles and Features
1. Conference Manager
Credentials:
•	Email: nancy.manager@example.com
•	Password: password
Features:
•	Manage Conferences:
o	Create, update, and delete conferences.
o	View a list of all managed conferences.
•	Manage Sessions:
o	Create, update, and delete sessions.
o	Assign speakers to sessions.
o	Manage session details like capacity, room, and time.
•	Manage Speakers:
o	Create and delete speaker accounts.
o	View a list of all speakers.
•	Manage Attendees:
o	Mark attendance for sessions.
o	Remove attendees from conferences.
•	Reports:
o	Generate and view session feedback reports.
o	Generate conference and session attendance reports.
•	Profile:
o	View manager profile details.
o	Logout.
________________________________________
2. Attendees
Existing Users:
1.	Mohamed
o	Email: mohamed@gmail.com
o	Password: 123
2.	Kareem
o	Email: kareem@gmail.com
o	Password: 123
Features:
•	Conference Selection:
o	View available conferences and register for one.
•	Personalized Schedule:
o	Register for sessions within a conference.
o	View a personalized schedule of registered sessions.
o	View speaker bios.
o	Unregister from sessions.
•	Feedback:
o	Submit feedback for attended sessions (rating and optional comments).
•	Certificate:
o	View or download a certificate of attendance after the conference ends.
•	Profile:
o	View attendee profile details.
o	Logout.
________________________________________
3. Speakers
Existing User:
•	Email: john@gmail.com
•	Password: 123
Features:
•	My Sessions:
o	View a list of assigned sessions.
o	View feedback received for sessions.
•	Profile:
o	Update profile details.
o	Logout.
________________________________________
How to Use the System
Login
1.	Launch the application.
2.	Enter your email and password on the login screen.
3.	Click Login to proceed to your respective portal.
Navigation
•	Use the tabs in your portal to navigate between features (e.g., Profile, Manage Sessions, etc.).
Common Actions
•	Logout: Click the Logout button in the Profile tab to securely exit the system.
________________________________________
Key Functionalities
1. Automated Certificate Generation
•	Certificates are automatically generated for attendees after the conference ends, provided they meet the attendance criteria.
•	Managers can also generate certificates manually via the "Generate Certificates" button.
2. Feedback Submission
•	Attendees can submit feedback for sessions they attended.
•	Feedback includes a mandatory rating (1-5) and an optional comment.
3. Reporting
•	Managers can generate reports for sessions and conferences:
o	Feedback Report: Includes attendee feedback for a session, aggregated and averaged.
o	Attendance Report: Includes attendees of a session or conference, detailing attendance records.
4. Session Scheduling
•	Managers can create and update sessions, including assigning speakers and setting session details (e.g., date, room, capacity).
•	Attendees can register for or unregister from sessions and view their personalized schedule.
5. Attendee Registration
•	Attendees can view all available conferences and register for one.
•	Conference registration automatically enables session scheduling and feedback submission features.
6. Speaker Management
•	Managers can create and delete speaker accounts.
•	Speakers can update their profiles and view their assigned sessions, along with feedback from attendees.
________________________________________
Technical Details
Data Persistence
The system uses file-based storage. Data files include:
•	Users: data/users.json
•	Sessions: data/sessions.json
•	Certificates: data/certificates.json
•	Feedback: data/feedback.json
•	Conferences: data/conferences.json
•	Reports: data/reports.json
Architecture
•	MVC Pattern: The system follows the Model-View-Controller design pattern to separate logic, data handling, and UI.
•	Service Layer: Handles business logic (e.g., session management, certificate generation).
•	Repository Layer: Handles data persistence and retrieval.

