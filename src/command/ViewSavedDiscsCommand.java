package command;
import service.DiscProcessingSystem;
import java.util.Scanner;

public class ViewSavedDiscsCommand implements Command {
    private DiscProcessingSystem system;

    public ViewSavedDiscsCommand(DiscProcessingSystem system) {
        this.system = system;
    }

    @Override
    public void execute(Scanner scanner) {
        system.viewCurrentDisc();
    }
}