package syncsquad.teamsync.model.schedule;

import static java.util.Objects.requireNonNull;
import static syncsquad.teamsync.commons.util.AppUtil.checkArgument;

/**
 * Represents a Time of day in 24-hour format.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {


    public static final String MESSAGE_CONSTRAINTS =
            "Time should be 4-digits with no spaces between in 24-hour format "
            + "(e.g 1800, 1430 etc)";
    public static final String VALIDATION_REGEX = "^(?:[01]\\d|2[0-3])[0-5]\\d$";
    public final String time;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.time = time.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.time;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Module)) {
            return false;
        }

        Time otherTime = (Time) other;
        return time.equals(otherTime.time);
    }

    @Override
    public int hashCode() {
        return this.time.hashCode();
    }
}
