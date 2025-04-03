package syncsquad.teamsync.components.timetable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A block that displays a tooltip for each event in the timetable.
 * Overlapping events are merged into a single tooltip.
 */
public class TooltipBlock implements TimetableDisplayable {
    private final Map<String, List<TooltipEvent>> dayEvents;
    private final Map<String, XYChart.Series<String, Number>> tooltipSeriesMap;

    /**
     * Constructs a new TooltipBlock.
     */
    public TooltipBlock() {
        this.dayEvents = new HashMap<>();
        this.tooltipSeriesMap = new HashMap<>();
    }

    public Collection<XYChart.Series<String, Number>> getTooltipSeries() {
        return this.tooltipSeriesMap.values();
    }

    /**
     * Adds an event to the timetable.
     * Overlapping events are merged into a single tooltip.
     *
     * @param eventName The name of the event.
     * @param day The day of the event.
     * @param startTime The start time of the event.
     * @param duration The duration of the event.
     */
    public void addEvent(String eventName, String day, double startTime, double duration) {
        TooltipEvent event = new TooltipEvent(eventName, startTime, startTime + duration);

        if (this.dayEvents.containsKey(day)) {
            appendEvent(day, event, 0);

            XYChart.Series<String, Number> series = this.tooltipSeriesMap.get(day);
            series.getData().setAll(this.dayEvents.get(day).stream()
                .map(e -> new XYChart.Data<String, Number>(day, -e.getStartTime(), e))
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        } else {
            List<TooltipEvent> events = new ArrayList<>();
            events.add(event);
            this.dayEvents.put(day, events);

            ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();
            data.add(new XYChart.Data<>(day, -event.getStartTime(), event));
            this.tooltipSeriesMap.put(day, new XYChart.Series<>(data));
        }
    }

    @Override
    public void layout(CategoryAxis dayAxis, NumberAxis hourAxis, double blockWidth) {
        for (String day : this.dayEvents.keySet()) {
            XYChart.Series<String, Number> series = this.tooltipSeriesMap.get(day);
            Iterator<XYChart.Data<String, Number>> iter = series.getData().iterator();

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

                assert item.getExtraValue() instanceof TooltipEvent;
                TooltipEvent tooltipEvent = (TooltipEvent) item.getExtraValue();
                StackPane region = (StackPane) block;

                Rectangle rectangle;

                if (region.getShape() == null) {
                    rectangle = new Rectangle(blockWidth,
                            tooltipEvent.getDuration()
                            * Math.abs(hourAxis.getScale()));
                    region.getChildren().add(rectangle);
                } else if (region.getShape() instanceof Rectangle existingRectangle) {
                    rectangle = existingRectangle;
                } else {
                    return;
                }
                rectangle.setHeight(tooltipEvent.getDuration()
                    * Math.abs(hourAxis.getScale()));
                rectangle.setWidth(blockWidth);

                // The second part of the dirty trick to invert yAxis
                y += rectangle.getHeight() / 2.0;
                // This puts the rectangle in the middle of the gridlines
                x += rectangle.getWidth() / 2.0 + 1;

                rectangle.setFill(Color.TRANSPARENT);

                Tooltip tooltip = new Tooltip(tooltipEvent.description);
                Tooltip.install(region, tooltip);

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
            }
        }
    }


    private final class TooltipEvent {
        private String description;
        private double startTime;
        private double endTime;

        public TooltipEvent(String description, double startTime, double endTime) {
            this.description = description;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public double getStartTime() {
            return this.startTime;
        }

        public double getDuration() {
            return this.endTime - this.startTime;
        }
    }

    private void appendEvent(String day, TooltipEvent event, int insertIndex) {
        List<TooltipEvent> events = this.dayEvents.get(day);
        for (int i = insertIndex; i < events.size(); i++) {
            TooltipEvent e = events.get(i);
            if (e.endTime <= event.startTime) {
                insertIndex++;
            }
        }

        if (insertIndex >= events.size()) {
            // N N E E
            events.add(event);
            return;
        }

        TooltipEvent nextEvent = events.get(insertIndex);

        if (event.endTime <= nextEvent.startTime) {
            // E E N N
            events.add(insertIndex, event);
            return;
        }

        if (event.startTime < nextEvent.endTime && event.endTime > nextEvent.endTime) {
            // E N N E
            TooltipEvent newEvent = new TooltipEvent(
                nextEvent.description + "\n" + event.description,
                event.startTime,
                nextEvent.endTime);
            nextEvent.endTime = newEvent.startTime;
            event.startTime = newEvent.endTime;
            events.add(insertIndex, newEvent);
            appendEvent(day, event, insertIndex + 1);
            return;
        }

        if (event.startTime < nextEvent.endTime && event.endTime <= nextEvent.endTime) {
            // E N E N
            TooltipEvent newEvent = new TooltipEvent(
                nextEvent.description + "\n" + event.description,
                event.startTime,
                event.endTime);
            TooltipEvent remEvent = new TooltipEvent(
                nextEvent.description,
                newEvent.endTime,
                nextEvent.endTime);
            nextEvent.endTime = newEvent.startTime;
            events.add(insertIndex + 1, newEvent);
            if (remEvent.startTime != remEvent.endTime) {
                appendEvent(day, remEvent, insertIndex + 1);
            }
            return;
        }

        if (nextEvent.startTime < event.endTime && nextEvent.endTime > event.endTime) {
            // N E E N
            TooltipEvent newEvent = new TooltipEvent(
                nextEvent.description + "\n" + event.description,
                nextEvent.startTime,
                event.endTime);
            event.endTime = newEvent.startTime;
            nextEvent.startTime = newEvent.endTime;
            events.add(insertIndex, newEvent);
            appendEvent(day, event, insertIndex + 1);
            return;
        }

        if (nextEvent.startTime < event.endTime && nextEvent.endTime <= event.endTime) {
            // N E N E
            TooltipEvent newEvent = new TooltipEvent(
                event.description,
                event.startTime,
                nextEvent.startTime);
            nextEvent.description = nextEvent.description + "\n" + event.description;
            TooltipEvent remEvent = new TooltipEvent(
                event.description,
                nextEvent.endTime,
                event.endTime);
            events.add(insertIndex, newEvent);
            if (remEvent.startTime != remEvent.endTime) {
                appendEvent(day, remEvent, insertIndex + 1);
            }
            return;
        }
        return;
    }
}
