# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

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