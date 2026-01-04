# CineLink

A BookMyShow clone Android application built with Jetpack Compose and modern Android development practices.

## About

CineLink is a movie ticket booking application that replicates the core functionality of BookMyShow. The app allows users to browse movies, view details, and book tickets for their favorite shows.

## Screen Recording

https://github.com/user-attachments/assets/09f84651-c13d-434d-95c8-b6acf9a5a76a

## Tech Stack

### Core
- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern declarative UI framework
- **Material 3** - Design system and UI components

### Architecture & Libraries
- **MVVM Architecture** - Clean separation of concerns
- **Kotlin Coroutines** - Asynchronous programming
- **Ktor Client** - HTTP client for API calls
- **Room Database** - Local data persistence
- **Koin** - Dependency injection
- **Navigation Compose** - In-app navigation
- **Coil** - Image loading library

### Code Quality
- **Detekt** - Static code analysis
- **MockK** - Testing framework

### Build Configuration
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Compile SDK**: 36
- **Java Version**: 17

## Features

- Browse movies and shows
- View detailed movie information
- Book tickets for shows
- Modern Material 3 UI design
- Offline support with Room database
- Clean architecture with MVVM pattern

## Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17
- Android SDK 36

### Installation

1. Clone the repository
```bash
git clone https://github.com/MohammedHamza0631/CineLink.git
cd CineLink
```

2. Open the project in Android Studio

3. Sync Gradle files

4. Run the app on an emulator or physical device

## Download APK

Pre-built APK files are available in the [Releases](https://github.com/MohammedHamza0631/CineLink/releases) section. Download the latest version and install it on your Android device.

### Installation Steps
1. Download the APK from the Releases page
2. Enable "Install from Unknown Sources" in your device settings
3. Open the APK file and install

## CI/CD

This project uses GitHub Actions for automated builds and releases. Every push to the main branch automatically:
- Builds a new APK
- Creates a GitHub release
- Uploads the APK for download

## License

This project is for educational purposes only.
