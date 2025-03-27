package syncsquad.teamsync.logic.commands;

import static syncsquad.teamsync.logic.commands.CommandTestUtil.assertCommandSuccess;
import static syncsquad.teamsync.testutil.TypicalAddressBook.getTypicalAddressBook;
import static syncsquad.teamsync.testutil.TypicalAddressBook.getTypicalMeetings;

import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.model.ModelManager;
import syncsquad.teamsync.model.UserPrefs;
import syncsquad.teamsync.model.meeting.Meeting;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListMeetingsCommandTest {

    private static final String EXPECTED_MESSAGE = ListMeetingsCommand.MESSAGE_SUCCESS + "\n"
            + getTypicalMeetings()
            .stream()
            .map(Meeting::toString)
            .collect(Collectors.joining("\n")) + "\n";
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_showsAllMeetings() {
        assertCommandSuccess(new ListMeetingsCommand(), model, EXPECTED_MESSAGE, model);
    }
}
