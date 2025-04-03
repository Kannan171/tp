package syncsquad.teamsync.model.meeting;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import syncsquad.teamsync.commons.exceptions.DuplicateItemException;
import syncsquad.teamsync.commons.exceptions.ItemNotFoundException;
import syncsquad.teamsync.logic.Messages;
import syncsquad.teamsync.model.UniqueItemList;
import syncsquad.teamsync.model.meeting.exceptions.DuplicateMeetingException;
import syncsquad.teamsync.model.meeting.exceptions.MeetingNotFoundException;

/**
 * A list of meetings that enforces uniqueness between its elements and does not allow nulls.
 * A meeting is considered unique by comparing using {@code Meeting#equals(Object)}. As such, adding and updating of
 * meetings uses {@code Meeting#equals(Object)} for equality so as to ensure that the meeting being added or updated is
 * unique in terms of identity in the UniqueMeetingList.
 *
 * Additionally, it is guaranteed that all meetings in the list are in sorted order, in their natural ordering
 * defined in {@code Meeting#compareTo(Meeting)}.
 *
 * Supports a minimal set of list operations.
 *
 * @see Meeting#equals(Object)
 */
public class UniqueMeetingList extends UniqueItemList<Meeting> {

    /**
     * Returns true if the list contains a meeting that overlaps in date and time with the given argument.
     */
    public boolean hasOverlap(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isOverlapping);
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
    protected DuplicateItemException duplicateItemException() {
        throw new DuplicateMeetingException();
    }

    @Override
    protected ItemNotFoundException itemNotFoundException() {
        throw new MeetingNotFoundException();
    }
}
