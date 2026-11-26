# Cinema Kit

Cinema Kit is an Android demo for choosing a movie, showtime and seats, then viewing a sample booking ticket. It uses Kotlin, Jetpack Compose, Navigation Compose and MVVM ViewModels with `StateFlow`.

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
- Discover screen with live Studio Ghibli films from a public REST API through Retrofit, including loading, success, empty, error and Retry states; Coil loads online posters
- Room database for saved bookings, with a My Bookings screen to read, edit seats and delete records
- Dark theme preference saved with DataStore and applied to the whole app

The sample booking path is **Dune: Part Two → 18:00 → Hall 2 → C4 → 2 500 ₸ → Confirm Booking → Book seats → Ticket → Back to Movies → My Bookings**. The ticket is a demonstration only and is not valid for admission. Bookings are saved locally in Room; there is no payment or account. Discover films are information from an external API and have no real showtimes or booking option.

See the [ticket screenshot](docs/screenshots/ticket.png), [large-font screenshot](docs/screenshots/ticket-large-font.png), and [manual emulator check](docs/tests/manual-smoke.md).

## Run and test

Open the repository in Android Studio, let Gradle sync and run the `app` configuration on an Android device or emulator. The command line equivalent is:

```bash
./gradlew :app:assembleDebug :app:testDebugUnitTest :app:connectedDebugAndroidTest
```

On macOS or Linux, if `gradlew` has no executable bit, use `bash gradlew` instead. Set `ANDROID_HOME` to your Android SDK location if Gradle cannot find the SDK.
The `connectedDebugAndroidTest` task needs a running emulator or connected Android device.

## Architecture

The [booking and navigation flow](docs/booking-flow.md) maps the original routes to the data they need.

- `data/model` contains `Movie`, `Showtime` and `Booking`.
- `data/repository` supplies sample movies and showtimes, talks to the Ghibli API and Room, and saves the theme preference in DataStore.
- `ui/viewmodel` owns booking validation, online loading state, saved bookings and theme state.
- `ui/screens` contains the booking flow, Discover, My Bookings, Settings and an unavailable-booking recovery screen.
- `ui/booking` formats KZT prices and the demo ticket share text.
- `ui/components` contains reusable movie, showtime, seat and detail row views.
- `navigation/AppNavigation.kt` owns the `NavHost` and connects screen callbacks to navigation and the ViewModel; `CinemaRoutes.kt` builds routes with validated IDs.

## Week 13 demonstration

1. Open Discover with internet access. The film list and poster URLs come from the API; a local image appears if a poster cannot load. Turn off the emulator network, reopen Discover and show the error and Retry; restore the network and retry. The empty response is covered by a fake repository unit test.
2. Book Dune at 18:00 in C4. Open My Bookings and show the saved record. Edit it to another free seat, then close and reopen the app: the changed record and occupied seat remain. Delete the booking and show that the seat becomes free.
3. In Settings, enable Dark theme. Close and reopen the app to show the switch and app colors remain dark.
4. Show the successful build and tests, GitHub commits and each member's own contribution.

Each member should understand and explain their own files. The online catalog and local booking schedule are deliberately separate: the external API does not provide showtimes or available seats.

The observed emulator checks and assignment criteria are recorded in [Week 13 integration check](docs/tests/week13-integration.md).
