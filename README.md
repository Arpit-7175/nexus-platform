# Nexus Platform

![Version](https://img.shields.io/badge/version-0.0.4--SNAPSHOT-blue)
[![Build Status](https://github.com/arpit/nexus-platform/actions/workflows/maven.yml/badge.svg)](https://github.com/arpit/nexus-platform/actions)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**Nexus Platform** is a production-grade, reusable Java library (`nexus-core`) designed to accelerate Microservices development with Spring Boot. It provides standard utilities for API responses, exception handling, auditing, security, and automation.

The project follows a **multi-module Maven architecture**, allowing you to include only the dependencies you need.

See [CHANGELOG.md](CHANGELOG.md) for version history.

## Modules

*   **nexus-core-web**: Standardized API responses (`ApiResponse`), Global Exception Handling, **OpenAPI/Swagger**, and **Distributed Tracing**.
*   **nexus-core-audit**: Performance monitoring with `@LogExecutionTime`.
*   **nexus-core-security**: JWT authentication, **Rate Limiting (@RateLimit)**, and security configuration.
*   **nexus-core-automation**: Slack and Email alert integrations.
*   **nexus-core-redis**: Standardized Redis configuration and utilities.

## Getting Started

### Prerequisites

*   Java 17+
*   Maven 3.8+
*   Redis (Optional, required for `nexus-core-redis`)

### Installation

1.  Clone the repository:
    ```bash
    git clone https://github.com/arpit/nexus-platform.git
    ```
2.  Build the project and install modules to your local Maven repository:
    ```bash
    cd nexus-platform
    mvn clean install
    ```

## Usage

### 1. Add Dependencies

Add the specific modules you need to your Spring Boot application's `pom.xml`.

**For Web Features (ApiResponse, Exception Handling, Swagger, Tracing):**
```xml
<dependency>
    <groupId>com.github.arpit.nexus</groupId>
    <artifactId>nexus-core-web</artifactId>
    <version>0.0.4-SNAPSHOT</version>
</dependency>
```

**For Auditing (@LogExecutionTime):**
```xml
<dependency>
    <groupId>com.github.arpit.nexus</groupId>
    <artifactId>nexus-core-audit</artifactId>
    <version>0.0.4-SNAPSHOT</version>
</dependency>
```

**For Security (JWT, Rate Limiting):**
```xml
<dependency>
    <groupId>com.github.arpit.nexus</groupId>
    <artifactId>nexus-core-security</artifactId>
    <version>0.0.4-SNAPSHOT</version>
</dependency>
```
*Note: Adding this module will enable a default Security Configuration that locks down all endpoints except `/auth/**`, `/public/**`, `/actuator/**`, and Swagger UI.*

**For Automation (Slack, Email):**
```xml
<dependency>
    <groupId>com.github.arpit.nexus</groupId>
    <artifactId>nexus-core-automation</artifactId>
    <version>0.0.4-SNAPSHOT</version>
</dependency>
```

**For Redis:**
```xml
<dependency>
    <groupId>com.github.arpit.nexus</groupId>
    <artifactId>nexus-core-redis</artifactId>
    <version>0.0.4-SNAPSHOT</version>
</dependency>
```

### 2. Enable Component Scanning

Ensure your main application class scans the `nexus-core` packages:

```java
@SpringBootApplication
@ComponentScan(basePackages = {"com.your.package", "com.github.arpit.nexus.core"})
public class MyApplication { ... }
```

### 3. Configuration

Add the following properties to your `application.properties` or `application.yml` if you are using the respective modules.

**Automation (Slack & Email):**
```properties
# Slack
nexus.automation.slack.webhooks.GENERAL=https://hooks.slack.com/services/YOUR/WEBHOOK/URL
nexus.automation.slack.webhooks.ALERTS=https://hooks.slack.com/services/YOUR/WEBHOOK/URL

# Email (Optional - Service is only created if host is defined)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
nexus.automation.email.from=noreply@nexus.com
```

**Redis:**
```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

## API Documentation (Swagger)

If you include `nexus-core-web`, Swagger UI is automatically available at:
`http://localhost:8080/swagger-ui.html`

You can use the **Authorize** button in Swagger UI to test secured endpoints by pasting your JWT token.

## Examples

**Return Standard Responses:**
```java
@GetMapping("/hello")
public ApiResponse<String> hello() {
    return ApiResponse.success("Hello World", "Success");
}
```

**Rate Limiting:**
```java
@GetMapping("/limited")
@RateLimit(limit = 10, duration = 60)
public ApiResponse<String> limited() {
    return ApiResponse.success("Success", "OK");
}
```

**Redis Operations:**
```java
@Autowired
private RedisService redisService;

public void cacheData() {
    redisService.set("user:1", new User("Arpit"), 10, TimeUnit.MINUTES);
}
```

**Log Execution Time:**
```java
@LogExecutionTime
public void processData() {
    // ...
}
```

**Send Slack Alert:**
```java
@Autowired
private SlackService slackService;

public void alert() {
    slackService.sendAlert(SlackChannel.ALERTS, "System Critical Error!");
}
```

**Login (Reference Service):**
POST `/auth/login`
```json
{
  "username": "arpit",
  "password": "password"
}
```

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.