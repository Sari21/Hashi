package main.database.dto;

public class BridgeDTO {
    private int startIsland;
    private int endIsland;
    private boolean isDouble;

    public BridgeDTO(int startIsland, int endIsland, boolean isDouble) {
        this.startIsland = startIsland;
        this.endIsland = endIsland;
        this.isDouble = isDouble;
    }

    public BridgeDTO() {
    }

    public int getStartIsland() {
        return startIsland;
    }

    public void setStartIsland(int startIsland) {
        this.startIsland = startIsland;
    }

    public int getEndIsland() {
        return endIsland;
    }

    public void setEndIsland(int endIsland) {
        this.endIsland = endIsland;
    }

    public BridgeDTO(int startIsland, int endIsland) {
        this.startIsland = startIsland;
        this.endIsland = endIsland;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public void setDouble(boolean aDouble) {
        isDouble = aDouble;
    }


}
