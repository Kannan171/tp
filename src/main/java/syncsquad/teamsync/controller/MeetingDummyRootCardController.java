package syncsquad.teamsync.controller;

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import atlantafx.base.theme.Styles;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A card for the dummy root in the {@code MeetingTreeView}.
 */
public class MeetingDummyRootCardController extends UiPart<Region> {

    private static final String FXML = "MeetingDummyRootCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;

    /**
     * Creates a {@code MeetingDummyRootCardController} to display a dummy root
     * card.
     */
    public MeetingDummyRootCardController() {
        super(FXML);
        title.getStyleClass().addAll(Styles.TITLE_3);
        FontIcon meetingIcon = new FontIcon(Material2AL.GROUPS);
        title.setGraphic(meetingIcon);
    }
}
