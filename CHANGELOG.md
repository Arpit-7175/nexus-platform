# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.0.4] - 2023-10-29

### Added
- **Redis Module**: New `nexus-core-redis` module for standardized Redis integration.
- **Redis Service**: `RedisService` utility for easy key-value operations.
- **JSON Serialization**: Configured `RedisTemplate` to use JSON serialization by default.
- **Reference**: Added `/api/redis` endpoints to demonstrate Redis usage.

## [0.0.3] - 2023-10-28

### Added
- **Rate Limiting**: Added `@RateLimit` annotation using Bucket4j in `nexus-core-security`.
- **Reference**: Added `/api/test/limited` endpoint to demonstrate rate limiting.

## [0.0.2] - 2023-10-27

### Added
- **CI/CD**: GitHub Actions workflow for automated builds.
- **Tracing**: `MdcFilter` for distributed tracing (Request ID).
- **Swagger**: OpenAPI documentation support in `nexus-core-web`.
- **Security**: `AntPathRequestMatcher` for robust endpoint security.

### Fixed
- **EmailService**: Made it conditional to prevent startup crashes when config is missing.

## [0.0.1] - 2023-10-27

### Added
- Initial release of Nexus Platform.
- **nexus-core-web**: `ApiResponse`, `GlobalExceptionHandler`.
- **nexus-core-audit**: `@LogExecutionTime` aspect.
- **nexus-core-security**: JWT authentication utilities.
- **nexus-core-automation**: Slack and Email services.
- **nexus-reference-service**: Example application.