package models;

import interfaces.CsvPrintable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Board implements CsvPrintable {
    private int width, height;
    private ArrayList<Island> islands = new ArrayList<>();
    private ArrayList<Bridge> bridges = new ArrayList<>();
    private Set<Coordinates> fields = new HashSet<>();

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Board() {
    }

    public void addBridge(Bridge bridge) {
        bridges.add(bridge);
        fields.addAll(bridge.getFields());
    }

    public void addIsland(Island island) {
        islands.add(island);
        fields.add(island.getPosition());
    }

    @Override
    public String printCsv() {
        StringBuilder printedBoard = new StringBuilder();
        printedBoard.append("board").append("\n")
                .append(this.width).append(CSV_SEPARATOR)
                .append(this.height).append("\n")
                .append("islands").append("\n");
        for (Island island : islands) {
            printedBoard.append(island.printCsv()).append("\n");
        }
        printedBoard.append("bridges").append("\n");
        for (Bridge bridge : bridges) {
            printedBoard.append(bridge.printCsv()).append("\n");
        }
        return printedBoard.toString();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public void setIslands(ArrayList<Island> islands) {
        this.islands = islands;
    }

    public ArrayList<Bridge> getBridges() {
        return bridges;
    }

    public void setBridges(ArrayList<Bridge> bridges) {
        this.bridges = bridges;
    }

    public Set<Coordinates> getFields() {
        return fields;
    }

    public void setFields(Set<Coordinates> fields) {
        this.fields = fields;
    }
}
