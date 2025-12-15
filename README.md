# MuseMix – Music Recommender & Generator

## Setup

1. **Clone repo** and open in IntelliJ / your IDE.
2. Create `src/main/resources/config.properties` (not committed) based on `config.properties.example`:
   - Get a **Spotify** Client ID/Secret from the [Spotify Developer Dashboard].
   - Get a **MusicAPI (Suno)** bearer token from https://musicapi.ai dashboard.
3. Fill in:
   - `spotify.clientId=...`
   - `spotify.clientSecret=...`
   - `suno.baseUrl=https://api.musicapi.ai`
   - `suno.apiKey=YOUR_MUSICAPI_TOKEN`
4. Build & run:

   ```bash
   mvn clean package
   mvn exec:java -Dexec.mainClass="org.example.Main"
