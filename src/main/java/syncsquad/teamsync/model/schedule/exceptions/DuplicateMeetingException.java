package syncsquad.teamsync.model.schedule.exceptions;

/**
 * Signals that the operation will result in duplicate Meetings (Meetings are considered duplicates if they have the
 * same date, start time and end time).
 */
public class DuplicateMeetingException extends RuntimeException {
    public DuplicateMeetingException() {
        super("Operation would result in duplicate meetings");
    }
}
