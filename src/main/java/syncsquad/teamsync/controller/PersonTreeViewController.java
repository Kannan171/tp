package syncsquad.teamsync.controller;

import java.util.HashSet;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;
import syncsquad.teamsync.model.person.Address;
import syncsquad.teamsync.model.person.Email;
import syncsquad.teamsync.model.person.Name;
import syncsquad.teamsync.model.person.Person;
import syncsquad.teamsync.model.person.Phone;
import syncsquad.teamsync.viewmodel.PersonListViewModel;

/**
 * Panel containing the {@code TreeView} of persons.
 * Each person has a root {@code TreeCell} containing their name and tags.
 * Then 2 children leaf items, one for their contact information and another for their modules.
 */
public class PersonTreeViewController extends UiPart<Region> {
    private static final String FXML = "PersonTreeView.fxml";

    private abstract static class PersonCard {

        private Person person;

        public PersonCard(Person person) {
            this.person = person;
        }

        public Person getPerson() {
            return person;
        }

    }

    private static class PersonDummyRootCard extends PersonCard {

        public PersonDummyRootCard(Person person) {
            super(person);
        }
    }

    private static class PersonRootCard extends PersonCard {

        private int index;

        public PersonRootCard(Person person, int index) {
            super(person);
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    private static class PersonInfoCard extends PersonCard {

        public PersonInfoCard(Person person) {
            super(person);
        }
    }

    private static class PersonModulesCard extends PersonCard {

        public PersonModulesCard(Person person) {
            super(person);
        }
    }

    //TreeView requires a root node to be set, so we create a dummy root node
    private static final Person ROOT_DUMMY_PERSON = new Person(
        new Name("People"), new Phone("777"), new Email("root@root.com"),
        new Address("dummy"), new HashSet<>(), new HashSet<>());
    private static final TreeItem<PersonCard> ROOT_TREE_ITEM =
            new TreeItem<>(new PersonDummyRootCard(ROOT_DUMMY_PERSON));


    @FXML
    private TreeView<PersonCard> personTreeView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonTreeViewController(PersonListViewModel viewModel) {
        super(FXML);

        personTreeView.setRoot(ROOT_TREE_ITEM);
        ROOT_TREE_ITEM.setExpanded(true);

        viewModel.personListProperty().forEach(person -> {
            TreeItem<PersonCard> personRootItem = new TreeItem<>(
                    new PersonRootCard(person, viewModel.personListProperty().indexOf(person) + 1));
            TreeItem<PersonCard> personInfoItem = new TreeItem<>(new PersonInfoCard(person));
            TreeItem<PersonCard> personModulesItem = new TreeItem<>(new PersonModulesCard(person));
            personRootItem.getChildren().add(personInfoItem);
            personRootItem.getChildren().add(personModulesItem);
            ROOT_TREE_ITEM.getChildren().add(personRootItem);
            personRootItem.setExpanded(true);
        });

        viewModel.personListProperty().addListener((ListChangeListener<Person>) change -> {
            ROOT_TREE_ITEM.getChildren().clear();
            viewModel.personListProperty().forEach(person -> {
                TreeItem<PersonCard> personRootItem = new TreeItem<>(
                        new PersonRootCard(person, viewModel.personListProperty().indexOf(person) + 1));
                TreeItem<PersonCard> personInfoItem = new TreeItem<>(new PersonInfoCard(person));
                TreeItem<PersonCard> personModulesItem = new TreeItem<>(new PersonModulesCard(person));
                personRootItem.getChildren().add(personInfoItem);
                personRootItem.getChildren().add(personModulesItem);
                ROOT_TREE_ITEM.getChildren().add(personRootItem);
                personRootItem.setExpanded(true);
            });
        });

        personTreeView.setCellFactory(treeView -> new PersonTreeCell());
    }

    /**
     * Custom {@code TreeCell} implementation for displaying {@code Person} objects.
     * DO NOT USE ATLANTAFX STYLES HERE. Learnt that the hard way.
     */
    private static class PersonTreeCell extends TreeCell<PersonCard> {
        @Override
        protected void updateItem(PersonCard personCard, boolean empty) {
            //lines 129 to 133 must not be modified
            super.updateItem(personCard, empty);
            if (empty || personCard == null) {
                setText(null);
                setGraphic(null);
            } else {
                if (personCard instanceof PersonDummyRootCard) {
                    setGraphic(new PersonDummyRootCardController().getRoot());
                } else if (personCard instanceof PersonRootCard) {
                    setGraphic(new PersonRootCardController(
                            personCard.getPerson(), ((PersonRootCard) personCard).getIndex()).getRoot());
                } else if (personCard instanceof PersonInfoCard) {
                    setGraphic(new PersonInfoCardController(personCard.getPerson()).getRoot());
                } else if (personCard instanceof PersonModulesCard) {
                    setGraphic(new PersonModulesCardController(personCard.getPerson()).getRoot());
                }
            }
        }
    }
}
