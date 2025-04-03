package syncsquad.teamsync.logic.commands.meeting;

import static java.util.Objects.requireNonNull;

import syncsquad.teamsync.commons.util.ToStringBuilder;
import syncsquad.teamsync.logic.commands.CommandResult;
import syncsquad.teamsync.model.Model;

/**
 * Lists all meetings in the address book to the user.
 */
public class ListMeetingsCommand extends MeetingCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all meetings";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String meetingsDisplay = String.join("\n", MESSAGE_SUCCESS, model.displayMeetingsString());
        return new CommandResult(meetingsDisplay);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // returns true if other is a ListMeetingsCommand object
        return other instanceof ListMeetingsCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
