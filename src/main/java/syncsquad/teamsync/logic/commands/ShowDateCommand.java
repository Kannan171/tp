package syncsquad.teamsync.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import syncsquad.teamsync.commons.util.ToStringBuilder;
import syncsquad.teamsync.logic.commands.exceptions.CommandException;
import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.model.TimetableWeek;

/**
 * Command to update the timetable to show the specified date's week.
 */
public class ShowDateCommand extends Command {
    public static final String COMMAND_WORD = "showdate";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the timetable for the week of the given date\n"
            + "Parameters: DATE\n"
            + "Example: " + COMMAND_WORD + " 15-04-2024";
    public static final String MESSAGE_SUCCESS = "Timetable updated to week of %s";

    private final LocalDate date;

    public ShowDateCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        model.setCurrentWeek(new TimetableWeek(startOfWeek));
        return new CommandResult(String.format(MESSAGE_SUCCESS, date));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ShowDateCommand)) {
            return false;
        }

        ShowDateCommand otherShowDateCommand = (ShowDateCommand) other;
        return date.equals(otherShowDateCommand.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("date", date)
                .toString();
    }
}
