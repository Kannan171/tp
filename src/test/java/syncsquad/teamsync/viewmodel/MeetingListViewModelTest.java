package syncsquad.teamsync.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import syncsquad.teamsync.model.meeting.Meeting;

/**
 * Unit tests for {@link MeetingListViewModel}.
 */
public class MeetingListViewModelTest {
    private ObservableList<Meeting> meetingList;
    private MeetingListViewModel viewModel;

    @BeforeEach
    public void setUp() {
        meetingList = FXCollections.observableArrayList();
        viewModel = new MeetingListViewModel(meetingList);
    }

    @Test
    public void constructor_validList_propertyInitialized() {
        assertNotNull(viewModel.meetingListProperty(),
                "Meeting list property should be initialized");
        assertTrue(viewModel.meetingListProperty().isEmpty(),
                "Meeting list should be empty initially");
    }

    @Test
    public void meetingListProperty_listModified_reflectsChanges() {
        // Create a test meeting
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        Meeting meeting = new Meeting(date, startTime, endTime);

        // Add meeting to the source list
        meetingList.add(meeting);

        // Verify the property reflects the change
        assertEquals(1, viewModel.meetingListProperty().size(),
                "Meeting list property should reflect added meeting");
        assertEquals(meeting, viewModel.meetingListProperty().get(0),
                "Meeting list property should contain the added meeting");
    }

    @Test
    public void meetingListProperty_returnsReadOnlyProperty() {
        // Verify that the property is read-only
        assertTrue(viewModel.meetingListProperty() instanceof javafx.beans.property.ReadOnlyListProperty,
                "Meeting list property should be read-only");
    }
}
