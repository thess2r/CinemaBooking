# Week 13 integration check

Checked on 26 November 2026 with Pixel 6 API 35 and Medium Phone API 37 emulators. Room PR #8 is merged; these results cover the integration branch before PR #9 is merged into `main`.

| Assignment criterion | Implementation | Check |
| --- | --- | --- |
| Public REST API, Retrofit, JSON models, repository | Discover uses the Studio Ghibli films API in `data/remote` and `OnlineMovieRepository` | Live film names and descriptions appeared on the emulator |
| Coroutines, ViewModel, StateFlow, Compose | `OnlineMoviesViewModel` publishes Loading, Success, Error and Empty to `DiscoverScreen` | Network ViewModel unit tests passed; Success and Error were seen on the emulator |
| Retry and online images | Discover has Retry and a Coil `AsyncImage` with a local fallback | With airplane mode on, Error and Retry appeared; after reconnecting, Retry returned film results. Poster URLs failed on this emulator, so the local placeholder appeared |
| Room entity, DAO, CRUD, Flow | Room stores bookings; My Bookings lists, edits seats and deletes them | Android test created, read, updated and deleted a booking; invalid occupied seat was rejected |
| Local persistence | Confirm inserts into Room before Ticket opens | A booking remained in My Bookings after force stopping and reopening the app |
| DataStore preference | Settings saves the dark theme and MainActivity applies it | Persistence test passed and the theme was checked after reopening the app |

Checks run from this branch:

- `./gradlew clean :app:assembleDebug :app:testDebugUnitTest :app:connectedDebugAndroidTest --quiet`: 27 Android tests passed on each emulator, and 41 JVM tests passed.
- Manual path: Dune: Part Two → 18:00 → C4 → confirm → Ticket → My Bookings → force stop/reopen → My Bookings → delete. The booking remained after reopen and disappeared after delete.
- Manual network path: airplane mode → Discover error and Retry → restore network → Retry → Studio Ghibli films.

For the demo, show the online Discover list separately from the local Dune booking. Online films have no real cinema sessions, payments or tickets.

## Berik: My Bookings sort preference and seat validation

The added sorting and validation tests are included in the results above. The preference was also checked after force stopping and reopening the Pixel 6 app.

| ID | Check | Expected result | Status |
| --- | --- | --- | --- |
| UNIT-01 | `SeatInputValidatorTest`: trimming, case, blanks, empty items, unknown seats, duplicates | 10 tests pass | Passed (JVM) |
| UNIT-02 | `BookingSortTest`: three sort orders, stable ties, unknown stored value falls back to default | 7 tests pass | Passed (JVM) |
| DS-02 | `ThemePreferencesRepositoryTest.bookingSortOrderRemainsSavedForNewRepositoryInstance` | Sort order survives a new repository instance | Passed on both emulators |
| DS-03 | Settings: pick "Highest price first", force stop, reopen Settings | The radio choice remains selected | Passed manually on Pixel 6; see [screenshot](../screenshots/week13-settings.png) |
| DB-06 | Create two bookings with different prices, switch each order in Settings | My Bookings reorders and the "Sorted:" caption matches | Not run yet (needs emulator) |
| DB-07 | Edit a booking and enter `a1, a1` or `D9` | Error message under the field, Room record unchanged | Not run yet (needs emulator) |

How the pieces fit for the defense: `SettingsScreen` calls `ThemeViewModel.setBookingSortOrder`, which writes through `ThemePreferencesRepository` into DataStore. `ThemeViewModel.bookingSortOrder` is a `StateFlow` that `AppNavigation` passes to `MyBookingsScreen`, which sorts the Room list with `sortBookings`. The Room data itself is not changed by sorting.

