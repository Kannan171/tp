package syncsquad.teamsync.logic.parser;

import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import syncsquad.teamsync.commons.core.index.Index;
import syncsquad.teamsync.logic.commands.DeleteModuleCommand;
import syncsquad.teamsync.logic.parser.exceptions.ParseException;
import syncsquad.teamsync.model.schedule.ModuleCode;

/**
 * Parses input arguments and creates a new DeleteModuleCommand object
 */
public class DeleteModuleCommandParser implements Parser<DeleteModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteModuleCommand
     * and returns a DeleteModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteModuleCommand parse(String args) throws ParseException {
        try {
            args = args.trim();
            String[] params = args.split(" ");
            System.out.println(params[0]);
            System.out.println(params[1]);
            Index index = ParserUtil.parseIndex(params[0]);
            ModuleCode moduleCode = ParserUtil.parseModuleCode(params[1]);
            return new DeleteModuleCommand(index, moduleCode);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteModuleCommand.MESSAGE_USAGE), pe);
        }
    }

}
