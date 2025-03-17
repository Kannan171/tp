package syncsquad.teamsync.model.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public class Meeting {

    public static final String MESSAGE_CONSTRAINTS =
            "Meeting time should be given in the following format: dd-mm-yyyy HH:mm HH:mm"
            + " followed by 4-digit numeric code with optional last alphabet";

    public final LocalDate date = null;
    public final LocalTime startTime = null;
    public final LocalTime endTime = null;
}
