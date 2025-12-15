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
