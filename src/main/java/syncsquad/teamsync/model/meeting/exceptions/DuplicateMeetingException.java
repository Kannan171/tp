package syncsquad.teamsync.model.meeting.exceptions;

import syncsquad.teamsync.commons.exceptions.DuplicateItemException;

/**
 * Signals that the operation will result in duplicate Meetings (Meetings are considered duplicates if they have the
 * same date, start time and end time).
 */
public class DuplicateMeetingException extends DuplicateItemException {
    public DuplicateMeetingException() {
        super("Operation would result in duplicate meetings");
    }
}
