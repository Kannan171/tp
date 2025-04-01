package syncsquad.teamsync.logic.commands;

import static java.util.Objects.requireNonNull;

import syncsquad.teamsync.model.Model;

/**
 * Lists all meetings in the address book to the user.
 */
public class ListMeetingsCommand extends Command {

    public static final String COMMAND_WORD = "listmeetings";

    public static final String MESSAGE_SUCCESS = "Listed all meetings";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String meetingsDisplay = String.join("\n", MESSAGE_SUCCESS, model.displayMeetingsString());
        return new CommandResult(meetingsDisplay);
    }
}
