package syncsquad.teamsync.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.testutil.Assert.assertThrows;
import static syncsquad.teamsync.testutil.TypicalAddressBook.JAN_MEETING;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import syncsquad.teamsync.logic.Messages;
import syncsquad.teamsync.logic.commands.exceptions.CommandException;
import syncsquad.teamsync.logic.commands.meeting.AddMeetingCommand;
import syncsquad.teamsync.model.AddressBook;
import syncsquad.teamsync.model.ReadOnlyAddressBook;
import syncsquad.teamsync.model.meeting.Meeting;
import syncsquad.teamsync.model.meeting.UniqueMeetingList;
import syncsquad.teamsync.testutil.MeetingBuilder;

public class AddMeetingCommandTest {

    @Test
    public void constructor_nullMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMeetingCommand(null));
    }

    @Test
    public void execute_meetingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMeetingAdded modelStub = new ModelStubAcceptingMeetingAdded();
        Meeting validMeeting = new MeetingBuilder().build();

        CommandResult commandResult = new AddMeetingCommand(validMeeting).execute(modelStub);

        assertEquals(String.format(AddMeetingCommand.MESSAGE_SUCCESS, Messages.format(validMeeting)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMeeting), modelStub.meetingsAdded);
    }

    @Test
    public void execute_duplicateMeeting_throwsCommandException() {
        Meeting validMeeting = new MeetingBuilder().build();
        AddMeetingCommand addMeetingCommand = new AddMeetingCommand(validMeeting);
        ModelStub modelStub = new AddMeetingCommandTest.ModelStubWithMeeting(validMeeting);

        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_DUPLICATE_MEETING, () -> {
            addMeetingCommand.execute(modelStub);
        });
    }

    /*
     * Tests for a situation where second meeting's start time overlaps with
     * an existing meeting.
     */
    @Test
    public void execute_overlapStartTime_throwsCommandException() {
        Meeting existingMeeting = new MeetingBuilder().withDate("01-01-2025")
                .withStartTime("10:00").withEndTime("12:00").build();
        Meeting overlappingMeeting = new MeetingBuilder().withDate("01-01-2025")
                .withStartTime("11:00").withEndTime("14:00").build();
        AddMeetingCommand addMeetingCommand1 = new AddMeetingCommand(existingMeeting);
        AddMeetingCommand addMeetingCommand2 = new AddMeetingCommand(overlappingMeeting);
        ModelStubWithMeetingList modelStub = new AddMeetingCommandTest.ModelStubWithMeetingList();

        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_OVERLAP_MEETING, () -> {
            addMeetingCommand1.execute(modelStub);
            addMeetingCommand2.execute(modelStub);
        });
    }

    /*
     * Tests for a situation where second meeting's end time overlaps with
     * an existing meeting.
     */
    @Test
    public void execute_overlapEndTime_throwsCommandException() {
        Meeting existingMeeting = new MeetingBuilder().withDate("01-01-2025")
                .withStartTime("10:00").withEndTime("12:00").build();
        Meeting overlappingMeeting = new MeetingBuilder().withDate("01-01-2025")
                .withStartTime("9:00").withEndTime("11:00").build();
        AddMeetingCommand addMeetingCommand1 = new AddMeetingCommand(existingMeeting);
        AddMeetingCommand addMeetingCommand2 = new AddMeetingCommand(overlappingMeeting);
        ModelStubWithMeetingList modelStub = new AddMeetingCommandTest.ModelStubWithMeetingList();

        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_OVERLAP_MEETING, () -> {
            addMeetingCommand1.execute(modelStub);
            addMeetingCommand2.execute(modelStub);
        });
    }

    /*
     * Tests for a situation where an existing meeting is entirely within the second
     * meeting.
     */
    @Test
    public void execute_overlapOutsideTime_throwsCommandException() {
        Meeting existingMeeting = new MeetingBuilder().withDate("01-01-2025")
                .withStartTime("10:00").withEndTime("14:00").build();
        Meeting overlappingMeeting = new MeetingBuilder().withDate("01-01-2025")
                .withStartTime("9:00").withEndTime("15:00").build();
        AddMeetingCommand addMeetingCommand1 = new AddMeetingCommand(existingMeeting);
        AddMeetingCommand addMeetingCommand2 = new AddMeetingCommand(overlappingMeeting);
        ModelStubWithMeetingList modelStub = new AddMeetingCommandTest.ModelStubWithMeetingList();

        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_OVERLAP_MEETING, () -> {
            addMeetingCommand1.execute(modelStub);
            addMeetingCommand2.execute(modelStub);
        });
    }

    /*
     * Tests for a situation where second meeting is entirely outside an existing
     * meeting.
     */
    @Test
    public void execute_overlapWithinTime_throwsCommandException() {
        Meeting existingMeeting = new MeetingBuilder().withDate("01-01-2025")
                .withStartTime("10:00").withEndTime("14:00").build();
        Meeting overlappingMeeting = new MeetingBuilder().withDate("01-01-2025")
                .withStartTime("11:00").withEndTime("13:00").build();
        AddMeetingCommand addMeetingCommand1 = new AddMeetingCommand(existingMeeting);
        AddMeetingCommand addMeetingCommand2 = new AddMeetingCommand(overlappingMeeting);
        ModelStubWithMeetingList modelStub = new AddMeetingCommandTest.ModelStubWithMeetingList();

        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_OVERLAP_MEETING, () -> {
            addMeetingCommand1.execute(modelStub);
            addMeetingCommand2.execute(modelStub);
        });
    }

    /*
     * Tests for a situation where the timings overlap, but on different dates.
     */
    @Test
    public void execute_overlapTimeDifferentDate_addSuccessful() throws Exception {
        Meeting existingMeeting = new MeetingBuilder().withDate("01-01-2025")
                .withStartTime("10:00").withEndTime("14:00").build();
        Meeting addedMeeting = new MeetingBuilder().withDate("02-01-2025")
                .withStartTime("11:00").withEndTime("15:00").build();
        AddMeetingCommand addMeetingCommand1 = new AddMeetingCommand(existingMeeting);
        AddMeetingCommand addMeetingCommand2 = new AddMeetingCommand(addedMeeting);
        ModelStubWithMeetingList modelStub = new AddMeetingCommandTest.ModelStubWithMeetingList();

        CommandResult commandResult1 = addMeetingCommand1.execute(modelStub);
        CommandResult commandResult2 = addMeetingCommand2.execute(modelStub);

        assertEquals(String.format(AddMeetingCommand.MESSAGE_SUCCESS,
                Messages.format(existingMeeting)), commandResult1.getFeedbackToUser());
        assertEquals(String.format(AddMeetingCommand.MESSAGE_SUCCESS,
                Messages.format(addedMeeting)), commandResult2.getFeedbackToUser());
        UniqueMeetingList expectedMeetingList = new UniqueMeetingList();
        expectedMeetingList.add(existingMeeting);
        expectedMeetingList.add(addedMeeting);
        assertEquals(expectedMeetingList, modelStub.meetings);
    }

    @Test
    public void equals() {
        Meeting aprilMeeting = new MeetingBuilder().withDate("01-04-2025").build();
        Meeting mayMeeting = new MeetingBuilder().withDate("01-05-2025").build();
        AddMeetingCommand addAprilMeetingCommand = new AddMeetingCommand(aprilMeeting);
        AddMeetingCommand addMayMeetingCommand = new AddMeetingCommand(mayMeeting);

        // same object -> returns true
        assertTrue(addAprilMeetingCommand.equals(addAprilMeetingCommand));

        // same values -> returns true
        AddMeetingCommand addFirstAprilCommandCopy = new AddMeetingCommand(aprilMeeting);
        assertTrue(addAprilMeetingCommand.equals(addFirstAprilCommandCopy));

        // different types -> returns false
        assertFalse(addAprilMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(addAprilMeetingCommand.equals(null));

        // different person -> returns false
        assertFalse(addAprilMeetingCommand.equals(addMayMeetingCommand));
    }

    @Test
    public void toStringMethod() {
        AddMeetingCommand addMeetingCommand = new AddMeetingCommand(JAN_MEETING);
        String expected = AddMeetingCommand.class.getCanonicalName() + "{toAdd=" + JAN_MEETING + "}";
        assertEquals(expected, addMeetingCommand.toString());
    }

    /**
     * A Model stub that contains a single meeting.
     */
    private class ModelStubWithMeeting extends ModelStub {
        private final Meeting meeting;

        ModelStubWithMeeting(Meeting meeting) {
            requireNonNull(meeting);
            this.meeting = meeting;
        }

        @Override
        public boolean hasMeeting(Meeting meeting) {
            requireNonNull(meeting);
            return this.meeting.equals(meeting);
        }
    }

    /**
     * A Model stub that always accept the meeting being added.
     */
    private class ModelStubAcceptingMeetingAdded extends ModelStub {
        final UniqueMeetingList meetings = new UniqueMeetingList();
        final ArrayList<Meeting> meetingsAdded = new ArrayList<>();

        @Override
        public boolean hasMeeting(Meeting meeting) {
            requireNonNull(meeting);
            return meetingsAdded.stream().anyMatch(meeting::equals);
        }

        @Override
        public void addMeeting(Meeting meeting) {
            requireNonNull(meeting);
            meetingsAdded.add(meeting);
        }

        @Override
        public ObservableList<Meeting> getMeetingList() {
            return meetings.asUnmodifiableObservableList();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that contains a list of meetings.
     */
    private class ModelStubWithMeetingList extends ModelStub {
        final UniqueMeetingList meetings = new UniqueMeetingList();

        @Override
        public void addMeeting(Meeting meeting) {
            requireNonNull(meeting);
            meetings.add(meeting);
        }

        @Override
        public boolean hasMeeting(Meeting meeting) {
            requireNonNull(meeting);
            return meetings.contains(meeting);
        }

        @Override
        public ObservableList<Meeting> getMeetingList() {
            return meetings.asUnmodifiableObservableList();
        }
    }
}
