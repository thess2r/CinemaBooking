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
