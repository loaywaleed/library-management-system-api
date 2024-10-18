# Library Management System

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java Development Kit (JDK) 17 or higher**
    - You can download the JDK from [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or [AdoptOpenJDK](https://adoptopenjdk.net/).

- **Maven**
    - Follow the instructions on the [Maven website](https://maven.apache.org/install.html) to install it.

- **Database**
    - MySQL

## Running the Application

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/your-repo.git
   cd your-repo

2. **Build the Application:**
   ```bash
   mvn clean install

3. **Run the Application:**
   ```bash
   mvn spring-boot:run

## Features

- CRUD Operations for books and patrons
- Ability to borrow books
- Error handling
- Unit testing using JUnit

## Todo

- Using javadoc
- Integration testing
- JWT Auth