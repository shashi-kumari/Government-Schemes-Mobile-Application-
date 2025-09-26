# GitHub Actions Workflows

This directory contains GitHub Actions workflows for automated code quality checks and continuous integration.

## Workflows

### 1. Code Quality Checks (`code-quality.yml`)
**Triggers:** Push to main/develop branches, Pull Requests to main/develop

**Features:**
- ✅ Build verification using Gradle
- ✅ Unit test execution with JUnit
- ✅ Android Lint analysis for code quality
- ✅ Security vulnerability scanning with Trivy
- ✅ Artifact upload (APK, test reports, lint results)

### 2. Android CI (`android-ci.yml`)
**Triggers:** Push to main branch, Pull Requests to main

**Features:**
- ✅ Basic build and test pipeline
- ✅ Gradle caching for faster builds
- ✅ Build artifact upload

### 3. PR Quality Check (`pr-quality-check.yml`)
**Triggers:** Pull Request events (opened, synchronized, reopened)

**Features:**
- ✅ Code formatting checks
- ✅ Common issue detection (hardcoded strings, TODO comments)
- ✅ Quick compilation and test verification
- ✅ Automated PR comment with quality summary

## Setup Requirements

These workflows require:
- **JDK 11**: Compatible with the project's Java 8 target
- **Android SDK**: Automatically set up via `android-actions/setup-android`
- **Gradle**: Uses the project's wrapper (`./gradlew`)

## Artifacts Generated

Each workflow run generates:
- **APK files**: Debug build outputs
- **Test reports**: JUnit test results
- **Lint reports**: Android Lint analysis results
- **Security scan**: Vulnerability assessment reports

## Customization

To customize these workflows:
1. Modify JDK version if project requirements change
2. Add additional quality tools (SonarQube, Detekt, etc.)
3. Adjust branch triggers based on your Git workflow
4. Configure notification settings for failures

## Best Practices

- **Caching**: Gradle dependencies are cached to improve build times
- **Parallel execution**: Multiple jobs run concurrently when possible
- **Fail-safe**: Critical failures stop the workflow, warnings continue
- **Artifact retention**: Build outputs are preserved for debugging