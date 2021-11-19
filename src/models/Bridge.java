package models;

import interfaces.CsvPrintable;

import java.util.HashSet;
import java.util.Set;

public class Bridge implements CsvPrintable {
    private Island startIsland, endIsland;
    private boolean isDouble = false;
    private boolean isVertical = true;

    public Bridge getBridge(){
        return this;
    }

    public Bridge(Island firstIsland, Island secondIsland, boolean isDouble) {
        if (firstIsland.getPosition().getX() < secondIsland.getPosition().getX() || firstIsland.getPosition().getY() < secondIsland.getPosition().getY()) {
            this.startIsland = firstIsland;
            this.endIsland = secondIsland;
        } else {
            this.startIsland = secondIsland;
            this.endIsland = firstIsland;
        }
        this.isDouble = isDouble;
        this.isVertical = firstIsland.getPosition().getX() == secondIsland.getPosition().getX();
    }

    public Bridge(Island firstIsland, Island secondIsland) {
        if (firstIsland.getPosition().getX() < secondIsland.getPosition().getX() || firstIsland.getPosition().getY() < secondIsland.getPosition().getY()) {
            this.startIsland = firstIsland;
            this.endIsland = secondIsland;
        } else {
            this.startIsland = secondIsland;
            this.endIsland = firstIsland;
        }
        this.isDouble = false;
        this.isVertical = firstIsland.getPosition().getX() == secondIsland.getPosition().getX();
    }
    public Set<Coordinates> getFields(){
        Set<Coordinates> fields = new HashSet<>();
        int x = this.startIsland.getPosition().getX();
        int y = this.startIsland.getPosition().getY();
        if(!isVertical){
            x++;
            while(x <= this.endIsland.getPosition().getX()){
                fields.add(new Coordinates(x, y));
                x++;
            }
        }
        else{
            y++;
            while(y <= this.endIsland.getPosition().getY()){
                fields.add(new Coordinates(x, y));
                y++;
            }
        }
        return fields;
    }

    public Bridge() {
    }

    public Island getStartIsland() {
        return startIsland;
    }

    public void setStartIsland(Island startIsland) {
        this.startIsland = startIsland;
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
        printedBridge.append(this.startIsland.getId()).append(CSV_SEPARATOR)
                .append(this.endIsland.getId()).append(CSV_SEPARATOR);
        printedBridge.append(isDouble ? "double" : "simple").append(CSV_SEPARATOR);
        printedBridge.append(isVertical ? "vertical" : "horizontal");

        return printedBridge.toString();
    }
}
