package syncsquad.teamsync.model.schedule;

import static syncsquad.teamsync.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class Meeting {

    public static final String MESSAGE_CONSTRAINTS =
            "Meeting time should be given in the following format: dd-mm-yyyy HH:mm HH:mm"
            + " followed by 4-digit numeric code with optional last alphabet";

    public final LocalDate date;
    public final LocalTime startTime;
    public final LocalTime endTime;

    public Meeting(LocalDate date, LocalTime startTime, LocalTime endTime) {
        requireAllNonNull(date, startTime, endTime);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
