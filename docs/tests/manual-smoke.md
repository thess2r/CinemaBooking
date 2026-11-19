# Week 12 manual smoke test

Run on 18 November 2026 using the Pixel 6 Android emulator (API 35) and a debug build from `feature/booking-week12-demo`.

| Check | Actual result |
| --- | --- |
| Search for `Steppe` | Steppe Riders appears |
| Open Details and select 18:00 | Correct film and session appear |
| Open seats and select C4 | Total becomes 2 500 ₸ |
| Continue to Summary | Film, C4 and 2 500 ₸ are retained |
| Confirm booking | Ticket #1001 shows the film, 18:00, Hall 2 and C4 |
| Return to Movies | Catalog is shown |
| Reopen same session | Confirmed C4 is disabled |
| Back from Seats and Details | Returns to the preceding screen |
| Search for `zzzz` | `No movies found` appears |

The local unit test task `:app:testDebugUnitTest` passed on the same build. This record describes the checks actually run; the other team members still need to review their code and record their own contributions.

## Booking and navigation follow-up — 19 November 2026

On `feature/booking-defense-polish`, the Pixel 6 emulator (API 35) completed the same movie-to-ticket path after the checkout changes. The summary showed C4 and `2 500 ₸`; the confirmation dialog appeared before booking; the ticket showed `#1001`, the film, session, hall, seat and total. The copy action was verified by an Android UI test against the clipboard. Tapping Share opened Android's share sheet with the film, session, seat, price and demo warning; closing it and choosing Back to Movies returned to the catalog.

`./gradlew :app:testDebugUnitTest :app:connectedDebugAndroidTest` passed on the branch. The Android UI suite includes the complete movie-to-ticket journey, confirmation and cancellation, ticket copying, reopening the session to check that C4 stays occupied, and the unavailable-booking recovery screen. The ticket remains a local demo; the share text labels it as such.

The same manual path also passed with the emulator font scale at 1.3. The system font scale was returned to 1.0 after the check.
