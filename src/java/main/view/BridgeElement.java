package main.view;

import main.models.Bridge;
import javafx.scene.shape.Line;

public class BridgeElement implements ViewElement{
    private Bridge bridge;
    private Line line = new Line();
    private Line doubleLine = new Line();
    private static final int FIELD_WIDTH = 80;

    public BridgeElement (Bridge bridge) {
        this.bridge = bridge;
        line.setStrokeWidth(5);
        doubleLine.setStrokeWidth(5);
        if (!bridge.isDouble() && !bridge.isVertical()) {
            line.setStartX(bridge.getStartIsland().getPosition().getX() * FIELD_WIDTH - 40 + 35);
            line.setStartY(bridge.getStartIsland().getPosition().getY() * FIELD_WIDTH - 40);
            line.setEndX(bridge.getEndIsland().getPosition().getX() * FIELD_WIDTH - 40 - 35);
            line.setEndY(bridge.getEndIsland().getPosition().getY() * FIELD_WIDTH - 40);
        } else if (!bridge.isDouble() && bridge.isVertical()) {
            line.setStartX(bridge.getStartIsland().getPosition().getX() * FIELD_WIDTH - 40);
            line.setStartY(bridge.getStartIsland().getPosition().getY() * FIELD_WIDTH - 40 + 35);
            line.setEndX(bridge.getEndIsland().getPosition().getX() * FIELD_WIDTH - 40);
            line.setEndY(bridge.getEndIsland().getPosition().getY() * FIELD_WIDTH - 40 - 35);
        } else if (bridge.isDouble() && bridge.isVertical()) {
            line.setStartX(bridge.getStartIsland().getPosition().getX() * FIELD_WIDTH - 40 - 10);
            line.setStartY(bridge.getStartIsland().getPosition().getY() * FIELD_WIDTH - 40 + 35 - 2);
            line.setEndX(bridge.getEndIsland().getPosition().getX() * FIELD_WIDTH - 40 - 10);
            line.setEndY(bridge.getEndIsland().getPosition().getY() * FIELD_WIDTH - 40 - 35 + 2);

            doubleLine.setStartX(bridge.getStartIsland().getPosition().getX() * FIELD_WIDTH - 40 + 10);
            doubleLine.setStartY(bridge.getStartIsland().getPosition().getY() * FIELD_WIDTH - 40 + 35 - 2);
            doubleLine.setEndX(bridge.getEndIsland().getPosition().getX() * FIELD_WIDTH - 40 + 10);
            doubleLine.setEndY(bridge.getEndIsland().getPosition().getY() * FIELD_WIDTH - 40 - 35 + 2);

        } else if (bridge.isDouble() && !bridge.isVertical()) {
            line.setStartX(bridge.getStartIsland().getPosition().getX() * FIELD_WIDTH - 40 + 35 - 2);
            line.setStartY(bridge.getStartIsland().getPosition().getY() * FIELD_WIDTH - 40 + 10);
            line.setEndX(bridge.getEndIsland().getPosition().getX() * FIELD_WIDTH - 40 - 35 + 2);
            line.setEndY(bridge.getEndIsland().getPosition().getY() * FIELD_WIDTH - 40 + 10);

            doubleLine.setStartX(bridge.getStartIsland().getPosition().getX() * FIELD_WIDTH - 40 + 35 - 2);
            doubleLine.setStartY(bridge.getStartIsland().getPosition().getY() * FIELD_WIDTH - 40 - 10);
            doubleLine.setEndX(bridge.getEndIsland().getPosition().getX() * FIELD_WIDTH - 40 - 35 + 2);
            doubleLine.setEndY(bridge.getEndIsland().getPosition().getY() * FIELD_WIDTH - 40 - 10);

        }
    }

    public Bridge getBridge() {
        return bridge;
    }

    public void setBridge(Bridge bridge) {
        this.bridge = bridge;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Line getDoubleLine() {
        return doubleLine;
    }

    public void setDoubleLine(Line doubleLine) {
        this.doubleLine = doubleLine;
    }

}

