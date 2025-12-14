package command;

import service.DiscProcessingSystem;
import ui.ConsoleHelper;
import model.composition.MusicStyle;
import java.util.Scanner;

public class AddTrackToDiscCommand implements Command {
    private DiscProcessingSystem system;

    public AddTrackToDiscCommand(DiscProcessingSystem system) {
        this.system = system;
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.println("1. Add Song (with lyrics)");
        System.out.println("2. Add Instrumental Track");
        int choice = ConsoleHelper.readInt(scanner, "Choice: ");

        String title = ConsoleHelper.readString(scanner, "Title: ");
        String artist = ConsoleHelper.readString(scanner, "Artist: ");
        int duration = ConsoleHelper.readInt(scanner, "Duration (sec): ");
        MusicStyle style = ConsoleHelper.readStyle(scanner);

        if (choice == 1) {
            String lang = ConsoleHelper.readString(scanner, "Language: ");
            system.addSong(title, artist, duration, style, lang);
        } else {
            String instr = ConsoleHelper.readString(scanner, "Main Instrument: ");
            system.addInstrumental(title, artist, duration, style, instr);
        }
    }
}