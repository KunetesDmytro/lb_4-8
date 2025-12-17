package ui;

import command.Command;
import org.junit.jupiter.api.Test;
import service.DiscProcessingSystem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MenuInvokerTest {

    @Test
    void testShowMenu() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        DiscProcessingSystem dummySystem = new DiscProcessingSystem("test.txt");
        MenuInvoker invoker = new MenuInvoker(dummySystem);

        invoker.showMenu();

        System.setOut(System.out);

        String output = outContent.toString();
        assertTrue(output.contains("MUSIC DISC MENU"));
        assertTrue(output.contains("1. View current disc"));
    }

    @Test
    void testExecuteInvalidCommand() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        DiscProcessingSystem dummySystem = new DiscProcessingSystem("test.txt");
        MenuInvoker invoker = new MenuInvoker(dummySystem);

        invoker.executeCommand("999");

        System.setOut(System.out);
        assertTrue(outContent.toString().contains("Invalid command"));
    }
}