package syncsquad.teamsync.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleCodeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }

    @Test
    public void constructor_invalidModuleCode_throwsIllegalArgumentException() {
        String invalidModule = "";
        assertThrows(IllegalArgumentException.class, () -> new ModuleCode(invalidModule));
    }

    @Test
    public void isValidModuleCode() {
        // null phone number
        assertThrows(NullPointerException.class, () -> ModuleCode.isValidModuleCode(null));

        // invalid module codes
        assertFalse(ModuleCode.isValidModuleCode("")); // empty string
        assertFalse(ModuleCode.isValidModuleCode(" ")); // spaces only
        assertFalse(ModuleCode.isValidModuleCode("comp")); // alphabet only
        assertFalse(ModuleCode.isValidModuleCode("2103")); // numbers only
        assertFalse(ModuleCode.isValidModuleCode("compsci2101")); // more than 4 letter department code
        assertFalse(ModuleCode.isValidModuleCode("c2101")); // less than 2 letter department code
        assertFalse(ModuleCode.isValidModuleCode("cs201")); // less than 3 letter numeric code
        assertFalse(ModuleCode.isValidModuleCode("cs21043")); // more than 4 letter numeric code
        assertFalse(ModuleCode.isValidModuleCode("cs2103tr")); // more than 1 alphabet suffix
        assertFalse(ModuleCode.isValidModuleCode("cs 2103t")); // spaces between code

        // valid module codes
        assertTrue(ModuleCode.isValidModuleCode("CS2103"));
        assertTrue(ModuleCode.isValidModuleCode("DAO2703"));
        assertTrue(ModuleCode.isValidModuleCode("GESS1035"));
        assertTrue(ModuleCode.isValidModuleCode("CS3233R"));
    }

    @Test
    public void equals() {
        ModuleCode moduleCode = new ModuleCode("CS2103T");

        // same values -> returns true
        assertTrue(moduleCode.equals(new ModuleCode("CS2103T")));

        // same object -> returns true
        assertTrue(moduleCode.equals(moduleCode));

        // null -> returns false
        assertFalse(moduleCode.equals(null));

        // different types -> returns false
        assertFalse(moduleCode.equals(5.0f));

        // different values -> returns false
        assertFalse(moduleCode.equals(new ModuleCode("CS2101")));
    }
}
