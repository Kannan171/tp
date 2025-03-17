package syncsquad.teamsync.model.schedule;

import static java.util.Objects.requireNonNull;
import static syncsquad.teamsync.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's code.
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleCode(String)}
 */
public class ModuleCode {


    public static final String MESSAGE_CONSTRAINTS =
            "Module codes should be alphabetical department tag (length 2-4)"
                    + " followed by 4-digit numeric code with optional last alphabet";
    public static final String VALIDATION_REGEX = "^[A-Za-z]{2,4}\\d{4}[A-Za-z]?$";
    public final String code;

    /**
     * Constructs a {@code ModuleCode}.
     *
     * @param moduleCode A valid module code.
     */
    public ModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        this.code = moduleCode.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.code;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCode)) {
            return false;
        }

        ModuleCode otherModuleCode = (ModuleCode) other;
        return code.equals(otherModuleCode.code);
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
    }
}
