# Incident Reports API

## Overview
The **Incident Reports API** is designed to manage incident reports securely and efficiently. It allows users to create, retrieve, update, and filter incident reports based on various parameters like severity and date range.

## Features
- **Create Incident**: Allows users to create a new incident report.
- **Get All Incidents**: Retrieve all incident reports with optional filtering by severity and date range.
- **Update Incident**: Update details of an existing incident.
- **Get Incident by ID**: Fetch a specific incident by its ID.
- **Validation**: Ensures that incident titles are unique and meet validation criteria.

## Technologies Used
- Spring Boot
- Spring Security
- JPA / Hibernate
- H2 Database (for in-memory data storage)
- Swagger for API documentation

## Setup Instructions

### 1. Clone the Repository
    git clone https://github.com/Sakshijain-Get/Incident-Report-Manager.git
    cd Incident-Report-Manager

### 2. Build the Project

    mvn clean install
### 3. Run the Application
After the application is up and running, you can interact with the API via Swagger UI:
Open new Incognito window and navigate to:
http://localhost:8080/swagger-ui/index.html#/

You will see a list of available endpoints. Click on any endpoint to expand and view its details.

Authorizing with Swagger UI
Endpoints are secured. To use them, follow these steps:

Click on the Authorize button (top right corner of the Swagger UI).
Enter the following credentials:
#### Username: admin
#### Password: 123456
Click Authorize to gain access.

## Using the Endpoints
### 1. Create Incident
#### Method: POST
#### Endpoint: /api/incident
### 2. Get All Incidents
#### Method: GET
#### Endpoint: /api/incident
#### Optional Query Parameters:
#### severity
#### startDate
#### endDate
### 3. Update Incident
#### Method: PUT
#### Endpoint: /api/incident/{id}
### 4. Get Incident by ID
#### Method: GET
#### Endpoint: /api/incident/{id}


