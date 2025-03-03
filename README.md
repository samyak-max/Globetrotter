_Because of Mixed Content (HTTP backend, HTTPS frontend), please allow insecure connection from site settings, to load the cities._

# **Project Documentation**
# Backend:
## **Overview**

This project consists of multiple microservices that together form "The Globetrotter Challenge – The Ultimate Travel Guessing Game!" Each microservice is responsible for a specific part of the application and they communicate with each other using REST APIs and Feign clients.

## **Microservices**

### **1. AI Service**

The AI Service is responsible for generating city data using AI models.

- **Dockerfile**: dockerfile
- **POM File**: pom.xml
- **Main Application**: AiServiceApplication.java
- **Controllers**:
    - AiController.java
    - CityListController.java
- **Services**:
    - GenerateCitiesData.java
- **Configuration**:
    - CityListConfig.java

### **2. Data Service**

The Data Service manages city data and interacts with the AI Service to populate city data.

- **Dockerfile**: dockerfile
- **POM File**: pom.xml
- **Main Application**: DataServiceApplication.java
- **Controllers**:
    - CityDataController.java
- **Services**:
    - CityDataService.java
- **Feign Clients**:
    - AIServiceFeignClient.java
- **Repositories**:
    - CityDataRepository.java
- **Models**:
    - CityData.java

### **3. Eureka Service**

The Eureka Service acts as a service registry for the microservices.

- **Dockerfile**: dockerfile
- **POM File**: pom.xml
- **Main Application**: EurekaServiceApplication.java

### **4. User Invite Service**

The User Invite Service handles user invitations and interactions.

- **Dockerfile**: dockerfile
- **POM File**: pom.xml
- **Main Application**: UserInviteServiceApplication.java

## **Common Files**

- **Docker Compose**: docker-compose.yml

## **Building and Running the Project**

### **Building**

To build each microservice, navigate to the respective directory and run:

./mvnw clean package -DskipTests

### **Running with Docker Compose**

To run the entire project using Docker Compose, navigate to the root directory and run:

docker-compose up --build

This will build and start all the microservices defined in the docker-compose.yml file.

## **API Endpoints**

### **AI Service**

- **Generate City Data**: `POST /ai/generate`

### **Data Service**

- **Populate City Data**: `GET /city-data/populate`
- **Fetch City Data**: `GET /city-data/fetch?city={city}`

## **Configuration**

### **AI Service**

- **API Key**: Set the `gemini.api.key` property in the application properties file. Runs on port 8081

### **Eureka Service**

- **Port**: The Eureka Service runs on port `8761`.

### **Data Service**

- **Ports**: The Data Service runs on port `8082`.

### **User Invite Service**

- **Ports**: The User Invite Service runs on port `8083`.

## **Dependencies**

Each microservice has its own dependencies defined in their respective pom.xml files. Common dependencies include:

- Spring Boot
- Spring Cloud
- Spring Data MongoDB
- Spring Cloud Netflix Eureka
- Spring Cloud OpenFeign
