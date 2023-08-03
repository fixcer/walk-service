# Walk Service


# Getting Started
1.	Installation java 17 and maven 3.9.2 or later
2.	You can use any IDE to develop this project. I recommend using IntelliJ IDEA
3.	Install postgresql, redis on your local machine or use docker
4.  Edit configuration postgresql, redis in **src/main/resources/application.yml** file

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
