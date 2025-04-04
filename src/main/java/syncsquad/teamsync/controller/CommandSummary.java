package syncsquad.teamsync.controller;

/**
 * Represents a command summary entry for the help dialog.
 */
public class CommandSummary {
    private final String category;
    private final String action;
    private final String format;
    private final String example;

    /**
     * Constructs a CommandSummary object with the specified parameters.
     *
     * @param category The category of the command.
     * @param action   The action of the command.
     * @param format   The format of the command.
     * @param example  An example of the command.
     */
    public CommandSummary(String category, String action, String format, String example) {
        this.category = category;
        this.action = action;
        this.format = format;
        this.example = example;
    }

    public String getCategory() {
        return category;
    }

    public String getAction() {
        return action;
    }

    public String getFormat() {
        return format;
    }

    public String getExample() {
        return example;
    }
}
