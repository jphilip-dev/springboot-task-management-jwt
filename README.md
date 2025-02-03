# Task Management API


## Description

Task Management API is a RESTful web service designed to manage tasks, user authentication, and user roles for a task management application. It provides endpoints for task creation, updates, deletions, and user management, including roles and statuses. Built using Spring Boot, the application supports JWT-based authentication and integrates with a MySQL database for persistent storage.

## Features

- **User Authentication & Authorization**: Register and login using JWT authentication.
- **Role Management**: Admins can assign and update user roles.
- **Task Management**: Users can create, update, delete, and view tasks.
- **Task Delegation**: Allows users to delegate tasks and manage assigned tasks.

## Tech Stack

- **Backend**: Java, Spring Boot, Spring Security, JPA
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)
- **Testing**: Spring Boot Starter Test, Spring Security Test
- **Utilities**: Lombok for reducing boilerplate code, Jakarta Validation for request validation


## Usage

Once the application is running, you can interact with the API using the following endpoints:

- **POST** `/api/auth/register`: Register a new user.
- **POST** `/api/auth/login`: Login to get a JWT token.
- **GET** `/api/tasks/my-tasks` : Get all tasks assigned to the logged-in user.
- **GET** `/api/tasks/delegated-tasks` : Get all tasks delegated by the logged-in user.
- **POST** `/api/tasks`: Create a new task.
- **PUT** `/api/tasks/{taskId}`: Update an existing task.
- **DELETE** `/api/tasks/{taskId}`: Delete a task.
- **GET** `/api/admin/users`: Get a list of all users (Admin only).
- **PUT** `/api/admin/users/{id}/roles`: Update a user's roles (Admin only).
- **PUT** `/api/admin/users/{id}/status`: Update a user's status (Admin only).
- **PUT** `/api/admin/users/{id}/reset-password`: Reset a user's password (Admin only).

## Setup

1. **Clone the repository**:

   ```bash
   git clone <repository-url>
   cd <project-directory>
   ```
   
2. **Configure MySQL Database**:
   
   Ensure you have MySQL running locally or use a cloud database. Create a new database and update application.properties with your MySQL credentials.
   
   Example application.properties configuration:

    ```bash
    spring.datasource.url=jdbc:mysql://localhost:3306/task_management_db
    spring.datasource.username=root
    spring.datasource.password=password
    spring.jpa.hibernate.ddl-auto=update

   ```
3. **Build the project**

   Use Maven or Eclipse (Any prefered IDE)

4. **Run the Application**



## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.


   
