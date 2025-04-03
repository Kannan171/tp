package syncsquad.teamsync.logic.parser.person;

import static java.util.Objects.requireNonNull;
import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static syncsquad.teamsync.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import syncsquad.teamsync.commons.core.LogsCenter;
import syncsquad.teamsync.logic.commands.HelpCommand;
import syncsquad.teamsync.logic.commands.person.AddPersonCommand;
import syncsquad.teamsync.logic.commands.person.DeletePersonCommand;
import syncsquad.teamsync.logic.commands.person.EditCommand;
import syncsquad.teamsync.logic.commands.person.ExportPersonCommand;
import syncsquad.teamsync.logic.commands.person.FindCommand;
import syncsquad.teamsync.logic.commands.person.ListCommand;
import syncsquad.teamsync.logic.commands.person.PersonCommand;
import syncsquad.teamsync.logic.parser.Parser;
import syncsquad.teamsync.logic.parser.exceptions.ParseException;

/**
 * Parses user input for person related commands.
 */
public final class PersonCommandsParser implements Parser<PersonCommand> {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(PersonCommandsParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the PersonCommand
     * and returns a PersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PersonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.stripLeading();

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args);
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case AddPersonCommand.COMMAND_WORD:
            return new AddPersonCommandParser().parse(arguments);
        case DeletePersonCommand.COMMAND_WORD:
            return new DeletePersonCommandParser().parse(arguments);
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);
        case ExportPersonCommand.COMMAND_WORD:
            return new ExportPersonCommandParser().parse(arguments);
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        default:
            logger.finer("This user input caused a ParseException: " + args);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
