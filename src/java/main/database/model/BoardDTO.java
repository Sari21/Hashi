package main.database.model;

import main.models.Level;

import java.util.ArrayList;
import java.util.List;

public class BoardDTO {
    private List<Integer> islands;
    private List<BridgeDTO> bridges;
    private Level level;
    private int id;
    private int size;

    public BoardDTO(Level level, int id, int size) {
        this.level = level;
        this.islands = new ArrayList<>();
        this.bridges = new ArrayList<>();
        this.id = id;
        this.size = size;
    }

    public List<Integer> getIslands() {
        return islands;
    }

    public void setIslands(List<Integer> islands) {
        this.islands = islands;
    }

    public List<BridgeDTO> getBridges() {
        return bridges;
    }

    public void setBridges(List<BridgeDTO> bridges) {
        this.bridges = bridges;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
