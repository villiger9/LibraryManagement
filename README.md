Library Management System API Documentation

Table of Contents

Introduction
Project Structure
Setup and Configuration
API Endpoints
Book Management
Patron Management
Borrowing Endpoints
Error Handling
Aspects

Introduction
This document provides an overview of the Library Management System API built using Spring Boot. The system allows librarians to manage books, patrons, and borrowing records.

Project Structure
src/main/java/com/example/library/controller/LibraryController.java: Contains the controllers that handle incoming HTTP requests and manage the flow of data between the client and the service.

src/main/java/com/example/library/entity: Defines the entities (data models) of the application, such as Book, Patron, and BorrowingRecord.

src/main/java/com/example/library/repository: Houses the repository interfaces that interact with the database, such as BookRepository, PatronRepository, and BorrowingRecordRepository.

src/main/java/com/example/library/response: Contains classes for handling responses, such as ErrorResponse.

src/main/java/com/example/library/LibraryApplication.java: The main class that serves as the entry point for the Spring Boot application.

src/resources/application.properties: Configuration file for application-level settings.

target: The default output directory for compiled code and build artifacts.

.gitignore: Specifies files and directories that should be ignored by version control (for Git).

mvnw, mvnw.cmd: Maven wrapper scripts, allowing the project to be built without a pre-installed Maven version.

pom.xml: Project Object Model file, which defines the project's configuration and dependencies.

Setup and Configuration

Prerequisites:
Java Development Kit (JDK) - The application is developed using Java. Make sure to set up the JAVA_HOME environment variable.
Maven - Maven is used for building and managing the project. Install Maven following the instructions on the official website.
Database - Choose and set up a database (e.g., H2) for persisting data.

Database
The Library Management System relies on a relational database to store information about books, patrons, and borrowing records. Follow these steps to set up the database:

Database Configuration:
Open the application.properties file in the resources directory and configure the database connection properties.

Prometheus Integration (Optional)
The Library Management System includes integration with Prometheus for performance monitoring.

API Endpoints
Book Management
GET /api/books: Retrieve a list of all books.

GET /api/books/{id}: Retrieve details of a specific book by ID.

POST /api/books: Add a new book to the library.

PUT /api/books/{id}: Update an existing book's information.

DELETE /api/books/{id}: Remove a book from the library.

Patron Management
GET /api/patrons: Retrieve a list of all patrons.

GET /api/patrons/{id}: Retrieve details of a specific patron by ID.

POST /api/patrons: Add a new patron to the system.

PUT /api/patrons/{id}: Update an existing patron's information.

DELETE /api/patrons/{id}: Remove a patron from the system.

Borrowing Endpoints
POST /api/borrow/{bookId}/patron/{patronId}: Allow a patron to borrow a book.

PUT /api/return/{bookId}/patron/{patronId}: Record the return of a borrowed book by a patron.

Error Handling
The Library Management System is designed to handle errors gracefully and provide meaningful responses in case of unexpected situations or invalid requests. The error handling mechanism includes the following aspects:

Input Validation
All incoming API requests undergo thorough input validation to ensure that the provided data adheres to the specified constraints. Validation errors, such as missing required fields or incorrect data formats, are communicated back to the client with clear error messages and appropriate HTTP status codes (e.g., 400 Bad Request).

Entity Not Found
When attempting to retrieve details of a specific book or patron by ID, the system checks for the existence of the requested entity. If the entity is not found, the system returns an HTTP 404 Not Found status along with a descriptive error message, indicating that the requested resource could not be located.

Database Operations
For operations involving the database, such as adding, updating, or deleting records, the system performs necessary checks before executing the operation. If an operation fails due to database-related issues, the system returns an HTTP 500 Internal Server Error status and logs relevant details for further investigation.

Generic Server Errors
In case of unexpected errors or exceptions during the execution of API endpoints, the system provides an HTTP 500 Internal Server Error status. Detailed error logs are generated to assist developers in identifying and resolving issues.

Response Formats
Error responses are consistently formatted to include a timestamp, HTTP status code, error type, and a descriptive error message. This format enhances readability and facilitates easier identification of the nature of the error.

Aspects
The Library Management System incorporates Aspect-Oriented Programming (AOP) to introduce cross-cutting concerns such as logging into the application. AOP allows for modularization of these concerns, promoting cleaner code and separation of business logic from system-wide concerns.

Logging
Logging aspects have been introduced to capture method calls, exceptions, and performance metrics of certain critical operations. The logging aspect provides a comprehensive view of the system's behavior, aiding in debugging, performance analysis, and understanding the flow of execution.

Method Call Logging
Every method call within the application is logged, including method name, input parameters, and timestamp. This provides developers with valuable insights into the sequence of operations and aids in tracing the flow of requests through the system.

Exception Logging
In the event of an exception, the system logs relevant details such as the exception type, message, and stack trace. This information is crucial for diagnosing issues and identifying the root causes of unexpected behavior.

Performance Metrics Logging
For specific operations like book additions, updates, and patron transactions, performance metrics are logged. These metrics include execution time, enabling developers to identify potential bottlenecks and optimize critical pathways.
