package syncsquad.teamsync.model.schedule;

import static syncsquad.teamsync.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Meeting in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Meeting {
    public static final String MESSAGE_CONSTRAINTS =
            "Meeting time should be given in the following format: dd-mm-yyyy HH:mm HH:mm"
            + " followed by 4-digit numeric code with optional last alphabet";

    public static final DateTimeFormatter DATE_TO_STRING_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter TIME_TO_STRING_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public final LocalDate date;
    public final LocalTime startTime;
    public final LocalTime endTime;

    /**
     * Creates a Meeting with the specified date, start time and end time.
     * Every field must be present and not null.
     */
    public Meeting(LocalDate date, LocalTime startTime, LocalTime endTime) {
        requireAllNonNull(date, startTime, endTime);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns a string representation of the date of the meeting.
     */
    public String getDateString() {
        return date.format(DATE_TO_STRING_FORMATTER);
    }

    /**
     * Returns a string representation of the start time of the meeting.
     */
    public String getStartTimeString() {
        return startTime.format(TIME_TO_STRING_FORMATTER);
    }

    /**
     * Returns a string representation of the end time of the meeting.
     */
    public String getEndTimeString() {
        return endTime.format(TIME_TO_STRING_FORMATTER);
    }

    @Override
    public String toString() {
        return getDateString() + " from " + getStartTimeString() + " to " + getEndTimeString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Meeting)) {
            return false;
        }

        Meeting otherMeeting = (Meeting) other;
        return date.equals(otherMeeting.date) && startTime.equals(otherMeeting.startTime)
                && endTime.equals(otherMeeting.endTime);
    }

    @Override
    public int hashCode() {
        return date.hashCode() ^ startTime.hashCode() ^ endTime.hashCode();
    }

}
