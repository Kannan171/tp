package syncsquad.teamsync.model;

import static java.util.Objects.requireNonNull;
import static syncsquad.teamsync.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import syncsquad.teamsync.commons.exceptions.DuplicateItemException;
import syncsquad.teamsync.commons.exceptions.ItemNotFoundException;

/**
 * A list of items that enforces uniqueness between its elements and does not allow nulls.
 * An item is considered unique by comparing using {@code isSameItem(T, T)}. As such, adding and updating of
 * items uses isSameItem(T, T) for equality so as to ensure that the item being added or updated is
 * unique in terms of identity in the UniqueList. However, the removal of an item uses T#equals(Object) so
 * as to ensure that the item with exactly the same details will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see UniqueItemList#isSameItem(T, T)
 */
public abstract class UniqueItemList<T> implements Iterable<T> {

    protected final ObservableList<T> internalList = FXCollections.observableArrayList();
    protected final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent item as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(item -> isSameItem(toCheck, item));
    }

    /**
     * Adds an item to the list.
     * The item must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw duplicateItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the item {@code target} in the list with {@code editedItem}.
     * {@code item} must exist in the list.
     * The item identity of {@code editedItem} must not be the same as another existing item in the list.
     */
    public void setItem(T target, T editedItem) {
        requireAllNonNull(target, editedItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw itemNotFoundException();
        }

        if (!isSameItem(target, editedItem) && contains(editedItem)) {
            throw duplicateItemException();
        }

        internalList.set(index, editedItem);
    }

    /**
     * Removes the equivalent item from the list.
     * The item must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw itemNotFoundException();
        }
    }

    public void setItems(UniqueItemList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code items}.
     * {@code items} must not contain duplicate items.
     */
    public void setItems(List<T> items) {
        requireAllNonNull(items);
        if (!itemsAreUnique(items)) {
            throw duplicateItemException();
        }

        internalList.setAll(items);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueItemList<?>)) {
            return false;
        }

        UniqueItemList<?> otherUniqueItemList = (UniqueItemList<?>) other;
        return internalList.equals(otherUniqueItemList.internalList);
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
     * Returns true if {@code items} contains only unique items.
     */
    private boolean itemsAreUnique(List<T> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (isSameItem(items.get(i), (items.get(j)))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Defines a notion of equality for two items in the UniqueList.
     * This methodd can be overridden to define a weaker or stronger notion of equality.
     */
    protected boolean isSameItem(T item1, T item2) {
        return item1.equals(item2);
    };

    /**
     * Returns a generic DuplicateItemException.
     * This method can be overridden to return a custom exception.
     */
    protected DuplicateItemException duplicateItemException() {
        throw new DuplicateItemException();
    }

    /**
     * Returns a generic ItemNotFoundException.
     * This method can be overridden to return a custom exception.
     */
    protected ItemNotFoundException itemNotFoundException() {
        throw new ItemNotFoundException();
    }
}
