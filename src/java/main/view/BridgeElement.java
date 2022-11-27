package main.view;

import main.models.Bridge;
import javafx.scene.shape.Line;

public class BridgeElement implements ViewElement {
    private Bridge bridge;
    private Line line = new Line();
    private Line doubleLine = new Line();

    public BridgeElement(Bridge bridge, int size) {
        this.bridge = bridge;
        line.setStrokeWidth(5);
        doubleLine.setStrokeWidth(5);

        int x1 = bridge.getStartIsland().getPosition().getX() + 1;
        int y1 = bridge.getStartIsland().getPosition().getY() + 1;
        int x2 = bridge.getEndIsland().getPosition().getX() + 1;
        int y2 = bridge.getEndIsland().getPosition().getY() + 1;
        float RADIUS = (WIDTH - 2 * MARGIN) / (4 * size - 2);

        float centerX1 = ((4 * x1 - 1) * RADIUS) + (RADIUS / 2);
        float centerX2 = ((4 * x2 - 1) * RADIUS) + (RADIUS / 2);
        float centerY1 = ((4 * y1 - 1) * RADIUS) + (RADIUS / 2);
        float centerY2 = ((4 * y2 - 1) * RADIUS) + (RADIUS / 2);
        if (bridge.isDouble()) {
            if (x1 == x2) {
                line.setStartX(centerX1 - 10);
                line.setStartY(centerY1);
                line.setEndX(centerX2 - 10);
                line.setEndY(centerY2);

                doubleLine.setStartX(centerX1 + 10);
                doubleLine.setStartY(centerY1);
                doubleLine.setEndX(centerX2 + 10);
                doubleLine.setEndY(centerY2);
            } else {
                line.setStartX(centerX1);
                line.setStartY(centerY1-10);
                line.setEndX(centerX2 );
                line.setEndY(centerY2-10);
                doubleLine.setStartX(centerX1);
                doubleLine.setStartY(centerY1+10);
                doubleLine.setEndX(centerX2 );
                doubleLine.setEndY(centerY2+10);
            }
        }
        else{
            line.setStartX(centerX1);
            line.setStartY(centerY1);
            line.setEndX(centerX2);
            line.setEndY(centerY2);
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

