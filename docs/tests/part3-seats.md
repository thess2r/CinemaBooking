# Part 3 — Seats & MVVM Testing

**Tester:** Ramazan

**Environment:**
- IDE: Android Studio
- Device/Emulator: Android Studio emulator

| ID | Action | Expected Result | Actual Result | Status |
|---|---|---|---|---|
| S-01 | Open Steppe Riders → 18:00 | 12 seats are displayed | | Run |
| S-02 | Tap A2, B3, C1 | Occupied seats cannot be selected | | Run |
| S-03 | Tap C4 | C4 selected, Total = 2,500 ₸ | | Run |
| S-04 | Tap C4 again | Selection removed, Total = 0 ₸ | | Run |
| S-05 | Select two available seats | Total = 5,000 ₸ | | Run |
| S-06 | Continue without selected seats | Continue disabled | | Run |
| S-07 | Change showtime and return to seats | Previous selection cleared | | Run |

**Notes:**
- Tests passed, the checks run normally