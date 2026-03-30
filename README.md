# Razorpay Demo

A simple full-stack Razorpay payment integration demo built with **Spring Boot** and **React**.

This project demonstrates the basic Razorpay payment flow:
- create an order from the backend
- open Razorpay Checkout from the frontend
- complete the payment in test mode
- verify the payment signature on the backend

It is a good beginner-friendly reference project for understanding how a payment gateway works in a Java + React application.

---

## Tech Stack

### Backend
- Java 21
- Spring Boot 4
- Spring Web MVC
- Razorpay Java SDK
- Maven

### Frontend
- React 19
- Vite
- JavaScript

---

### Features
- Full-stack Razorpay payment flow
- Backend API to create Razorpay orders
- Razorpay Checkout integration in React
- Payment signature verification on backend
- Cross-origin support for local frontend-backend communication
- Simple and minimal code for learning purposes

---

### How It Works
1. User enters an amount
The frontend allows the user to enter a payment amount.

2. Frontend requests order creation
When the user clicks Pay now, the frontend sends a request to the backend.

Endpoint:
```bash
POST /api/payments/create-order
```

3. Backend creates Razorpay order
The Spring Boot backend uses the Razorpay SDK to create an order and returns:
- order ID
- amount
- currency
- Razorpay key

4. Razorpay Checkout opens
The frontend uses the returned order details to open the Razorpay Checkout popup.

5. Payment is completed
After payment, Razorpay returns:
- ```razorpay_order_id```
- ```razorpay_payment_id```
- ```razorpay_signature```

6. Backend verifies payment
The frontend sends these values to the backend verification API.

Endpoint:
```bash
POST /api/payments/verify
```
The backend verifies the payment signature and returns a success/failure response.

---

### Learning Highlights
This project is useful for learning:
- how frontend and backend communicate during a payment flow
- why payment order creation should happen on the backend
- how Razorpay Checkout is opened from React
- why payment signature verification is important
- how to keep payment integration logic simple in a starter project

### Author
**Karthi**

If this project helped you understand Razorpay integration, consider starring the repository.
