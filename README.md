Project Title: NearU

Project Description

    -

Technologies Used
    Backend
        - Spring Boot 
            Spring Security
            Spring Data JPA
            BCrypt Password Encoder

    Frontend (Web)
        - ReactJS
        - JavaScript
        - HTML / CSS

    Mobile
        - Android (Kotlin)

    Database

        - MySQL (via XAMPP)

    Version Control
        - Git
        - GitHub

Steps to run backend
    1. Open the backend folder in your IDE .
    2. Make sure MySQL (XAMPP) is running.
    3. Create a database.
    4. Update application.properties with your database credentials.
    5. Run the application by executing DemoApplication.java

Steps to run web app
    1. Navigate to web folder
    2. Install dependecies.
    3. Start the development server using npm start.

Steps to run mobile app
    1. Open Android Studio.
    2. Select Open and choose the mobile folder.
    3. Allow Gradle to sync completely.
    4. Start an Android Emulator using Device Manager.
    5. Ensure backend is running on your PC.
    6. Run the app using the Run button.

List of API endpoints
    Authentication
        /api/auth/register
        /api/auth/login

    User
        /api/user/me