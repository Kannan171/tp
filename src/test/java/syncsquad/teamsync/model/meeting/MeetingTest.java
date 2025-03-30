package syncsquad.teamsync.model.meeting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_DATE_SEP_MEETING;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_END_TIME_SEP_MEETING;
import static syncsquad.teamsync.logic.commands.CommandTestUtil.VALID_START_TIME_SEP_MEETING;
import static syncsquad.teamsync.testutil.TypicalAddressBook.FEB_MEETING;
import static syncsquad.teamsync.testutil.TypicalAddressBook.JAN_MEETING;

import org.junit.jupiter.api.Test;

import syncsquad.teamsync.testutil.MeetingBuilder;

public class MeetingTest {

    @Test
    public void equals() {
        // same values -> returns true
        Meeting janMeetingCopy = new MeetingBuilder(JAN_MEETING).build();
        assertTrue(JAN_MEETING.equals(janMeetingCopy));

        // same object -> returns true
        assertTrue(JAN_MEETING.equals(JAN_MEETING));

        // null -> returns false
        assertFalse(JAN_MEETING.equals(null));

        // different type -> returns false
        assertFalse(JAN_MEETING.equals(5));

        // different person -> returns false
        assertFalse(JAN_MEETING.equals(FEB_MEETING));

        // different date -> returns false
        Meeting editedJanMeeting = new MeetingBuilder(JAN_MEETING).withDate(VALID_DATE_SEP_MEETING).build();
        assertFalse(JAN_MEETING.equals(editedJanMeeting));

        // different start time -> returns false
        editedJanMeeting = new MeetingBuilder(JAN_MEETING).withStartTime(VALID_START_TIME_SEP_MEETING).build();
        assertFalse(JAN_MEETING.equals(editedJanMeeting));

        // different end time -> returns false
        editedJanMeeting = new MeetingBuilder(JAN_MEETING).withEndTime(VALID_END_TIME_SEP_MEETING).build();
        assertFalse(JAN_MEETING.equals(editedJanMeeting));
    }

    @Test
    public void toStringMethod() {
        String expected = Meeting.class.getCanonicalName() + "{date=" +  JAN_MEETING.getDate()
                + ", startTime=" + JAN_MEETING.getStartTime() + ", endTime=" + JAN_MEETING.getEndTime() + "}";
        assertEquals(expected, JAN_MEETING.toString());
    }
}
