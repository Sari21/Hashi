package main.models;

import main.interfaces.CsvPrintable;
import main.models.comparators.SortIslandsVertically;

import java.util.*;

public class Board implements CsvPrintable {
    private int width, height;
    private ArrayList<Island> islands = new ArrayList<>();
    private ArrayList<Bridge> solutionBridges = new ArrayList<>();
    private ArrayList<Bridge> bridges = new ArrayList<>();
    private boolean[][] islandFields;
    private boolean[][] bridgeFields;
    //    private Set<Coordinates> islandFields = new HashSet<>();
//    private Set<Coordinates> bridgeFields = new HashSet<>();
    private String fileName;
    private Level level;
    private int id;


    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        islandFields = new boolean[this.width][height];
        bridgeFields = new boolean[this.width][height];

        for (int i = 0; i < islandFields.length; i++)
            for (int j = 0; j < islandFields[i].length; j++) {
                islandFields[i][j] = false;
                bridgeFields[i][j] = false;
            }
    }

    public Board() {
    }

    public void addBridge(Bridge bridge) {
        bridges.add(bridge);
        for (Coordinates c : bridge.getFields()) {
            bridgeFields[c.getX()][c.getY()] = true;
        }
    }  public void addSolutionBridge(Bridge bridge) {
        solutionBridges.add(bridge);
        for (Coordinates c : bridge.getFields()) {
            bridgeFields[c.getX()][c.getY()] = true;
        }
    }

    public void addIsland(Island island) {
        islands.add(island);
        islandFields[island.getPosition().getX()][island.getPosition().getY()] = true;
    }

    @Override
    public String printCsv() {
        StringBuilder printedBoard = new StringBuilder();
        printedBoard.append(this.level.toString()).append("\n");
        this.sortIslands();
        int i = 0;
        Island island = islands.get(i);
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (island.getPosition().getX() == x && island.getPosition().getY() == y) {
                    printedBoard.append(island.getValue());
                    if (i < islands.size() - 1)
                        island = islands.get(++i);
                } else {
                    printedBoard.append(0);
                }
                if (x != this.width - 1)
                    printedBoard.append(",");
            }
            printedBoard.append("\n");
        }
        printedBoard.append("bridges").append("\n");
        for (Bridge bridge : solutionBridges) {
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

    public ArrayList<Bridge> getSolutionBridges() {
        return solutionBridges;
    }

    public void setSolutionBridges(ArrayList<Bridge> solutionBridges) {
        for (Bridge b : solutionBridges) {
            addBridge(b);
        }
    }

    public boolean[][] getIslandFields() {
        return islandFields;
    }

    public void setIslandFields(boolean[][] islandFields) {
        this.islandFields = islandFields;
    }

    public boolean[][] getBridgeFields() {
        return bridgeFields;
    }

    public void setBridgeFields(boolean[][] bridgeFields) {
        this.bridgeFields = bridgeFields;
    }

    public void sortIslands() {
        Collections.sort(islands, new SortIslandsVertically());
//        Collections.sort(islands);
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
        Collections.sort(board.getSolutionBridges());
        Collections.sort(this.bridges);

        return bridges.equals(board.bridges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islands, bridges, fileName);
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    //todo
    public void setBridgeAndIslandFields() {
        islandFields = new boolean[this.width][height];
        bridgeFields = new boolean[this.width][height];

        for (int i = 0; i < islandFields.length; i++)
            for (int j = 0; j < islandFields[i].length; j++) {
                islandFields[i][j] = false;
                bridgeFields[i][j] = false;
            }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Bridge> getBridges() {
        return bridges;
    }

    public void setBridges(ArrayList<Bridge> bridges) {
        this.bridges = bridges;
    }
}
