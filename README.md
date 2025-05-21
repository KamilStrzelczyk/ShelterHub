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
