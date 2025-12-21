# vehicle-parking-system

🚗 Vehicle Parking Control &amp; Fee System
A Java Swing–based Smart Vehicle Parking Management System with SQLite database integration, designed as a college mini-project.
The system provides a modern GUI for parking operations with permanent data storage, clean UI, and smooth user experience.


📌 Features

🔐 Login System
Secure login screen with modern vehicle-themed UI
Custom “Login Successful” dialog

🅿️ Parking Operations
Park a vehicle using vehicle plate number
Remove a parked vehicle and calculate parking fee
View all currently parked vehicles

💾 Database Support
Uses SQLite for permanent storage
Data persists even after application restarts

🎨 Modern UI
Java Swing–based GUI
Gradient backgrounds and icons
Dashboard with table view for parked vehicles


🛠️ Technologies Used

| Technology         | Purpose                 |
| ------------------ | ----------------------- |
| Java (JDK 8+)      | Core application        |
| Java Swing         | GUI                     |
| SQLite             | Database                |
| JDBC               | Database connectivity   |
| VS Code / Terminal | Development & Execution |


📂 Project Structure

Sample Java/
│
├── src/
│   └── com/
│       └── parking/
│           ├── ui/
│           │   ├── LoginGUI.java
│           │   └── MainDashboard.java
│           │
│           ├── service/
│           │   └── ParkingLot.java
│           │
│           ├── model/
│           │   └── Vehicle.java
│           │
│           ├── db/
│           │   └── Database.java
│           │
│           └── exception/
│               ├── ParkingFullException.java
│               └── VehicleNotFoundException.java
│
├── lib/
│   └── sqlite-jdbc-3.36.0.3.jar
│
├── bin/   (compiled files)
│
└── parking.db   (SQLite database)


⚙️ Database Details

Database Name: parking.db
Database Type: SQLite
Table: vehicles

Table Structure:
CREATE TABLE vehicles (
    plate TEXT PRIMARY KEY,
    entry_time TEXT
);


▶️ How to Compile & Run (VS Code / Terminal)

1️⃣ Clean old compiled files
cd "D\......"
rmdir bin /S /Q
mkdir bin
2️⃣ Compile the project
cd src
javac -d ..\bin -cp "..\lib\sqlite-jdbc-3.36.0.3.jar" (Get-ChildItem -Recurse -Filter *.java).FullName
3️⃣ Run the application
cd ..\bin
java -cp ".;..\lib\sqlite-jdbc-3.36.0.3.jar" com.parking.ui.LoginGUI


🧪 How to Test

1.Launch the application
2.Login using valid credentials
3.Enter a vehicle plate number
4.Click:
     PARK → stores data in database
     REMOVE → removes vehicle & calculates fee
     VIEW PARKED → displays all parked vehicles
5.Open parking.db using DB Browser for SQLite to verify data

🎯 Learning Outcomes

Java Swing GUI development
JDBC database connectivity
SQLite database handling
MVC-style project structure
Exception handling
Event-driven programming


📌 Suitable For

College Mini Project
Java + Database coursework
Viva & Practical examinations


👨‍💻 Developed By

Name: MOHAMMED UVEZ AND SHAIK MOHAMMED ARMAAN
Course: B.TECH IN CSE(IoT AND CYBERSECURITY INCLUDING BLOCKCHAIN TECHNOLOGY)
College: REVA UNIVERSITY
