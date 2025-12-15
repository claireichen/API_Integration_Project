package org.example.service;

import org.example.model.APIClient;
import org.example.model.Track;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpotifyServiceParsingTest {

    @Test
    void parseTracksFromSearchExtractsTrackInfo() {
        String json = """
                {
                  "tracks": {
                    "items": [
                      {
                        "id": "1",
                        "name": "Song A",
                        "artists": [{ "name": "Artist A" }],
                        "album": { "name": "Album A" },
                        "preview_url": "https://example.com/preview1"
                      },
                      {
                        "id": "2",
                        "name": "Song B",
                        "artists": [{ "name": "Artist B" }],
                        "album": { "name": "Album B" },
                        "preview_url": null
                      }
                    ]
                  }
                }
                """;

        SpotifyService service = new SpotifyService(APIClient.getInstance());

        List<Track> tracks = service.parseTracksFromSearch(json);

        assertEquals(2, tracks.size());

        Track t1 = tracks.get(0);
        assertEquals("Song A", t1.getName());
        assertEquals("Artist A", t1.getArtist());
        assertEquals("Album A", t1.getAlbum());
        assertEquals("https://example.com/preview1", t1.getPreviewUrl());

        Track t2 = tracks.get(1);
        assertEquals("Song B", t2.getName());
        assertEquals("Artist B", t2.getArtist());
        assertEquals("Album B", t2.getAlbum());
        assertNull(t2.getPreviewUrl());
    }
}
