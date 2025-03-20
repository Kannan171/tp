package syncsquad.teamsync.logic.commands;

import static java.util.Objects.requireNonNull;

import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.model.meeting.Meeting;

/**
 * Lists all meetings in the address book to the user.
 */
public class ListMeetingsCommand extends Command {

    public static final String COMMAND_WORD = "listmeetings";

    public static final String MESSAGE_SUCCESS = "Listed all meetings";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String meetings = "";
        for (Meeting item : model.getMeetingList()) {
            meetings += item.toString() + "\n";
        }
        return new CommandResult(MESSAGE_SUCCESS + "\n" + meetings);
    }
}
