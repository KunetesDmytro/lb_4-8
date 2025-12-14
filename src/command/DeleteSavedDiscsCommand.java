package command;

import service.DiscProcessingSystem;
import java.util.Scanner;

public class DeleteSavedDiscsCommand implements Command {
    private DiscProcessingSystem system;

    public DeleteSavedDiscsCommand(DiscProcessingSystem system) {
        this.system = system;
    }

    @Override
    public void execute(Scanner scanner) {
        // Викликаємо метод сервісу для видалення
        system.deleteSavedData();
    }
}