package syncsquad.teamsync.model.person;

import syncsquad.teamsync.commons.exceptions.DuplicateItemException;
import syncsquad.teamsync.commons.exceptions.ItemNotFoundException;
import syncsquad.teamsync.model.UniqueItemList;
import syncsquad.teamsync.model.person.exceptions.DuplicatePersonException;
import syncsquad.teamsync.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList extends UniqueItemList<Person> {

    /**
     * Use a Person specific notion of equality to enforce uniqueness
     */
    @Override
    protected boolean isSameItem(Person item1, Person item2) {
        return item1.isSamePerson(item2);
    }

    @Override
    protected DuplicateItemException duplicateItemException() {
        return new DuplicatePersonException();
    }

    @Override
    protected ItemNotFoundException itemNotFoundException() {
        return new PersonNotFoundException();
    }
}

