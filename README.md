# Library Management System

A desktop-based Library Management System built with Java and SQL. This application provides a simple graphical interface for managing books, tracking inventory, and handling library records without requiring manual bookkeeping.

## About the Project

Managing a library by hand—tracking which books are available, storing author and ISBN details, and recording prices—can quickly become messy. This project solves that by connecting a Java Swing desktop interface directly to a relational database. 

It allows librarians or administrators to add, view, search, and update book records through straightforward desktop forms.

## Key Features

- **Book Inventory Management:** Add new books with essential details including Book ID, Title, Author, ISBN, and Price.
- **Search and View Records:** Quickly look up books in the database by ID, title, or author.
- **Update and Remove Entries:** Modify existing book details or remove outdated stock from the system.
- **Persistent Storage:** All records are saved securely in an SQL database so data is preserved between application sessions.
- **Clean Interface:** A user-friendly desktop GUI built with Java Swing and AWT.

## Tech Stack

- **Language:** Java (JDK 8 or later recommended)
- **User Interface:** Java Swing & AWT
- **Database:** MySQL / SQL Relational Database
- **Database Connectivity:** Java Database Connectivity (JDBC)

## Prerequisites

Before running this project on your local machine, make sure you have the following installed:

1. **Java Development Kit (JDK):** JDK 8 or higher. You can verify your installation by running `java -version` in your terminal.
2. **MySQL Server:** Installed and running locally or accessible via a remote server.
3. **MySQL Connector/J (JDBC Driver):** Required to allow Java to communicate with your MySQL database.

## Getting Started

### 1. Clone the Repository

Open your terminal or command prompt and clone this repository:

```bash
git clone [https://github.com/199shreeram-gif/Library-management-system.git](https://github.com/199shreeram-gif/Library-management-system.git)
cd Library-management-system
