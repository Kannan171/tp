package syncsquad.teamsync.model.meeting;

import static java.util.Objects.requireNonNull;
import static syncsquad.teamsync.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import syncsquad.teamsync.logic.Messages;
import syncsquad.teamsync.model.meeting.exceptions.DuplicateMeetingException;
import syncsquad.teamsync.model.meeting.exceptions.MeetingNotFoundException;

/**
 * A list of meetings that enforces uniqueness between its elements and does not allow nulls.
 * A meeting is considered unique by comparing using {@code Meeting#equals(Object)}. As such, adding and updating of
 * meetings uses Meeting#equals(Object) for equality so as to ensure that the meeting being added or updated is
 * unique in terms of identity in the UniqueMeetingList.
 * Supports a minimal set of list operations.
 *
 * @see Meeting#equals(Object)
 */
public class UniqueMeetingList implements Iterable<Meeting> {

    private final ObservableList<Meeting> internalList = FXCollections.observableArrayList();
    private final ObservableList<Meeting> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent meeting as the given argument.
     */
    public boolean contains(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list contains a meeting that overlaps with the given argument.
     */
    public boolean hasOverlap(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isOverlapping);
    }

    /**
     * Adds a meeting to the list.
     * The meeting must not already exist in the list.
     */
    public void add(Meeting toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMeetingException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Meeting toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MeetingNotFoundException();
        }
    }

    public void setMeetings(UniqueMeetingList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code meetings}.
     * {@code meetings} must not contain duplicate meetings.
     */
    public void setMeetings(List<Meeting> meetings) {
        requireAllNonNull(meetings);
        if (!meetingsAreUnique(meetings)) {
            throw new DuplicateMeetingException();
        }

        internalList.setAll(meetings);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Meeting> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Meeting> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueMeetingList)) {
            return false;
        }

        UniqueMeetingList otherUniqueMeetingList = (UniqueMeetingList) other;
        return internalList.equals(otherUniqueMeetingList.internalList);
    }

    /**
     * Returns the meetings list as a string formatted for display to the user
     */
    public String toDisplayString() {
        // Iterator which contains the indices formatted for display to the user
        Iterator<String> indicesIterator = IntStream.range(1, internalList.size() + 1)
                .mapToObj(i -> i + ". ")
                .iterator();
        // Iterator which contains the meetings formatted for display to the user
        Iterator<String> meetingsIterator = internalList.stream()
                .map(Messages::format)
                .iterator();

        return IntStream.range(0, internalList.size())
                .mapToObj(i -> indicesIterator.next() + meetingsIterator.next())
                .collect(Collectors.joining("\n"));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code meetings} contains only unique meetings.
     */
    private boolean meetingsAreUnique(List<Meeting> meetings) {
        for (int i = 0; i < meetings.size() - 1; i++) {
            for (int j = i + 1; j < meetings.size(); j++) {
                if (meetings.get(i).equals(meetings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
