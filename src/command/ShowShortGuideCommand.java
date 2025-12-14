package command;

import java.util.Scanner;

public class ShowShortGuideCommand implements Command {

    @Override
    public void execute(Scanner scanner) {
        System.out.println("\n--- Short Guide ---");
        System.out.println("This program manages music discs.");
        System.out.println("1. Create a disc or load it from file.");
        System.out.println("2. Add tracks (Songs or Instrumental).");
        System.out.println("3. Use Sort/Search to manage tracks.");
        System.out.println("4. SAVE your changes before exiting!");
        System.out.println("-------------------\n");
    }
}