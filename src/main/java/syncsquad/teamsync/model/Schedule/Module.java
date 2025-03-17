package syncsquad.teamsync.model.Schedule;

import static syncsquad.teamsync.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import syncsquad.teamsync.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    // Identity fields
    private final ModuleCode moduleCode;

    // Data fields
    private final Day day;
    private final Time startTime;
    private final Time endTime;

    /**
     * Every field must be present and not null.
     */
    public Module(ModuleCode moduleCode, Day day, Time startTime, Time endTime){
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

    public Time getStartTime() {
        return this.startTime;
    }

    public Time getEndTime() {
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
     * Returns true if both modules have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
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
        return new ToStringBuilder(this)
                .add("module code", moduleCode)
                .add("day", day)
                .add("start time", startTime)
                .add("end time", endTime)
                .toString();
    }

}
