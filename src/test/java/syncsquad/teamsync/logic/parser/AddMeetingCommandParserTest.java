package syncsquad.teamsync.logic.parser;

import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.DATE_DESC_SEP_MEETING;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.END_TIME_DESC_SEP_MEETING;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.START_TIME_DESC_SEP_MEETING;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_DATE_SEP_MEETING;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_END_TIME_SEP_MEETING;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_START_TIME_SEP_MEETING;
import static syncsquad.teamsync.logic.parser.CommandParserTestUtil.assertParseFailure;
import static syncsquad.teamsync.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static syncsquad.teamsync.testutil.TypicalAddressBook.SEP_MEETING;

import org.junit.jupiter.api.Test;

import syncsquad.teamsync.logic.commands.AddMeetingCommand;
import syncsquad.teamsync.model.meeting.Meeting;
import syncsquad.teamsync.testutil.MeetingBuilder;

public class AddMeetingCommandParserTest {
    private AddMeetingCommandParser parser = new AddMeetingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Meeting expectedMeeting = new MeetingBuilder(SEP_MEETING).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DATE_DESC_SEP_MEETING + START_TIME_DESC_SEP_MEETING
                + END_TIME_DESC_SEP_MEETING, new AddMeetingCommand(expectedMeeting));
    }

    @Test
    public void parse_repeatedFields_failure() {
        String validExpectedMeetingString = DATE_DESC_SEP_MEETING + START_TIME_DESC_SEP_MEETING
                + END_TIME_DESC_SEP_MEETING;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE);

        // multiple dates
        assertParseFailure(parser, DATE_DESC_SEP_MEETING + validExpectedMeetingString, expectedMessage);

        // extra times
        assertParseFailure(parser, validExpectedMeetingString + START_TIME_DESC_SEP_MEETING, expectedMessage);
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE);

        // missing date
        assertParseFailure(parser, VALID_START_TIME_SEP_MEETING + VALID_END_TIME_SEP_MEETING, expectedMessage);

        // missing either start or end time
        assertParseFailure(parser, VALID_DATE_SEP_MEETING + VALID_START_TIME_SEP_MEETING, expectedMessage);

        // missing both start and end time
        assertParseFailure(parser, VALID_DATE_SEP_MEETING, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC + START_TIME_DESC_SEP_MEETING + END_TIME_DESC_SEP_MEETING,
                ParserUtil.MESSAGE_INVALID_DATE_FORMAT);

        // invalid time
        assertParseFailure(parser, DATE_DESC_SEP_MEETING + INVALID_TIME_DESC + END_TIME_DESC_SEP_MEETING,
                ParserUtil.MESSAGE_INVALID_TIME_FORMAT);
    }
}
