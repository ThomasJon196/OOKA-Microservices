## Dockerizing .JAR APPLICATION

1. Create !executable .jar
2. Copy into docker image

```sh
FROM eclipse-temurin:17
RUN mkdir /opt/app
COPY build/libs/demo-0.0.1-SNAPSHOT.jar /opt/app/app.jar
CMD ["java", "-jar", "/opt/app/app.jar"]
```


3. Build & Run image

```sh
docker build -t kotlin-docker-example -f docker/Dockerfile .

// Run image with required port mapping from local docker network to host network.
docker run -p 8080:8080 kotlin-docker-example
```


Source: https://www.baeldung.com/java-dockerize-app