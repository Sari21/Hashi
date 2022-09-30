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
        int startIslandX = bridge.getStartIsland().getPosition().getX() + 1;
        int startIslandY = bridge.getStartIsland().getPosition().getY() + 1;
        int endIslandX = bridge.getEndIsland().getPosition().getX() + 1;
        int endIslandY = bridge.getEndIsland().getPosition().getY() + 1;
        if (!bridge.isDouble() && !bridge.isVertical()) {
            line.setStartX(startIslandX * FIELD_WIDTH - 40 + 35);
            line.setStartY(startIslandY * FIELD_WIDTH - 40);
            line.setEndX(endIslandX * FIELD_WIDTH - 40 - 35);
            line.setEndY(endIslandY * FIELD_WIDTH - 40);
        } else if (!bridge.isDouble() && bridge.isVertical()) {
            line.setStartX(startIslandX * FIELD_WIDTH - 40);
            line.setStartY(startIslandY * FIELD_WIDTH - 40 + 35);
            line.setEndX(endIslandX * FIELD_WIDTH - 40);
            line.setEndY(endIslandY * FIELD_WIDTH - 40 - 35);
        } else if (bridge.isDouble() && bridge.isVertical()) {
            line.setStartX(startIslandX * FIELD_WIDTH - 40 - 10);
            line.setStartY(startIslandY * FIELD_WIDTH - 40 + 35 - 2);
            line.setEndX(endIslandX * FIELD_WIDTH - 40 - 10);
            line.setEndY(endIslandY * FIELD_WIDTH - 40 - 35 + 2);

            doubleLine.setStartX(startIslandX * FIELD_WIDTH - 40 + 10);
            doubleLine.setStartY(startIslandY * FIELD_WIDTH - 40 + 35 - 2);
            doubleLine.setEndX(endIslandX * FIELD_WIDTH - 40 + 10);
            doubleLine.setEndY(endIslandY * FIELD_WIDTH - 40 - 35 + 2);

        } else if (bridge.isDouble() && !bridge.isVertical()) {
            line.setStartX(startIslandX * FIELD_WIDTH - 40 + 35 - 2);
            line.setStartY(startIslandY * FIELD_WIDTH - 40 + 10);
            line.setEndX(endIslandX * FIELD_WIDTH - 40 - 35 + 2);
            line.setEndY(endIslandY * FIELD_WIDTH - 40 + 10);

            doubleLine.setStartX(startIslandX * FIELD_WIDTH - 40 + 35 - 2);
            doubleLine.setStartY(startIslandY * FIELD_WIDTH - 40 - 10);
            doubleLine.setEndX(endIslandX * FIELD_WIDTH - 40 - 35 + 2);
            doubleLine.setEndY(endIslandY * FIELD_WIDTH - 40 - 10);

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

