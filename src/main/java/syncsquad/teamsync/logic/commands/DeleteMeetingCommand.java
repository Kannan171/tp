package syncsquad.teamsync.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import syncsquad.teamsync.commons.core.index.Index;
import syncsquad.teamsync.logic.Messages;
import syncsquad.teamsync.logic.commands.exceptions.CommandException;
import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.model.meeting.Meeting;

/**
 * Deletes a person identified using its displayed index from the address book.
 */
public class DeleteMeetingCommand extends Command {
    public static final String COMMAND_WORD = "delmeeting";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the meeting identified by the index number used in the displayed meeting list\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEETING_SUCCESS = "Deleted Meeting: %1$s";

    private final Index targetIndex;

    /**
     * Creates a DeleteMeetingCommand to delete the meeting at the specified {@code targetIndex}
     */
    public DeleteMeetingCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Meeting> lastShownList = model.getMeetingList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }

        Meeting meetingToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteMeeting(meetingToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MEETING_SUCCESS, meetingToDelete));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteMeetingCommand)) {
            return false;
        }

        // state check
        DeleteMeetingCommand otherDeleteMeetingCommand = (DeleteMeetingCommand) other;
        return targetIndex.equals(otherDeleteMeetingCommand.targetIndex);
    }

}
