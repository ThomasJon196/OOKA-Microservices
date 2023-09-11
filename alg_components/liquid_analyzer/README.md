# Microservice optional equipment algorithm

## Setup

- Install Java 17
- Install Gradle 7.2
- Setup VPN connection for Kafka access. (Endpoint currently hardcoded inside `KafkaMessanger.kt`)
- (opt.) Install Docker

```bash
# Install Gradle Wrapper
gradle wrapper

# Build executable Jar
./gradlew bootJar
```


## Deployment

```bash
# Deploy via java
java -jar build/libs/motor_analyzer-0.0.1-SNAPSHOT.jar

# Deploy via Docker
# # Build Docker Image
docker build -t motor_analyzer -f docker/Dockerfile .

# # Run Docker Image
docker run -p 8082:8082 motor_analyzer
```

- Endoint: `http://localhost:8082/test` should be available 


## Usage

> Requires connection to Kafka (currently HBRS VPN)

```bash
# Check if server is running
curl localhost:8082/healthCheck

# Start Kafka Consumer
curl localhost:8082/startConsumingKafka

# Simulate Kafka Topic
curl localhost:8082/simulateNewConfig
```

Debugging via Server logs. (Client wont display any data.)


## Sources:

SpringWeb-Unittests: https://medium.com/marc-donkers/test-your-rest-api-that-you-wrote-in-kotlin-1260c4784f5b
Gradle Wrapper (CLI Tool): https://docs.gradle.org/current/userguide/gradle_wrapper.html