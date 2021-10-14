package fields;

import interfaces.CsvPrintable;

import java.util.ArrayList;

public class Board implements CsvPrintable {
    private int width, height;
    private ArrayList<Island> islands;
    private ArrayList<Bridge> bridges ;

    public Board(int width, int height, ArrayList<Island> islands, ArrayList<Bridge> bridges) {
        this.width = width;
        this.height = height;
        this.islands = islands;
        this.bridges = bridges;
    }
    public Board(){
        islands = new ArrayList<>();
        bridges = new ArrayList<>();

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
    public void addBridges(Bridge bridge){
        bridges.add(bridge);
    }
    public void addIsland(Island island){
        islands.add(island);
    }

    @Override
    public String printCsv() {
        StringBuilder printedBoard = new StringBuilder();
        printedBoard.append("board").append("\n")
                .append(this.width).append(CSV_SEPARATOR)
                .append(this.height).append("\n")
                .append("islands").append("\n");
        for(Island island : islands){
            printedBoard.append(island.printCsv()).append("\n");
        }
        printedBoard.append("bridges").append("\n");
        for(Bridge bridge : bridges){
            printedBoard.append(bridge.printCsv()).append("\n");
        }
        return printedBoard.toString();
    }
}
