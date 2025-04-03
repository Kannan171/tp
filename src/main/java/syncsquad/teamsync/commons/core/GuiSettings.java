package syncsquad.teamsync.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

import syncsquad.teamsync.commons.util.ToStringBuilder;

/**
 * A Serializable class that contains the GUI settings.
 * Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 720;
    private static final double DEFAULT_WIDTH = 1280;
    private static final double DEFAULT_VERTICAL_DIVIDER_POSITION = 0.5;
    private static final double DEFAULT_HORIZONTAL_DIVIDER_POSITION = 0.7;

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;
    private final double verticalDividerPosition;
    private final double horizontalDividerPosition;
    private final boolean isMaximized;

    /**
     * Constructs a {@code GuiSettings} with the default height, width and position.
     */
    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = null; // null represent no coordinates
        isMaximized = false;
        verticalDividerPosition = DEFAULT_VERTICAL_DIVIDER_POSITION;
        horizontalDividerPosition = DEFAULT_HORIZONTAL_DIVIDER_POSITION;
    }

    /**
     * Constructs a {@code GuiSettings}
     */
    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition,
            double verticalDividerPosition, double horizontalDividerPosition, boolean isMaximized) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = new Point(xPosition, yPosition);
        this.verticalDividerPosition = verticalDividerPosition;
        this.horizontalDividerPosition = horizontalDividerPosition;
        this.isMaximized = isMaximized;
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public double getVerticalDividerPosition() {
        return verticalDividerPosition;
    }

    public double getHorizontalDividerPosition() {
        return horizontalDividerPosition;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates != null ? new Point(windowCoordinates) : null;
    }

    public boolean getIsMaximized() {
        return isMaximized;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuiSettings)) {
            return false;
        }

        GuiSettings otherGuiSettings = (GuiSettings) other;
        return windowWidth == otherGuiSettings.windowWidth
                && windowHeight == otherGuiSettings.windowHeight
                && Objects.equals(windowCoordinates, otherGuiSettings.windowCoordinates)
                && isMaximized == otherGuiSettings.isMaximized
                && verticalDividerPosition == otherGuiSettings.verticalDividerPosition
                && horizontalDividerPosition == otherGuiSettings.horizontalDividerPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates, verticalDividerPosition,
                horizontalDividerPosition, isMaximized);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("windowWidth", windowWidth)
                .add("windowHeight", windowHeight)
                .add("windowCoordinates", windowCoordinates)
                .add("verticalDividerPosition", verticalDividerPosition)
                .add("horizontalDividerPosition", horizontalDividerPosition)
                .add("isMaximized", isMaximized)
                .toString();
    }
}
