package syncsquad.teamsync.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.assertCommandFailure;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.assertCommandSuccess;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.showPersonAtIndex;
import static syncsquad.teamsync.testutil.TypicalAddressBook.getTypicalAddressBook;
import static syncsquad.teamsync.testutil.TypicalIndexes.INDEX_FIRST;
import static syncsquad.teamsync.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import syncsquad.teamsync.commons.core.index.Index;
import syncsquad.teamsync.logic.Messages;
import syncsquad.teamsync.logic.commands.exceptions.CommandException;
import syncsquad.teamsync.logic.commands.person.AddPersonCommand;
import syncsquad.teamsync.logic.commands.person.ExportPersonCommand;
import syncsquad.teamsync.logic.parser.AddressBookParser;
import syncsquad.teamsync.logic.parser.exceptions.ParseException;
import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.model.ModelManager;
import syncsquad.teamsync.model.UserPrefs;
import syncsquad.teamsync.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class ExportPersonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToExport = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        ExportPersonCommand exportPersonCommand = new ExportPersonCommand(INDEX_FIRST);

        String expectedMessage = String.format(ExportPersonCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
                personToExport.toExportString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(exportPersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ExportPersonCommand exportPersonCommand = new ExportPersonCommand(outOfBoundIndex);

        assertCommandFailure(exportPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Person personToExport = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        ExportPersonCommand exportPersonCommand = new ExportPersonCommand(INDEX_FIRST);

        String expectedMessage = String.format(ExportPersonCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
                personToExport.toExportString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST);

        assertCommandSuccess(exportPersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ExportPersonCommand exportPersonCommand = new ExportPersonCommand(outOfBoundIndex);

        assertCommandFailure(exportPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_exportedPersonAddable() throws CommandException, ParseException {
        Person personToExport = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        ExportPersonCommand exportPersonCommand = new ExportPersonCommand(INDEX_FIRST);
        CommandResult commandResult = exportPersonCommand.execute(model);

        // Delete the person from the model
        model.deletePerson(personToExport);

        // Get the import string from the command result
        String importString = commandResult.getFeedbackToUser().split("\n")[1];
        AddPersonCommand addPersonCommand = (AddPersonCommand) parser.parseCommand(importString);

        String expectedMessage = String.format(AddPersonCommand.MESSAGE_SUCCESS, Messages.format(personToExport));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(personToExport);

        assertCommandSuccess(addPersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ExportPersonCommand exportFirstCommand = new ExportPersonCommand(INDEX_FIRST);
        ExportPersonCommand exportSecondCommand = new ExportPersonCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(exportFirstCommand.equals(exportFirstCommand));

        // same values -> returns true
        ExportPersonCommand exportFirstCommandCopy = new ExportPersonCommand(INDEX_FIRST);
        assertTrue(exportFirstCommand.equals(exportFirstCommandCopy));

        // different types -> returns false
        assertFalse(exportFirstCommand.equals(1));

        // null -> returns false
        assertFalse(exportFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(exportFirstCommand.equals(exportSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ExportPersonCommand exportPersonCommand = new ExportPersonCommand(targetIndex);
        String expected = ExportPersonCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, exportPersonCommand.toString());
    }
}
