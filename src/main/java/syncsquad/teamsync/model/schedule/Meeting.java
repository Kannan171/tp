package syncsquad.teamsync.model.schedule;

import static syncsquad.teamsync.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import syncsquad.teamsync.model.person.Address;

public class Meeting {
    public static final String MESSAGE_CONSTRAINTS =
            "Meeting time should be given in the following format: dd-mm-yyyy HH:mm HH:mm"
            + " followed by 4-digit numeric code with optional last alphabet";

    public final DateTimeFormatter DATE_TO_STRING_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public final DateTimeFormatter TIME_TO_STRING_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");


    public final LocalDate date;
    public final LocalTime startTime;
    public final LocalTime endTime;

    public Meeting(LocalDate date, LocalTime startTime, LocalTime endTime) {
        requireAllNonNull(date, startTime, endTime);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDateString() {
        return date.format(DATE_TO_STRING_FORMATTER);
    }

    public String getStartTimeString() {
        return startTime.format(TIME_TO_STRING_FORMATTER);
    }

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
