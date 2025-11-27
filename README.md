# Government Schemes Mobile Application

A comprehensive Android mobile application providing detailed information about various Government Schemes for citizens under District Panchayat, featuring user authentication, sector-wise scheme browsing, and push notifications.

## Project Overview

This is a fully functional Android application that serves citizens by providing easy access to information about various government schemes and benefits available under District Panchayat. The app features user registration/login, organized scheme categories, detailed scheme information, and notification services to keep users updated about new schemes.

## Features

### User Features
- **User Authentication**: Complete login and signup functionality with Firebase integration
  - Secure password encryption using SHA-256 with salt
  - Comprehensive input validation for all fields
- **Scheme Categories**: Six organized sectors covering all major government schemes:
  - Agriculture Sector - Agricultural schemes and benefits
  - Banking Sector - Banking and financial schemes
  - Business Sector - Business development and entrepreneurship schemes  
  - Education Sector - Educational schemes and scholarships
  - Health Sector - Healthcare and medical schemes
  - Housing Sector - Housing and infrastructure schemes
- **Scheme Information**: Detailed scheme information fetched from Firebase Realtime Database
  - Latest schemes displayed first for easy access
  - Clickable URLs to view scheme details in browser
- **Push Notifications**: Notification subscription service to stay updated
- **In-App Notifications**: Notification list with clickable links to scheme information
- **Material Design UI**: Modern Android interface following Material Design guidelines
- **Theme Support**: Light and dark theme management

### Admin Features
- **Admin Dashboard**: Centralized admin control panel for managing the application
- **User Management**: View, edit, and delete user accounts
  - Toggle admin access for users
  - View all registered users with admin badges
- **Scheme Management**: Add new schemes to any category
  - Create schemes with name, description, notification date, and URL
  - Optional push notification trigger for new schemes
  - View recently added schemes (last 24 hours)
- **URL Management**: Modify state-wise URLs for each scheme category
  - Category-based URL organization
  - State-by-state URL configuration

## Project Structure

```
app/
├── src/main/
│   ├── java/com/app/GovernmentSchemes/
│   │   ├── MainActivity.java                # Main dashboard with scheme categories
│   │   ├── LoginActivity.java              # User login functionality
│   │   ├── SignupActivity.java             # User registration functionality
│   │   ├── LogoutActivity.java             # User logout functionality
│   │   ├── SplashActivity.java             # App splash screen
│   │   ├── WelcomeActivity.java            # Welcome screen
│   │   ├── BaseActivity.java               # Base activity with common functionality
│   │   │
│   │   ├── # Sector Activities
│   │   ├── Agriculture_activity.java       # Agriculture schemes display
│   │   ├── banking_activity.java           # Banking schemes display
│   │   ├── business_activity.java          # Business schemes display
│   │   ├── education_activity.java         # Education schemes display
│   │   ├── health_activity.java            # Health schemes display
│   │   ├── housing_activity.java           # Housing schemes display
│   │   ├── GovernmentSchemesActivity.java  # Government schemes browsing
│   │   │
│   │   ├── # Admin Activities
│   │   ├── AdminDashboardActivity.java     # Admin control panel
│   │   ├── AdminUserListActivity.java      # User management list
│   │   ├── AdminUserDetailActivity.java    # User detail editing
│   │   ├── AdminAddSchemeActivity.java     # Add new schemes
│   │   ├── AdminRecentSchemesActivity.java # View recently added schemes
│   │   ├── AdminUrlManagementActivity.java # Manage state URLs by category
│   │   │
│   │   ├── # Data Models
│   │   ├── HelperClass.java                # User data model
│   │   ├── SchemeData.java                 # Scheme data model
│   │   ├── StateUrlData.java               # State URL data model
│   │   ├── Notification.java               # Notification data model
│   │   │
│   │   ├── # Data Providers
│   │   ├── SchemeDataProvider.java         # Firebase scheme data fetching
│   │   ├── StateSchemeProvider.java        # State scheme URL provider
│   │   ├── SchemeSector.java               # Scheme sector enumeration
│   │   ├── StateScheme.java                # State scheme enumeration
│   │   │
│   │   ├── # Utilities
│   │   ├── PasswordUtils.java              # Password encryption utilities
│   │   ├── ValidationUtils.java            # Input validation utilities
│   │   ├── ThemeManager.java               # Theme management
│   │   │
│   │   └── NotificationListActivity.java   # In-app notification list
│   │
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml           # Main dashboard layout
│   │   │   ├── activity_login.xml          # Login screen layout
│   │   │   ├── activity_sign_up.xml        # Signup screen layout
│   │   │   ├── activity_view.xml           # Scheme details view layout
│   │   │   ├── activity_admin_*.xml        # Admin screen layouts
│   │   │   └── item_*.xml                  # List item layouts
│   │   ├── values/
│   │   │   ├── strings.xml                 # String resources
│   │   │   ├── colors.xml                  # Color resources
│   │   │   └── themes.xml                  # App themes
│   │   ├── drawable/                       # App icons and graphics
│   │   ├── mipmap-*/                       # App launcher icons
│   │   └── xml/
│   │       ├── backup_rules.xml            # Backup configuration
│   │       └── data_extraction_rules.xml   # Data extraction rules
│   └── AndroidManifest.xml                 # App manifest with permissions
├── build.gradle                            # App-level build configuration
├── google-services.json                    # Firebase configuration
└── proguard-rules.pro                      # ProGuard configuration
```

## Technical Specifications

- **Target SDK**: Android 13 (API 33)
- **Minimum SDK**: Android 5.0 (API 23)
- **Language**: Java
- **Build Tool**: Gradle 8.13
- **UI Framework**: Material Components for Android
- **Database**: Firebase Realtime Database
- **Authentication**: Firebase Authentication
- **Notifications**: Firebase Cloud Messaging (FCM)
- **Data Storage**: Firebase Realtime Database for schemes and user data
- **Image Loading**: Picasso library for efficient image handling
- **Security**: SHA-256 password hashing with salt

## Dependencies

### Core Android Libraries
- **AndroidX AppCompat**: Backward compatibility support (1.6.1)
- **Material Components**: Material Design components (1.5.0)
- **ConstraintLayout**: Advanced layout management (2.1.4)
- **SwipeRefreshLayout**: Pull-to-refresh functionality (1.1.0)
- **MultiDex**: Support for large applications (2.0.1)

### Firebase Services
- **Firebase Realtime Database**: Real-time data synchronization (20.1.0)
- **Firebase Cloud Messaging**: Push notifications (23.0.0)
- **Firebase Analytics**: App analytics and insights
- **Firebase BOM**: Bill of Materials for version management (32.1.0)

### Additional Libraries
- **Picasso**: Image loading and caching (2.71828)
- **JSoup**: HTML parsing library (1.14.3)

### Testing Frameworks
- **JUnit**: Unit testing framework (4.13.2)
- **AndroidX Test**: Android testing framework (1.1.5, 1.3.0)
- **Espresso**: UI testing framework (3.5.1)

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 8 or higher
- Android SDK with API 23+ support
- Firebase project setup (for authentication and database)

### Firebase Setup

1. Create a new Firebase project at [Firebase Console](https://console.firebase.google.com)
2. Add an Android app to your Firebase project
3. Download the `google-services.json` file
4. Place the `google-services.json` file in the `app/` directory
5. Enable Firebase Realtime Database and Authentication in the Firebase console
6. Configure Firebase Cloud Messaging for push notifications

### Building the Project

1. Clone the repository
   ```bash
   git clone https://github.com/shashi-kumari/Government-Schemes-Mobile-Application-.git
   ```
2. Open the project in Android Studio
3. Ensure `google-services.json` is properly placed in the `app/` directory
4. Sync project with Gradle files
5. Build and run the application

### App Usage

#### Regular Users
1. **Registration**: New users can sign up with their details (validated inputs, encrypted password)
2. **Login**: Existing users can log in with their credentials
3. **Browse Schemes**: Select from six different scheme categories
4. **View Details**: Tap on any category to view detailed scheme information (latest first)
5. **Open URLs**: Click on scheme URLs to view more details in browser
6. **Notifications**: View in-app notifications and subscribe to push notifications

#### Admin Users
1. **Admin Dashboard**: Access centralized admin controls (visible to admin users)
2. **User Management**: View, edit, or delete user accounts; toggle admin access
3. **Add Schemes**: Create new schemes for any category with optional push notifications
4. **View Recent Schemes**: See all schemes added in the last 24 hours
5. **Manage URLs**: Update state-wise URLs for each scheme category

## Firebase Database Structure

The application uses Firebase Realtime Database for storing scheme and user data. The database structure is organized as follows:

### Schemes Data
```json
{
  "schemes": {
    "agriculture": [
      {
        "scheme": "PM-KISAN",
        "description": "Income support to farmers...",
        "notificationDate": "2024-01-15",
        "url": "https://pmkisan.gov.in",
        "createdAt": 1700000000000
      }
    ],
    "banking": [...],
    "business": [...],
    "education": [...],
    "health": [...],
    "housing": [...]
  }
}
```

### State URLs Data
```json
{
  "url": {
    "agriculture": {
      "states": [
        {
          "code": "AP",
          "name": "Andhra Pradesh",
          "url": "https://..."
        }
      ]
    }
  }
}
```

### Users Data
```json
{
  "users": {
    "username": {
      "name": "John Doe",
      "email": "john@example.com",
      "username": "johndoe",
      "password": "encrypted_password",
      "admnAccess": false
    }
  }
}
```

### Available Scheme Categories

1. **Agriculture** - Farming, subsidies, and agricultural development schemes
2. **Banking** - Financial services, loans, and banking schemes
3. **Business** - Entrepreneurship, startup support, and business development schemes
4. **Education** - Educational scholarships, grants, and academic support schemes
5. **Health** - Healthcare schemes, medical assistance, and wellness programs
6. **Housing** - Housing schemes, infrastructure development, and shelter programs

## Architecture

The application follows a simple activity-based architecture with role-based access control:

### User Flow
- **Authentication Flow**: Splash → Welcome → Login/Signup → Main Dashboard
- **Main Dashboard**: Category selection with navigation to specific scheme activities
- **Scheme Activities**: Firebase data fetching and display of scheme information
- **Notification Service**: Firebase Cloud Messaging integration

### Admin Flow
- **Admin Authentication**: Login → Admin Dashboard (if admin user)
- **User Management**: View all users → Edit/Delete user details
- **Scheme Management**: Add new schemes → View recent schemes → Send notifications
- **URL Management**: Select category → Edit state-wise URLs

### Data Layer
- **User Data**: Firebase Realtime Database with encrypted passwords
- **Scheme Data**: Firebase Realtime Database with real-time sync
- **State URLs**: Firebase Realtime Database with category-wise organization
- **Caching**: In-memory caching for improved performance

## CI/CD & Code Quality

This project includes automated GitHub Actions workflows for continuous integration and code quality assurance:

### 🔄 Automated Workflows

- **Code Quality Checks**: Runs on every push and pull request
  - Build verification using Gradle
  - Unit test execution with JUnit
  - Android Lint analysis for code quality issues
  - Security vulnerability scanning
  - Automated artifact generation (APK, reports)

- **Pull Request Quality Check**: Enhanced checks for PRs
  - Code formatting validation
  - Common issue detection (hardcoded strings, TODO comments)
  - Automated quality summary comments on PRs

### 📊 Quality Metrics

Each build generates:
- **Build Artifacts**: Debug APK files
- **Test Reports**: Unit test results and coverage
- **Lint Reports**: Code quality analysis
- **Security Reports**: Vulnerability assessments

### ⚙️ Workflow Configuration

Workflows are configured to use:
- **JDK 11** for compatibility
- **Android SDK** with automated setup
- **Gradle caching** for faster builds
- **Artifact retention** for debugging and deployment

See `.github/workflows/` for detailed workflow configurations.

## License

This project is released under CC0 1.0 Universal license - see the LICENSE file for details.

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.
