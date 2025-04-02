
package syncsquad.teamsync.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.DATE_DESC_SEP_MEETING;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.END_TIME_DESC_SEP_MEETING;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.START_TIME_DESC_SEP_MEETING;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import syncsquad.teamsync.commons.core.GuiSettings;
import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.model.ReadOnlyAddressBook;
import syncsquad.teamsync.model.ReadOnlyUserPrefs;
import syncsquad.teamsync.model.TimetableWeek;
import syncsquad.teamsync.model.meeting.Meeting;
import syncsquad.teamsync.model.person.Person;

public class ListMeetingsCommandTest {

    private static final String MEETING_DISPLAY = "1." + DATE_DESC_SEP_MEETING + "; Start time:"
            + START_TIME_DESC_SEP_MEETING + "; End time:" + END_TIME_DESC_SEP_MEETING;

    @Test
    public void execute_displaysMeetings_listMeetingsSuccessful() {
        ModelStubAcceptingDisplayMeetings modelStub = new ModelStubAcceptingDisplayMeetings();

        CommandResult commandResult = new ListMeetingsCommand().execute(modelStub);

        assertEquals(String.join("\n", ListMeetingsCommand.MESSAGE_SUCCESS, MEETING_DISPLAY),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        ListMeetingsCommand listMeetingsCommand = new ListMeetingsCommand();
        ListMeetingsCommand otherListMeetingsCommand = new ListMeetingsCommand();

        // same object -> returns true
        assertTrue(listMeetingsCommand.equals(listMeetingsCommand));

        // both objects are instances of ListMeetingsCommand -> returns true
        assertTrue(listMeetingsCommand.equals(otherListMeetingsCommand));

        // different types -> returns false
        assertFalse(listMeetingsCommand.equals(1));

        // null -> returns false
        assertFalse(listMeetingsCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        ListMeetingsCommand listMeetingsCommand = new ListMeetingsCommand();
        String expected = ListMeetingsCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, listMeetingsCommand.toString());
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

        public String displayMeetingsString() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Meeting> getMeetingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TimetableWeek getCurrentWeek() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentWeek(TimetableWeek week) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that always accepts displaying meetings
     */
    private class ModelStubAcceptingDisplayMeetings extends ModelStub {

        @Override
        public String displayMeetingsString() {
            return MEETING_DISPLAY;
        }
    }

}
