package syncsquad.teamsync.model;

import javafx.collections.ObservableList;
import syncsquad.teamsync.model.person.Person;
import syncsquad.teamsync.model.schedule.Meeting;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    ObservableList<Meeting> getMeetingList();

}
