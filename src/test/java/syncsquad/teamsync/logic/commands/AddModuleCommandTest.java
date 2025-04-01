package syncsquad.teamsync.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.commons.util.CollectionUtil.requireAllNonNull;
import static syncsquad.teamsync.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import syncsquad.teamsync.commons.core.GuiSettings;
import syncsquad.teamsync.commons.core.index.Index;
import syncsquad.teamsync.logic.Messages;
import syncsquad.teamsync.logic.commands.exceptions.CommandException;
import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.model.ReadOnlyAddressBook;
import syncsquad.teamsync.model.ReadOnlyUserPrefs;
import syncsquad.teamsync.model.meeting.Meeting;
import syncsquad.teamsync.model.module.Module;
import syncsquad.teamsync.model.person.Person;
import syncsquad.teamsync.model.person.UniquePersonList;
import syncsquad.teamsync.testutil.ModuleBuilder;
import syncsquad.teamsync.testutil.PersonBuilder;

public class AddModuleCommandTest {

    /*
     * Tests for null index and module arguments.
     */
    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(null, null));
    }

    /*
     * Tests for a successful attempt to add a module.
     */
    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {

        Module validModule = new ModuleBuilder().withModuleCode("CS2103T")
                .withDay("FRI").withStartTime("14:00")
                .withEndTime("16:00").build();
        Person dummyPerson = new PersonBuilder().build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(dummyPerson);
        CommandResult commandResult = new AddModuleCommand(Index.fromOneBased(1), validModule).execute(modelStub);

        Person dummyPersonWithModule = new PersonBuilder().withModules("CS2103T FRI 14:00 16:00").build();
        assertEquals(String.format(AddModuleCommand.MESSAGE_SUCCESS, Messages.format(dummyPersonWithModule)),
                commandResult.getFeedbackToUser());
    }

    /*
     * Tests for a successful attempt to add a module to second person in the list.
     */
    @Test
    public void execute_moduleForSecondPersonAcceptedByModel_addSuccessful() throws Exception {
        Module validModule = new ModuleBuilder().withModuleCode("CS2103T")
                .withDay("FRI").withStartTime("14:00")
                .withEndTime("16:00").build();
        Person dummyPerson = new PersonBuilder().build();
        Person dummyPerson2 = new PersonBuilder().withName("Buddy").build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(dummyPerson);
        modelStub.addPerson(dummyPerson2);

        CommandResult commandResult = new AddModuleCommand(Index.fromOneBased(2), validModule).execute(modelStub);

        Person dummyPersonWithModule = new PersonBuilder().withName("Buddy")
                .withModules("CS2103T FRI 14:00 16:00").build();

        assertEquals(String.format(AddModuleCommand.MESSAGE_SUCCESS, Messages.format(dummyPersonWithModule)),
                commandResult.getFeedbackToUser());
    }

    /*
     * Tests for a situation where a duplicate Module is added.
     */
    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Person dummyPersonWithModule = new PersonBuilder().withModules("CS2103T FRI 14:00 16:00").build();
        Module duplicateModule = new ModuleBuilder().withModuleCode("CS2103T")
                .withDay("FRI").withStartTime("14:00")
                .withEndTime("16:00").build();
        Module duplicateModule2 = new ModuleBuilder().withModuleCode("CS2103T")
                .withDay("THU").withStartTime("11:00")
                .withEndTime("13:00").build();

        ModelStubWithPerson modelStub = new ModelStubWithPerson(dummyPersonWithModule);
        AddModuleCommand addModuleCommand = new AddModuleCommand(Index.fromOneBased(1), duplicateModule);
        AddModuleCommand addModuleCommand2 = new AddModuleCommand(Index.fromOneBased(1), duplicateModule2);

        assertThrows(CommandException.class,
                AddModuleCommand.MESSAGE_DUPLICATE_MODULE, () -> {
                    addModuleCommand.execute(modelStub);
                });
        assertThrows(CommandException.class,
                AddModuleCommand.MESSAGE_DUPLICATE_MODULE, () -> {
                    addModuleCommand2.execute(modelStub);
                });
    }

    /*
     * Tests for a situation where second module's start time overlaps with
     * an existing module.
     */
    @Test
    public void execute_overlapStartTime_throwsCommandException() {
        Module invalidModule = new ModuleBuilder().withModuleCode("CS2101")
                .withDay("FRI").withStartTime("15:00")
                .withEndTime("17:00").build();

        Person dummyPersonWithModule = new PersonBuilder().withModules("CS2103T FRI 14:00 16:00").build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(dummyPersonWithModule);
        AddModuleCommand addModuleCommand = new AddModuleCommand(Index.fromOneBased(1), invalidModule);

        assertThrows(CommandException.class,
                AddModuleCommand.MESSAGE_OVERLAP_MODULE, () -> {
                    addModuleCommand.execute(modelStub);
                });
    }

    /*
     * Tests for a situation where second module's end time overlaps with
     * an existing module.
     */
    @Test
    public void execute_overlapEndTime_throwsCommandException() {
        Module invalidModule = new ModuleBuilder().withModuleCode("CS2101")
                .withDay("FRI").withStartTime("11:00")
                .withEndTime("14:00").build();
        Person dummyPersonWithModule = new PersonBuilder().withModules("CS2103T FRI 13:00 16:00").build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(dummyPersonWithModule);
        AddModuleCommand addModuleCommand = new AddModuleCommand(Index.fromOneBased(1), invalidModule);

        assertThrows(CommandException.class,
                AddModuleCommand.MESSAGE_OVERLAP_MODULE, () -> {
                    addModuleCommand.execute(modelStub);
                });
    }

    /*
     * Tests for a situation where an existing module is entirely within the
     * second module.
     */
    @Test
    public void execute_overlapOutsideTime_throwsCommandException() {
        Module invalidModule = new ModuleBuilder().withModuleCode("CS2101")
                .withDay("FRI").withStartTime("11:00")
                .withEndTime("17:00").build();
        Person dummyPersonWithModule = new PersonBuilder().withModules("CS2103T FRI 14:00 16:00").build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(dummyPersonWithModule);
        AddModuleCommand addModuleCommand = new AddModuleCommand(Index.fromOneBased(1), invalidModule);

        assertThrows(CommandException.class,
                AddModuleCommand.MESSAGE_OVERLAP_MODULE, () -> {
                    addModuleCommand.execute(modelStub);
                });
    }

    /*
     * Tests for a situation where second module is entirely outside an existing
     * module.
     */
    @Test
    public void execute_overlapWithinTime_throwsCommandException() {
        Module invalidModule = new ModuleBuilder().withModuleCode("CS2101")
                .withDay("FRI").withStartTime("14:00")
                .withEndTime("15:00").build();
        Person dummyPersonWithModule = new PersonBuilder().withModules("CS2103T FRI 12:00 16:00").build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(dummyPersonWithModule);
        AddModuleCommand addModuleCommand = new AddModuleCommand(Index.fromOneBased(1), invalidModule);

        assertThrows(CommandException.class,
                AddModuleCommand.MESSAGE_OVERLAP_MODULE, () -> {
                    addModuleCommand.execute(modelStub);
                });
    }

    /*
     * Tests for a situation where the module timings overlap, but on different
     * dates.
     */
    @Test
    public void execute_overlapTimeDifferentDate_addSuccessful() throws Exception {
        Module validModule = new ModuleBuilder().withModuleCode("CS2101")
                .withDay("THU").withStartTime("14:00")
                .withEndTime("16:00").build();
        Person dummyPersonWithModule = new PersonBuilder().withModules("CS2103T FRI 14:00 16:00").build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(dummyPersonWithModule);
        CommandResult commandResult = new AddModuleCommand(Index.fromOneBased(1), validModule).execute(modelStub);

        Person dummyPersonWith2Modules = new PersonBuilder()
                .withModules("CS2103T FRI 14:00 16:00", "CS2101 THU 14:00 16:00").build();

        assertEquals(String.format(AddModuleCommand.MESSAGE_SUCCESS, Messages.format(dummyPersonWith2Modules)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Index index = Index.fromOneBased(1);
        Module friModule = new ModuleBuilder().withDay("FRI").build();
        Module thuModule = new ModuleBuilder().withDay("THU").build();
        Module cs2103tModule = new ModuleBuilder().withModuleCode("CS2103T").build();
        Module cs2101Module = new ModuleBuilder().withModuleCode("CS2101").build();
        AddModuleCommand addFriModuleCommand = new AddModuleCommand(index, friModule);
        AddModuleCommand addThuModuleCommand = new AddModuleCommand(index, thuModule);
        AddModuleCommand addCs2103tModuleCommand = new AddModuleCommand(index, cs2103tModule);
        AddModuleCommand addCs2101ModuleCommand = new AddModuleCommand(index, cs2101Module);

        // same object -> returns true
        assertTrue(addFriModuleCommand.equals(addFriModuleCommand));

        // same values -> returns true
        AddModuleCommand addFriModuleCommandCopy = new AddModuleCommand(index, friModule);
        assertTrue(addFriModuleCommand.equals(addFriModuleCommandCopy));

        // different types -> returns false
        assertFalse(addFriModuleCommand.equals(1));

        // null -> returns false
        assertFalse(addFriModuleCommand.equals(null));

        // different day -> returns false
        assertFalse(addFriModuleCommand.equals(addThuModuleCommand));

        // different module code -> returns false
        assertFalse(addCs2103tModuleCommand.equals(addCs2101ModuleCommand));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        Module validModule = new ModuleBuilder().withModuleCode("CS2103T")
                .withDay("FRI").withStartTime("14:00")
                .withEndTime("16:00").build();
        AddModuleCommand addModuleCommand = new AddModuleCommand(Index.fromOneBased(1), validModule);
        String expected = AddModuleCommand.class.getCanonicalName() + "{index="
                + index.toString() + ", module=" + validModule.toString() + "}";
        assertEquals(expected, addModuleCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMeeting(Meeting meeting) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMeeting(Meeting meeting) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMeeting(Meeting meeting) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Meeting> getMeetingList() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single Person.
     */
    private class ModelStubWithPerson extends AddModuleCommandTest.ModelStub {
        private final UniquePersonList persons = new UniquePersonList();

        ModelStubWithPerson(Person person) {
            persons.add(person);
        }

        @Override
        public void addPerson(Person person) {
            persons.add(person);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            requireAllNonNull(target, editedPerson);
            persons.setPerson(target, editedPerson);
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
