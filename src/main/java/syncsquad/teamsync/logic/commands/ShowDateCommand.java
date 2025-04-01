package syncsquad.teamsync.logic.commands;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import syncsquad.teamsync.logic.commands.exceptions.CommandException;
import syncsquad.teamsync.model.Model;
import syncsquad.teamsync.viewmodel.CurrentWeekViewModel;

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
        CurrentWeekViewModel currentWeekViewModel = model.getCurrentWeekViewModel();
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        currentWeekViewModel.currentWeekProperty().set(startOfWeek);
        return new CommandResult(String.format(MESSAGE_SUCCESS, startOfWeek));
    }
}