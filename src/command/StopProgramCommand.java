package command;

import service.DiscProcessingSystem;
import java.util.Scanner;

public class StopProgramCommand implements Command {
    private DiscProcessingSystem system;

    public StopProgramCommand(DiscProcessingSystem system) {
        this.system = system;
    }

    @Override
    public void execute(Scanner scanner) {
        system.stopProgram();
    }
}