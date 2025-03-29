package syncsquad.teamsync.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * A chart based on {@link XYChart} that displays a timetable.
 *
 * Heavily inspired by @Roland's GanttChart.java, which is itself inspired by openjfx BubbleChart.java
 * https://stackoverflow.com/a/27978436
 * https://github.com/ojdkbuild/lookaside_openjfx/
 */
public class TimetableChart<X, Y> extends XYChart<X, Y> {

    /**
     * {@code XYChart.Data} supports extra values that can be plotted in any way the chart needs.
     * The {@code TimetableChart} uses this to store the length of the block and the style class.
     */
    protected static class ExtraData {
        private double length;
        private ObservableList<String> styleClass;

        public ExtraData(double length, ObservableList<String> styleClass) {
            super();
            this.length = length;
            this.styleClass = FXCollections.observableArrayList(styleClass);
        }
        public double getLength() {
            return length;
        }
        public void setLength(long length) {
            this.length = length;
        }
        public ObservableList<String> getStyleClass() {
            return styleClass;
        }

        @Override
        public String toString() {
            return "ExtraData [length=" + length + ", styleClass=" + styleClass + "]";
        }
    }

    private double blockWidth = 50;

    public TimetableChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis) {
        this(xAxis, yAxis, FXCollections.<Series<X, Y>>observableArrayList());
    }

    /**
     * Constructs a {@code TimetableChart} with the specified axes and data.
     * @param xAxis
     * @param yAxis
     * @param data
     */
    public TimetableChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis,
                          @NamedArg("data") ObservableList<Series<X, Y>> data) {
        super(xAxis, yAxis);
        if (!(xAxis instanceof CategoryAxis && yAxis instanceof ValueAxis)) {
            throw new IllegalArgumentException("Axis types are incorrect");
        }
        setData(data);
    }

    private static ObservableList<String> getStyleClass(Object obj) {
        assert obj instanceof ExtraData;
        return ((ExtraData) obj).getStyleClass();
    }

    private static double getLength(Object obj) {
        assert obj instanceof ExtraData;
        return ((ExtraData) obj).getLength();
    }

    @Override
    protected void layoutPlotChildren() {
        for (int seriesIndex = 0; seriesIndex < getData().size(); seriesIndex++) {
            Series<X, Y> series = getData().get(seriesIndex);
            Iterator<Data<X, Y>> iter = getDisplayedDataIterator(series);
            while (iter.hasNext()) {
                Data<X, Y> item = iter.next();
                double x = getXAxis().getDisplayPosition(item.getXValue());
                double y = getYAxis().getDisplayPosition(item.getYValue());
                System.out.println("item: " + item + "x: " + x + " y: " + y);
                if (Double.isNaN(x) || Double.isNaN(y)) {
                    continue;
                }
                Node block = item.getNode();
                Rectangle ellipse;
                if (block != null) {
                    if (block instanceof StackPane) {
                        StackPane region = (StackPane) item.getNode();
                        if (region.getShape() == null) {
                            ellipse = new Rectangle(getBlockWidth(),
                                                    getLength(item.getExtraValue())
                                                        * Math.abs(((NumberAxis) getYAxis()).getScale()));
                            region.getChildren().add(ellipse); // Ensure rectangle is added to StackPane
                        } else if (region.getShape() instanceof Rectangle rectangle) {
                            ellipse = rectangle;
                        } else {
                            return;
                        }
                        ellipse.setHeight(getLength(item.getExtraValue())
                                          * Math.abs(((NumberAxis) getYAxis()).getScale()));
                        ellipse.setWidth(getBlockWidth());

                        y -= ellipse.getHeight() / 2.0;
                        // x -= ellipse.getWidth() / 2.0;

                        ellipse.getStyleClass().addAll(getStyleClass(item.getExtraValue()));
                        ellipse.setFill(javafx.scene.paint.Color.PINK);
                        ellipse.toFront();

                        // Note: workaround for RT-7689 - saw this in ProgressControlSkin
                        // The region doesn't update itself when the shape is mutated in place, so we
                        // null out and then restore the shape in order to force invalidation.
                        region.setShape(null);
                        region.setShape(ellipse);
                        region.setScaleShape(false);
                        region.setCenterShape(false);
                        region.setCacheShape(false);

                        block.setLayoutX(x);
                        block.setLayoutY(y);
                    }
                }
            }
        }
    }

    public double getBlockWidth() {
        return blockWidth;
    }

    public void setBlockWidth(double blockWidth) {
        this.blockWidth = blockWidth;
    }

    @Override
    protected void dataItemAdded(Series<X, Y> series, int itemIndex, Data<X, Y> item) {
        Node block = createContainer(item);
        getPlotChildren().add(block);
    }

    @Override
    protected void dataItemRemoved(final Data<X, Y> item, final Series<X, Y> series) {
        final Node block = item.getNode();
        getPlotChildren().remove(block);
        removeDataItemFromDisplay(series, item);
    }

    @Override
    protected void dataItemChanged(Data<X, Y> item) {
    }

    @Override
    protected void seriesAdded(Series<X, Y> series, int seriesIndex) {
        for (int j = 0; j < series.getData().size(); j++) {
            Data<X, Y> item = series.getData().get(j);
            Node container = createContainer(item);
            getPlotChildren().add(container);
        }
    }

    @Override
    protected void seriesRemoved(final Series<X, Y> series) {
        for (XYChart.Data<X, Y> d : series.getData()) {
            final Node container = d.getNode();
            getPlotChildren().remove(container);
        }
        removeSeriesFromDisplay(series);
    }

    private Node createContainer(Data<X, Y> item) {
        Node container = item.getNode();

        if (container == null) {
            container = new StackPane();
            item.setNode(container);
        }

        container.getStyleClass().addAll(getStyleClass(item.getExtraValue()));

        return container;
    }

    @Override
    protected void updateAxisRange() {
        final Axis<Y> ya = getYAxis();
        List<Y> yData = null;

        if (ya.isAutoRanging()) {
            yData = new ArrayList<>();
        }

        if (yData != null) {
            for (Series<X, Y> series : getData()) {
                for (Data<X, Y> data : series.getData()) {
                    yData.add(data.getYValue());
                }
            }
            ya.invalidateRange(yData);
        }
    }
}
