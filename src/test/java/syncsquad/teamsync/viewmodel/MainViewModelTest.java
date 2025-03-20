package syncsquad.teamsync.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import syncsquad.teamsync.logic.Logic;
import syncsquad.teamsync.logic.commands.CommandResult;
import syncsquad.teamsync.logic.commands.exceptions.CommandException;
import syncsquad.teamsync.model.meeting.Meeting;
import syncsquad.teamsync.model.person.Person;


/**
 * Unit tests for {@link MainViewModel}.
 */
public class MainViewModelTest {
    private static final String COMMAND_TEXT = "test command";
    private static final String FEEDBACK_MESSAGE = "Command executed successfully";
    private static final CommandException COMMAND_EXCEPTION = new CommandException("Command execution failed");

    @Mock
    private Logic logic;

    private MainViewModel viewModel;
    private ObservableList<Person> personList;
    private ObservableList<Meeting> meetingList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up mock lists
        personList = FXCollections.observableArrayList();
        meetingList = FXCollections.observableArrayList();

        // Configure logic mock
        when(logic.getFilteredPersonList()).thenReturn(personList);
        when(logic.getMeetingList()).thenReturn(meetingList);

        // Create view model
        viewModel = new MainViewModel(logic);
    }

    @Test
    public void constructor_validLogic_viewModelsInitialized() {
        assertNotNull(viewModel.getCommandBoxViewModel(),
                "CommandBoxViewModel should be initialized");
        assertNotNull(viewModel.getResultDisplayViewModel(),
                "ResultDisplayViewModel should be initialized");
        assertNotNull(viewModel.getPersonListViewModel(),
                "PersonListViewModel should be initialized");
        assertNotNull(viewModel.getMeetingListViewModel(),
                "MeetingListViewModel should be initialized");
    }

    @Test
    public void executeCommand_commandException_displayMessage() throws Exception {
        when(logic.execute(COMMAND_TEXT)).thenThrow(COMMAND_EXCEPTION);

        viewModel.getCommandBoxViewModel().commandInputProperty().set(COMMAND_TEXT);
        assertThrows(CommandException.class, () -> viewModel.getCommandBoxViewModel().executeCommand());
        assertEquals(COMMAND_EXCEPTION.getMessage(),
                viewModel.getResultDisplayViewModel().feedbackToUserProperty().get());
    }

    @Test
    public void executeCommand_showHelp_setsHelpFlag() throws Exception {
        // Set up command that shows help
        CommandResult result = new CommandResult(FEEDBACK_MESSAGE, true, false);
        when(logic.execute(COMMAND_TEXT)).thenReturn(result);

        // Execute command
        viewModel.getCommandBoxViewModel().commandInputProperty().set(COMMAND_TEXT);
        viewModel.getCommandBoxViewModel().executeCommand();

        // Verify help flag is set
        assertTrue(viewModel.getIsShowingHelpProperty().get(),
                "Help flag should be set when command shows help");
    }

    @Test
    public void executeCommand_exit_setsExitFlag() throws Exception {
        // Set up command that exits
        CommandResult result = new CommandResult(FEEDBACK_MESSAGE, false, true);
        when(logic.execute(COMMAND_TEXT)).thenReturn(result);

        // Execute command
        viewModel.getCommandBoxViewModel().commandInputProperty().set(COMMAND_TEXT);
        viewModel.getCommandBoxViewModel().executeCommand();

        // Verify exit flag is set
        assertTrue(viewModel.getIsExiting().get(),
                "Exit flag should be set when command exits");
    }

    @Test
    public void isShowingHelp_initialState_isFalse() {
        assertFalse(viewModel.getIsShowingHelpProperty().get(),
                "Help flag should be false initially");
    }

    @Test
    public void isExiting_initialState_isFalse() {
        assertFalse(viewModel.getIsExiting().get(),
                "Exit flag should be false initially");
    }
}
