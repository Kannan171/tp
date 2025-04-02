package syncsquad.teamsync.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DayTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Module(null, null, null, null));
    }

    @Test
    public void isValidDay() {
        assertTrue(Day.isValidDay("MON"));

        assertTrue(Day.isValidDay("Fri"));

        assertFalse(Day.isValidDay("Wednesday"));
    }

    @Test
    public void toString_test() {
        Day day = new Day("MON");
        assertEquals(day.toString(), "MON");

        Day day2 = new Day("tue");
        assertEquals(day2.toString(), "TUE");
    }

    @Test
    public void equals() {
        Day day = new Day("MON");
        Day day2 = new Day("tue");

        // same values -> returns true
        assertTrue(day.equals(new Day("MON")));

        // same object -> returns true
        assertTrue(day.equals(day));

        // null -> returns false
        assertFalse(day.equals(null));

        // different types -> returns false
        assertFalse(day.equals(5.0f));

        // different values -> returns false
        assertFalse(day.equals(day2));
    }
}
