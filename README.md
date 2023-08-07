# Walk Service


# Getting Started
1.	Installation java 17 and maven 3.9.2 or later
2.	You can use any IDE to develop this project. I recommend using IntelliJ IDEA
3.	Install postgresql, redis on your local machine or use docker
4.  Edit configuration postgresql, redis in **src/main/resources/application.yml** file
5.  Create an empty database **walk-service** and schema **public**

# Swagger
Project using API-First approach, you can see swagger at `src/main/resources/swagger/api.yaml`

# Build and Test
These steps show how to build your code and run the tests.
1.  Create an empty database **walk-service** and schema **public**, you can run the following command:
```bash
make createdb
```

2.  Build project:
```bash
mvn clean package
```

3.  Run project with default profile:
```bash
java -jar target/walk-service.jar
```

# Run with docker
You can run project with docker, you can run the following command:
```bash
docker-compose up -d
```
You don't need to install postgresql, redis on your local machine because it's already installed in docker

To stop docker, you can run the following command:
```bash
docker-compose down --volumes
```