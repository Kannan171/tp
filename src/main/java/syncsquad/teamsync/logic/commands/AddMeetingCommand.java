package syncsquad.teamsync.logic.commands;

import static java.util.Objects.requireNonNull;

import syncsquad.teamsync.logic.commands.exceptions.CommandException;
import syncsquad.teamsync.model.Model;

/**
 * Adds a meeting.
 */
public class AddMeetingCommand extends Command {

    public static final String COMMAND_WORD = "meeting";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$s";

    private final String date;

    public AddMeetingCommand(String date) {
        requireNonNull(date);
        this.date = date;
    }

    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, date));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddMeetingCommand)) {
            return false;
        }

        // state check
        AddMeetingCommand e = (AddMeetingCommand) other;
        return date.equals(e.date);
    }

}
