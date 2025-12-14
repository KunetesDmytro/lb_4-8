import ui.MenuInvoker;
import service.DiscProcessingSystem;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Ініціалізація системи з файлом збереження "my_disc.txt"
        DiscProcessingSystem system = new DiscProcessingSystem("my_disc.txt");

        system.loadDisc();

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