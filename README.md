# ShelterHub
ShelterHub is a desktop application written in Java, using JavaFX for the user interface and MSSQL as the database.

### Static Code Analysis

Static Code Analysis
The application uses Spotless and Checkstyle, which help analyze and enforce coding standards for the codebase.
These tools ensure that the code remains clean and adheres to best practices.

The analysis is automatically performed during the CI/CD process.
To run Spotless and Checkstyle manually, use the following Gradle commands:

```
./gradlew spotlessApply
./gradlew checkstyleMain
```
