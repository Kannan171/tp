package syncsquad.teamsync.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import syncsquad.teamsync.testutil.ModuleBuilder;

public class ModuleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Module(null, null, null, null));
    }

    @Test
    public void isSameModule() {
        Module module1 = new ModuleBuilder().withDay("TUE").build();
        Module module2 = new ModuleBuilder().withDay("MON").build();
        Module module3 = new ModuleBuilder().withModuleCode("CS9876").build();

        // same module object
        assertTrue(module1.isSameModule(module1));

        // same module code different object
        assertTrue(module1.isSameModule(module2));

        // different module code
        assertFalse(module1.isSameModule(module3));

        // null
        assertFalse(module1.isSameModule(null));
    }

    @Test
    public void isOverlappingModule() {
        Module module1 = new ModuleBuilder().withStartTime("12:00").withEndTime("14:00").build();
        Module module2 = new ModuleBuilder().withStartTime("13:00").withEndTime("15:00").build();
        Module module3 = new ModuleBuilder().withStartTime("13:00").withEndTime("13:30").build();
        Module module4 = new ModuleBuilder().withStartTime("11:00").withEndTime("16:30").build();
        Module module5 = new ModuleBuilder().withStartTime("14:30").withEndTime("16:00").build();

        Module module6 = new ModuleBuilder().withStartTime("14:00").withEndTime("16:00").withDay("Sat").build();

        // start time before previous end time -> true
        assertTrue(module1.isOverlapping(module2));

        // end time before previous start time -> true
        assertTrue(module2.isOverlapping(module1));

        // second module within first module -> true
        assertTrue(module1.isOverlapping(module3));

        // first module within second module -> true
        assertTrue(module1.isOverlapping(module4));

        // no overlap -> false
        assertFalse(module1.isOverlapping(module5));

        // different day -> false
        assertFalse(module1.isOverlapping(module6));
    }

    @Test
    public void equals() {
        Module module = new ModuleBuilder().build();

        // same values -> returns true
        assertTrue(module.equals(new ModuleBuilder().build()));

        // same object -> returns true
        assertTrue(module.equals(module));

        // null -> returns false
        assertFalse(module.equals(null));

        // different types -> returns false
        assertFalse(module.equals(5.0f));

        // different values -> returns false
        assertFalse(module.equals(new ModuleBuilder().withDay("Sat").build()));
        assertFalse(module.equals(new ModuleBuilder().withStartTime("19:00").build()));
        assertFalse(module.equals(new ModuleBuilder().withEndTime("19:00").build()));
    }
}
