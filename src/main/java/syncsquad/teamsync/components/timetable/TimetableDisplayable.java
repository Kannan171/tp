package syncsquad.teamsync.components.timetable;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

/**
 * Interface for objects that can be displayed in the timetable.
 */
public interface TimetableDisplayable {
    /**
     * Lays out the object in the timetable.
     * @param xAxis the x-axis of the timetable
     * @param yAxis the y-axis of the timetable
     * @param blockHeight the width of the block
     */
    void layout(NumberAxis xAxis, CategoryAxis yAxis, double blockHeight);
}
