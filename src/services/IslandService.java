package services;

import models.Island;
import services.interfaces.IIslandService;

public class IslandService implements IIslandService {
    private Island island;

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }
}
