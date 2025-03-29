package syncsquad.teamsync.controller;

import java.time.LocalDateTime;

import atlantafx.base.theme.Styles;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import syncsquad.teamsync.model.module.Day;
import syncsquad.teamsync.viewmodel.MeetingListViewModel;
import syncsquad.teamsync.viewmodel.PersonListViewModel;

/**
 * Panel containing the timetable for a specified date.
 */
public class TimetableController extends UiPart<Region> {
    private static final String FXML = "Timetable.fxml";

    //TODO: make this sexy as f**k
    private static final String[] COLORS = {};

    @FXML
    private VBox mainVBox;

    //TODO: not implemented
    private LocalDateTime date;
    private TimetableChart<String, Number> timetable;

    /**
     * Creates a {@code Timetable} for all people and meetings.
     */
    public TimetableController(PersonListViewModel personListViewModel, MeetingListViewModel meetingListViewModel) {
        super(FXML);

        // Initialize the timetable chart
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.sideProperty().setValue(javafx.geometry.Side.TOP);
        xAxis.setCategories(FXCollections.observableArrayList(Day.VALID_DAYS));

        // Dirty trick to invert axis: set them to negative values, then get the Formatter to
        // strip the negative sign.
        NumberAxis yAxis = new NumberAxis(0, 24, 1); // Time axis from 0000 to 2400
        yAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                int hour = Math.abs(object.intValue());
                return String.format("%02d:00", hour);
            }

            @Override
            public Number fromString(String string) {
                return Integer.valueOf(string.split(":")[0]);
            }
        });

        timetable = new TimetableChart<>(xAxis, yAxis);

        // Add the timetable chart to the main VBox
        mainVBox.getChildren().add(timetable);
        VBox.setVgrow(timetable, javafx.scene.layout.Priority.ALWAYS);

        // Populate the timetable with data from the PersonListViewModel
        personListViewModel.personListProperty().forEach(person -> {
            XYChart.Series<String, Number> personSeries = new XYChart.Series<>();

            person.getModules().forEach(module -> {
                String day = module.getDay().toString();
                double startTime = module.getStartTime().toSecondOfDay() / 3600.0; // Convert to hours
                double endTime = module.getEndTime().toSecondOfDay() / 3600.0; // Convert to hours
                double duration = endTime - startTime;
                System.out.println("Adding " + person.getName() + " " + day + " "
                                + startTime + " " + endTime + " " + duration);
                personSeries.getData().add(new XYChart.Data<>(day, startTime,
                    new TimetableChart.ExtraData(duration, FXCollections.observableArrayList(Styles.ACCENT))));
            });

            timetable.getData().add(personSeries);
        });
    }
}
