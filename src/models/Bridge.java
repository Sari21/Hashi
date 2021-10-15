package models;

import interfaces.CsvPrintable;

public class Bridge implements CsvPrintable {
    private Island sartIsland, endIsland;
    private boolean isDouble = false;
    private boolean isVertical = true;

    public Bridge(Island sartIsland, Island endIsland, boolean isDouble, boolean isVertical) {
        this.sartIsland = sartIsland;
        this.endIsland = endIsland;
        this.isDouble = isDouble;
        this.isVertical = isVertical;
    }

    public Bridge(Island firstIsland, Island secondIsland) {
        if (firstIsland.getPosition().getX() < secondIsland.getPosition().getX() || firstIsland.getPosition().getY() < firstIsland.getPosition().getY()) {
            this.sartIsland = firstIsland;
            this.endIsland = secondIsland;
        } else {
            this.sartIsland = secondIsland;
            this.endIsland = firstIsland;
        }
        this.isDouble = false;
        this.isVertical = firstIsland.getPosition().getX() == secondIsland.getPosition().getX();
    }

    public Bridge() {
    }

    public Island getSartIsland() {
        return sartIsland;
    }

    public void setSartIsland(Island sartIsland) {
        this.sartIsland = sartIsland;
    }

    public Island getEndIsland() {
        return endIsland;
    }

    public void setEndIsland(Island endIsland) {
        this.endIsland = endIsland;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public void setDouble(boolean aDouble) {
        isDouble = aDouble;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }

    @Override
    public String printCsv() {
        StringBuilder printedBridge = new StringBuilder();
        printedBridge.append(this.sartIsland.getId()).append(CSV_SEPARATOR)
                .append(this.endIsland.getId()).append(CSV_SEPARATOR);
        printedBridge.append(isDouble ? "double" : "simple").append(CSV_SEPARATOR);
        printedBridge.append(isVertical ? "vertical" : "horizontal");

        return printedBridge.toString();
    }
}
