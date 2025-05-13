# Lost and Found Web Application

## Overview
The Lost and Found Web Application allows users to submit lost and found items. Users can provide details about the item, including its name, description, status, contact information, and an image of the item. The application stores this information in a MySQL database.

## Project Structure
```
lost-and-found-webapp
├── src
│   ├── main
│   │   ├── java
│   │   │   └── SubmitItemServlet.java
│   │   └── webapp
│   │       ├── WEB-INF
│   │       │   └── web.xml
│   │       └── index.jsp
├── pom.xml
└── README.md
```

## Files Description

- **src/main/java/SubmitItemServlet.java**: Contains the `SubmitItemServlet` class which handles POST requests for submitting items. It processes form data, manages file uploads, and interacts with the MySQL database to store item information.

- **src/main/webapp/WEB-INF/web.xml**: The deployment descriptor for the web application. It defines servlet mappings, context parameters, and other configuration settings necessary for the application.

- **src/main/webapp/index.jsp**: The main JSP page that presents the HTML form for users to submit items. It includes fields for item name, description, status, contact information, and file upload.

- **pom.xml**: The Maven configuration file that specifies project dependencies, build settings, and plugins required for the web application.

## Setup Instructions

1. **Clone the Repository**: 
   Clone the project repository to your local machine.

2. **Install Dependencies**: 
   Navigate to the project directory and run the following command to install the required dependencies:
   ```
   mvn install
   ```

3. **Database Configuration**: 
   Ensure you have a MySQL database set up. Update the database connection parameters in the `SubmitItemServlet.java` file with your MySQL username and password.

4. **Deploy the Application**: 
   Deploy the application on a servlet container such as Apache Tomcat.

5. **Access the Application**: 
   Open your web browser and navigate to `http://localhost:8080/lost-and-found-webapp/` to access the application.

## Usage
Users can fill out the form on the main page to submit lost or found items. After submission, the details will be stored in the database, and the uploaded image will be saved on the server.

## License
This project is licensed under the MIT License.