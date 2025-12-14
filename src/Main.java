import ui.MenuInvoker;
import service.DiscProcessingSystem;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DiscProcessingSystem system = new DiscProcessingSystem("music_storage.txt");

        MenuInvoker menuInvoker = new MenuInvoker(system);
        Scanner input = new Scanner(System.in);

        while (true) {
            menuInvoker.showMenu();
            System.out.print("\nYour choice: ");
            String choice = input.nextLine().trim();
            menuInvoker.executeCommand(choice);
        }
    }
}