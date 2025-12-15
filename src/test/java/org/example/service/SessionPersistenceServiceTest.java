package org.example.service;

import org.example.model.Session;
import org.example.model.Track;
import org.example.model.UserQuery;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionPersistenceServiceTest {

    @Test
    void saveAndLoadRoundTripPreservesSessionData() throws IOException {
        SessionPersistenceService service = new SessionPersistenceService();

        UserQuery query = new UserQuery()
                .setText("lofi chill")
                .setMood("relaxed")
                .setGenre("lofi");

        Track t1 = new Track()
                .setId("1")
                .setName("Song A")
                .setArtist("Artist A")
                .setAlbum("Album A");

        Track t2 = new Track()
                .setId("2")
                .setName("Song B")
                .setArtist("Artist B")
                .setAlbum("Album B");

        Session original = new Session(query, List.of(t1, t2));

        Path tempFile = Files.createTempFile("session-test", ".json");
        tempFile.toFile().deleteOnExit();

        service.save(original, tempFile.toFile());

        Session loaded = service.load(tempFile.toFile());
        assertNotNull(loaded);
        assertNotNull(loaded.getQuery());
        assertEquals("lofi chill", loaded.getQuery().getText());
        assertEquals("relaxed", loaded.getQuery().getMood());
        assertEquals("lofi", loaded.getQuery().getGenre());

        assertNotNull(loaded.getTracks());
        assertEquals(2, loaded.getTracks().size());
        assertEquals("Song A", loaded.getTracks().get(0).getName());
        assertEquals("Artist B", loaded.getTracks().get(1).getArtist());
    }

    @Test
    void loadNonExistingFileThrowsIOException() {
        SessionPersistenceService service = new SessionPersistenceService();
        Path path = Path.of("non-existing-session-file.json");

        assertThrows(IOException.class, () -> service.load(path.toFile()));
    }
}
