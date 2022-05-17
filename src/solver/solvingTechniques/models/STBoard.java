package solver.solvingTechniques.models;

import solver.solvingTechniques.Levels;

import java.util.ArrayList;
import java.util.HashSet;

public class STBoard implements Cloneable  {
    private int width, height;
    private String filename;
    private Levels level;
    
    private  ArrayList<STIsland> unfinishedIslands = new ArrayList<>();
    private ArrayList<STIsland> finishedIslands = new ArrayList<>();
    private ArrayList<STBridge> bridges = new ArrayList<>();

    public STBoard(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public STBoard() {
    }

    //true, ha van még befejezetlen szomszéd
    //false ha különálló szegmensek vannak
    public boolean checkFinishedIslands(){
        HashSet<STIsland> segment = new HashSet<>();
        ArrayList<STIsland> islands = new ArrayList<>(finishedIslands);
        boolean notEmpty =  !islands.isEmpty();
        while(notEmpty){
            getSegment(islands.get(0), segment);
            if(segment.size() == finishedIslands.size() + unfinishedIslands.size()){
                return true;
            }
            for(STIsland i : segment){
                if(!i.isFinished()){
                    islands.removeAll(segment);
                    notEmpty = !islands.isEmpty();
                    break;
                }
                return false;
            }
            notEmpty = !islands.isEmpty();
        }

        return true;
    }

    //ellenőrzi, hogy nincsenek-e különálló szegmensek
    private HashSet<STIsland> getSegment(STIsland island, HashSet<STIsland> segment) {
        if (!segment.contains(island)) {
            segment.add(island);
            if (island.getDownBridges() != null) {
                getSegment(island.getDownNeighbour(), segment);
            }
            if (island.getUpBridges() != null) {
                getSegment(island.getUpNeighbour(), segment);
            }
            if (island.getRightBridges() != null) {
                getSegment(island.getRightNeighbour(), segment);
            }
            if (island.getLeftBridges() != null) {
                getSegment(island.getLeftNeighbour(), segment);
            }
        }
        return segment;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Levels getLevel() {
        return level;
    }

    public void setLevel(Levels level) {
        this.level = level;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
