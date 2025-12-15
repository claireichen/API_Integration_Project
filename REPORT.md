
---

## `REPORT.md`

```markdown
# Project Report – MuseMix (Music Recommender & Generator)

## Overview

MuseMix is a Java Swing application that combines **music recommendation** and
**AI music generation** in a single desktop tool.

- For recommendations, it calls the **Spotify Web API** using the Client Credentials
  flow and searches for tracks by mood, genre, or artist.
- For generation, it uses **MusicAPI.ai** (a Suno-style API) to create custom
  instrumental tracks and polls the API until the audio is ready.
- The app is built with an MVC structure and uses several design patterns:
  **Singleton, Strategy, Factory, and Observer**.

---

## Challenges I Faced

### Challenge 1: Spotify Authentication & JSON Parsing

**Problem:**  
The first version of my Spotify client either failed with authentication
errors or returned `0` tracks because my parsing logic was fragile.
I also struggled with where to store credentials safely.

**Solution:**  

- Implemented a dedicated `APIClient` singleton that:
  - Loads `spotify.clientId` and `spotify.clientSecret` from `config.properties`.
  - Requests a Client Credentials access token and caches it.
- Used Java’s `HttpClient` to call `https://api.spotify.com/v1/search`.
- Switched from manual string parsing to **Gson**, parsing JSON into
  `JsonObject`/`JsonArray` and converting results into a `Track` domain model.

**What I Learned:**

- The importance of separating **API concerns** into a reusable client.
- Why you should never parse complex JSON by hand when a stable library exists.
- How to handle HTTP status codes and propagate clear error messages.

---

### Challenge 2: Suno / MusicAPI Polling and Timeouts

**Problem:**  
The MusicAPI (Suno) integration was tricky because generation is asynchronous.
The `/create` endpoint returns a `task_id`, but the `/task/{id}` endpoint
responds with `202` and `"task not ready"` while the audio is still rendering.
My initial implementation treated any non-200 status as a fatal error, which
caused “poll failed” exceptions instead of just waiting.

**Solution:**

- Implemented `SunoService` with two steps:
  1. `requestInstrumental(UserQuery)` calls `POST /api/v1/sonic/create` and
     extracts `task_id`.
  2. `pollGenerationStatus(String taskId)` repeatedly calls
     `GET /api/v1/sonic/task/{taskId}`:
     - If status code is `202` → sleep and poll again.
     - If status code is `200` → parse `state` and `audio_url`.
     - If `state = "succeeded"` or `audio_url` is non-null → return.
     - If `state` contains `"failed"` → treat as error.
- Added a maximum number of poll attempts and returned `"timeout"` if the track
  never finished.

**What I Learned:**

- How to design **polling** logic for asynchronous APIs.
- That HTTP 202 (“Accepted”) is a normal part of job-style APIs and should not
  always be treated as an error.
- How to avoid freezing the UI by running the entire process inside a
  `SwingWorker`.

---

### Challenge 3: Keeping the UI Responsive with SwingWorker

**Problem:**  
My first attempts at calling external APIs from the Swing action listeners
blocked the Event Dispatch Thread, which made the UI freeze while waiting
for network responses.

**Solution:**

- Moved all external API calls into `SwingWorker` instances inside
  `MainController`:
  - `requestRecommendations(...)` → `doInBackground()` calls the appropriate
    `RecommendationStrategy`.
  - `requestGeneration(...)` → `doInBackground()` calls the
    `InstrumentalGenerationStrategy`.
- Used the `done()` method to safely update the `AppModel` and fire events
  back to the views on the EDT.
- Updated the status bar and buttons based on event types:
  `RECOMMENDATION_STARTED`, `RECOMMENDATION_COMPLETED`,
  `GENERATION_STARTED`, `GENERATION_COMPLETED`, `ERROR`.

**What I Learned:**

- The difference between background threads and the Swing UI thread.
- How to structure controller logic so the view never directly waits on an API.
- Why the Observer pattern is a good fit for async UI updates.

---

## Design Pattern Justifications

### Singleton – `APIClient`

I used the **Singleton** pattern for `APIClient` to ensure there is exactly one
instance responsible for:

- Loading configuration from `config.properties`.
- Managing the shared `HttpClient`.
- Handling Spotify token refresh and generic GET/POST calls.

This avoids duplicating connection and configuration logic and ensures
consistent behavior across services.

---

### Strategy – `RecommendationStrategy` & `MusicGenerationStrategy`

**Why Strategy?**

The app supports multiple recommendation “modes” that all behave slightly
differently but share the same interface:

- `MoodRecommendationStrategy`
- `GenreRecommendationStrategy`
- `ArtistSeedRecommendationStrategy`

All implement `RecommendationStrategy#getRecommendations(UserQuery)` but use
different fields of `UserQuery`. Likewise, `InstrumentalGenerationStrategy`
implements `MusicGenerationStrategy#generate(UserQuery)` for Suno/MusicAPI.

Using Strategy allowed `MainController` to remain simple:

- It doesn’t care *which* strategy it gets; it just calls `generate(...)` or
  `getRecommendations(...)`.
- Adding a new mode in the future (for example, “Top Tracks” or “Playlist
  Builder”) would only require a new strategy and a minor update in the factory.

---

### Factory – `MusicServiceFactory`

`MusicServiceFactory` is a **Factory** that maps enum modes to concrete strategies:

- `RecommendationMode` → mood/genre/artist strategies.
- `GenerationMode` → instrumental generation strategy.

This concentrates the object creation logic in one place instead of scattering
`if` / `switch` statements across the UI or controller. It also makes unit
testing easy: the factory tests simply assert that the correct strategy type
is created for each mode.

---

### Observer – `MusicEventSource` / `MusicEventListener`

To keep the GUI decoupled from the controller logic, I implemented a simple
**Observer** pattern:

- `MusicEventSource` holds a list of `MusicEventListener`s and fires `MusicEvent`s
  (with a type and optional payload).
- `MainController` extends `MusicEventSource` and fires events when:
  - Recommendations start / finish.
  - Generation starts / finishes.
  - Errors occur.
- `MainFrame` implements `MusicEventListener` and updates the UI based on the
  events, delegating to `ResultPanel`, `GenerationPanel`, and `StatusBar`.

This design lets me add more views in the future or change the UI without
modifying the controller’s core logic.

---

## Testing Strategy

I wrote **11 JUnit 5 tests** focusing on:

- **Singleton behavior** (`APIClientSingletonTest`)
- **Factory correctness** (`MusicServiceFactoryTest`)
- **Routing logic** in the Spotify service:
  - Clear tests that show mood/genre/artist recommendation methods pull from
    the right `UserQuery` field.
- **JSON parsing** of Spotify search responses without hitting the real API.
- **Session persistence**:
  - Save+load round-trip using a temp file.
  - Error case when loading a non-existing file.
- **Model encapsulation** (optional `AppModelTest`).

Most tests stub out external calls so they are fast, deterministic, and do not
consume API quota.

---

## AI Usage (Honest Disclosure)

I used AI tools (ChatGPT) as **a coding assistant**, not as a code generator
for the entire project.

Specific uses:

- **Debugging JSON parsing:**  
  I asked how to parse Spotify’s search response with Gson. I then wrote my own
  `parseTracksFromSearch` method and adapted field names and types to my domain
  `Track` model.

- **MusicAPI polling logic ideas:**  
  I consulted AI about typical patterns for polling asynchronous APIs and
  handling status codes like `202`. I then wrote and refined
  `pollGenerationStatus` myself, adjusting it after seeing real responses like
  `"task not ready"`.

- **JUnit test ideas:**  
  I asked for suggestions on what to test (singleton behavior, factory
  selection, parsing, persistence) and then implemented the tests, fixing them
  to compile against my actual classes and APIs.

- **Documentation wording:**  
  I used AI to help draft sections of this README and REPORT and then edited
  them to match my codebase and personal style.

Verification steps:

- I read and understood every line of code before keeping it.
- I refactored or rewrote AI-suggested snippets to fit my project structure.
- I verified behavior with my own tests and manual runs.

---

## Time Spent

Approximate breakdown:

- **Planning & design:** 4–6 hours  
- **Spotify integration + parsing:** 8–10 hours  
- **MusicAPI (Suno) integration & polling:** 8–10 hours  
- **Swing GUI + MVC wiring:** 8–10 hours  
- **Save/load sessions & persistence:** 3–4 hours  
- **Testing & debugging:** 5–6 hours  
- **Documentation & demo video:** 2–3 hours  

**Total:** ~35–45 hours.

---

## Possible Future Improvements

- Add multi-language support (e.g., localized UI labels).
- Add the ability to open tracks directly in the Spotify client.
- Support playlists or multiple generated clips per session.
- More robust error dialogs and logging to a file instead of just console.

