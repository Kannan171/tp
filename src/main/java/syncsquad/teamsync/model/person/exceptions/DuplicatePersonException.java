package syncsquad.teamsync.model.person.exceptions;

import syncsquad.teamsync.commons.exceptions.DuplicateItemException;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicatePersonException extends DuplicateItemException {
    public DuplicatePersonException() {
        super("Operation would result in duplicate persons");
    }
}
