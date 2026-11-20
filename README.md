# Cinema Kit

Cinema Kit is a local demo Android app for choosing a movie, showtime and seats, then viewing a sample booking ticket. It uses Kotlin, Jetpack Compose, Navigation Compose and a single MVVM `CinemaViewModel` with `StateFlow`.

## Team members

- Bigali Assanali — movies and catalog
- Шыңғыс — movie details and showtimes
- Рамазан — seats and shared state
- Берік — booking, ticket and navigation

## Features

- Five sample movies with title search and an empty result state
- Film details and three showtimes per film
- Twelve seats, occupied seat protection and live price calculation
- Booking summary with a confirmation dialog and a demo ticket that can be copied or shared
- `movieId` and `showtimeId` route arguments with working back navigation and a recovery screen for missing booking data

The sample defense path is **Dune: Part Two → 18:00 → Hall 2 → C4 → 2 500 ₸ → Confirm Booking → Book seats → Ticket → Back to Movies**. The ticket is a demonstration only and is not valid for admission. Bookings are held in memory for the current app process; there is no payment, account or database.

See the [ticket screenshot](docs/screenshots/ticket.png), [large-font screenshot](docs/screenshots/ticket-large-font.png), and [manual emulator check](docs/tests/manual-smoke.md).

## Run and test

Open the repository in Android Studio, let Gradle sync and run the `app` configuration on an Android device or emulator. The command line equivalent is:

```bash
./gradlew :app:assembleDebug :app:testDebugUnitTest :app:connectedDebugAndroidTest
```

On macOS or Linux, if `gradlew` has no executable bit, use `bash gradlew` instead. Set `ANDROID_HOME` to your Android SDK location if Gradle cannot find the SDK.
The `connectedDebugAndroidTest` task needs a running emulator or connected Android device.

## Architecture

The [booking and navigation flow](docs/booking-flow.md) maps each route to the data it needs.

- `data/model` contains `Movie`, `Showtime` and `Booking`.
- `data/repository` supplies local sample movies, sessions and initial occupied seats.
- `ui/viewmodel` owns the shared `CinemaUiState`, booking validation and user actions.
- `ui/screens` contains the five journey screens and an unavailable-booking recovery screen.
- `ui/booking` formats KZT prices and the demo ticket share text.
- `ui/components` contains reusable movie, showtime, seat and detail row views.
- `navigation/AppNavigation.kt` owns the `NavHost` and connects screen callbacks to navigation and the ViewModel; `CinemaRoutes.kt` builds routes with validated IDs.

## Team work still required

Each member must review and understand their area, make their own meaningful development commits, and be ready to explain it during the defense. The assignment requires at least ten meaningful commits **per student** and all members to have repository access. A single developer's work cannot satisfy those individual requirements. Verify collaborators, contributions and the final `main` branch before submission.
