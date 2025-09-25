# Government Schemes Mobile Application

A comprehensive Android mobile application providing detailed information about various Government Schemes for citizens under District Panchayat, featuring user authentication, sector-wise scheme browsing, and push notifications.

## Project Overview

This is a fully functional Android application that serves citizens by providing easy access to information about various government schemes and benefits available under District Panchayat. The app features user registration/login, organized scheme categories, detailed scheme information, and notification services to keep users updated about new schemes.

## Features

- **User Authentication**: Complete login and signup functionality with Firebase integration
- **Scheme Categories**: Six organized sectors covering all major government schemes:
  - Agriculture Sector - Agricultural schemes and benefits
  - Banking Sector - Banking and financial schemes
  - Business Sector - Business development and entrepreneurship schemes  
  - Education Sector - Educational schemes and scholarships
  - Health Sector - Healthcare and medical schemes
  - Housing Sector - Housing and infrastructure schemes
- **Scheme Information**: Detailed scheme information parsed from XML data assets
- **Push Notifications**: Notification subscription service to stay updated
- **Material Design UI**: Modern Android interface following Material Design guidelines
- **Firebase Integration**: Real-time database for user management and data storage
- **Offline Data Access**: Scheme information stored locally in XML assets

## Project Structure

```
app/
├── src/main/
│   ├── java/com/app/GovernmentSchemes/
│   │   ├── MainActivity.java                # Main dashboard with scheme categories
│   │   ├── LoginActivity.java              # User login functionality
│   │   ├── SignupActivity.java             # User registration functionality
│   │   ├── LogoutActivity.java             # User logout functionality
│   │   ├── HelperClass.java                # Data model for user information
│   │   ├── Agriculture_activity.java       # Agriculture schemes display
│   │   ├── banking_activity.java           # Banking schemes display
│   │   ├── business_activity.java          # Business schemes display
│   │   ├── education_activity.java         # Education schemes display
│   │   ├── health_activity.java            # Health schemes display
│   │   └── housing_activity.java           # Housing schemes display
│   ├── assets/
│   │   ├── agridata.xml                    # Agriculture schemes data
│   │   ├── bankingdata.xml                 # Banking schemes data
│   │   ├── businessdata.xml                # Business schemes data
│   │   ├── education.xml                   # Education schemes data
│   │   ├── health.xml                      # Health schemes data
│   │   └── housing.xml                     # Housing schemes data
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml           # Main dashboard layout
│   │   │   ├── activity_login.xml          # Login screen layout
│   │   │   ├── activity_sign_up.xml        # Signup screen layout
│   │   │   └── activity_view.xml           # Scheme details view layout
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
- **Minimum SDK**: Android 5.0 (API 21)
- **Language**: Java
- **Build Tool**: Gradle 8.13
- **UI Framework**: Material Components for Android
- **Database**: Firebase Realtime Database
- **Authentication**: Firebase Authentication
- **Notifications**: Firebase Cloud Messaging (FCM)
- **Data Storage**: XML assets for scheme information
- **Image Loading**: Picasso library for efficient image handling

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
- Android SDK with API 21+ support
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

1. **Registration**: New users can sign up with their details
2. **Login**: Existing users can log in with their credentials
3. **Browse Schemes**: Select from six different scheme categories
4. **View Details**: Tap on any category to view detailed scheme information
5. **Notifications**: Subscribe to get notified about new schemes and updates

## Scheme Data Structure

The application uses XML files stored in the `assets` folder to provide scheme information. Each sector has its own XML file containing:

- **SCHEME**: Name of the government scheme
- **DESC**: Detailed description of the scheme, eligibility criteria, and benefits

### Available Scheme Categories

1. **Agriculture** (`agridata.xml`) - Farming, subsidies, and agricultural development schemes
2. **Banking** (`bankingdata.xml`) - Financial services, loans, and banking schemes
3. **Business** (`businessdata.xml`) - Entrepreneurship, startup support, and business development schemes
4. **Education** (`education.xml`) - Educational scholarships, grants, and academic support schemes
5. **Health** (`health.xml`) - Healthcare schemes, medical assistance, and wellness programs
6. **Housing** (`housing.xml`) - Housing schemes, infrastructure development, and shelter programs

## Architecture

The application follows a simple activity-based architecture:

- **Authentication Flow**: Login → Signup → Main Dashboard
- **Main Dashboard**: Category selection with navigation to specific scheme activities
- **Scheme Activities**: XML parsing and display of scheme information
- **Data Layer**: Firebase for user data, XML assets for scheme information
- **Notification Service**: Firebase Cloud Messaging integration

## License

This project is released under CC0 1.0 Universal license - see the LICENSE file for details.

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.
