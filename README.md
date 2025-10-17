# 🏥 E Health Care

## 📋 Overview
**E Health Care** is a desktop application developed using **Java Swing** and **AWT** that helps manage both **medical shop inventory** and **hospital appointment scheduling**.  
It allows users to efficiently manage medicines, suppliers, and sales data while providing an alert system for low stock.  
The hospital module handles basic appointment management for patients.

---

## ⚙️ Features
- 🧾 **Inventory Management** – Add, update, delete, and view medicine stock details  
- 💊 **Drug & Company Records** – Maintain detailed information about medicines and the companies supplying them  
- 💰 **Sales Management** – Track and update medicine sales information  
- 🚨 **Low Stock Alert System** – Automatically notifies users when stock is running low  
- 🏥 **Appointment Management** – Schedule and manage hospital appointments  
- 🔍 **Search & View Records** – Quick access to stored data  

---

## 🧰 Tech Stack
- **Programming Language:** Java  
- **GUI Framework:** Java Swing & AWT  
- **Database:** MySQL  
- **Database Connectivity:** JDBC  
- **IDE:** Any Java-compatible IDE (e.g., NetBeans, Eclipse, IntelliJ IDEA)  

---

## 🗃️ Database Setup
1. Install **MySQL** and create the required database (ensure table structures exist beforehand).  
2. Update your JDBC connection details (database name, username, password) in the Java source code.  
3. Ensure the **MySQL Connector JAR** file is added to your project classpath.  
   - Example: `mysql-connector-j-8.0.xx.jar`  

---

## 🚀 How to Run the Application
1. Open the project in your preferred IDE.  
2. Make sure the **JDBC connector** JAR file is added to your project’s library path.  
3. Connect to your MySQL database.  
4. Run the main class file (usually containing the `main()` method).  
5. Use the GUI to manage medicines, companies, sales, and appointments.

---

## 📦 Prerequisites
- Java JDK 8 or higher  
- MySQL Server  
- MySQL JDBC Connector JAR  

---

## 👨‍💻 Developer
**Developed by:** *[Your Name]*  

*(You can replace this section with your name or team details.)*

---

## 📄 License
This project is provided for educational purposes.  
You may modify or extend it as needed for learning and development.

---

## 🖼️ Screenshots
> *(Add screenshots of your UI here when available — e.g., main dashboard, inventory window, appointment window)*

---

### 💡 Notes
- Ensure all database tables are created before running the application.  
- The alert system depends on threshold values defined in the stock management logic.  
- Can be extended to support advanced features like billing, reporting, and authentication.

---

