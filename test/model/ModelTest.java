
import model.MusicDisc;
import model.composition.InstrumentalTrack;
import model.composition.MusicStyle;
import model.composition.Song;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void testSongCreation() {
        Song song = new Song("Title", "Artist", 120, MusicStyle.ROCK, "English");

        assertEquals("Title", song.getTitle());
        assertEquals("Artist", song.getArtist());
        assertEquals(120, song.getDurationSeconds());
        assertEquals(MusicStyle.ROCK, song.getStyle());
        assertEquals("English", song.getLyricsLanguage());

        assertTrue(song.toString().contains("[Song]"));
        assertTrue(song.toString().contains("English"));
    }

    @Test
    void testInstrumentalCreation() {
        InstrumentalTrack track = new InstrumentalTrack("Title", "Artist", 300, MusicStyle.JAZZ, "Saxophone");

        assertEquals("Saxophone", track.getMainInstrument());
        assertTrue(track.toString().contains("[Inst]"));
        assertTrue(track.toString().contains("Saxophone"));
    }

    @Test
    void testMusicDiscBasics() {
        MusicDisc disc = new MusicDisc("My Super Disc");

        assertEquals("My Super Disc", disc.getDiscName());
        assertTrue(disc.getTrackList().isEmpty());
        assertEquals(0, disc.getTotalDuration());
    }

    @Test
    void testMusicDiscClear() {
        MusicDisc disc = new MusicDisc("Disc");
        disc.addTrack(new Song("A", "B", 1, MusicStyle.POP, "En"));

        assertFalse(disc.getTrackList().isEmpty());

        disc.clear();

        assertTrue(disc.getTrackList().isEmpty());
    }

    @Test
    void testEnumStyles() {
        for (MusicStyle style : MusicStyle.values()) {
            assertNotNull(style.name());
        }
    }
}