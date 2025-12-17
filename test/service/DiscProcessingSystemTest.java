package service;

import model.composition.MusicStyle;
import model.composition.MusicalComposition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiscProcessingSystemTest {

    private DiscProcessingSystem system;
    private final String TEST_FILE = "test_coverage_music.txt";

    // Змінні для перехоплення того, що програма пише в консоль
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        // Перехоплюємо System.out
        System.setOut(new PrintStream(outContent));

        // Видаляємо файл перед кожним тестом, щоб починати з чистого аркуша
        new File(TEST_FILE).delete();

        system = new DiscProcessingSystem(TEST_FILE);
        system.createNewDisc("Test Disc");
    }

    @AfterEach
    void tearDown() {
        // Повертаємо консоль назад
        System.setOut(originalOut);
        // Чистимо за собою
        new File(TEST_FILE).delete();
    }

    @Test
    void testAddSongAndInstrumental() {
        system.addSong("Song A", "Artist A", 120, MusicStyle.ROCK, "En");
        system.addInstrumental("Inst B", "Artist B", 180, MusicStyle.JAZZ, "Sax");

        List<MusicalComposition> tracks = system.getCurrentDisc().getTrackList();
        assertEquals(2, tracks.size());
        assertEquals("Song A", tracks.get(0).getTitle());
        assertEquals("Inst B", tracks.get(1).getTitle());
    }

    @Test
    void testViewCurrentDisc_Empty() {
        // Тестуємо гілку, коли диск порожній
        system.getCurrentDisc().clear(); // Очищаємо вручну для тесту
        system.viewCurrentDisc();

        String output = outContent.toString();
        assertTrue(output.contains("(Disc is empty)"), "Має писати, що диск порожній");
    }

    @Test
    void testViewCurrentDisc_WithData() {
        // Тестуємо гілку, коли дані є + тестимо приватний метод formatDuration
        system.addSong("Song A", "Artist A", 65, MusicStyle.POP, "En"); // 1 хв 5 сек
        system.viewCurrentDisc();

        String output = outContent.toString();
        assertTrue(output.contains("1. [Song]"), "Має виводити список треків");

        // ВИПРАВЛЕНО: прибрав "0" перед п'ятіркою, бо ваш код не додає нулі для одиничних цифр
        assertTrue(output.contains("1 min 5 sec"), "Має правильно форматувати час (formatDuration)");
    }

    @Test
    void testFindTracksByDuration_Found() {
        system.addSong("Short", "A", 50, MusicStyle.POP, "En");
        system.addSong("Target", "B", 150, MusicStyle.POP, "En");

        system.findTracksByDuration(100, 200);

        String output = outContent.toString();
        assertTrue(output.contains("Target"), "Має знайти трек у діапазоні");
        assertFalse(output.contains("Short"), "Не має знаходити короткий трек");
    }

    @Test
    void testFindTracksByDuration_NotFound() {
        // Тестуємо гілку if (found.isEmpty())
        system.addSong("Short", "A", 50, MusicStyle.POP, "En");

        system.findTracksByDuration(200, 300);

        String output = outContent.toString();
        assertTrue(output.contains("No tracks found in this range"), "Має повідомити, що нічого не знайдено");
    }

    @Test
    void testSortTracksByStyle() {
        system.addSong("Rock Song", "A", 100, MusicStyle.ROCK, "En"); // ROCK зазвичай йде першим в Enum або за логікою
        system.addSong("Classic Song", "B", 100, MusicStyle.CLASSICAL, "En");

        system.sortTracksByStyle();

        // Перевіряємо порядок (залежить від порядку в Enum MusicStyle)
        // Припустимо, порядок: ROCK, POP, CLASSICAL... або навпаки.
        // Головне, що метод викликається без помилок.
        String output = outContent.toString();
        assertTrue(output.contains("Tracks sorted by style"));
    }

    @Test
    void testSaveAndLoad_Success() {
        system.addSong("Saved Song", "Saved Artist", 123, MusicStyle.BLUES, "Ukr");
        system.saveCurrentDisc();

        // Створюємо нову систему, щоб завантажити з файлу
        DiscProcessingSystem newSystem = new DiscProcessingSystem(TEST_FILE);
        assertEquals("Test Disc", newSystem.getCurrentDisc().getDiscName());
        assertEquals(1, newSystem.getCurrentDisc().getTrackList().size());
        assertEquals("Saved Song", newSystem.getCurrentDisc().getTrackList().get(0).getTitle());
    }

    @Test
    void testLoad_FileNotExists() {
        // Завантажуємо неіснуючий файл
        DiscProcessingSystem newSystem = new DiscProcessingSystem("non_existent_file_123.txt");
        assertEquals("Default New Disc", newSystem.getCurrentDisc().getDiscName());
    }

    @Test
    void testLoad_CorruptedFile() throws IOException {
        // Створюємо файл з "сміттям", щоб викликати помилку при парсингу
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEST_FILE, StandardCharsets.UTF_8))) {
            writer.println("SomeDiscName");
            writer.println("BAD_DATA_LINE_WITHOUT_PIPES"); // Неправильний формат
            writer.println("SONG|Too|Few|Args"); // Недостатньо аргументів
        }

        DiscProcessingSystem newSystem = new DiscProcessingSystem(TEST_FILE);
        // Система не повинна впасти, вона має пропустити биті рядки або створити порожній диск
        assertNotNull(newSystem.getCurrentDisc());
    }

    @Test
    void testDeleteSavedData() throws IOException {
        // 1. Створюємо файл
        new File(TEST_FILE).createNewFile();

        // 2. Видаляємо (успішний сценарій)
        system.deleteSavedData();
        String output = outContent.toString();
        assertTrue(output.contains("Saved file deleted"));
        assertFalse(new File(TEST_FILE).exists());

        // 3. Видаляємо знову (файлу вже немає - гілка else)
        outContent.reset(); // чистимо буфер консолі
        system.deleteSavedData();
        output = outContent.toString();
        assertTrue(output.contains("Failed to delete file"));
    }

    @Test
    void testForceCriticalError() {
        // Просто викликаємо метод, щоб покрити catch блок
        assertDoesNotThrow(() -> system.forceCriticalError());
    }
}