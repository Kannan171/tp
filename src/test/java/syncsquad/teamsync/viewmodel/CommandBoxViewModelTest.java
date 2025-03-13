package syncsquad.teamsync.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.StringProperty;
import syncsquad.teamsync.logic.commands.exceptions.CommandException;
import syncsquad.teamsync.logic.parser.exceptions.ParseException;


class CommandBoxViewModelTest {

    private CommandBoxViewModel.CommandExecutor mockExecutor;
    private CommandBoxViewModel viewModel;

    @BeforeEach
    void setUp() {
        mockExecutor = mock();
        viewModel = new CommandBoxViewModel(mockExecutor);
    }

    @Test
    void testInitialCommandInputIsEmpty() {
        assertEquals("", viewModel.commandInputProperty().get());
    }

    @Test
    void testCommandInputPropertyBinding() {
        StringProperty commandInput = viewModel.commandInputProperty();
        commandInput.set("test command");
        assertEquals("test command", viewModel.commandInputProperty().get());
    }

    @Test
    void testExecuteCommand_callsCommandExecutor() throws ParseException, CommandException {
        String testCommand = "sample command";
        viewModel.commandInputProperty().set(testCommand);

        viewModel.executeCommand();

        verify(mockExecutor, times(1)).execute(testCommand);
        assertEquals("", viewModel.commandInputProperty().get());
    }

    @Test
    void testExecuteCommand_emptyInput_doesNotCallExecutor() throws ParseException, CommandException {
        viewModel.commandInputProperty().set("");

        viewModel.executeCommand();

        verify(mockExecutor, never()).execute(anyString());
        assertEquals("", viewModel.commandInputProperty().get());
    }

    @Test
    void testExecuteCommand_whitespaceOnlyInput_doesNotCallExecutor() throws ParseException, CommandException {
        viewModel.commandInputProperty().set("   ");

        viewModel.executeCommand();

        verify(mockExecutor, never()).execute(anyString());
        assertEquals("", viewModel.commandInputProperty().get());
    }

    @Test
    void testExecuteCommand_propagatesCommandException() throws ParseException, CommandException {
        String testCommand = "invalid command";
        doThrow(new CommandException("Execution failed")).when(mockExecutor).execute(testCommand);

        viewModel.commandInputProperty().set(testCommand);

        CommandException thrown = assertThrows(CommandException.class, viewModel::executeCommand);
        assertEquals("Execution failed", thrown.getMessage());
    }

    @Test
    void testExecuteCommand_propagatesParseException() throws ParseException, CommandException {
        String testCommand = "invalid syntax";
        doThrow(new ParseException("Parse error")).when(mockExecutor).execute(testCommand);

        viewModel.commandInputProperty().set(testCommand);

        ParseException thrown = assertThrows(ParseException.class, viewModel::executeCommand);
        assertEquals("Parse error", thrown.getMessage(), "Exception message should match");
    }
}
