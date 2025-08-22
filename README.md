# Doctor Appointment Booking System

## Overview
The Doctor Appointment Booking System is a web application designed to facilitate the booking and management of doctor appointments. This project leverages Java Servlets, JSP, and various web development technologies to provide a seamless experience for both patients and doctors.

#### 📂 Project Structure

The repository tree can be explored here:  
[Doctor's Appointment Booking Application - Repo Tree](https://githubtree.mgks.dev/repo/sai24k/Doctor-s-Appointment-Booking-Application-/main/)

## Features
- **User Authentication**: Secure login and registration for patients and doctors.
- **Appointment Booking**: Patients can book, view, and cancel appointments.
- **Doctor Management**: Doctors can manage their schedules and view appointments.
- **Responsive Design**: User-friendly interface compatible with various devices.

## Technologies Used
- **Frontend**: HTML, CSS, JavaScript
- **Backend**: Java Servlets, JSP
- **Database**: Oracle
- **Server**: GlassFish Server

## Installation
1. **Clone the repository**:
    ```bash
    git clone https://github.com/Duvva-S-N-Kusuma-Haranadh/Web-Development-Project---DocConnect.git
    ```
2. **Import the project** into your preferred IDE (e.g., Eclipse, IntelliJ IDEA).
3. **Configure the database**:
    - Create a oracle database named `Appointments` , `Doctors`, `Patients`.
    - Import the provided SQL script to set up the tables.
4. **Update database credentials** in `dbconfig.properties` file:
    ```properties
    db.url=jdbc:oracle:thin:@localhost:1521:xe
    db.username=system
    db.password=lbrce
    ```
5. **Deploy the project** on Apache Tomcat server.
6. **Run the application** and access it via `http://localhost:8081/DocConnect`.

## Usage
1. **Register** as a new user (patient or doctor).
2. **Login** with your credentials.
3. **Book an appointment** by selecting a doctor and available time slot.
4. **Manage appointments** through your dashboard.

## Contributing
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes and commit them (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements
- Thanks to all contributors and open-source libraries used in this project.

