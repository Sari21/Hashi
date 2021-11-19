package solver.solvingTechniques.models;

import java.util.ArrayList;

public class STBoard {
    private int width, height;
    
    private ArrayList<STIsland> unfinishedIslands = new ArrayList<>();
    private ArrayList<STIsland> finishedIslands = new ArrayList<>();
    private ArrayList<STBridge> bridges = new ArrayList<>();

    public STBoard(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public STBoard() {
    }
    public void finishIsland(STIsland island){
        finishedIslands.add(island);
        unfinishedIslands.remove(island);
    }


    public void addBridge(STBridge bridge) {
        bridges.add(bridge);

    }

    public void addIsland(STIsland island) {
        unfinishedIslands.add(island);
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

    public ArrayList<STIsland> getUnfinishedIslands() {
        return unfinishedIslands;
    }

    public ArrayList<STBridge> getBridges() {
        return bridges;
    }

    public void setUnfinishedIslands(ArrayList<STIsland> unfinishedIslands) {
        this.unfinishedIslands = unfinishedIslands;
    }

    public void setBridges(ArrayList<STBridge> bridges) {
        this.bridges = bridges;
    }

    public ArrayList<STIsland> getFinishedIslands() {
        return finishedIslands;
    }

    public void setFinishedIslands(ArrayList<STIsland> finishedIslands) {
        this.finishedIslands = finishedIslands;
    }

}
