package ui;

import model.composition.MusicStyle;
import java.util.Scanner;

public class ConsoleHelper {
    public static String readString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return val;
    }

    public static MusicStyle readStyle(Scanner scanner) {
        System.out.println("Styles: ROCK, POP, CLASSICAL, JAZZ, ELECTRONIC");
        System.out.print("Enter style: ");
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return MusicStyle.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.print("Invalid style. Try again: ");
            }
        }
    }
}