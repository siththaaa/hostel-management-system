Hostel Management System
This is a comprehensive Java Spring Boot application designed to manage hostel operations, including student registration, room allocation, fee tracking, complaint management, and event scheduling. The system also features a dynamic reporting dashboard with real-time data visualization using Chart.js.

🚀 Getting Started
1. Database Setup
The application uses MySQL as the primary database. Follow these steps to prepare your environment:

Open your MySQL terminal or a GUI tool like phpMyAdmin or MySQL Workbench.

Create a new database named db1:

SQL

CREATE DATABASE db1;
The application is configured to connect to db1 on localhost. Ensure your application.properties matches these settings.

2. Default Login Credentials
To access the Admin Dashboard, use the following credentials:

Username: admin

Password: admin123

🛠️ Installation & Execution
Clone the Repository: Download the project files to your local machine.

Open in IDE: Open the project in IntelliJ IDEA or Eclipse.

Maven Reload: Allow the IDE to download all required dependencies (Spring Web, JPA, MySQL Connector, Thymeleaf, and Spring Security).

Run the Application: Locate the HostelSystemApplication.java file and run it as a Java Application.

Access the App: Open your web browser and go to: http://localhost:8080

📊 Key Features
Admin Dashboard: Overview of system operations.

Student Management: Register, update, and track active/inactive students.

Room Allocation: Monitor room status as "Occupied" or "Available".

Fee Tracking: Manage paid and pending payments.

Complaint System: Track resolution progress from "Pending" to "Resolved".

Events: Schedule and manage hostel events.

Visual Reports: Real-time charts for data distribution.

📂 Project Structure
Controllers: Handles web requests and navigation logic.

Models (Entities): Defines the database schema for Students, Rooms, Fees

Repositories: Manages database communication using Spring Data JPA.

Templates: Thymeleaf HTML files for the front-end user interface.

Static: CSS files and images for styling.
