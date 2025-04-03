package syncsquad.teamsync.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

import atlantafx.base.theme.Styles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
    private HelpWindowController helpWindow;
    private MainViewModel viewModel;

    @FXML
    private SplitPane splitPane;

    @FXML
    private HBox titleBar;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private MenuBar menuBar;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane meetingListPanelPlaceholder;

    @FXML
    private StackPane timetablePlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private Button closeButton;

    @FXML
    private Button minimizeButton;

    @FXML
    private Button maximizeButton;

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
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindowController();

        this.viewModel.getIsShowingHelpProperty().addListener((unused1) -> handleHelp());
        this.viewModel.getIsExiting().addListener((unused1) -> handleExit());

        // TODO: If we are adding `nextWeek` or `previousWeek` actions
        //  we should create a currentWeekController to handle the logic
        this.logic.getCurrentWeek().currentWeekProperty()
            .addListener((unused1, oldValue, newValue) -> updateCurrentWeekLabel(newValue));
        updateCurrentWeekLabel(this.logic.getCurrentWeek().currentWeekProperty().get());

        menuBar.getStyleClass().add(Styles.BG_ACCENT_SUBTLE);

        //TODO: refactor to group buttons into one function
        FontIcon closeIcon = new FontIcon(Material2AL.CLOSE);
        closeButton.setGraphic(closeIcon);
        closeButton.getStyleClass().addAll(Styles.BUTTON_ICON, Styles.FLAT, Styles.ACCENT);

        FontIcon minimizeIcon = new FontIcon(Material2MZ.MINIMIZE);
        minimizeButton.setGraphic(minimizeIcon);
        minimizeButton.getStyleClass().addAll(Styles.BUTTON_ICON, Styles.FLAT, Styles.ACCENT);
        minimizeButton.setOnAction(event -> primaryStage.setIconified(true));

        FontIcon maximizeIcon = new FontIcon(Material2AL.CHECK_BOX_OUTLINE_BLANK);
        FontIcon restoreIcon = new FontIcon(Material2AL.FILTER_NONE);
        maximizeButton.setGraphic(maximizeIcon);
        maximizeButton.getStyleClass().addAll(Styles.BUTTON_ICON, Styles.FLAT, Styles.ACCENT);
        maximizeButton.setOnAction(event -> {
            if (primaryStage.isMaximized()) {
                primaryStage.setMaximized(false);
                maximizeButton.setGraphic(maximizeIcon);
            } else {
                primaryStage.setMaximized(true);
                maximizeButton.setGraphic(restoreIcon);
            }
        });

        this.titleBar.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        this.titleBar.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
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
        String dateFormat = "MMM d yyyy";
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
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
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
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }
}
