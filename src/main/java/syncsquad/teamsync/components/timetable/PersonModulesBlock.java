package syncsquad.teamsync.components.timetable;

import java.util.Iterator;
import java.util.stream.Collectors;

import atlantafx.base.theme.Styles;
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
import javafx.scene.shape.StrokeType;
import syncsquad.teamsync.model.person.Person;

/**
 * A block that displays a person's modules in the timetable.
 */
public final class PersonModulesBlock implements TimetableDisplayable {
    private static final Color[] COLORS = {
        Color.web("#F5426f", 0.6), // vibrant pink-red
        Color.web("#FF7E5F", 0.6), // warm coral
        Color.web("#FFAD5A", 0.6), // soft orange
        Color.web("#FFD46B", 0.6), // golden yellow
        Color.web("#F76680", 0.6), // rose
        Color.web("#F89D9D", 0.6), // light pink
        Color.web("#FFAA85", 0.6), // peach
        Color.web("#FCBC8D", 0.6) // soft orange-beige
    };

    private final XYChart.Series<String, Number> moduleSeries;

    /**
     * Constructs a {@code PersonModulesBlock} with the specified person.
     * @param person the person to display
     */
    public PersonModulesBlock(Person person) {
        ObservableList<XYChart.Data<String, Number>> moduleData = person.getModules().stream()
            .map(module -> {
                String day = module.getDay().toString();
                double startTime = module.getStartTime().toSecondOfDay() / 3600.0;
                double endTime = module.getEndTime().toSecondOfDay() / 3600.0;
                double duration = endTime - startTime;
                StyledModule styledModule = new StyledModule(
                    module.getModuleCode().toString(),
                    person.getName().toString(),
                    duration,
                    FXCollections.observableArrayList(Styles.ACCENT));
                return new XYChart.Data<String, Number>(
                    day,
                    -startTime,
                    styledModule);
            })
            .collect(Collectors.toCollection(FXCollections::observableArrayList));

        this.moduleSeries = new XYChart.Series<>(moduleData);
    }

    public XYChart.Series<String, Number> getModuleSeries() {
        return moduleSeries;
    }

    /**
     * {@code XYChart.Data} supports extra values that can be plotted in any way the chart needs.
     * The {@code TimetableChart} uses this to store the length of the block and the style class.
     */
    private final class StyledModule {
        private final String moduleCode;
        private final String personName;
        private final double duration;
        private final ObservableList<String> styleClass;

        public StyledModule(String moduleCode, String personName, double duration, ObservableList<String> styleClass) {
            this.moduleCode = moduleCode;
            this.personName = personName;
            this.duration = duration;
            this.styleClass = styleClass;
        }
    }

    /**
     * Lays out the person modules block in the timetable.
     * @param dayAxis the x-axis of the timetable
     * @param hourAxis the y-axis of the timetable
     * @param blockWidth the width of the block
     */
    public void layout(CategoryAxis dayAxis, NumberAxis hourAxis, double blockWidth) {
        Iterator<XYChart.Data<String, Number>> iter = moduleSeries.getData().iterator();

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

            assert item.getExtraValue() instanceof StyledModule;
            StyledModule styledModule = (StyledModule) item.getExtraValue();
            StackPane region = (StackPane) block;
            Rectangle rectangle;

            if (region.getShape() == null) {
                rectangle = new Rectangle(blockWidth,
                        styledModule.duration
                        * Math.abs(hourAxis.getScale()));
                region.getChildren().add(rectangle);
            } else if (region.getShape() instanceof Rectangle existingRectangle) {
                rectangle = existingRectangle;
            } else {
                return;
            }
            rectangle.setHeight(styledModule.duration
                    * Math.abs(hourAxis.getScale()));
            rectangle.setWidth(blockWidth);
            rectangle.setArcWidth(10);
            rectangle.setArcHeight(10);
            rectangle.setStrokeWidth(1.5);
            rectangle.setStrokeType(StrokeType.INSIDE);

            // The second part of the dirty trick to invert yAxis
            y += rectangle.getHeight() / 2.0;
            // This puts the rectangle in the middle of the gridlines
            x += rectangle.getWidth() / 2.0 + 1;

            rectangle.getStyleClass().addAll(styledModule.styleClass);
            Color color = COLORS[(styledModule.personName.hashCode() & 0x7fffffff) % COLORS.length];
            rectangle.setFill(color);
            Color strokeColor = Color.color(color.getRed(), color.getGreen(), color.getBlue(), 1);
            rectangle.setStroke(strokeColor);

            String tooltipText = styledModule.moduleCode + '\n' + styledModule.personName;
            Tooltip tooltip = new Tooltip(tooltipText);
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
