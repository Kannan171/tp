package syncsquad.teamsync.model.schedule;

import static syncsquad.teamsync.commons.util.CollectionUtil.requireAllNonNull;

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
    private final ScheduleTime startScheduleTime;
    private final ScheduleTime endScheduleTime;

    /**
     * Every field must be present and not null.
     */
    public Module(ModuleCode moduleCode, Day day, ScheduleTime startScheduleTime, ScheduleTime endScheduleTime) {
        requireAllNonNull(moduleCode, day, startScheduleTime, endScheduleTime);
        this.moduleCode = moduleCode;
        this.day = day;
        this.startScheduleTime = startScheduleTime;
        this.endScheduleTime = endScheduleTime;
    }

    public ModuleCode getModuleCode() {
        return this.moduleCode;
    }

    public Day getDay() {
        return this.day;
    }

    public ScheduleTime getStartTime() {
        return this.startScheduleTime;
    }

    public ScheduleTime getEndTime() {
        return this.endScheduleTime;
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
                && startScheduleTime.equals(otherModule.startScheduleTime)
                && endScheduleTime.equals(otherModule.endScheduleTime);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleCode, day, startScheduleTime, endScheduleTime);
    }

    @Override
    public String toString() {
        return "[" + this.moduleCode + "]";
    }

}
