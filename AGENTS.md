# ProverbOTD Agent Guide

## Development Commands

- Build: `./gradlew build`
- Run unit tests: `./gradlew test`
- Run instrumented tests: `./gradlew connectedAndroidTest`
- Run a single unit test: `./gradlew test --tests "<test_class>.<test_method>"`
- Run a single instrumented test: `./gradlew connectedAndroidTest --tests "<test_class>#<test_method>"`

## Project Structure

- Source code: `app/src/main`
- Unit tests: `app/src/test`
- Instrumented tests: `app/src/androidTest`
- Gradle version catalog: `gradle/libs.versions.toml`
- Build scripts: `build.gradle.kts` (root) and `app/build.gradle.kts`

## Important Notes

- Uses Kotlin 2.0.21, Compose BOM 2024.09.00, Room 2.6.1 (versions in `gradle/libs.versions.toml`)
- Targets SDK 36 (minSdk, targetSdk, compileSdk)
- Jetpack Compose for UI, Room for local storage
- Dependencies referenced via version catalog (e.g., `libs.androidx.core.ktx`)
- KSP plugin used for Room code generation (see `ksp("androidx.room:room-compiler:$roomVersion")`)
- Test runner: AndroidJUnitRunner (instrumented), JUnit (local)
- Database is pre-populated from `app/src/main/assets/databases/KJV.db` (see `VerseDatabase.createFromAsset`)
