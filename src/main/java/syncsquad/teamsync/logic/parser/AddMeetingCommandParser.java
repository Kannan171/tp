package syncsquad.teamsync.logic.parser;

import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import syncsquad.teamsync.logic.commands.AddMeetingCommand;
import syncsquad.teamsync.logic.parser.exceptions.ParseException;

public class AddMeetingCommandParser {

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

        return new AddMeetingCommand(ParserUtil.parseDate(date), ParserUtil.parseTime(startTime), ParserUtil.parseTime(endTime));
    }
}
