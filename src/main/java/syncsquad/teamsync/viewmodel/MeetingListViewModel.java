package syncsquad.teamsync.viewmodel;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.ObservableList;
import syncsquad.teamsync.model.meeting.Meeting;

/**
 * ViewModel class for managing a list of Meeting objects.
 * This class provides a read-only property for the list of meetings.
 */
public class MeetingListViewModel {
    private final ReadOnlyListWrapper<Meeting> meetingList;

    /**
     * Constructs a MeetingListViewModel with the specified unmodifiable list of Meeting objects.
     *
     * @param meetingList the unmodifiable observable list of Person objects
     */
    public MeetingListViewModel(ObservableList<Meeting> meetingList) {
        this.meetingList = new ReadOnlyListWrapper<Meeting>(meetingList);
    }

    public ReadOnlyListProperty<Meeting> meetingListProperty() {
        return meetingList.getReadOnlyProperty();
    }
}
