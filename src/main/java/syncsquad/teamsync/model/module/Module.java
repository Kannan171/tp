package syncsquad.teamsync.model.module;

import static syncsquad.teamsync.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents a Module of a Person.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    // Identity fields
    private final ModuleCode moduleCode;

    // Data fields
    private final Day day;
    private final LocalTime startTime;
    private final LocalTime endTime;

    /**
     * Every field must be present and not null.
     */
    public Module(ModuleCode moduleCode, Day day, LocalTime startTime, LocalTime endTime) {
        requireAllNonNull(moduleCode, day, startTime, endTime);
        this.moduleCode = moduleCode;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ModuleCode getModuleCode() {
        return this.moduleCode;
    }

    public Day getDay() {
        return this.day;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    /**
     * Returns true if both modules have the same code.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }
        return otherModule != null
                && otherModule.getModuleCode().equals(getModuleCode());
    }

    /**
     * Returns true if both modules overlap in terms of timing.
     * Both start and end time are considered to be non-inclusive,
     * i.e. endTime of 12pm and startTime of 12pm of another module is allowed.
     */
    public boolean isOverlapping(Module otherModule) {
        boolean timeOverlap = this.getEndTime().isAfter(otherModule.getStartTime())
                && this.getStartTime().isBefore(otherModule.getEndTime());
        boolean dayOverlap = this.getDay().equals(otherModule.getDay());
        return timeOverlap && dayOverlap;
    }

    /**
     * Returns true if both modules have the same identity and data fields.
     * This defines a stronger notion of equality between two modules.
     */
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
        return moduleCode.equals(otherModule.moduleCode)
                && day.equals(otherModule.day)
                && startTime.equals(otherModule.startTime)
                && endTime.equals(otherModule.endTime);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleCode, day, startTime, endTime);
    }

    @Override
    public String toString() {
        return "[" + this.moduleCode + "]";
    }

}
