# Week 13 integration check

Checked on 26 November 2026 with the Pixel 6 API 35 emulator. This is a check of the current integration branch, not a claim that every teammate's pull request is merged into `main`.

| Assignment criterion | Implementation | Check |
| --- | --- | --- |
| Public REST API, Retrofit, JSON models, repository | Discover uses the Studio Ghibli films API in `data/remote` and `OnlineMovieRepository` | Live film names and descriptions appeared on the emulator |
| Coroutines, ViewModel, StateFlow, Compose | `OnlineMoviesViewModel` publishes Loading, Success, Error and Empty to `DiscoverScreen` | Network ViewModel unit tests passed; Success and Error were seen on the emulator |
| Retry and online images | Discover has Retry and a Coil `AsyncImage` with a local fallback | With airplane mode on, Error and Retry appeared; after reconnecting, Retry returned film results. Poster URLs failed on this emulator, so the local placeholder appeared |
| Room entity, DAO, CRUD, Flow | Room stores bookings; My Bookings lists, edits seats and deletes them | Android test created, read, updated and deleted a booking; invalid occupied seat was rejected |
| Local persistence | Confirm inserts into Room before Ticket opens | A booking remained in My Bookings after force stopping and reopening the app |
| DataStore preference | Settings saves the dark theme and MainActivity applies it | Persistence test passed and the theme was checked after reopening the app |

Checks run from this branch:

- `./gradlew clean :app:connectedDebugAndroidTest :app:testDebugUnitTest --quiet`: 21 Android tests and 24 JVM tests passed.
- Manual path: Dune: Part Two → 18:00 → C4 → confirm → Ticket → My Bookings → force stop/reopen → My Bookings → delete. The booking remained after reopen and disappeared after delete.
- Manual network path: airplane mode → Discover error and Retry → restore network → Retry → Studio Ghibli films.

For the demo, show the online Discover list separately from the local Dune booking. Online films have no real cinema sessions, payments or tickets.
