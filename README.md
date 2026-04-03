# TaskPlayer

An expert learning platform for Android built with Kotlin and Jetpack Compose. Users can browse expert video content, unlock premium sessions, save videos, follow experts, and get AI-generated insights for each video.

---

## Screenshots

<p float="left">
  <img src="screenshots/screen1.jpeg" width="180"/>
  <img src="screenshots/screen2.jpeg" width="180"/>
  <img src="screenshots/screen3.jpeg" width="180"/>
  <img src="screenshots/screen4.jpeg" width="180"/>
  <img src="screenshots/screen5.jpeg" width="180"/>
  <img src="screenshots/screen6.jpeg" width="180"/>
</p>

---

## Tech Stack

| | |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | MVVM + Repository Pattern |
| State | StateFlow + ViewModel |
| Navigation | Compose Navigation |
| Video Player | ExoPlayer (Media3) |
| Networking | Retrofit + OkHttp |
| Image Loading | Coil |
| Video Source | Pexels API |
| Async | Kotlin Coroutines |
| Testing | JUnit4, Coroutines Test, Compose UI Test |


---

## Features

- Vertical scrollable expert video feed
- ExoPlayer streams real MP4 videos inside the app
- Free videos play directly
- Premium videos show a lock overlay with price and Unlock button
- Mock unlock flow with lock-to-open animation
- Like and save buttons with toggle state
- Follow / Unfollow expert button
- View Profile navigates to expert screen with bio, stats, and video list
- Book Session button on every card and profile screen
- AI Insights bottom sheet with Key Topics and Smart Summary
- Saved Videos screen
- Shimmer loading cards while fetching
- App title scrolls with content like YouTube
- Lifecycle-aware ExoPlayer with audio focus management

---

## How to Run

**Requirements**
- Android Studio Hedgehog or later
- Android SDK 26+
- Free Pexels API key from https://www.pexels.com/api/

**Steps**

1. Clone the repo
```bash
git clone https://github.com/YOUR_USERNAME/TaskPlayer.git
```

2. Open in Android Studio

3. Add your Pexels API key in `core/utils/Constants.kt`
```kotlin
const val PEXELS_API_KEY = "YOUR_KEY_HERE"
```

4. Run on emulator or device (API 26+)

---

## Running Tests

```bash
# Unit tests
./gradlew test

# UI tests (emulator or device required)
./gradlew connectedAndroidTest
```

| Test File | Covers |
|---|---|
| VideoRepositoryTest | like, save, unlock, follow, getExpert |
| FeedViewModelTest | state changes, smart sheet, access types |
| SavedViewModelTest | saved list empty, fill, remove |
| FeedScreenTest | UI elements visible, button callbacks |

---

## Assumptions

- No real payment — unlock is a simulated 1.2 second mock flow
- Expert data is local mock data, not from an API
- Video titles and descriptions are curated and mapped to Pexels video slots
- AI Insights summary is generated locally from video metadata
- State is in memory — not persisted across app restarts
- Portrait mode only

---

## Author

Nadim
Android Developer — Kotlin, Jetpack Compose, AI/ML, FastAPI
