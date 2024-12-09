
# Bus Ticket Management Web Application 🚌


## Table of Contents
1. [Introduction](#introduction)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [System Architecture](#system-architecture)
5. [Database Structure](#database-structure)
6. [Usage](#usage)
7. [Screenshots](#screenshots)
8. [Future Improvements](#future-improvements)
9. [Contributors](#contributors)


---

## 1. Introduction
The **Bus Ticket Management Web App** is designed to facilitate easy booking, managing trips, and invoicing for both passengers and administrators. This system offers a seamless experience, ensuring accurate ticket reservations and customer notifications.

![Introduction Image](#)  <!-- Replace # with the actual image path -->

---

## 2. Features
- User registration and login.
- Search and filter trips by route and date.
- Book tickets for single and round trips..
- Admin dashboard for managing trips, routes, customers and bill.
- Generate and send invoices upon successful booking.
- View booking history and manage cancellations.

---

## 3. Technologies Used
- **Frontend**: HTML, CSS, JavaScript, Bootstrap, Thymleaf
- **Backend**: Spring Boot
- **Database**: MySQL
- **Others**: Postman (API Testing)

---

## 4. System Architecture
This project follows a client-server architecture, with separate components for the frontend, backend, and database for improved scalability and maintainability.

---

## 5. Database Structure
The core entities include:
- **Account**: Quản lý thông tin tài khoản người dùng.
- **Customer, Driver, Controller**: Quản lý thông tin của khách hàng, tài xế, phụ xe có trong hệ thống.
- **Bill**: Quản lý hóa đơn thanh toán.
- **Bill_Detail**: Lưu chi tiết từng hóa đơn.
- **Bus**: Quản lý thông tin xe.
- **Bus**: Quản lý thông tin các ghế của xe.
- **Trip**: Quản lý thông tin chuyến đi.
- **Booking**: Quản lý thông tin các vé.
- **Route**: Quản lý thông tin các tuyến đường.
- **Checkpoint**: Quản lý các trạm xe, điểm dừng chân.
- **RouteCheckpoint**: Quản lý các checkpoint trên từng route.
- **SeatReservation**: Quản lý trạng thái đặt chỗ của các ghế theo bus.
  
![Database Diagram](https://github.com/user-attachments/assets/54b005fc-b49f-42ae-91d8-bc7cf433cec2)
<p align="center">
  <img src="https://github.com/user-attachments/assets/54b005fc-b49f-42ae-91d8-bc7cf433cec2" alt="Database Diagram" width="500">
</p>


---

## 7. Usage
1. **Login or Register** as a user or admin.
2. **Search for available trips** by entering origin, destination, and date.
3. **Book a trip** and proceed to payment.
4. **Receive a booking confirmation and invoice**.
5. **Admin access**: Manage trips, routes, users, and view reports.

---

## 8. Screenshots
### Login Page
![Login Page](#)  <!-- Replace # with the actual image path -->

### Trip Search and Booking
![Trip Search](#)  <!-- Replace # with the actual image path -->

### Admin Dashboard
![Admin Dashboard](#)  <!-- Replace # with the actual image path -->

---

## 9. Future Improvements
- **Integration with payment gateways** for online payments.
- **Support for multiple languages**.
- **Mobile app version** for Android and iOS.
- **Analytics dashboard** for sales reports and insights.

---

## 10. Contributors
- **Your Name** – Developer
- **Collaborator Name** – Designer
- **Mentor Name** – Project Advisor

---

## 11. License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

### Thank you for using our Bus Ticket Management Web App! 🎟️  
