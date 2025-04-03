package syncsquad.teamsync.controller;

import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import syncsquad.teamsync.commons.core.LogsCenter;

/**
 * Controller for a help dialog
 */
public class HelpDialogController extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(HelpDialogController.class);
    private static final String FXML = "HelpDialog.fxml";

    @FXML
    private VBox mainVBox;

    /**
     * Constructs a {@code HelpDialogController}
     */
    public HelpDialogController() {
        super(FXML);
        mainVBox.setStyle("-fx-background-color: -color-bg-default");
    }

    /**
     * Opens the user guide URL.
     */
    @FXML
    private void openUserGuide() {
        try {
            Desktop.getDesktop().browse(new URI("https://ay2425s2-cs2103t-f10-1.github.io/tp/UserGuide.html"));
        } catch (Exception e) {
            logger.warning("Failed to open User Guide URL: " + e.getMessage());
        }
    }

}
