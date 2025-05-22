ChowNaija Backend
Welcome to the backend repository for ChowNaija, a vibrant platform where food lovers can explore restaurants across Nigeria, share reviews, and express their culinary experiences. Built with Spring Boot, this backend powers a seamless, scalable, and secure API to connect users with Nigeria's rich dining scene.
Project Overview
ChowNaija is a backend API designed to support a community-driven platform for restaurant discovery in Nigeria. Users can browse restaurants, read and write reviews, rate dining experiences, and share their thoughts on the vibrant Nigerian food culture. The backend is built using Spring Boot, leveraging its robust ecosystem to deliver a secure, RESTful API with JWT-based authentication, database integration, and efficient data management.
Key Features

Restaurant Management: Create, read, update, and delete (CRUD) restaurant profiles with details like name, location, cuisine, and contact info.
User Reviews: Allow users to post reviews, ratings, and comments for restaurants, fostering an interactive community.
Authentication & Authorization: Secure endpoints with JWT (JSON Web Tokens) to ensure only authenticated users can post reviews or manage data.
Database Integration: Persist restaurant and review data using a relational database (e.g., PostgreSQL) with Spring Data JPA.
Scalable Architecture: Leverage Spring Boot’s microservices-ready design for scalability and maintainability.

Tech Stack

Framework: Spring Boot (Java)
Security: Spring Security with JWT for authentication
Database: Spring Data JPA with PostgreSQL (configurable for other databases)
Dependency Management: Maven
API Documentation: OpenAPI/Swagger (planned)
Testing: JUnit and Mockito (planned)

Getting Started
Prerequisites

Java 17 or higher
Maven 3.8+
PostgreSQL (or your preferred database)
IDE (e.g., IntelliJ IDEA, Eclipse)

Installation

Clone the Repository:
git clone https://github.com/bascom241/ChownaijaServer.git
cd ChowNaijaMobile


Configure the Database:

Set up a PostgreSQL database.
Update src/main/resources/application.properties with your database credentials:spring.datasource.url=jdbc:postgresql://localhost:5432/chownaija
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
jwt.secret.key=your-secure-base64-encoded-key




Build and Run:
mvn clean install
mvn spring-boot:run


Access the API:

The API will be available at http://localhost:8080.
Example endpoints (to be implemented):
POST /api/auth/register: Register a new user
POST /api/auth/login: Authenticate and receive a JWT
GET /api/restaurants: List all restaurants
POST /api/restaurants/{id}/reviews: Add a review





Project Structure
chownaija-backend/
├── src/
│   ├── main/
│   │   ├── java/com/example/chownaija/
│   │   │   ├── config/        # Spring configuration (e.g., SecurityConfig)
│   │   │   ├── controller/    # REST controllers for API endpoints
│   │   │   ├── dao/          # Entities and repositories (e.g., User, Restaurant)
│   │   │   ├── service/      # Business logic (e.g., JwtService, UserDetailsService)
│   │   ├── resources/
│   │       ├── application.properties  # Configuration file
│   ├── test/                 # Unit and integration tests
├── pom.xml                   # Maven dependencies
├── README.md                 # This file

Contributing
We welcome contributions to make ChowNaija even better! To contribute:

Fork the repository.
Create a feature branch (git checkout -b feature/your-feature).
Commit your changes (git commit -m "Add your feature").
Push to the branch (git push origin feature/your-feature).
Open a pull request.

Please ensure your code follows our coding standards and includes tests where applicable.
Future Enhancements

API Documentation: Integrate Swagger for interactive API docs.
Search & Filtering: Add search by cuisine, location, or rating.
Image Uploads: Allow restaurants to upload photos via the API.
Analytics: Provide insights into popular restaurants or trending reviews.
Testing: Expand unit and integration tests for robust coverage.

Why ChowNaija?
ChowNaija celebrates Nigeria’s diverse culinary landscape, from bustling Lagos eateries to hidden gems in Abuja. By providing a platform for authentic user reviews, we empower foodies to discover and share their dining experiences, fostering a community around Nigeria’s vibrant food culture.
Join us in building a deliciously connected Nigeria! 🍲
Contact
Have questions or ideas? Reach out via [your-email@example.com] or open an issue on GitHub.
#SpringBoot #Java #API #Nigeria #Foodie #BackendDevelopment
