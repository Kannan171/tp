package syncsquad.teamsync.commons.exceptions;

public class DuplicateItemException extends RuntimeException {

    public DuplicateItemException() {

    }

    public DuplicateItemException(String message) {
        super(message);
    }
}
