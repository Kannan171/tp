package syncsquad.teamsync.logic.parser;

import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import syncsquad.teamsync.logic.commands.AddMeetingCommand;
import syncsquad.teamsync.logic.parser.exceptions.ParseException;
import syncsquad.teamsync.model.meeting.Meeting;

/**
 * Parses input arguments and creates a new AddMeetingCommand object
 */
public class AddMeetingCommandParser implements Parser<AddMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMeetingCommand
     * and returns an AddMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddMeetingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }

        String[] dateTimeSplit = trimmedArgs.split("\\s+");

        if (dateTimeSplit.length != 3) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }

        String date = dateTimeSplit[0];
        String startTime = dateTimeSplit[1];
        String endTime = dateTimeSplit[2];

        Meeting meeting = new Meeting(ParserUtil.parseDate(date), ParserUtil.parseTime(startTime),
                ParserUtil.parseTime(endTime));

        return new AddMeetingCommand(meeting);
    }
}
