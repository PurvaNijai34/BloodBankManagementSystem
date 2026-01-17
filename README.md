# 🩸 Blood Bank Management System (Java)

A **console-based Blood Bank Management System** developed in **Java** using **Object-Oriented Programming (OOP)** principles.  
This project helps manage **donors, hospitals, blood donations, blood stock, and blood requests** in an organized way.

---

## 🧠 OOP Concepts Used

### ✔ Abstraction
- Implemented using the abstract class `Person`

### ✔ Inheritance
- `Donor` and `Hospital` classes inherit from `Person`

### ✔ Polymorphism
- Method overriding of `registerPerson()` and `display()`

### ✔ Encapsulation
- Private/protected data members with getters and setters

---

## 🧩 Class Structure

### 🔹 Person (Abstract Class)
- Attributes: `name`, `contact`, `bloodType`, `rhesus`
- Methods:
  - `registerPerson()`
  - `display()`

---

### 🔹 Donor
- Attributes:
  - `fitness` (1 = fit, 0 = unfit)
  - `DNR` (Donor Registration Number)
- Functions:
  - Register donor
  - Update fitness/contact
  - Display donor details

---

### 🔹 Hospital
- Attributes:
  - `HPID` (Hospital ID)
- Functions:
  - Register hospital
  - Payment verification
  - Display hospital details

---

### 🔹 BloodPacket
- Represents one blood unit
- Attributes:
  - Blood group (A, B, O, AB)
  - Rhesus factor (+ / -)
  - Expiry year

---

### 🔹 BloodBank
- Core logic of the system
- Manages:
  - Donors
  - Hospitals
  - Blood packets
  - Blood stock
  - Authentication

---

### 🔹 BloodBankSystem
- Main class
- Displays menu and controls program flow

---

## 🔑 Default Login Credentials

```text
Username: 123
Password: 123
