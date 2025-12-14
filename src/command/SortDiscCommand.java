package command;
import service.DiscProcessingSystem;
import java.util.Scanner;

public class SortDiscCommand implements Command {
    private DiscProcessingSystem system;
    public SortDiscCommand(DiscProcessingSystem system) { this.system = system; }
    @Override
    public void execute(Scanner scanner) { system.sortTracksByStyle(); }
}