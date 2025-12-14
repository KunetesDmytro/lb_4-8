package command;
import service.DiscProcessingSystem;
import ui.ConsoleHelper;
import java.util.Scanner;

public class CreateNewDiscCommand implements Command {
    private DiscProcessingSystem system;

    public CreateNewDiscCommand(DiscProcessingSystem system) {
        this.system = system;
    }

    @Override
    public void execute(Scanner scanner) {
        String name = ConsoleHelper.readString(scanner, "Enter new disc name: ");
        system.createNewDisc(name);
    }
}