package syncsquad.teamsync.viewmodel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import javafx.beans.property.SimpleObjectProperty;

/**
 * ViewModel for the current week.
 */
public class CurrentWeekViewModel {
    private SimpleObjectProperty<LocalDate> currentWeek;

    /**
     * Constructs a CurrentWeekViewModel with the current week.
     */
    public CurrentWeekViewModel() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        this.currentWeek = new SimpleObjectProperty<>(startOfWeek);
    }

    /**
     * Returns the current week property.
     *
     * @return the current week property
     */
    public SimpleObjectProperty<LocalDate> currentWeekProperty() {
        return currentWeek;
    }
}
