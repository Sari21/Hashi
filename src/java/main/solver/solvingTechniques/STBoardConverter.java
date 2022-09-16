package main.solver.solvingTechniques;

import main.models.Board;
import main.models.Bridge;
import main.models.Island;
import main.solver.solvingTechniques.models.STBoard;
import main.solver.solvingTechniques.models.STBridge;
import main.solver.solvingTechniques.models.STIsland;

import java.util.ArrayList;

public class STBoardConverter {
    private static STBoard stBoard;

    public static STBoard convertBoardToSTBoard(Board board) {
        stBoard = new STBoard(board.getWidth(), board.getHeight());
        stBoard.setFilename(board.getFileName());
        stBoard.setLevel(board.getLevel());
        board.sortIslands();
        for (Island island : board.getIslands()) {
            STIsland stIsland = new STIsland();
            stIsland.setId(island.getId());
            stIsland.setValue(island.getValue());
            stIsland.setRemainingValue(island.getValue());
            stIsland.setPosition(island.getPosition());
            stBoard.addIsland(stIsland);
        }
        for (int i = 0; i <= stBoard.getUnfinishedIslands().size(); i++) {
            for (int j = i + 1; j < stBoard.getUnfinishedIslands().size(); j++) {
                if (i != j) {
                    STIsland firstIsland = stBoard.getUnfinishedIslands().get(i);
                    STIsland secondIsland = stBoard.getUnfinishedIslands().get(j);

                    if (firstIsland.getPosition().getX() == secondIsland.getPosition().getX()
                            && firstIsland.getDownNeighbour() == null // && firstIsland.isHasDownNeighbour()
                            && secondIsland.getUpNeighbour() == null){ // && secondIsland.isHasUpNeighbour())  {
                        if (secondIsland.getPosition().getY() - firstIsland.getPosition().getY() > 1) {
                            firstIsland.setDownNeighbour(secondIsland);
                            secondIsland.setUpNeighbour(firstIsland);
                        } else {
//                            firstIsland.setHasDownNeighbour(false);
//                            secondIsland.setHasUpNeighbour(false);
                        }
                    } else if (firstIsland.getPosition().getY() == secondIsland.getPosition().getY()
                            && firstIsland.getRightNeighbour() == null //&& firstIsland.isHasRightNeighbour()
                            && secondIsland.getLeftNeighbour() == null //&& secondIsland.isHasLeftNeighbour()
                    ) {
                        if (secondIsland.getPosition().getX() - firstIsland.getPosition().getX() > 1) {
                            firstIsland.setRightNeighbour(secondIsland);
                            secondIsland.setLeftNeighbour(firstIsland);
                        } else {
//                            firstIsland.setHasRightNeighbour(false);
//                            secondIsland.setHasLeftNeighbour(false);
                        }
                    }
                }
            }

        }
        return stBoard;
    }

    public static Board convertSTBoardToBoard(STBoard stBoard) {
        Board board = new Board(stBoard.getWidth(), stBoard.getHeight());
        board.setFileName(stBoard.getFilename());
        board.setLevel(stBoard.getLevel());
        ArrayList<Bridge> bridges = new ArrayList<>();
        for (STBridge stBridge : stBoard.getBridges()) {
            bridges.add(stBridge.getBridge());
        }
        board.setBridges(bridges);
        ArrayList<Island> islands = new ArrayList<>();
        for (STIsland stIsland : stBoard.getUnfinishedIslands()) {
            islands.add(stIsland.getIsland());
        }
        for (STIsland stIsland : stBoard.getFinishedIslands()) {
            islands.add(stIsland.getIsland());
        }
        board.setIslands(islands);

        return board;
    }

    public static Board convertSTBoardNeighboursToBoard(STBoard stBoard) {
        Board board = new Board(stBoard.getWidth(), stBoard.getHeight());
        ArrayList<Bridge> bridges = new ArrayList<>();
        for (STBridge stBridge : stBoard.getBridges()) {
            bridges.add(stBridge.getBridge());
        }
        board.setBridges(bridges);
        for (STIsland i : stBoard.getUnfinishedIslands()) {
            if (i.getUpNeighbour() != null) {
                Bridge b = new Bridge();
                b.setEndIsland(i);
                b.setStartIsland(i.getUpNeighbour());
                b.setVertical(true);
                board.addBridge(b);
            }
//            if (i.getDownNeighbour() != null) {
//                Bridge b = new Bridge();
//                b.setStartIsland(i);
//                b.setEndIsland(i.getDownNeighbour());
//                b.setVertical(true);
//                board.addBridge(b);
//            }
            if (i.getRightNeighbour() != null) {
                Bridge b = new Bridge();
                b.setStartIsland(i);
                b.setEndIsland(i.getRightNeighbour());
                b.setVertical(false);
                board.addBridge(b);
            }
//            if (i.getLeftNeighbour() != null) {
//                Bridge b = new Bridge();
//                b.setEndIsland(i);
//                b.setStartIsland(i.getLeftNeighbour());
//                b.setVertical(false);
//                board.addBridge(b);
//            }
        }
        ArrayList<Island> islands = new ArrayList<>();
        for (STIsland stIsland : stBoard.getUnfinishedIslands()) {
            islands.add(stIsland.getIsland());
        }
        board.setIslands(islands);

        return board;
    }

}