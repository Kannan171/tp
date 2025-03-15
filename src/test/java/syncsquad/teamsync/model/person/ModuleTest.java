package syncsquad.teamsync.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Module(null));
    }

    @Test
    public void constructor_invalidModule_throwsIllegalArgumentException() {
        String invalidModule = "";
        assertThrows(IllegalArgumentException.class, () -> new Module(invalidModule));
    }

    @Test
    public void isValidModule() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Module.isValidModule(null));

        // invalid module codes
        assertFalse(Module.isValidModule("")); // empty string
        assertFalse(Module.isValidModule(" ")); // spaces only
        assertFalse(Module.isValidModule("comp")); // alphabet only
        assertFalse(Module.isValidModule("2103")); // numbers only
        assertFalse(Module.isValidModule("compsci2101")); // more than 4 letter department code
        assertFalse(Module.isValidModule("c2101")); // less than 2 letter department code
        assertFalse(Module.isValidModule("cs201")); // less than 3 letter numeric code
        assertFalse(Module.isValidModule("cs21043")); // more than 4 letter numeric code
        assertFalse(Module.isValidModule("cs2103tr")); // more than 1 alphabet suffix
        assertFalse(Module.isValidModule("cs 2103t")); // spaces between code

        // valid module codes
        assertTrue(Module.isValidModule("CS2103"));
        assertTrue(Module.isValidModule("DAO2703"));
        assertTrue(Module.isValidModule("GESS1035"));
        assertTrue(Module.isValidModule("CS3233R"));
    }

    @Test
    public void equals() {
        Module module = new Module("CS2103T");

        // same values -> returns true
        assertTrue(module.equals(new Module("CS2103T")));

        // same object -> returns true
        assertTrue(module.equals(module));

        // null -> returns false
        assertFalse(module.equals(null));

        // different types -> returns false
        assertFalse(module.equals(5.0f));

        // different values -> returns false
        assertFalse(module.equals(new Module("CS2101")));
    }
}
