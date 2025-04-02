package syncsquad.teamsync.commons.exceptions;

/**
 * Signals that an operation will result in duplicate items.
 */
public class DuplicateItemException extends RuntimeException {

    public DuplicateItemException() {
        super("Operation would result in duplicate items");
    }

    public DuplicateItemException(String message) {
        super(message);
    }
}
