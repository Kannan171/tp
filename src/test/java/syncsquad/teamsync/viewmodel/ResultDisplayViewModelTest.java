package syncsquad.teamsync.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link ResultDisplayViewModel}.
 * Tests the functionality of displaying feedback messages.
 */
public class ResultDisplayViewModelTest {
    private ResultDisplayViewModel viewModel;

    @BeforeEach
    public void setUp() {
        viewModel = new ResultDisplayViewModel();
    }

    @Test
    public void setFeedbackToUser_nullFeedback_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> viewModel.setFeedbackToUser(null));
    }

    @Test
    public void setFeedbackToUser_validFeedback_feedbackPropertyUpdated() {
        String testFeedback = "Test feedback message";
        viewModel.setFeedbackToUser(testFeedback);

        assertEquals(testFeedback, viewModel.feedbackToUserProperty().get(),
                "Feedback property should contain the set message");
    }

    @Test
    public void setFeedbackToUser_multipleUpdates_latestFeedbackDisplayed() {
        String firstFeedback = "First message";
        String secondFeedback = "Second message";

        viewModel.setFeedbackToUser(firstFeedback);
        assertEquals(firstFeedback, viewModel.feedbackToUserProperty().get(),
                "Feedback property should contain the first message");

        viewModel.setFeedbackToUser(secondFeedback);
        assertEquals(secondFeedback, viewModel.feedbackToUserProperty().get(),
                "Feedback property should be updated to the second message");
    }

    @Test
    public void setFeedbackToUser_emptyString_acceptsEmptyFeedback() {
        String emptyFeedback = "";
        viewModel.setFeedbackToUser(emptyFeedback);

        assertEquals(emptyFeedback, viewModel.feedbackToUserProperty().get(),
                "Feedback property should accept empty string");
    }
}
