package options;

public enum MenuOptions {
    VIEW_DISC("1", "View current disc"),
    CREATE_DISC("2", "Create new disc"),
    ADD_TRACK("3", "Add track"),
    SORT_STYLE("4", "Sort by style"),
    SEARCH_DURATION("5", "Search by duration"),
    SAVE("6", "Save to file"),
    DELETE_SAVED("7", "Delete saved file"),
    HELP("8", "Show help"),
    EXIT("0", "Exit");
    private final String code;
    private final String description;

    MenuOptions(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() { return code; }
    public String getDescription() { return description; }
}