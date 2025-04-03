package syncsquad.teamsync.logic.parser;

import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;

import syncsquad.teamsync.logic.commands.ShowDateCommand;
import syncsquad.teamsync.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShowDateCommand object
 */
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
        LocalDate date = ParserUtil.parseDate(trimmedArgs);

        return new ShowDateCommand(date);
    }
}
