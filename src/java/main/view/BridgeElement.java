package main.view;

import main.models.Bridge;
import javafx.scene.shape.Line;

public class BridgeElement implements ViewElement {
    private Bridge bridge;
    private Line line = new Line();
    private Line doubleLine = new Line();

    public BridgeElement(Bridge bridge, int size) {
        int lineWidth = (size < 15) ? 5 : 2;
        int lineGap = (size < 15) ? 5 : 2;
        this.bridge = bridge;
        line.setStrokeWidth(lineWidth);
        doubleLine.setStrokeWidth(lineWidth);

        int x1 = bridge.getStartIsland().getPosition().getX();
        int y1 = bridge.getStartIsland().getPosition().getY();
        int x2 = bridge.getEndIsland().getPosition().getX();
        int y2 = bridge.getEndIsland().getPosition().getY() ;
        float RADIUS = (WIDTH - 2 * MARGIN) / (4 * size - 2);

        float centerX1 = ((4 * (x1 + (float) 0.5) - 1) * RADIUS) + (RADIUS / 2);
        float centerX2 = ((4 * (x2 + (float) 0.5) - 1) * RADIUS) + (RADIUS / 2);
        float centerY1 = ((4 * (y1 + (float) 0.5) - 1) * RADIUS) + (RADIUS / 2);
        float centerY2 = ((4 * (y2 + (float) 0.5) - 1) * RADIUS) + (RADIUS / 2);
        if (bridge.isDouble()) {
            if (x1 == x2) {
                line.setStartX(centerX1 - lineGap);
                line.setStartY(centerY1);
                line.setEndX(centerX2 - lineGap);
                line.setEndY(centerY2);
                doubleLine.setStartX(centerX1 + lineGap);
                doubleLine.setStartY(centerY1);
                doubleLine.setEndX(centerX2 + lineGap);
                doubleLine.setEndY(centerY2);
            } else {
                line.setStartX(centerX1);
                line.setStartY(centerY1 - lineGap);
                line.setEndX(centerX2);
                line.setEndY(centerY2 - lineGap);
                doubleLine.setStartX(centerX1);
                doubleLine.setStartY(centerY1 + lineGap);
                doubleLine.setEndX(centerX2);
                doubleLine.setEndY(centerY2 + lineGap);
            }
        } else {
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

