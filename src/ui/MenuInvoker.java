package ui;

import command.*;
import options.MenuOptions;
import service.DiscProcessingSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuInvoker {
    private final Map<String, Command> menuItems = new HashMap<>();
    private final DiscProcessingSystem system;
    private final Scanner scanner;

    public MenuInvoker(DiscProcessingSystem system) {
        this.system = system;
        this.scanner = new Scanner(System.in);
        initializeCommands();
    }

    private void initializeCommands() {
        menuItems.put(MenuOptions.VIEW_DISC.getCode(), new ViewSavedDiscsCommand(system));
        menuItems.put(MenuOptions.CREATE_DISC.getCode(), new CreateNewDiscCommand(system));
        menuItems.put(MenuOptions.ADD_TRACK.getCode(), new AddTrackToDiscCommand(system));
        menuItems.put(MenuOptions.SORT_STYLE.getCode(), new SortDiscCommand(system));
        menuItems.put(MenuOptions.SEARCH_DURATION.getCode(), new SearchTrackCommand(system));
        menuItems.put(MenuOptions.HELP.getCode(), new ShowShortGuideCommand());
        menuItems.put(MenuOptions.SAVE.getCode(), new SaveCurrentDiscCommand(system));
        menuItems.put(MenuOptions.DELETE_SAVED.getCode(), new DeleteSavedDiscsCommand(system));
        menuItems.put(MenuOptions.EXIT.getCode(), new StopProgramCommand(system));
    }

    public void showMenu() {
        System.out.println("\n--- MUSIC DISC MENU ---");
        for (MenuOptions option : MenuOptions.values()) {
            System.out.println(option.getCode() + ". " + option.getDescription());
        }
    }

    public void executeCommand(String key) {
        if (menuItems.containsKey(key)) {
            menuItems.get(key).execute(scanner);
        } else {
            System.out.println("Invalid command!");
        }
    }
}