package syncsquad.teamsync.logic.parser;

import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_START_END_TIME;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.DAY_DESC_CS2103T_MODULE;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.END_TIME_DESC_CS2103T_MODULE;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.INDEX_DESC_CS2103T_MODULE;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC_CS2103T_MODULE;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.INVALID_INDEX_DESC_CS2103T_MODULE;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC_CS2103T_MODULE;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.INVALID_SAME_START_END_TIME_DESC_CS2103T_MODULE;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC_CS2103T_MODULE;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2103T_MODULE;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.START_TIME_DESC_CS2103T_MODULE;
import static syncsquad.teamsync.logic.parser.CommandParserTestUtil.assertParseFailure;
import static syncsquad.teamsync.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static syncsquad.teamsync.testutil.TypicalAddressBook.CS2103T_MODULE;

import org.junit.jupiter.api.Test;

import syncsquad.teamsync.commons.core.index.Index;
import syncsquad.teamsync.logic.commands.AddModuleCommand;
import syncsquad.teamsync.model.module.Module;
import syncsquad.teamsync.model.module.ModuleCode;
import syncsquad.teamsync.testutil.ModuleBuilder;

public class AddModuleCommandParserTest {
    private AddModuleCommandParser parser = (AddModuleCommandParser) new AddModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index index = Index.fromOneBased(1);
        Module expectedModule = new ModuleBuilder(CS2103T_MODULE).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + INDEX_DESC_CS2103T_MODULE + MODULE_CODE_DESC_CS2103T_MODULE
                        + DAY_DESC_CS2103T_MODULE
                        + START_TIME_DESC_CS2103T_MODULE + END_TIME_DESC_CS2103T_MODULE,
                new AddModuleCommand(index, expectedModule));
    }

    @Test
    public void parse_repeatedFields_failure() {
        String validExpectedMeetingString = INDEX_DESC_CS2103T_MODULE + MODULE_CODE_DESC_CS2103T_MODULE
                + DAY_DESC_CS2103T_MODULE
                + START_TIME_DESC_CS2103T_MODULE + END_TIME_DESC_CS2103T_MODULE;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE);

        // multiple module codes
        assertParseFailure(parser, MODULE_CODE_DESC_CS2103T_MODULE + validExpectedMeetingString, expectedMessage);

        // multiple days
        assertParseFailure(parser, DAY_DESC_CS2103T_MODULE + validExpectedMeetingString, expectedMessage);

        // extra times
        assertParseFailure(parser, validExpectedMeetingString + START_TIME_DESC_CS2103T_MODULE, expectedMessage);
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE);

        // missing fields
        assertParseFailure(parser, "", expectedMessage);

        // missing index
        assertParseFailure(parser,
                MODULE_CODE_DESC_CS2103T_MODULE + DAY_DESC_CS2103T_MODULE + START_TIME_DESC_CS2103T_MODULE
                        + END_TIME_DESC_CS2103T_MODULE,
                expectedMessage);

        // missing module code
        assertParseFailure(parser,
                INDEX_DESC_CS2103T_MODULE + DAY_DESC_CS2103T_MODULE + START_TIME_DESC_CS2103T_MODULE
                        + END_TIME_DESC_CS2103T_MODULE,
                expectedMessage);

        // missing date
        assertParseFailure(parser,
                INDEX_DESC_CS2103T_MODULE + MODULE_CODE_DESC_CS2103T_MODULE + START_TIME_DESC_CS2103T_MODULE
                        + END_TIME_DESC_CS2103T_MODULE,
                expectedMessage);

        // missing either start or end time
        assertParseFailure(parser,
                INDEX_DESC_CS2103T_MODULE + MODULE_CODE_DESC_CS2103T_MODULE + DAY_DESC_CS2103T_MODULE
                        + START_TIME_DESC_CS2103T_MODULE,
                expectedMessage);
        assertParseFailure(parser,
                INDEX_DESC_CS2103T_MODULE + MODULE_CODE_DESC_CS2103T_MODULE + DAY_DESC_CS2103T_MODULE
                        + END_TIME_DESC_CS2103T_MODULE,
                expectedMessage);

        // missing both start and end time
        assertParseFailure(parser,
                INDEX_DESC_CS2103T_MODULE + MODULE_CODE_DESC_CS2103T_MODULE + DAY_DESC_CS2103T_MODULE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid index
        assertParseFailure(parser,
                INVALID_INDEX_DESC_CS2103T_MODULE + MODULE_CODE_DESC_CS2103T_MODULE + DAY_DESC_CS2103T_MODULE
                        + START_TIME_DESC_CS2103T_MODULE + END_TIME_DESC_CS2103T_MODULE,
                ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid module code
        assertParseFailure(parser,
                INDEX_DESC_CS2103T_MODULE + INVALID_MODULE_CODE_DESC_CS2103T_MODULE + DAY_DESC_CS2103T_MODULE
                        + START_TIME_DESC_CS2103T_MODULE + END_TIME_DESC_CS2103T_MODULE,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser,
                INDEX_DESC_CS2103T_MODULE + MODULE_CODE_DESC_CS2103T_MODULE + DAY_DESC_CS2103T_MODULE
                        + INVALID_TIME_DESC
                        + END_TIME_DESC_CS2103T_MODULE,
                ParserUtil.MESSAGE_INVALID_TIME_FORMAT);
    }

    @Test
    public void parse_invalidTimes_failure() {
        // start time is equal to end time
        assertParseFailure(parser,
                INDEX_DESC_CS2103T_MODULE + MODULE_CODE_DESC_CS2103T_MODULE + DAY_DESC_CS2103T_MODULE
                        + INVALID_SAME_START_END_TIME_DESC_CS2103T_MODULE
                        + INVALID_SAME_START_END_TIME_DESC_CS2103T_MODULE,
                MESSAGE_INVALID_START_END_TIME);

        // start time is after end time
        assertParseFailure(parser,
                INDEX_DESC_CS2103T_MODULE + MODULE_CODE_DESC_CS2103T_MODULE + DAY_DESC_CS2103T_MODULE
                        + INVALID_START_TIME_DESC_CS2103T_MODULE
                        + INVALID_END_TIME_DESC_CS2103T_MODULE,
                MESSAGE_INVALID_START_END_TIME);
    }
}
