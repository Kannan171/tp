package syncsquad.teamsync.components.timetable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;

/**
 * A chart based on {@link XYChart}, used to hack in a third axis for the timetable.
 */
public class DateAxisChart extends XYChart<Number, String> { // Flip axis types

    /**
     * Constructs a {@code DateAxisChart} with the given dates.
     */
    public DateAxisChart(
        ObservableList<String> dates
    ) {
        super(
                new NumberAxis(0, 24, 1), // X axis
                new CategoryAxis(dates)
        );
        CategoryAxis yAxis = (CategoryAxis) getYAxis();
        // Dirty trick to make the gridlines timetable-like
        yAxis.heightProperty().addListener((observable, oldValue, newValue) -> {
            double displacement = -newValue.doubleValue() / (yAxis.getCategories().size() * 2);
            yAxis.translateYProperty().set(displacement);
        });
        yAxis.setTickLabelGap(10);
        yAxis.setTickMarkVisible(false);
        yAxis.sideProperty().setValue(javafx.geometry.Side.RIGHT);
        setVerticalGridLinesVisible(false);
        setHorizontalGridLinesVisible(false);

        // Dirty trick to invert yAxis
        FXCollections.reverse(yAxis.getCategories());
        yAxis.getCategories().add("");

        yAxis.toBack();

        getXAxis().sideProperty().setValue(javafx.geometry.Side.TOP);
        NumberAxis xAxis = (NumberAxis) getXAxis();
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                int hour = (int) Math.abs(object.doubleValue()); // Cast to integer after handling Double
                return String.format("%02d:00", hour);
            }

            @Override
            public Number fromString(String string) {
                return Double.valueOf(string.split(":")[0]); // Use Double for consistency
            }
        });
    }

    @Override
    protected void layoutPlotChildren() {
        // Always plot a point in the top-left and bottom-right corners
        double xMin = getXAxis().getDisplayPosition(((NumberAxis) getXAxis()).getLowerBound());
        double xMax = getXAxis().getDisplayPosition(((NumberAxis) getXAxis()).getUpperBound());
        double yMin = getYAxis().getDisplayPosition(((CategoryAxis) getYAxis()).getCategories().get(0));
        double yMax = getYAxis().getDisplayPosition(((CategoryAxis) getYAxis())
                .getCategories().get(((CategoryAxis) getYAxis()).getCategories().size() - 1
            ));

        // Example of plotting points (visualization logic can be adjusted as needed)
        getPlotChildren().add(new javafx.scene.shape.Circle(xMin, yMin, 2)); // Top-left
        getPlotChildren().add(new javafx.scene.shape.Circle(xMax, yMax, 2)); // Bottom-right
    }

    @Override
    protected void dataItemAdded(Series<Number, String> series, int itemIndex, Data<Number, String> item) {
    }

    @Override
    protected void dataItemRemoved(final Data<Number, String> item, final Series<Number, String> series) {
    }

    @Override
    protected void dataItemChanged(Data<Number, String> item) {
    }

    @Override
    protected void seriesAdded(Series<Number, String> series, int seriesIndex) {
    }

    @Override
    protected void seriesRemoved(final Series<Number, String> series) {
    }

    @Override
    protected void updateAxisRange() {
    }
}
