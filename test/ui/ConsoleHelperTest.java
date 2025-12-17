package ui;

import model.composition.MusicStyle;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class ConsoleHelperTest {

    private Scanner scanner(String input) {
        return new Scanner(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    void testReadString() {
        String result = ConsoleHelper.readString(scanner("Hello World\n"), "Enter: ");
        assertEquals("Hello World", result);
    }

    @Test
    void testReadInt_Valid() {
        int result = ConsoleHelper.readInt(scanner("123\n"), "Number: ");
        assertEquals(123, result);
    }

    @Test
    void testReadInt_InvalidThenValid() {
        int result = ConsoleHelper.readInt(scanner("abc\n456\n"), "Number: ");
        assertEquals(456, result);
    }

    @Test
    void testReadStyle_Valid() {
        MusicStyle style = ConsoleHelper.readStyle(scanner("ROCK\n"));
        assertEquals(MusicStyle.ROCK, style);
    }

    @Test
    void testReadStyle_LowerCase() {
        MusicStyle style = ConsoleHelper.readStyle(scanner("jazz\n"));
        assertEquals(MusicStyle.JAZZ, style);
    }

    @Test
    void testReadStyle_InvalidThenValid() {
        MusicStyle style = ConsoleHelper.readStyle(scanner("TECHNO\nPOP\n"));
        assertEquals(MusicStyle.POP, style);
    }
}