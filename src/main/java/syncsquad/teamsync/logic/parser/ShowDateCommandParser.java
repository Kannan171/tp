package syncsquad.teamsync.logic.parser;

import syncsquad.teamsync.logic.commands.AddMeetingCommand;
import syncsquad.teamsync.logic.commands.ShowDateCommand;
import syncsquad.teamsync.logic.parser.exceptions.ParseException;
import syncsquad.teamsync.model.meeting.Meeting;

import java.time.LocalDate;
import java.time.LocalTime;

import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_START_END_TIME;

public class ShowDateCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ShowDateCommand
     * and returns an ShowDateCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ShowDateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowDateCommand.MESSAGE_USAGE));
        }
        LocalDate date = ParserUtil.parseDate(args);

        return new ShowDateCommand(date);
    }
}
