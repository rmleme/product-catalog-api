# product-catalog-api

## Instructions

**Requirements:** docker, docker-compose, JDK 8, Maven.

1. Download the source code to your local box. Linux is recommended. 
2. Build the application with maven: `mvn install` (in the same directory of `pom.xml`).
3. Copy the generated file (`api-1.0.jar`) to `docker/build/`
4. Run `build.sh`. A new Docker image will be created.
5. Go to directory `docker/` and run `docker-compose up`
6. Two containers will be started, one for MySQL and the other for the application.
7. Once both containers are ready, the APIs may be accessed through the base URL `http://<IP>:8080/v1/api/shoes`
