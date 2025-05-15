# Student Management System

A fully functional desktop-based student management application built with Java Swing and MySQL. This system supports secure login for both admin and regular users, with a comprehensive user interface for managing student records. Admin users can perform advanced actions like managing user accounts and backing up or restoring the database. Designed with simplicity and clarity in mind, this project is ideal for educational institutions or as a college project.

---

## Features

### Student Management (All Users)

* Add, edit, and delete student records.
* View all students in a sortable table.
* Search students by roll number.
* Export student data to a `.csv` file.
* Refresh the student list in real-time.
* Input validation and error handling for better data integrity.

### User Authentication

* Login system with support for both `admin` and `user` roles.
* Admin login through a dedicated interface.
* Role-based access: Admins access more features than normal users.

### Admin Dashboard (Admins Only)

* Create new user accounts with roles (`admin`, `user`).
* Reset any user's password.
* Delete users from the system.
* Backup and restore the MySQL database with a single click.

### Additional UI Features

* Dark Mode: Toggle between light and dark themes dynamically.
* GUI design optimized for desktop resolution (800x600).
* Clean layout using GridLayout and GridBagLayout managers.

---

## Technologies Used

* **Language:** Java
* **UI Framework:** Swing (javax.swing)
* **Database:** MySQL
* **Connector:** JDBC (MySQL Connector/J)
* **IDE Support:** IntelliJ IDEA, Eclipse, NetBeans
* **Build System:** Manual or IDE-based (no Maven/Gradle needed)

---

## User Interface Overview

This application provides a well-structured multi-screen GUI, allowing both regular users and administrators to perform their respective tasks. Below is a breakdown of each major interface and its functionality.

### Login Screen

The entry point of the application. Users can log in using their credentials. Admins can access a separate login form via the **Admin Login** button.

![Screenshot 2025-05-14 211717](https://github.com/user-attachments/assets/db91e251-ffbb-4843-ba4b-923fff65af20)

---

### Admin Login

This is a simplified version of the login screen, strictly for administrators. Only users with the `admin` role will be allowed to proceed.

![Screenshot 2025-05-14 211739](https://github.com/user-attachments/assets/41496d82-e422-483b-a976-88d4467c83ec)

---

### Admin Dashboard

Upon successful admin login, the user is taken to the Admin Dashboard. From here, the administrator can:

* Create new user accounts
* Reset user passwords
* Delete users
* Perform database backup or restore

Each button opens a dedicated form to carry out the selected task.

![Screenshot 2025-05-14 211805](https://github.com/user-attachments/assets/ee048c0e-0282-4669-819f-b7be6dd65fdf)

---

### Create User UI

Allows the admin to create a new user by specifying the username, password, and role (`admin` or `user`). Input validation ensures all fields are filled correctly.

![Screenshot 2025-05-14 211812](https://github.com/user-attachments/assets/9994ed4b-49a0-4f51-953f-7e76cf426a39)

---

### Reset Password UI

The admin can reset the password of any existing user. The admin inputs the target username and a new password. Useful for account recovery or credential changes.

![Screenshot 2025-05-14 211820](https://github.com/user-attachments/assets/11cf69ca-63b1-486a-81c4-138e7f2f598c)

---

### Delete User UI

This interface enables the admin to permanently delete a user account by providing the username. Confirmation prompts may be used to avoid accidental deletions.

![Screenshot 2025-05-14 211827](https://github.com/user-attachments/assets/71a32e8c-e60a-4655-a5cc-3e877174216a)

---

### Student Management Panel

This is the core interface accessible by both admins and regular users. It provides all necessary controls for managing student records:

* Add new student using the form at the top.
* Select a row from the table and update or delete that record.
* Search students by roll number.
* Export all student data to a CSV file.
* Refresh button to reload the student list.

![Screenshot 2025-05-14 211956](https://github.com/user-attachments/assets/72d11b24-fe89-41ea-87ac-da9e3b71164a)

---

### Dark Mode

The application includes a dark mode toggle which immediately switches the theme. Useful for prolonged usage or low-light environments. All UI elements (fields, buttons, tables) are updated dynamically.

![Screenshot 2025-05-14 212011](https://github.com/user-attachments/assets/b4f88cbe-e634-4a2a-93e1-f4e923a314c4)

---

## Configuration

Before running the application, make sure to update the following settings in your code.

### 1. Database Connection

**File:** `DatabaseConnection.java`

```java
private static final String USER = "YOUR_USERNAME";
private static final String PASSWORD = "YOUR_PASSWORD";
```

Replace these with your own MySQL credentials.

---

### 2. Backup/Restore Commands

**File:** `AdminDashboard.java`

```java
// Backup
new ProcessBuilder("mysqldump", "-u", "YOUR_USERNAME", "-pYOUR_PASSWORD", "StudentManagement");

// Restore
new ProcessBuilder("mysql", "-u", "YOUR_USERNAME", "-pYOUR_PASSWORD", "StudentManagement");
```

Ensure that `mysqldump` and `mysql` are available in your system PATH and that the credentials are correct.

---

## Database Setup

Run the following SQL script to set up the database and necessary tables:

```sql
CREATE DATABASE StudentManagement;

USE StudentManagement;

CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(10) NOT NULL
);

CREATE TABLE Students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    roll_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    grade VARCHAR(5),
    age INT,
    address VARCHAR(255)
);

CREATE TABLE AuditLogs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    action TEXT,
    username VARCHAR(50),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

Insert an initial admin user:

```sql
INSERT INTO Users (username, password, role) VALUES ('admin', 'admin', 'admin');
```

---

## How to Run the Project

1. Clone the repository or download the source code.
2. Open the project in your preferred IDE (IntelliJ, Eclipse, etc).
3. Update the database credentials and command paths as explained above.
4. Ensure MySQL server is running and the database/tables are set up.
5. Run the `LoginUI.java` file to start the application.
6. Login using the default admin account:

   * Username: `admin`
   * Password: `admin`

---

## Project Structure

| File                              | Description                     |
| --------------------------------- | ------------------------------- |
| `LoginUI.java`                    | Main login window               |
| `AdminLoginUI.java`               | Admin-specific login interface  |
| `AdminDashboard.java`             | Admin control panel             |
| `StudentManagementUI.java`        | UI for managing student records |
| `User.java`, `Student.java`       | Model classes                   |
| `UserDAO.java`, `StudentDAO.java` | Data access classes             |
| `DatabaseConnection.java`         | JDBC connection handler         |

---

## Security Notes

* Passwords are stored in plaintext — for demonstration only.
* Avoid hardcoding credentials in production environments.
* Use hashed passwords (e.g., BCrypt) and environment configs in real deployments.
* Backup and restore functions use root MySQL credentials — restrict this in live environments.

---

## License
- **This project**: [MIT License](LICENSE) - Free for any use  
