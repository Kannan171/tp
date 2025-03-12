package syncsquad.teamsync.model.person;

import static java.util.Objects.requireNonNull;
import static syncsquad.teamsync.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's module in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidModule(String)}
 */
public class Module {


    public static final String MESSAGE_CONSTRAINTS =
            "Module codes should be alphabetical department tag (length 2-4) followed by 4-digit numeric code and optional last alphabet";
    public static final String VALIDATION_REGEX = "^[A-Za-z]{2,4}\\d{4}[A-Za-z]?$";
    public final String code;

    /**
     * Constructs a {@code Module}.
     *
     * @param module A valid module code.
     */
    public Module(String module) {
        requireNonNull(module);
        checkArgument(isValidModule(module), MESSAGE_CONSTRAINTS);
        code = module.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidModule(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "[" + code + "]";
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

        Module otherModule = (Module) other;
        return code.equals(otherModule.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
