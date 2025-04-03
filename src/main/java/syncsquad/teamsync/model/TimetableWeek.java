package syncsquad.teamsync.model;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a Meeting in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class TimetableWeek {
    private final SimpleObjectProperty<LocalDate> currentDate;

    /**
     * Creates a TimetableWeek with the current date.
     */
    public TimetableWeek() {
        LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        this.currentDate = new SimpleObjectProperty<>(startOfWeek);
    }

    /**
     * Creates a TimetableWeek with the current date.
     */
    public TimetableWeek(LocalDate date) {
        this.currentDate = new SimpleObjectProperty<>(date);
    }

    public void setCurrentWeek(TimetableWeek week) {
        this.currentDate.set(week.currentDate.get());
    }

    public SimpleObjectProperty<LocalDate> currentWeekProperty() {
        return currentDate;
    }
}
