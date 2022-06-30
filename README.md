# Software System for Booking Meeting Rooms in IT Companies’ Offices

The system consists of Backend and Frontend parts.

UML designs are located [here](https://drive.google.com/drive/folders/1OAO73wSq8ICPdtr4KCyCe0GbAkAYn_Jw?usp=sharing).

# Technologies and details

1. The backend was implemented using Java 11, Spring Boot, Spring MVC, Spring Data JPA (PostgreSQL Driver), Spring Security, JWT, Maven, Lombok, Apache Commons, javax.mail. REST architectural style and REST API was implemented, documentation using Swagger. Backend was implemented mostly using functional programming concepts, and technologies like Stream API, Functional Interfaces, Optional API. Logging is provided by Log4j. ResponseEntityExceptionHandler was used for exceptions translations to HTTP responses. For testing purposes used JUnit and Mockito, implemented unit tests for the functionality of finding available for booking meeting rooms. Spring Scheduling was used to create CRON jobs for booking statuses updating and for notification remainders about the meetings.
2. The frontend was implemented using React and JavaScript, used some libraries for modals, animating loaders and progress bars. Building with npm. Axios library is used for HTTP communication. React Components are developed with using of the React Hooks and JS Functions. 

# Implemented features

Briefly about the implemented functions: registration, authorization, profile management, booking creation, sending invitation for the booking, canceling the booking with notification, receiving remainders about upcoming meetings, all CRUD operations, sending letters to mail, administration functions: managing user accounts, blocking accounts, CRUD for all entities and bookings management.<br>
Business logic: system has two user roles: employee and administrator. One the most important use case for the employee is creating a booking of the meeting room. To create a booking employee must enter the date, time, duration and choose the office. The he receives a list of meeting rooms that are available for booking (i.e. there is no interval conflicts with this timeslot). After booking creation employee receives the confirmation letter. Also, an employee can send an invitation for the meeting, cancel it and receive remainders about the upcoming meetings.

## How to run
1. Backend  
    In backend root directory run next terminal command:<br>
    ``mvn spring-boot:run``<br>
    (note: [Maven](https://www.baeldung.com/install-maven-on-windows-linux-mac) has to be installed)<br>
    Script for initializing the database is located at the `med-track-backend/src/main/resources/db/tables_init.sql`<br>
    Database properties are located at the `application.properties` file <br>
    Then you can navigate to the http://localhost:8181/swagger-ui.html and check backend **REST API**.  
2. Frontend  
    - [install](https://phoenixnap.com/kb/install-node-js-npm-on-windows) **node.js** and **npm**
    - in frontend root directory run ```npm install```
    - in frontend root directory run ```npm start```
    - navigate to http://localhost:3000
