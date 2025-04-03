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
import syncsquad.teamsync.logic.Logic;
import syncsquad.teamsync.viewmodel.MainViewModel;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindowController extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private MainViewModel viewModel;

    @FXML
    private StackPane mainStackPane;

    @FXML
    private SplitPane splitPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane timetablePlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane titleBarPlaceholder;

    @FXML
    private Label currentWeekLabel;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindowController(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        this.viewModel = new MainViewModel(logic);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindow(logic.getGuiSettings());

        // TODO: If we are adding `nextWeek` or `previousWeek` actions
        //  we should create a currentWeekController to handle the logic
        this.viewModel.getCurrentWeekViewModel().currentWeekProperty()
            .addListener((unused1, oldValue, newValue) -> updateCurrentWeekLabel(newValue));
        updateCurrentWeekLabel(this.viewModel.getCurrentWeekViewModel().currentWeekProperty().get());

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Logic getLogic() {
        return logic;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        ModalPane helpModalPane = new ModalPane();
        mainStackPane.getChildren().add(helpModalPane);
        StackPane.setAlignment(helpModalPane, javafx.geometry.Pos.CENTER);

        TitleBarController titleBarController = new TitleBarController(primaryStage, logic, helpModalPane);
        titleBarPlaceholder.getChildren().add(titleBarController.getRoot());

        PersonTreeViewController personListPanel = new PersonTreeViewController(
            this.viewModel.getPersonListViewModel());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        TimetableController timetable = new TimetableController(
                this.viewModel.getPersonListViewModel(), this.viewModel.getMeetingListViewModel());
        timetablePlaceholder.getChildren().add(timetable.getRoot());

        ResultDisplayController resultDisplay = new ResultDisplayController(
            this.viewModel.getResultDisplayViewModel());
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooterController statusBarFooter = new StatusBarFooterController(logic.getAddressBookFilePath());
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
        String dateFormat = "MMM d";
        String weekRange = String.format("%s - %s",
            startOfWeek.format(DateTimeFormatter.ofPattern(dateFormat)),
            endOfWeek.format(DateTimeFormatter.ofPattern(dateFormat)));
        currentWeekLabel.setText("Current Week: " + weekRange);
    }

    /**
     * Needs to be called after stage is set, as dynamic layout will override
     * any default divider position for the {@code SplitPane}.
     */
    void forceDividerPosition(GuiSettings guiSettings) {
        splitPane.setDividerPositions(guiSettings.getDividerPosition());
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
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), primaryStage.isMaximized());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }
}
