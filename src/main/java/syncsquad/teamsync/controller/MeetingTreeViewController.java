package syncsquad.teamsync.controller;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;
import syncsquad.teamsync.model.meeting.Meeting;
import syncsquad.teamsync.viewmodel.MeetingListViewModel;

/**
 * Panel containing the {@code TreeView} of meetings.
 * Each meeting has a root {@code TreeCell} containing the date, start time, and end time.
 */
public class MeetingTreeViewController extends UiPart<Region> {
    private static final String FXML = "MeetingTreeView.fxml";

    private abstract static class MeetingCard {

        private Meeting meeting;

        public MeetingCard(Meeting meeting) {
            this.meeting = meeting;
        }

        public Meeting getMeeting() {
            return meeting;
        }

    }

    private static class MeetingDummyRootCard extends MeetingCard {
        public MeetingDummyRootCard() {
            super(null);
        }
    }

    private static class MeetingRootCard extends MeetingCard {

        private int index;

        public MeetingRootCard(Meeting meeting, int index) {
            super(meeting);
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    //TreeView requires a root node to be set, so we create a dummy root node
    private static final TreeItem<MeetingCard> ROOT_TREE_ITEM =
            new TreeItem<>(new MeetingDummyRootCard());


    @FXML
    private TreeView<MeetingCard> meetingTreeView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public MeetingTreeViewController(MeetingListViewModel viewModel) {
        super(FXML);

        meetingTreeView.setRoot(ROOT_TREE_ITEM);
        ROOT_TREE_ITEM.setExpanded(true);

        viewModel.meetingListProperty().stream().forEach((meeting) -> {
            TreeItem<MeetingCard> meetingRootItem = new TreeItem<>(
                    new MeetingRootCard(meeting, viewModel.meetingListProperty().indexOf(meeting) + 1));
            ROOT_TREE_ITEM.getChildren().add(meetingRootItem);
            meetingRootItem.setExpanded(true);
        });

        viewModel.meetingListProperty().addListener((ListChangeListener<Meeting>) change -> {
            ROOT_TREE_ITEM.getChildren().clear();
            change.getList().stream().forEach((meeting) -> {
                TreeItem<MeetingCard> meetingRootItem = new TreeItem<>(
                        new MeetingRootCard(meeting, viewModel.meetingListProperty().indexOf(meeting) + 1));
                ROOT_TREE_ITEM.getChildren().add(meetingRootItem);
                meetingRootItem.setExpanded(true);
            });
        });

        meetingTreeView.setCellFactory(treeView -> new MeetingTreeCell());
    }

    /**
     * Custom {@code TreeCell} implementation for displaying {@code Person} objects.
     * DO NOT USE ATLANTAFX STYLES HERE. Learnt that the hard way.
     */
    private static class MeetingTreeCell extends TreeCell<MeetingCard> {
        @Override
        protected void updateItem(MeetingCard meetingCard, boolean empty) {
            //lines 129 to 133 must not be modified
            super.updateItem(meetingCard, empty);
            if (empty || meetingCard == null) {
                setText(null);
                setGraphic(null);
            } else {
                if (meetingCard instanceof MeetingDummyRootCard) {
                    setGraphic(new MeetingDummyRootCardController().getRoot());
                } else if (meetingCard instanceof MeetingRootCard) {
                    setGraphic(new MeetingRootCardController(
                        meetingCard.getMeeting(), ((MeetingRootCard) meetingCard).getIndex()).getRoot());
                }
            }
        }
    }
}
