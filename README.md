# Razorpay Demo

A full-stack Razorpay payment integration demo built with:

- **Backend:** Spring Boot, Spring Web MVC, Spring Data JPA, Flyway, PostgreSQL
- **Frontend:** React, React Router, Vite
- **Payment Gateway:** Razorpay Test Mode

This project demonstrates a complete test payment flow:
- Create Razorpay order from backend
- Open Razorpay Checkout from frontend
- Verify payment signature on backend
- Store payment details in PostgreSQL
- Show success/failure pages in frontend

---

## Project Structure

```bash
razorpay-demo/
├── razorpay-demo/        # Spring Boot backend
├── razorpay-ui/          # React frontend
└── docker-compose.yaml   # PostgreSQL setup
```

## Features
- Create Razorpay order from Spring Boot backend
- Verify payment signature securely on backend
- Persist payment details in PostgreSQL
- Track payment status as:
  - PENDING
  - SUCCESS
  - FAILED
- Flyway migration for schema creation
- React UI with:
  - Payment page
  - Success page 
  - Failure page

## Tech Stack
- Backend
  - Java 21
  - Spring Boot 
  - Spring Web MVC
  - Spring Data JPA
  - Flyway
  - PostgreSQL
  - Razorpay Java SDK
  - Lombok
  - java-dotenv
  - Docker (postgresql database)
- Frontend 
  - React
  - React Router DOM
  - Vite

## Payment Flow
- User enters the amount on the frontend
- Frontend sends request to backend to create Razorpay order
- Backend creates order using Razorpay API
- Backend stores order details in database with PENDING status
- Razorpay Checkout opens in frontend
- On successful payment:
  - frontend sends payment details to backend
  - backend verifies Razorpay signature
  - backend updates payment status to SUCCESS
- On failure or checkout close:
  - backend updates payment status to FAILED
  - frontend redirects to failure page

## Environment Variables
- This project requires Razorpay test credentials.
- Create a .env file inside the razorpay-demo backend folder.
- Path
- razorpay-demo/.env
- Add the following secrets
  - RAZORPAY_KEY_ID=your_razorpay_test_key_id
  - RAZORPAY_KEY_SECRET=your_razorpay_test_key_secret

**Use your Razorpay Test Mode credentials.** \
**Do not commit the .env file to GitHub.**

***Author - Karthi***

**If you found this project useful, consider giving it a star on GitHub.**
