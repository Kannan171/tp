package syncsquad.teamsync.components.timetable;

import java.util.Iterator;

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import atlantafx.base.theme.Styles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import syncsquad.teamsync.model.meeting.Meeting;

/**
 * A block that displays a meeting in the timetable.
 */
public final class MeetingBlock implements TimetableDisplayable {
    private static final Color COLOR = Color.web("#9580ff");
    private final XYChart.Series<String, Number> meetingSeries;

    /**
     * Constructs a {@code MeetingBlock} with the specified meeting.
     * @param meeting the meeting to display
     */
    public MeetingBlock(Meeting meeting) {
        String day = meeting.getDay();
        double startTime = meeting.getStartTime().toSecondOfDay() / 3600.0;
        double endTime = meeting.getEndTime().toSecondOfDay() / 3600.0;
        double duration = endTime - startTime;
        StyledMeeting styledMeeting = new StyledMeeting(
                    duration,
                    FXCollections.observableArrayList(Styles.ACCENT));

        XYChart.Data<String, Number> meetingData = new XYChart.Data<>(day, -startTime, styledMeeting);

        this.meetingSeries = new XYChart.Series<>();
        this.meetingSeries.getData().add(meetingData);
    }

    public XYChart.Series<String, Number> getMeetingSeries() {
        return meetingSeries;
    }

    /**
     * {@code XYChart.Data} supports extra values that can be plotted in any way the chart needs.
     * The {@code TimetableChart} uses this to store the length of the block and the style class.
     */
    private final class StyledMeeting {
        private final double duration;
        private final ObservableList<String> styleClass;

        public StyledMeeting(double duration, ObservableList<String> styleClass) {
            this.duration = duration;
            this.styleClass = styleClass;
        }
    }

    /**
     * Lays out the meeting block in the timetable.
     * @param dayAxis the x-axis of the timetable
     * @param hourAxis the y-axis of the timetable
     * @param blockWidth the width of the block
     */
    public void layout(CategoryAxis dayAxis, NumberAxis hourAxis, double blockWidth) {
        Iterator<XYChart.Data<String, Number>> iter = meetingSeries.getData().iterator();

        while (iter.hasNext()) {
            XYChart.Data<String, Number> item = iter.next();
            double x = dayAxis.getDisplayPosition(item.getXValue());
            double y = hourAxis.getDisplayPosition(item.getYValue());
            if (Double.isNaN(x) || Double.isNaN(y)) {
                continue;
            }
            Node block = item.getNode();
            if (block == null || !(block instanceof StackPane)) {
                continue;
            }

            assert item.getExtraValue() instanceof StyledMeeting;
            StyledMeeting styledMeeting = (StyledMeeting) item.getExtraValue();
            StackPane region = (StackPane) block;
            Rectangle rectangle;

            if (region.getShape() == null) {
                rectangle = new Rectangle(blockWidth,
                        styledMeeting.duration
                        * Math.abs(hourAxis.getScale()));
                region.getChildren().add(rectangle);
            } else if (region.getShape() instanceof Rectangle existingRectangle) {
                rectangle = existingRectangle;
            } else {
                return;
            }
            rectangle.setHeight(styledMeeting.duration
                    * Math.abs(hourAxis.getScale()));
            rectangle.setWidth(blockWidth);
            rectangle.setArcWidth(5);
            rectangle.setArcHeight(5);
            rectangle.setStroke(Color.WHITE);
            rectangle.setStrokeWidth(1.5);
            rectangle.setStrokeType(StrokeType.INSIDE);

            // The second part of the dirty trick to invert yAxis
            y += rectangle.getHeight() / 2.0;
            // This puts the rectangle in the middle of the gridlines
            x += rectangle.getWidth() / 2.0 + 1;

            rectangle.getStyleClass().addAll(styledMeeting.styleClass);
            rectangle.setFill(COLOR);

            // Note: workaround for RT-7689 - saw this in ProgressControlSkin
            // The region doesn't update itself when the shape is mutated in place, so we
            // null out and then restore the shape in order to force invalidation.
            region.setShape(null);
            region.setShape(rectangle);
            region.setScaleShape(false);
            region.setCenterShape(false);
            region.setCacheShape(false);

            region.setLayoutX(x);
            region.setLayoutY(y);

            FontIcon meetingIcon = new FontIcon(Material2AL.GROUPS);
            meetingIcon.getStyleClass().addAll(Styles.BUTTON_ICON, Styles.FLAT);
            meetingIcon.setIconSize(16);
            meetingIcon.setIconColor(Color.WHITE);
            region.getChildren().add(meetingIcon);

            region.toFront();
        }
    }
}
