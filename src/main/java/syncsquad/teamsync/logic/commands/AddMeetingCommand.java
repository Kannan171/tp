package syncsquad.teamsync.logic.commands;

import static java.util.Objects.requireNonNull;
import static syncsquad.teamsync.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;

import syncsquad.teamsync.logic.commands.exceptions.CommandException;
import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.model.schedule.Meeting;

/**
 * Adds a meeting.
 */
public class AddMeetingCommand extends Command {

    public static final String COMMAND_WORD = "meeting";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a meeting with the specified date, start and end time"
            + "Parameters: DATE START_TIME END_TIME\n"
            + "Example: " + COMMAND_WORD + " 15-11-2025 11:00 15:00";

    public static final String MESSAGE_ADD_MEETING_SUCCESS = "Meeting added with the following details: %1$s";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists in the address book";

    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public AddMeetingCommand(LocalDate date, LocalTime startTime, LocalTime endTime) {
        requireAllNonNull(date, startTime, endTime);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Meeting meeting = createMeeting(date, startTime, endTime);

        if (model.hasMeeting(meeting)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        }

        model.addMeeting(meeting);
        return new CommandResult(String.format(MESSAGE_ADD_MEETING_SUCCESS, meeting));
    }

    public Meeting createMeeting(LocalDate date, LocalTime startTime, LocalTime endTime) {
        assert date != null;
        assert startTime != null;
        assert endTime != null;

        return new Meeting(date, startTime, endTime);
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
