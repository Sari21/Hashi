package services;

import models.Bridge;
import services.interfaces.IBridgeService;

public class BridgeService implements IBridgeService {
    private Bridge bridge;

    public Bridge getBridge() {
        return bridge;
    }

    public void setBridge(Bridge bridge) {
        this.bridge = bridge;
    }
}
