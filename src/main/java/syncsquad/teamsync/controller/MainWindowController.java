package syncsquad.teamsync.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import atlantafx.base.controls.ModalPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import syncsquad.teamsync.commons.core.GuiSettings;
import syncsquad.teamsync.viewmodel.MainViewModel;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindowController extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private Stage primaryStage;

    // Independent Ui parts residing in this Ui container
    private MainViewModel viewModel;

    @FXML
    private StackPane mainStackPane;

    @FXML
    private SplitPane splitPane;
    private SplitPane verticalSplitPane;

    @FXML
    private HBox titleBar;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private MenuBar menuBar;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane meetingListPanelPlaceholder;

    @FXML
    private StackPane timetablePlaceholder;

    @FXML
    private SplitPane horizontalSplitPane;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane titleBarPlaceholder;

    @FXML
    private Label currentWeekLabel;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage}.
     */
    public MainWindowController(Stage primaryStage, MainViewModel viewModel) {
        super(FXML, primaryStage);

        this.viewModel = viewModel;

        // Set dependencies
        this.primaryStage = primaryStage;

        // Configure the UI
        setWindow(viewModel.getGuiSettings().get());

        // TODO: If we are adding `nextWeek` or `previousWeek` actions
        //  we should create a currentWeekController to handle the logic
        this.viewModel.getCurrentWeek()
            .addListener((unused1, oldValue, newValue) -> updateCurrentWeekLabel(newValue));
        updateCurrentWeekLabel(this.viewModel.getCurrentWeek().get());

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        ModalPane helpModalPane = new ModalPane();
        mainStackPane.getChildren().add(helpModalPane);
        StackPane.setAlignment(helpModalPane, javafx.geometry.Pos.CENTER);

        TitleBarController titleBarController = new TitleBarController(primaryStage, viewModel, helpModalPane);
        titleBarPlaceholder.getChildren().add(titleBarController.getRoot());

        verticalSplitPane.setDividerPositions(viewModel.getGuiSettings().get().getVerticalDividerPosition());
        horizontalSplitPane.setDividerPositions(viewModel.getGuiSettings().get().getHorizontalDividerPosition());

        PersonTreeViewController personListPanel = new PersonTreeViewController(
            this.viewModel.getPersonListViewModel());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        MeetingTreeViewController meetingListPanel = new MeetingTreeViewController(
            this.viewModel.getMeetingListViewModel());
        meetingListPanelPlaceholder.getChildren().add(meetingListPanel.getRoot());

        TimetableController timetable = new TimetableController(
                this.viewModel.getPersonListViewModel(), this.viewModel.getMeetingListViewModel());
        timetablePlaceholder.getChildren().add(timetable.getRoot());

        ResultDisplayController resultDisplay = new ResultDisplayController(
            this.viewModel.getResultDisplayViewModel());
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooterController statusBarFooter = new StatusBarFooterController(
            this.viewModel.getAddressBookFilePath().get());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBoxController commandBox = new CommandBoxController(
            this.viewModel.getCommandBoxViewModel());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Updates the current week label with the current week's date range.
     */
    private void updateCurrentWeekLabel(LocalDate startOfWeek) {
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        String dateFormat = "MMM d yyyy";
        String weekRange = String.format("%s to %s",
            startOfWeek.format(DateTimeFormatter.ofPattern(dateFormat)),
            endOfWeek.format(DateTimeFormatter.ofPattern(dateFormat)));
        currentWeekLabel.setText("Current Week: " + weekRange);
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindow(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
        primaryStage.setMaximized(guiSettings.getIsMaximized());
        verticalSplitPane.setDividerPositions(guiSettings.getVerticalDividerPosition());
        horizontalSplitPane.setDividerPositions(guiSettings.getHorizontalDividerPosition());
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        boolean wasMaximized = primaryStage.isMaximized();
        primaryStage.setMaximized(false);
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), wasMaximized);
                (int) primaryStage.getX(), (int) primaryStage.getY(),
                verticalSplitPane.getDividerPositions()[0], horizontalSplitPane.getDividerPositions()[0]);
        viewModel.saveGuiSettings(guiSettings);
        primaryStage.hide();
    }
}
