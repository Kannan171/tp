package syncsquad.teamsync.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static syncsquad.teamsync.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static syncsquad.teamsync.testutil.Assert.assertThrows;
import static syncsquad.teamsync.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import syncsquad.teamsync.logic.commands.ClearCommand;
import syncsquad.teamsync.logic.commands.CommandTestUtil;
import syncsquad.teamsync.logic.commands.ExitCommand;
import syncsquad.teamsync.logic.commands.HelpCommand;
import syncsquad.teamsync.logic.commands.meeting.AddMeetingCommand;
import syncsquad.teamsync.logic.commands.meeting.DeleteMeetingCommand;
import syncsquad.teamsync.logic.commands.meeting.ListMeetingsCommand;
import syncsquad.teamsync.logic.commands.meeting.MeetingCommand;
import syncsquad.teamsync.logic.commands.module.AddModuleCommand;
import syncsquad.teamsync.logic.commands.module.DeleteModuleCommand;
import syncsquad.teamsync.logic.commands.module.ModuleCommand;
import syncsquad.teamsync.logic.commands.person.AddPersonCommand;
import syncsquad.teamsync.logic.commands.person.DeletePersonCommand;
import syncsquad.teamsync.logic.commands.person.EditCommand;
import syncsquad.teamsync.logic.commands.person.EditCommand.EditPersonDescriptor;
import syncsquad.teamsync.logic.commands.person.FindCommand;
import syncsquad.teamsync.logic.commands.person.ListCommand;
import syncsquad.teamsync.logic.commands.person.PersonCommand;
import syncsquad.teamsync.logic.parser.exceptions.ParseException;
import syncsquad.teamsync.model.person.NameContainsKeywordsPredicate;
import syncsquad.teamsync.model.person.Person;
import syncsquad.teamsync.testutil.EditPersonDescriptorBuilder;
import syncsquad.teamsync.testutil.PersonBuilder;
import syncsquad.teamsync.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddPersonCommand command = (AddPersonCommand) parser.parseCommand(
            PersonUtil.getAddCommand(person)
        );
        assertEquals(new AddPersonCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(
            PersonCommand.COMMAND_GROUP_WORD + " "
                + DeletePersonCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeletePersonCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(
            PersonCommand.COMMAND_GROUP_WORD + " "
                + EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            PersonCommand.COMMAND_GROUP_WORD + " "
                + FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(PersonCommand.COMMAND_GROUP_WORD + " "
                + ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(PersonCommand.COMMAND_GROUP_WORD + " "
                + ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_addMeeting() throws Exception {
        assertTrue(parser.parseCommand(MeetingCommand.COMMAND_GROUP_WORD + " "
                + AddMeetingCommand.COMMAND_WORD + CommandTestUtil.DATE_DESC_SEP_MEETING
                + CommandTestUtil.START_TIME_DESC_SEP_MEETING
                + CommandTestUtil.END_TIME_DESC_SEP_MEETING) instanceof AddMeetingCommand);
    }

    @Test
    public void parseCommand_deleteMeeting() throws Exception {
        assertTrue(parser.parseCommand(MeetingCommand.COMMAND_GROUP_WORD + " "
                + DeleteMeetingCommand.COMMAND_WORD + " 3") instanceof DeleteMeetingCommand);
    }

    @Test
    public void parseCommand_listMeetings() throws Exception {
        assertTrue(parser.parseCommand(MeetingCommand.COMMAND_GROUP_WORD + " "
                + ListMeetingsCommand.COMMAND_WORD) instanceof ListMeetingsCommand);
        assertTrue(parser.parseCommand(MeetingCommand.COMMAND_GROUP_WORD + " "
                + ListMeetingsCommand.COMMAND_WORD + " 3") instanceof ListMeetingsCommand);
    }

    @Test
    public void parseCommand_addModule() throws Exception {
        assertTrue(parser.parseCommand(ModuleCommand.COMMAND_GROUP_WORD + " "
                + AddModuleCommand.COMMAND_WORD + CommandTestUtil.INDEX_DESC_CS2103T_MODULE
                + CommandTestUtil.MODULE_CODE_DESC_CS2103T_MODULE + CommandTestUtil.DAY_DESC_CS2103T_MODULE
                + CommandTestUtil.START_TIME_DESC_CS2103T_MODULE
                + CommandTestUtil.END_TIME_DESC_CS2103T_MODULE) instanceof AddModuleCommand);
    }

    @Test
    public void parseCommand_deleteModule() throws Exception {
        assertTrue(parser.parseCommand(ModuleCommand.COMMAND_GROUP_WORD + " "
                + DeleteModuleCommand.COMMAND_WORD + " 3"
                + CommandTestUtil.MODULE_CODE_DESC_CS2103T_MODULE) instanceof DeleteModuleCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
