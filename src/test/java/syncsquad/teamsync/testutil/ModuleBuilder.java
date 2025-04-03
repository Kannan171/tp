package syncsquad.teamsync.testutil;

import static syncsquad.teamsync.logic.parser.ParserUtil.TIME_FORMATTER;

import java.time.LocalTime;

import syncsquad.teamsync.model.module.Day;
import syncsquad.teamsync.model.module.Module;
import syncsquad.teamsync.model.module.ModuleCode;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODULE_CODE = "CS2103T";
    public static final String DEFAULT_DAY = "FRI";
    public static final String DEFAULT_START_TIME = "14:00";
    public static final String DEFAULT_END_TIME = "16:00";

    // Identity fields
    private ModuleCode moduleCode;

    // Data fields
    private Day day;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * Creates a {@code ModuleBuilder} with the default details.
     */
    public ModuleBuilder() {
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        day = new Day(DEFAULT_DAY);
        startTime = LocalTime.parse(DEFAULT_START_TIME, TIME_FORMATTER);
        endTime = LocalTime.parse(DEFAULT_END_TIME, TIME_FORMATTER);
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        moduleCode = moduleToCopy.getModuleCode();
        day = moduleToCopy.getDay();
        startTime = moduleToCopy.getStartTime();
        endTime = moduleToCopy.getEndTime();
    }

    /**
     * Sets the {@code moduleCode} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
        return this;
    }

    /**
     * Sets the {@code day} of the {@code Module} that we are building.
     */
    public ModuleBuilder withDay(String day) {
        this.day = new Day(day);
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code Module} that we are building.
     */
    public ModuleBuilder withStartTime(String startTime) {
        this.startTime = LocalTime.parse(startTime, TIME_FORMATTER);
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code Module} that we are building.
     */
    public ModuleBuilder withEndTime(String endTime) {
        this.endTime = LocalTime.parse(endTime, TIME_FORMATTER);
        return this;
    }

    public Module build() {
        return new Module(moduleCode, day, startTime, endTime);
    }

}
