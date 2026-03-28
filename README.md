# AidChain Field

AidChain Field is the Android field-operations application for AidChain. It is designed for hardware-assisted, in-person workflows such as beneficiary verification, device capture, and field-side execution where camera, fingerprint, location, and offline-capable mobile infrastructure matter.

This repository is published as a clean portfolio snapshot. Project provenance is documented in [PROVENANCE.md](./PROVENANCE.md).

## What This App Does

- supports field execution workflows on Android devices
- integrates biometric and device-level libraries for field verification scenarios
- uses camera, location, storage, and network components for operational capture
- persists local data and synchronizes with backend services
- includes release automation hooks for Codemagic and Android signing

## Tech Stack

- Android native app
- Kotlin and Java
- Android SDK 33
- Room
- Retrofit / OkHttp
- Coroutines
- CameraX
- ML Kit face detection
- Firebase Crashlytics and Analytics
- AWS S3 and Cloudinary client libraries
- Koin dependency injection

## Repository Layout

```text
app/                    Android app module
app/src/main/java/
  chats/cash/chats_field/   application bootstrap
  com/fgtit/                biometric / device integration code
  com/treebo/               network availability checker
  android_serialport_api/   serial port integration
lib-eyecooliris-uvc/    device library module
docs/                   supporting docs
keystore/               signing-related assets for local workflows
```

## Device and Hardware Integrations

The app includes code paths for:

- fingerprint-related device integrations
- camera and image capture pipelines
- face detection
- location services
- serial or USB device communication
- offline/local persistence with Room

This makes the app materially different from the web and Flutter clients in the platform.

## Prerequisites

- Android Studio
- JDK 17
- Android SDK 33
- Gradle wrapper included in the repo

## Local Development

Build debug APK:

```bash
./gradlew assembleDebug
```

Run unit tests:

```bash
./gradlew test
```

## Release and Signing Notes

The Gradle configuration expects release signing information through either:

- local `key.properties`
- or CI environment variables used by Codemagic

Do not commit signing secrets or local machine-specific properties.

## Backend Dependency

This app is part of the broader AidChain system and depends on backend services for operational sync and business workflows.

## Notes

- This repository is Android-specific and includes device-oriented dependencies that require real hardware for full validation.
- The published org repository is a clean snapshot without prior git history.
