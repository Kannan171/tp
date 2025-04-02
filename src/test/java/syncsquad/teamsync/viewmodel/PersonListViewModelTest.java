package syncsquad.teamsync.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static syncsquad.teamsync.testutil.TypicalAddressBook.ALICE;
import static syncsquad.teamsync.testutil.TypicalAddressBook.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import syncsquad.teamsync.model.person.Person;

class PersonListViewModelTest {

    private ObservableList<Person> internalList;
    private ObservableList<Person> internalUnmodifiableList;

    private PersonListViewModel viewModel;

    @BeforeEach
    void setUp() {
        internalList = FXCollections.observableArrayList();
        internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);
        // NOTE: By default, PersonListViewModel should receive a unmodifiable list
        viewModel = new PersonListViewModel(internalUnmodifiableList);
    }

    @Test
    void testInitialPersonListIsEmpty() {
        assertTrue(viewModel.personListProperty().isEmpty());
    }

    @Test
    void testAddingPersonUpdatesViewModel() {
        internalList.add(ALICE);
        assertEquals(1, viewModel.personListProperty().size());
        assertEquals(ALICE, viewModel.personListProperty().get(0));
    }

    @Test
    void testRemovingPersonUpdatesViewModel() {
        internalList.addAll(ALICE, BOB);

        internalList.remove(ALICE);
        assertEquals(1, viewModel.personListProperty().size());
        assertEquals(BOB, viewModel.personListProperty().get(0));
    }

    @Test
    void testClearingListUpdatesViewModel() {
        internalList.addAll(ALICE, BOB);
        internalList.clear();
        assertTrue(viewModel.personListProperty().isEmpty());
    }

    @Test
    void testReadOnlyPropertyCannotBeModifiedExternally() {
        ReadOnlyListProperty<Person> readOnlyList = viewModel.personListProperty();
        assertThrows(UnsupportedOperationException.class, () -> readOnlyList.add(BOB));
    }
}
