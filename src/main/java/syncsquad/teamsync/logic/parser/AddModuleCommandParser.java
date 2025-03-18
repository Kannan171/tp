package syncsquad.teamsync.logic.parser;

import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import syncsquad.teamsync.commons.core.index.Index;
import syncsquad.teamsync.logic.commands.AddModuleCommand;
import syncsquad.teamsync.logic.parser.exceptions.ParseException;
import syncsquad.teamsync.model.schedule.Day;
import syncsquad.teamsync.model.schedule.Module;
import syncsquad.teamsync.model.schedule.ModuleCode;
import syncsquad.teamsync.model.schedule.Time;

/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class AddModuleCommandParser implements Parser<AddModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddModuleCommand
     * and returns a AddModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddModuleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE));
        }

        String[] parameters = trimmedArgs.split("\\s+");
        if (parameters.length != 5) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(parameters[0]);
        ModuleCode moduleCode = ParserUtil.parseModuleCode(parameters[1]);
        Day day = ParserUtil.parseDay(parameters[2]);
        Time startTime = ParserUtil.parseTime(parameters[3]);
        Time endTime = ParserUtil.parseTime(parameters[4]);
        return new AddModuleCommand(index, new Module(moduleCode, day, startTime, endTime));
    }
}
