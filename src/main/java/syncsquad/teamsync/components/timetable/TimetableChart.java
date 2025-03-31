package syncsquad.teamsync.components.timetable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import syncsquad.teamsync.model.module.Day;

/**
 * A chart based on {@link XYChart} that displays a timetable.
 *
 * Heavily inspired by @Roland's GanttChart.java, which is itself inspired by openjfx BubbleChart.java
 * https://stackoverflow.com/a/27978436
 * https://github.com/ojdkbuild/lookaside_openjfx/
 */
public class TimetableChart extends XYChart<String, Number> {
    private ObservableList<PersonModulesBlock> personModulesBlocks = FXCollections.observableArrayList();
    private ObservableList<MeetingBlock> meetingBlocks = FXCollections.observableArrayList();

    /**
     * Constructs a {@code TimetableChart} with the specified data.
     * @param personModulesBlocks
     * @param meetingBlocks
     */
    public TimetableChart(
        ObservableList<PersonModulesBlock> personModulesBlocks,
        ObservableList<MeetingBlock> meetingBlocks) {
        super(
            new CategoryAxis(FXCollections.observableArrayList(Day.VALID_DAYS)),
            new NumberAxis(-24, 0, 1) // Time axis from 0000 to 2400
        );
        getXAxis().sideProperty().setValue(javafx.geometry.Side.TOP);

        // Dirty trick to invert axis: set them to negative values, then get the Formatter to
        // strip the negative sign.
        NumberAxis yAxis = (NumberAxis) getYAxis();
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

        setData(FXCollections.observableArrayList());

        this.personModulesBlocks = personModulesBlocks;
        this.meetingBlocks = meetingBlocks;

        loadData();

        personModulesBlocks.addListener((ListChangeListener<PersonModulesBlock>) change -> {
            loadData();
        });
        meetingBlocks.addListener((ListChangeListener<MeetingBlock>) change -> {
            loadData();
        });
    }

    public void loadPersonModulesBlocks(Collection<PersonModulesBlock> personModulesBlocks) {
        this.personModulesBlocks.setAll(personModulesBlocks);
    }

    public void loadMeetingBlocks(Collection<MeetingBlock> meetingBlocks) {
        this.meetingBlocks.setAll(meetingBlocks);
    }

    private void loadData() {
        getData().clear();
        getData().addAll(personModulesBlocks.stream()
            .map(PersonModulesBlock::getModuleSeries)
            .collect(Collectors.toList()));
        getData().addAll(meetingBlocks.stream()
            .map(MeetingBlock::getMeetingSeries)
            .collect(Collectors.toList()));
    }

    @Override
    protected void layoutPlotChildren() {
        double blockWidth = getBlockWidth();
        CategoryAxis xAxis = (CategoryAxis) getXAxis();
        NumberAxis yAxis = (NumberAxis) getYAxis();

        personModulesBlocks.forEach(block -> block.layout(xAxis, yAxis, blockWidth));
        meetingBlocks.forEach(block -> block.layout(xAxis, yAxis, blockWidth));
    }

    public double getBlockWidth() {
        double chartWidth = getXAxis().getWidth();
        return chartWidth / ((CategoryAxis) getXAxis()).getCategories().size() - 1;
    }

    @Override
    protected void dataItemAdded(Series<String, Number> series, int itemIndex, Data<String, Number> item) {
        Node block = createContainer(item);
        getPlotChildren().add(block);
    }

    @Override
    protected void dataItemRemoved(final Data<String, Number> item, final Series<String, Number> series) {
        final Node block = item.getNode();
        getPlotChildren().remove(block);
        removeDataItemFromDisplay(series, item);
    }

    @Override
    protected void dataItemChanged(Data<String, Number> item) {
    }

    @Override
    protected void seriesAdded(Series<String, Number> series, int seriesIndex) {
        for (int j = 0; j < series.getData().size(); j++) {
            Data<String, Number> item = series.getData().get(j);
            Node container = createContainer(item);
            getPlotChildren().add(container);
        }
    }

    @Override
    protected void seriesRemoved(final Series<String, Number> series) {
        for (XYChart.Data<String, Number> d : series.getData()) {
            final Node container = d.getNode();
            getPlotChildren().remove(container);
        }
        removeSeriesFromDisplay(series);
    }

    private Node createContainer(Data<String, Number> item) {
        Node container = item.getNode();

        if (container == null) {
            container = new StackPane();
            item.setNode(container);
        }

        return container;
    }

    @Override
    protected void updateAxisRange() {
        final Axis<Number> ya = getYAxis();
        List<Number> yData = null;

        if (ya.isAutoRanging()) {
            yData = new ArrayList<>();
        }

        if (yData != null) {
            for (Series<String, Number> series : getData()) {
                for (Data<String, Number> data : series.getData()) {
                    yData.add(data.getYValue());
                }
            }
            ya.invalidateRange(yData);
        }
    }
}
