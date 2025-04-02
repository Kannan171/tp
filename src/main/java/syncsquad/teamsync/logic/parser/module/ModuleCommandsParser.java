package syncsquad.teamsync.logic.parser.module;

import static java.util.Objects.requireNonNull;
import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static syncsquad.teamsync.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import syncsquad.teamsync.commons.core.LogsCenter;
import syncsquad.teamsync.logic.commands.HelpCommand;
import syncsquad.teamsync.logic.commands.module.AddModuleCommand;
import syncsquad.teamsync.logic.commands.module.DeleteModuleCommand;
import syncsquad.teamsync.logic.commands.module.ModuleCommand;
import syncsquad.teamsync.logic.parser.Parser;
import syncsquad.teamsync.logic.parser.exceptions.ParseException;

/**
 * Parses user input for module related commands.
 */
public final class ModuleCommandsParser implements Parser<ModuleCommand> {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(ModuleCommandsParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the MeetingCommand
     * and returns a MeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ModuleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.stripLeading();

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args);
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case AddModuleCommand.COMMAND_WORD:
            return new AddModuleCommandParser().parse(arguments);
        case DeleteModuleCommand.COMMAND_WORD:
            return new DeleteModuleCommandParser().parse(arguments);
        default:
            logger.finer("This user input caused a ParseException: " + args);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
