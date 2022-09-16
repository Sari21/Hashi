package main.models;

import main.interfaces.CsvPrintable;
import main.solver.solvingTechniques.Levels;

import java.util.*;

public class Board implements CsvPrintable {
    private int width, height;
    private ArrayList<Island> islands = new ArrayList<>();
    private ArrayList<Bridge> bridges = new ArrayList<>();
    private Set<Coordinates> fields = new HashSet<>();
    private String fileName;
    private Levels level;


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
        printedBoard.append(this.level.toString()).append("\n");
        this.sortIslands();
        int i = 0;
        Island island = islands.get(i);
        for (int x = 1; x <= this.height; x++) {
            for (int y = 1; y <= this.width; y++) {
                if (island.getPosition().getX() == x && island.getPosition().getY() == y) {
                    printedBoard.append(island.getValue());
                    if (i < islands.size() -1)
                        island = islands.get(++i);
                } else {
                    printedBoard.append(0);
                }
                if (y != this.width)
                    printedBoard.append(",");
            }
            printedBoard.append("\n");
        }
        printedBoard.append("bridges").append("\n");
        for (Bridge bridge : bridges) {
            printedBoard.append(bridge.printCsv()).append("\n");
        }
        return printedBoard.toString();
//        StringBuilder printedBoard = new StringBuilder();
//        printedBoard.append("board").append("\n")
//                .append(this.width).append(CSV_SEPARATOR)
//                .append(this.height).append("\n")
//                .append("islands").append("\n");
//        for (Island island : islands) {
//            printedBoard.append(island.printCsv()).append("\n");
//        }
//        printedBoard.append("bridges").append("\n");
//        for (Bridge bridge : bridges) {
//            printedBoard.append(bridge.printCsv()).append("\n");
//        }
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

    public void sortIslands() {
        Collections.sort(islands);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        Collections.sort(board.getBridges());
        Collections.sort(this.bridges);

        return bridges.equals(board.bridges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islands, bridges, fileName);
    }

    public Levels getLevel() {
        return level;
    }

    public void setLevel(Levels level) {
        this.level = level;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
