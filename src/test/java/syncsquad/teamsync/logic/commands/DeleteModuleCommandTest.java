package syncsquad.teamsync.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.commons.util.CollectionUtil.requireAllNonNull;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.assertCommandFailure;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import syncsquad.teamsync.commons.core.index.Index;
import syncsquad.teamsync.logic.Messages;
import syncsquad.teamsync.logic.commands.module.DeleteModuleCommand;
import syncsquad.teamsync.model.AddressBook;
import syncsquad.teamsync.model.module.ModuleCode;
import syncsquad.teamsync.model.person.Person;
import syncsquad.teamsync.model.person.UniquePersonList;
import syncsquad.teamsync.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteModuleCommand}.
 */
public class DeleteModuleCommandTest {

    @Test
    public void execute_validIndexList_success() throws Exception {
        Person person = new PersonBuilder().withModules("CS2103T FRI 14:00 16:00").build();
        Person personWithoutModule = new PersonBuilder().build();
        Index index = Index.fromOneBased(1);
        ModuleCode moduleCode = new ModuleCode("CS2103T");
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(index, moduleCode);

        String expectedMessage = String.format(DeleteModuleCommand.MESSAGE_DELETE_MODULE_SUCCESS,
                Messages.format(personWithoutModule));

        ModelStub modelStub = new ModelStubWithPerson(person);
        CommandResult commandResult = deleteModuleCommand.execute(modelStub);

        assertEquals(commandResult.getFeedbackToUser(), expectedMessage);
    }

    @Test
    public void execute_invalidIndexList_throwsCommandException() {
        Person person = new PersonBuilder().withModules("CS2103T FRI 14:00 16:00").build();
        Index outOfBoundIndex = Index.fromOneBased(2);
        ModuleCode moduleCode = new ModuleCode("CS2103T");
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(outOfBoundIndex, moduleCode);

        ModelStub modelStub = new ModelStubWithPerson(person);

        assertCommandFailure(deleteModuleCommand, modelStub, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        Person person = new PersonBuilder().withModules("CS2103T FRI 14:00 16:00").build();
        Index index = Index.fromOneBased(1);
        ModuleCode invalidModuleCode = new ModuleCode("CS2101");
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(index, invalidModuleCode);

        ModelStub modelStub = new ModelStubWithPerson(person);

        assertCommandFailure(deleteModuleCommand, modelStub, Messages.MESSAGE_INVALID_MODULE);
    }

    @Test
    public void equals() {
        Index index = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);
        ModuleCode moduleCode = new ModuleCode("CS2103T");
        ModuleCode moduleCode2 = new ModuleCode("CS2101");
        DeleteModuleCommand deleteFirstCommand = new DeleteModuleCommand(index, moduleCode);
        DeleteModuleCommand deleteSecondCommand = new DeleteModuleCommand(index, moduleCode2);
        DeleteModuleCommand deleteThirdCommand = new DeleteModuleCommand(index2, moduleCode2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteModuleCommand deleteFirstCommandCopy = new DeleteModuleCommand(index, moduleCode);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different module code and index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different module code and index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteThirdCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ModuleCode moduleCode = new ModuleCode("CS2103T");
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(targetIndex, moduleCode);
        String expected = DeleteModuleCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + ", moduleCode="
                + moduleCode + "}";
        assertEquals(expected, deleteModuleCommand.toString());
    }

    /**
     * A Model stub that contains a single Person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final UniquePersonList persons = new UniquePersonList();
        private final AddressBook addressBook = new AddressBook();

        ModelStubWithPerson(Person person) {
            persons.add(person);
        }

        @Override
        public AddressBook getAddressBook() {
            return addressBook;
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            requireAllNonNull(target, editedPerson);
            persons.setItem(target, editedPerson);
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            return;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return persons.asUnmodifiableObservableList();
        }
    }
}
