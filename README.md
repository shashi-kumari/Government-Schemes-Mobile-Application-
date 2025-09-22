# Government Schemes Mobile Application

Mobile Application providing information about various Government Schemes for citizens under District Panchayat

## Project Overview

This is a basic Android application template designed to serve as a foundation for a government schemes information app. The application provides citizens with easy access to information about various government schemes and benefits available under District Panchayat.

## Features

- **Clean Material Design UI**: Modern Android interface following Material Design guidelines
- **Welcome Screen**: Introductory screen with app branding and description
- **Government Schemes Focus**: Specifically designed for government scheme information
- **Ready for Extension**: Template structure ready for adding scheme listings, search, and other features

## Project Structure

```
app/
├── src/main/
│   ├── java/com/govschemes/app/
│   │   └── MainActivity.java          # Main activity with welcome screen
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml      # Main activity layout
│   │   ├── values/
│   │   │   ├── strings.xml            # String resources
│   │   │   ├── colors.xml             # Color resources
│   │   │   └── themes.xml             # App themes
│   │   ├── drawable/
│   │   │   └── ic_government.xml      # Government icon
│   │   ├── mipmap-*/
│   │   │   ├── ic_launcher.xml        # App launcher icons
│   │   │   └── ic_launcher_round.xml  # Round launcher icons
│   │   └── xml/
│   │       ├── backup_rules.xml       # Backup configuration
│   │       └── data_extraction_rules.xml # Data extraction rules
│   └── AndroidManifest.xml           # App manifest
├── build.gradle                       # App-level build configuration
└── proguard-rules.pro                 # ProGuard configuration
```

## Technical Specifications

- **Target SDK**: Android 14 (API 34)
- **Minimum SDK**: Android 5.0 (API 21)
- **Language**: Java
- **Build Tool**: Gradle 8.0
- **UI Framework**: Material Components
- **Architecture**: Ready for MVVM implementation

## Dependencies

- **AndroidX AppCompat**: Backward compatibility support
- **Material Components**: Material Design components
- **ConstraintLayout**: Advanced layout management
- **JUnit**: Unit testing framework
- **Espresso**: UI testing framework

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 8 or higher
- Android SDK with API 21+ support

### Building the Project

1. Clone the repository
2. Open the project in Android Studio
3. Sync project with Gradle files
4. Build and run the application

### Next Steps

This template provides a solid foundation for developing the government schemes application. Consider adding:

- **Scheme Listings**: List of available government schemes
- **Search Functionality**: Search schemes by category, eligibility, etc.
- **Scheme Details**: Detailed information pages for each scheme
- **Application Forms**: Digital forms for scheme applications
- **User Authentication**: User accounts and application tracking
- **Offline Support**: Cache scheme information for offline access
- **Multi-language Support**: Regional language support

## License

This project is released under CC0 1.0 Universal license - see the LICENSE file for details.

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.
