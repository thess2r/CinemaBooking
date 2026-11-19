
# Part 1 — Movies & Catalog Testing

## Developer
Bigali Assanali

## Features
- Movie catalog with 5 movies
- Movie posters
- Search by title
- Empty search state
- Movie details callback

## Manual Test Cases

| ID | Test Case | Expected Result | Status |
|---|---|---|--------|
| M-01 | Open MoviesScreen | All 5 movies displayed | Passed |
| M-02 | Check movie posters | Correct posters displayed | Passed |
| M-03 | Search "inter" | Interstellar displayed | Passed |
| M-04 | Search "xyz123" | "No movies found" displayed | Passed |
| M-05 | Click View Details | Correct movieId passed | Passed |

## Testing Notes

- M-01 to M-04: Passed using Android Studio Interactive Preview.
- M-05: Pending Navigation Compose integration by Part 4.
- Unit Tests: 7 passed.
- Build: BUILD SUCCESSFUL.

## Unit Tests
File: MovieRepositoryTest.kt

7 JUnit tests for:
- Movie count
- Existing movie ID
- Unknown movie ID
- Case-insensitive search
- Unknown movie title
- Empty search query
- Whitespace handling

Result: Passed

## Build
assembleDebug: Passed

## Environment
- Android Studio
- Kotlin
- Jetpack Compose
- JUnit 4
