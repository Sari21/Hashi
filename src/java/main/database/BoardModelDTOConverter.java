package main.database;

import main.database.model.BoardDTO;
import main.database.model.BridgeDTO;
import main.models.Board;
import main.models.Bridge;
import main.models.Island;
import main.models.Level;

import java.util.*;

public class BoardModelDTOConverter {

    public static Board DTOToModelConverter(BoardDTO dto) {
        Board model = new Board();
        model.setId(dto.getId());
        model.setFileName(String.valueOf(dto.getId()));
        model.setLevel(dto.getLevel());
        int id = 0;
        int x = 0;
        int y = 0;
        for (Integer i : dto.getIslands()) {
            Island island = new Island();
            island.setId(id);
            island.setValue(i);
            island.getPosition().setX(x);
            island.getPosition().setY(y);
            model.addIsland(island);
            id++;
            y++;
            if (y == dto.getSize() - 1) {
                x++;
                y = 0;
            }
        }

        for (BridgeDTO bridgeDTO : dto.getBridges()) {
            Bridge bridge = new Bridge();
            Island startIsland = model.getIslands().stream().filter(isl -> isl.getId() == bridgeDTO.getStartIsland()).findFirst().orElse(null);
            Island endIsland = model.getIslands().stream().filter(isl -> isl.getId() == bridgeDTO.getEndIsland()).findFirst().orElse(null);
            bridge.setStartIsland(startIsland);
            bridge.setEndIsland(endIsland);
            bridge.setDouble(bridgeDTO.isDouble());
            bridge.setVertical(bridge.getStartIsland().getPosition().getX() == bridge.getEndIsland().getPosition().getX());
            model.addBridge(bridge);
        }

        return model;
    }



    public static Board mapToModelConverter(Map<String, Object> map, Level level, int size) {
//        model.setId((Long) map.get("id"));
//        model.setFileName();
        Board model = new Board(size, size);
        model.setHeight(size);
        model.setWidth(size);
        model.setLevel(level);
        int id = 1;
        int x = 0;
        int y = 0;
        ArrayList<Long> islands = (ArrayList<Long>) map.get("islands");
        for (Long i : islands) {
            if (y == size) {
                x++;
                y = 0;
            }
            if(i.intValue() == 0){
                y++;
                continue;
            }
            Island island = new Island();
            island.setId(id);
            island.setValue(i.intValue());
            island.getPosition().setX(x);
            island.getPosition().setY(y);
            model.addIsland(island);
            id++;
            y++;

        }
        ArrayList<HashMap<String, Object>> bridgeObjects = (ArrayList<HashMap<String, Object>>) map.get("bridges");
        for (HashMap<String, Object> bridgeMap : bridgeObjects) {
            Bridge bridge = new Bridge();
            long startIslandIdLong = (long) bridgeMap.get("startIsland");
            int startIslandId = (int) startIslandIdLong;
            long endIslandIdLong = (long) bridgeMap.get("endIsland");
            int endIslandId = (int) endIslandIdLong;
            boolean isDouble = (boolean) bridgeMap.get("double");

            Island startIsland = model.getIslands().stream().filter(isl -> isl.getId() == startIslandId).findFirst().orElse(null);
            Island endIsland = model.getIslands().stream().filter(isl -> isl.getId() == endIslandId).findFirst().orElse(null);
            bridge.setStartIsland(startIsland);
            bridge.setEndIsland(endIsland);
            bridge.setDouble(isDouble);
            bridge.setVertical(bridge.getStartIsland().getPosition().getX() == bridge.getEndIsland().getPosition().getX());
            model.addBridge(bridge);
        }

        return model;
    }

    public static BoardDTO ModelToDTOConverter(Board model) {
        BoardDTO dto = new BoardDTO(model.getLevel(), model.getId(), model.getWidth());
        Integer[][] tmpIslands = new Integer[model.getWidth()][model.getHeight()];
        int i = 0;
        Island island = model.getIslands().get(i);
        for (int y = 0; y < model.getHeight(); y++) {
            for (int x = 0; x < model.getWidth(); x++) {
                if (island.getPosition().getX() == x && island.getPosition().getY() == y) {
                    tmpIslands[x][y] = island.getValue();
                    if (i < model.getIslands().size() - 1)
                        island = model.getIslands().get(++i);
                } else {
                    tmpIslands[x][y] = 0;
                }
            }
        }
        List<Integer> islands = new ArrayList<>();
        for (Integer[] tmpIsland : tmpIslands) {
            islands.addAll(Arrays.asList(tmpIsland));
        }
        dto.setIslands(islands);
        List<BridgeDTO> bridgeDTOS = new ArrayList<>();
        BridgeDTO bridgeDTO = new BridgeDTO();
        for (Bridge b : model.getBridges()) {
            bridgeDTO = new BridgeDTO();
            bridgeDTO.setDouble(b.isDouble());
            bridgeDTO.setStartIsland(b.getStartIsland().getId());
            bridgeDTO.setEndIsland(b.getEndIsland().getId());
            bridgeDTOS.add(bridgeDTO);
        }
        dto.setBridges(bridgeDTOS);
        return dto;
    }
}
