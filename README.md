# ShelterHub
ShelterHub is a desktop application written in Java, using JavaFX for the user interface and MSSQL as the database.

### Static Code Analysis

The application uses **Spotless** and **Checkstyle** to analyze and enforce coding standards.
These tools ensure the code remains clean, consistent, and aligned with best practices.

Code analysis and formatting are automatically performed during the CI/CD process.

#### 🔧 Run locally

To manually check and auto-format the code, use the following command:

```bash
./gradlew fixAndCheck
```

### 🚀 Build and package with jlink

ShelterHub uses **jlink** to create a custom runtime image containing only the required modules of the JDK and the application itself.  
This approach results in a lightweight, self-contained executable package optimized for distribution and deployment.

To build a clean project and generate the custom runtime image, run:

```bash
./gradlew clean jlink
```