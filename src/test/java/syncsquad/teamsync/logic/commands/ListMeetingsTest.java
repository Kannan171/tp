package syncsquad.teamsync.logic.commands;

import static syncsquad.teamsync.logic.commands.CommandTestUtil.assertCommandSuccess;
import static syncsquad.teamsync.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.model.ModelManager;
import syncsquad.teamsync.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListMeetingsTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_showsAllMeetings() {
        assertCommandSuccess(new ListMeetingsCommand(), model, ListMeetingsCommand.MESSAGE_SUCCESS + "\n", model);
    }
}
