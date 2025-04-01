package syncsquad.teamsync.model.meeting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.testutil.Assert.assertThrows;
import static syncsquad.teamsync.testutil.TypicalAddressBook.FEB_MEETING;
import static syncsquad.teamsync.testutil.TypicalAddressBook.JAN_MEETING;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import syncsquad.teamsync.model.meeting.exceptions.DuplicateMeetingException;
import syncsquad.teamsync.model.meeting.exceptions.MeetingNotFoundException;
import syncsquad.teamsync.testutil.MeetingBuilder;

public class UniqueMeetingListTest {

    private final UniqueMeetingList uniqueMeetingList = new UniqueMeetingList();

    @Test
    public void contains_nullMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList.contains(null));
    }

    @Test
    public void contains_meetingNotInList_returnsFalse() {
        assertFalse(uniqueMeetingList.contains(JAN_MEETING));
    }

    @Test
    public void contains_meetingInList_returnsTrue() {
        uniqueMeetingList.add(JAN_MEETING);
        assertTrue(uniqueMeetingList.contains(JAN_MEETING));
    }

    @Test
    public void add_nullMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList.add(null));
    }

    @Test
    public void add_duplicateMeeting_throwsDuplicateMeetingException() {
        uniqueMeetingList.add(JAN_MEETING);
        assertThrows(DuplicateMeetingException.class, () -> uniqueMeetingList.add(JAN_MEETING));
    }

    @Test
    public void remove_nullMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList.remove(null));
    }

    @Test
    public void remove_meetingDoesNotExist_throwsMeetingNotFoundException() {
        assertThrows(MeetingNotFoundException.class, () -> uniqueMeetingList.remove(JAN_MEETING));
    }

    @Test
    public void remove_existingMeeting_removesMeeting() {
        uniqueMeetingList.add(JAN_MEETING);
        uniqueMeetingList.remove(JAN_MEETING);
        UniqueMeetingList expectedUniqueMeetingList = new UniqueMeetingList();
        assertEquals(expectedUniqueMeetingList, uniqueMeetingList);
    }

    @Test
    public void setMeetings_nullUniqueMeetingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList.setMeetings((UniqueMeetingList) null));
    }

    @Test
    public void setMeetings_uniqueMeetingList_replacesOwnListWithProvidedUniqueMeetingList() {
        uniqueMeetingList.add(JAN_MEETING);
        UniqueMeetingList expectedUniqueMeetingList = new UniqueMeetingList();
        expectedUniqueMeetingList.add(FEB_MEETING);
        uniqueMeetingList.setMeetings(expectedUniqueMeetingList);
        assertEquals(expectedUniqueMeetingList, uniqueMeetingList);
    }

    @Test
    public void setMeetings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList.setMeetings((List<Meeting>) null));
    }

    @Test
    public void setMeetings_list_replacesOwnListWithProvidedList() {
        uniqueMeetingList.add(JAN_MEETING);
        List<Meeting> meetingList = Collections.singletonList(FEB_MEETING);
        uniqueMeetingList.setMeetings(meetingList);
        UniqueMeetingList expectedUniqueMeetingList = new UniqueMeetingList();
        expectedUniqueMeetingList.add(FEB_MEETING);
        assertEquals(expectedUniqueMeetingList, uniqueMeetingList);
    }

    @Test
    public void setMeetings_listWithDuplicateMeetings_throwsDuplicateMeetingException() {
        List<Meeting> listWithDuplicateMeetings = Arrays.asList(JAN_MEETING, JAN_MEETING);
        assertThrows(DuplicateMeetingException.class, () -> uniqueMeetingList.setMeetings(listWithDuplicateMeetings));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            uniqueMeetingList.asUnmodifiableObservableList().remove(0);
        });
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueMeetingList.asUnmodifiableObservableList().toString(), uniqueMeetingList.toString());
    }

    @Test
    public void equalsMethod() {
        // same values -> returns true
        UniqueMeetingList uniqueMeetingList = new UniqueMeetingList();
        Meeting meeting = new MeetingBuilder().build();
        UniqueMeetingList uniqueMeetingList2 = new UniqueMeetingList();
        uniqueMeetingList2.add(meeting);
        assertTrue(uniqueMeetingList.equals(new UniqueMeetingList()));

        // same object -> returns true
        assertTrue(uniqueMeetingList.equals(uniqueMeetingList));

        // null -> returns false
        assertFalse(uniqueMeetingList.equals(null));

        // different type -> returns false
        assertFalse(uniqueMeetingList.equals(5));

        // different values -> returns false
        assertFalse(JAN_MEETING.equals(uniqueMeetingList2));
    }
}
