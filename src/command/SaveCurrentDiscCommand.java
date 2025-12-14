package command;
import service.DiscProcessingSystem;
import java.util.Scanner;

public class SaveCurrentDiscCommand implements Command {
    private DiscProcessingSystem system;
    public SaveCurrentDiscCommand(DiscProcessingSystem system) { this.system = system; }
    @Override
    public void execute(Scanner scanner) { system.saveCurrentDisc(); }
}