package command;

import model.MusicDisc;
import model.composition.MusicStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DiscProcessingSystem;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TestDiscSystem extends DiscProcessingSystem {
    public String lastAction = "";

    public TestDiscSystem() {
        super("test_dummy.txt");
    }


    @Override
    public void createNewDisc(String name) {
        lastAction = "createNewDisc: " + name;
    }

    @Override
    public void addSong(String title, String artist, int duration, MusicStyle style, String language) {
        lastAction = "addSong: " + title;
    }

    @Override
    public void addInstrumental(String title, String artist, int duration, MusicStyle style, String instrument) {
        lastAction = "addInstrumental: " + title;
    }

    @Override
    public void findTracksByDuration(int min, int max) {
        lastAction = "findTracks: " + min + "-" + max;
    }

    @Override
    public void sortTracksByStyle() {
        lastAction = "sortTracks";
    }

    @Override
    public void saveCurrentDisc() {
        lastAction = "saveDisc";
    }

    @Override
    public void deleteSavedData() {
        lastAction = "deleteData";
    }

    @Override
    public void viewCurrentDisc() {
        lastAction = "viewDisc";
    }

    @Override
    public void loadDisc() {
    }
}

class CommandTest {

    private TestDiscSystem system;

    @BeforeEach
    void setUp() {
        system = new TestDiscSystem();
    }

    private Scanner prepareInput(String data) {
        InputStream stdin = new ByteArrayInputStream(data.getBytes());
        return new Scanner(stdin);
    }

    @Test
    void testCreateNewDiscCommand() {
        Scanner scanner = prepareInput("My Best Disc\n");
        Command command = new CreateNewDiscCommand(system);
        command.execute(scanner);

        assertEquals("createNewDisc: My Best Disc", system.lastAction);
    }

    @Test
    void testAddSongCommand() {
        String input = "1\nYesterday\nBeatles\n200\nROCK\nEnglish\n";
        Scanner scanner = prepareInput(input);

        Command command = new AddTrackToDiscCommand(system);
        command.execute(scanner);

        assertEquals("addSong: Yesterday", system.lastAction);
    }

    @Test
    void testAddInstrumentalCommand() {
        String input = "2\nTake Five\nDave Brubeck\n300\nJAZZ\nSaxophone\n";
        Scanner scanner = prepareInput(input);

        Command command = new AddTrackToDiscCommand(system);
        command.execute(scanner);

        assertEquals("addInstrumental: Take Five", system.lastAction);
    }

    @Test
    void testSearchTrackCommand() {
        Scanner scanner = prepareInput("60\n180\n");
        Command command = new SearchTrackCommand(system);
        command.execute(scanner);

        assertEquals("findTracks: 60-180", system.lastAction);
    }

    @Test
    void testSortDiscCommand() {
        Scanner scanner = prepareInput("");
        Command command = new SortDiscCommand(system);
        command.execute(scanner);

        assertEquals("sortTracks", system.lastAction);
    }

    @Test
    void testSaveCurrentDiscCommand() {
        Command command = new SaveCurrentDiscCommand(system);
        command.execute(prepareInput(""));
        assertEquals("saveDisc", system.lastAction);
    }

    @Test
    void testDeleteSavedDiscsCommand() {
        Command command = new DeleteSavedDiscsCommand(system);
        command.execute(prepareInput(""));
        assertEquals("deleteData", system.lastAction);
    }

    @Test
    void testViewSavedDiscsCommand() {
        Command command = new ViewSavedDiscsCommand(system);
        command.execute(prepareInput(""));
        assertEquals("viewDisc", system.lastAction);
    }
}