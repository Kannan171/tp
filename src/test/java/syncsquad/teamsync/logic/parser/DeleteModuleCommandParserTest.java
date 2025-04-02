package syncsquad.teamsync.logic.parser;

import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static syncsquad.teamsync.logic.parser.CommandParserTestUtil.assertParseFailure;
import static syncsquad.teamsync.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import syncsquad.teamsync.commons.core.index.Index;
import syncsquad.teamsync.logic.commands.DeleteModuleCommand;
import syncsquad.teamsync.model.module.ModuleCode;

/**
 * As we are only doing white-box testing, our test cases do not cover path
 * variations
 * outside of the DeleteModuleCommand code. For example, inputs "1" and "1 abc"
 * take the
 * same path through the DeleteModuleCommand, and therefore we test only one of
 * them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteModuleCommandParserTest {

    private DeleteModuleCommandParser parser = new DeleteModuleCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteMeetingCommand() {
        Index index = Index.fromOneBased(1);
        ModuleCode moduleCode = new ModuleCode("CS2103T");
        assertParseSuccess(parser, "1 CS2103T", new DeleteModuleCommand(index, moduleCode));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "ab cs",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteModuleCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 cs",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteModuleCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "ab CS2103T",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteModuleCommand.MESSAGE_USAGE));
    }
}
