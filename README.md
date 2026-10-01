# Real Estate Investment Manager

A Java Swing desktop application for tracking real estate investments, managing owned and interested properties, searching the portfolio, and generating simple profitability reports.

## Features

- User registration, login, password reset, and per-user property data
- Owned property management with purchase price, market value, expenses, and acquisition date
- Interested property tracking with realtor contact details and follow-up status
- Search by property type, country, year, price range, and features
- Monthly, yearly, and country-level profit reports
- Seven-day follow-up alert for interested properties without a realtor response
- Printable report view

## Tech Stack

- Java 20
- Swing
- Maven
- PostgreSQL
- JCalendar
- JUnit 5

## Getting Started

1. Create a PostgreSQL database:

   ```sql
   CREATE DATABASE realestatemanager;
   ```

2. Run the schema and seed scripts:

   ```powershell
   psql -U postgres -d realestatemanager -f database/schema.sql
   psql -U postgres -d realestatemanager -f database/seed.sql
   ```

3. Set environment variables:

   ```powershell
   $env:REM_DB_URL="jdbc:postgresql://localhost:5432/realestatemanager"
   $env:REM_DB_USER="postgres"
   $env:REM_DB_PASSWORD="your-password"
   ```

4. Run the app:

   ```powershell
   $env:JAVA_HOME="C:\Program Files\Java\jdk-20"
   .\mvnw.cmd compile exec:java
   ```

5. Run tests:

   ```powershell
   $env:JAVA_HOME="C:\Program Files\Java\jdk-20"
   .\mvnw.cmd test
   ```

## Demo Login

The seed script creates a demo user:

- Username: `demo`
- Password: `password123`
- Security answer: `demo`

## Portfolio Notes

This project was originally built as a school assignment and has since been cleaned for portfolio use. The cleanup focused on reproducible setup, safer credential handling, password hashing, per-user data isolation, validation, basic tests, and a more polished Swing interface.

## Future Improvements

- Add a richer dashboard with charts for ROI, equity, expense ratio, and portfolio appreciation
- Move from Swing to JavaFX or a web frontend for a more modern UI layer
- Add Flyway or Liquibase migrations
- Add repository/service tests backed by Testcontainers
- Export reports to PDF or CSV
