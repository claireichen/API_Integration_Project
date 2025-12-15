# MuseMix – Music Recommender & Generator

A desktop music tool built with Java Swing that:

- Recommends Spotify tracks by **mood**, **genre**, or **artist**
- Generates **AI instrumental tracks** using a Suno-style API (MusicAPI.ai)
- Lets you **save & load sessions** so you can revisit playlists later

---

## Tech Stack

- Java 17
- Swing (GUI)
- Maven
- Spotify Web API (Client Credentials flow)
- MusicAPI.ai (Suno / Sonic model)
- Gson (JSON parsing)
- JUnit 5 (testing)
- Design patterns: **Strategy, Factory, Singleton, Observer, MVC**

---

## Project Structure

```text
src/
 └── main/java/org/example
     ├── controller/        # Controllers + event system (Observer)
     ├── model/             # Domain models + strategies + singleton APIClient
     ├── service/           # API services + factory
     ├── view/              # Swing UI (MVC views)
     └── Main.java          # App entry point

src/
 └── test/java/org/example  # JUnit 5 tests

resources/
 ├── config.properties.example   # Template config (no real keys!)
 └── config.properties           # (local only, ignored by git)

```
---

## Setup

1. Prereqs
- Java 17+
- Maven
- Spotify Developer account
- MusicAPI.ai account (for Suno-style generation)

2. Configure API keys

- Copy the example config:
```text
cp src/main/resources/config.properties.example \
   src/main/resources/config.properties
```
- Edit src/main/resources/config.properties and fill in your keys:
```text
# Spotify client credentials
spotify.clientId=YOUR_SPOTIFY_CLIENT_ID
spotify.clientSecret=YOUR_SPOTIFY_CLIENT_SECRET

# MusicAPI.ai (Suno-style) – Bearer token
suno.baseUrl=https://api.musicapi.ai
suno.apiKey=YOUR_MUSICAPI_BEARER_TOKEN
```

3. Build
From the project root:
```text
mvn clean test
```
You should see all tests pass (11 tests). 

4. Run
From IntelliJ IDEA (recommended):
 1. Open the project as a Maven project.
 2. Ensure Main is the run configuration
    - Right-click org.example.Main -> Run 'Main.main()'.
 3. The MuseMix window should appear.

---

## Features

Spotify Recommendations
- Search Spotify tracks with three modes (Strategy pattern):
  - Mood - e.g., "lofi chill", "focus", "happy"
  - Genre - e.g., "jazz", "rock", "classical"
  - Artist - e.g., "Taylor Swift"
AI Music Generation (MusicAPI/Suno)
- Generation tab:
  - Prompt text (description/lyrics)
  - Optional genre + mood
  - "Generate Instrumental" button
- Uses MusicAPI.ai's Sonic endpoints
- Displays status and a clickable link:
  - Click the link to open the generated MP3 in your browser
Save/Load Sessions
- Save the current session (query + track results) to a JSON file
- Load a session back:
  - Restores search fields
  - Repopulates the results table
- Implemented with SessionPersistenceService using Gson
Async & Error Handling
- All API calls are wrapped in SwingWorker:
  - UI stays responsive (no frozen window)
  - Status bar shows progress
- Errors are propagated through the Observer event system and shown in the status bar (and can be extended to show dialogs). 
