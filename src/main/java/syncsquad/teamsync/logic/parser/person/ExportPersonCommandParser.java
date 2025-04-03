package syncsquad.teamsync.logic.parser.person;

import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import syncsquad.teamsync.commons.core.index.Index;
import syncsquad.teamsync.logic.commands.person.ExportPersonCommand;
import syncsquad.teamsync.logic.parser.Parser;
import syncsquad.teamsync.logic.parser.ParserUtil;
import syncsquad.teamsync.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportPersonCommand object
 */
public class ExportPersonCommandParser implements Parser<ExportPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportPersonCommand
     * and returns an ExportPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportPersonCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ExportPersonCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT,
                ExportPersonCommand.MESSAGE_USAGE), pe);
        }
    }
}
