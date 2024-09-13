# Spring Boot Application with Redis Cache, Pub/Sub, PostgreSQL, and Grafana Monitoring

This project is a simple web-based REST API application built using Spring Boot that demonstrates cache management using Redis and implements Redis Pub/Sub for event-driven cache invalidation and communication between services. The application also integrates with a PostgreSQL database as the backend data source and includes Grafana for monitoring and visualizing cache performance and database queries.

## Table of Contents
1. Project Structure
2. Technologies Used
3. Prerequisites
4. Setup and Installation
5. Application Endpoints
6. Redis Pub/Sub and Streams
7. Monitoring with Grafana
8. Testing the Application


## Project Structure
```aiignore
├── src
│   └── main
│       ├── java
│       │   └── com.example.cacheapp
│       │       ├── config
│       │       │   └── RedisConfig.java
│       │       ├── contoller
│       │       │   └── DataController.java
│       │       ├── entity
│       │       │   └── DataEntity.java
│       │       ├── repository
│       │       │   └── DataRepository.java
│       │       ├── service
│       │       │   ├── CacheServic.java
│       │       │   ├── DataService.java
│       │       │   ├── PubSubService.java
│       │       │   ├── RedisMessageSubscriber.java
│       │       │   └── StreamService.java
│       │       └── CacheConfig.java
│       └── resources
│           ├── application.yml
│           └── schema.sql
└── Dockerfile
└── docker-compose.yml
└── README.md
```
## Technologies Used
- Java 17
- Spring Boot (version 2.7.x)
- Redis (as a caching solution and Pub/Sub for communication)
- PostgreSQL (as the backend database)
- Docker (for containerization)
- Grafana (for monitoring Redis and PostgreSQL metrics)
- Maven (for project build management)

## Prerequisites
Before you can run this application, make sure you have the following installed on your system:
- Java 17
- Maven
- Docker
- Docker Compose
- Postman or curl for API testing.

## Setup and Installation
### Step 1: Clone the repository
```git clone <repository-url>
cd <project-directory>
```
### Step 2: Build the Project
You can build the project using Maven:
```mvn clean install```

### Step 3: Setup Docker Containers
The docker-compose.yml file is provided to spin up Redis, PostgreSQL, and Grafana containers.
```docker-compose up -d```

### Step 4: Configure the Database
The application uses PostgreSQL as the database. You need to run the schema file to create the table:

```
CREATE TABLE data_store (
id SERIAL PRIMARY KEY,
key VARCHAR(50),
value VARCHAR(255)
);
```

Make sure that PostgreSQL is running on localhost:5432, and the credentials match the values in application.yml.

### Step 5: Run the Application
Once all dependencies are set up, you can start the Spring Boot application:
```mvn spring-boot:run```

The application will start at http://localhost:8080.

## Application Endpoints
The following endpoints are exposed by the application:

### 1. GET /data/{key}

- Retrieves the value for the given key from Redis cache or PostgreSQL if cache is empty.
Example:

```
curl http://localhost:8080/data/exampleKey
```

### 2. POST /data/{key}

- Updates the value for the given key in both the Redis cache and PostgreSQL database.
Example:

```
curl -X POST -d "newValue" http://localhost:8080/data/exampleKey
```

## Redis Pub/Sub and Streams
### Redis Pub/Sub
The application uses Redis Pub/Sub to invalidate the cache across multiple instances of the service. When data is updated, the Pub/Sub mechanism broadcasts a message to all subscribers to invalidate their local caches.

```
redisTemplate.convertAndSend("cache-invalidation", key);
```

### Redis Streams (Optional)
For a more advanced setup, you can implement Redis Streams to handle cache updates. The StreamService.java class shows an example of how to send and listen to Redis Streams messages.

## Monitoring with Grafana
Grafana is used to monitor the performance of Redis and PostgreSQL in real-time. Here's how you can set it up:

1. Access Grafana: http://localhost:3000
2. Add Redis and PostgreSQL data sources in Grafana:
   - Redis: Use localhost:6379 as the Redis connection.
   - PostgreSQL: Use localhost:5432 with the same credentials as defined in application.yml.
3. Create dashboards to monitor:
   - Cache hits/misses in Redis.
   - Queries executed on PostgreSQL.
4. Set up alerts based on your performance criteria.
   
## Testing the Application
   Once the application is running, you can test the REST endpoints using Postman or Curl.

- Check if data is cached in Redis:

```
docker exec -it <redis-container-id> redis-cli
GET exampleKey
```

- Check PostgreSQL entries:

```
docker exec -it <postgres-container-id> psql -U postgres
SELECT * FROM data_store;
```

## Conclusion
This project demonstrates the integration of Redis for caching, Redis Pub/Sub for event-driven communication, and PostgreSQL as the database layer. Additionally, 
Grafana allows monitoring of the system's performance, including cache utilization and database queries.