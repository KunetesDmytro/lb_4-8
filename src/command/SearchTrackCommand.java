package command;
import service.DiscProcessingSystem;
import ui.ConsoleHelper;
import java.util.Scanner;

public class SearchTrackCommand implements Command {
    private DiscProcessingSystem system;
    public SearchTrackCommand(DiscProcessingSystem system) { this.system = system; }
    @Override
    public void execute(Scanner scanner) {
        int min = ConsoleHelper.readInt(scanner, "Min duration (sec): ");
        int max = ConsoleHelper.readInt(scanner, "Max duration (sec): ");
        system.findTracksByDuration(min, max);
    }
}