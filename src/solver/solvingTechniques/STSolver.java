package solver.solvingTechniques;

import models.Board;
import models.Coordinates;
import solver.solvingTechniques.models.STBoard;
import solver.solvingTechniques.models.STBridge;
import solver.solvingTechniques.models.STIsland;

public class STSolver {
    private static STBoard stBoard;
    private static int points = 0;

    public static Board solve(Board board) {
        board.sortIslands();
        stBoard = STBoardConverter.convertBoardToSTBoard(board);

        int i = 0;
        int idx = 0;
        boolean changed = false;
        while (i < stBoard.getUnfinishedIslands().size()) {
            for (i = idx; i < stBoard.getUnfinishedIslands().size(); i++) {
                changed = threeInTheCornerFiveOnTheSideAndSevenInTheMiddle(stBoard.getUnfinishedIslands().get(i));
                if (changed) {
                    idx = i;
                    break;
                }
            }
        }
        while (i < stBoard.getUnfinishedIslands().size()) {
            for (i = idx; i < stBoard.getUnfinishedIslands().size(); i++) {
                changed = justEnoughDoubleNeighbours(stBoard.getUnfinishedIslands().get(i));
                if (changed) {
                    idx = i;
                    break;
                }
            }
        }
        i = 0;
        idx = 0;

        i = 0;
        idx = 0;
        while (i < stBoard.getUnfinishedIslands().size()) {
            for (i = idx; i < stBoard.getUnfinishedIslands().size(); i++) {
                changed = fourOnTheSide(stBoard.getUnfinishedIslands().get(i));
                if (changed) {
                    idx = i;
                    break;
                }
            }
        }
        i = 0;
        idx = 0;
        while (i < stBoard.getUnfinishedIslands().size()) {
            for (i = idx; i < stBoard.getUnfinishedIslands().size(); i++) {
                changed = sixInTheMiddle(stBoard.getUnfinishedIslands().get(i));
                if (changed) {
                    idx = i;
                    break;
                }
            }
        }
        i = 0;
        idx = 0;
        while (i < stBoard.getUnfinishedIslands().size()) {
            for (i = idx; i < stBoard.getUnfinishedIslands().size(); i++) {
                changed = oneUnsolvedNeighbour(stBoard.getUnfinishedIslands().get(i));
                if (changed) {
                    idx = i;
                    break;
                }
            }
        }
        i = 0;
        idx = 0;
        while (i < stBoard.getUnfinishedIslands().size()) {
            for (i = idx; i < stBoard.getUnfinishedIslands().size(); i++) {
                changed = islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue(stBoard.getUnfinishedIslands().get(i));
                if (changed) {
                    idx = i;
                    break;
                }
            }
        }
        i = 0;
        idx = 0;
        while (i < stBoard.getUnfinishedIslands().size()) {
            for (i = idx; i < stBoard.getUnfinishedIslands().size(); i++) {
                changed = oneUnsolvedNeighbour(stBoard.getUnfinishedIslands().get(i));
                if (changed) {
                    idx = i;
                    break;
                }
            }
        }

        points = points + (stBoard.getUnfinishedIslands().size() * 40);
        int numberOfBridges = stBoard.getUnfinishedIslands().size() + stBoard.getFinishedIslands().size();
        double level = (double) points / (double) numberOfBridges;
        points = 0;
        System.out.println(level);
//        return STBoardConverter.convertSTBoardNeighboursToBoard(stBoard);
        return STBoardConverter.convertSTBoardToBoard(stBoard);

    }


    //1. Islands with a single neighbor:
    private static boolean islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue(STIsland island) {
        boolean isFinishedChanged = false;
        if (island.getRemainingValueOfUnfinishedNeighbours() == island.getRemainingValue()) {
            if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished()) {
                if (addBridges(island, island.getDownNeighbour(), island.getDownNeighbour().getRemainingValue() == 2, true, true))
                    isFinishedChanged = true;
            }
            if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished()) {
                if (addBridges(island.getUpNeighbour(), island, island.getUpNeighbour().getRemainingValue() == 2, true, true))
                    isFinishedChanged = true;
            }
            if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished()) {
                if (addBridges(island, island.getRightNeighbour(), island.getRightNeighbour().getRemainingValue() == 2, false, true))
                    isFinishedChanged = true;
            }
            if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished()) {
                if (addBridges(island.getLeftNeighbour(), island, island.getLeftNeighbour().getRemainingValue() == 2, false, true))
                    isFinishedChanged = true;
            }
            points += 25;
        }
        return isFinishedChanged;
    }

    //todo: pipa
    private static boolean threeInTheCornerFiveOnTheSideAndSevenInTheMiddle(STIsland island) {
        boolean isFinishedChanged = false;
        if ((island.getNumberOfNeighbours() == 2 && island.getValue() == 3)
                || (island.getNumberOfNeighbours() == 3 && island.getValue() == 5)
                || (island.getNumberOfNeighbours() == 4 && island.getValue() == 7)) {
//                3. Special cases of 3 in the corner, 5 on the side and 7 in the middle
            if (island.numberOfNeighboursWithValueOne() == 1) {
                boolean isDouble = false;
                if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished()) {
                    isDouble = island.getDownNeighbour().getValue() != 1;
                    if (addBridges(island, island.getDownNeighbour(), isDouble, true, isDouble))
                        isFinishedChanged = true;
                }
                if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished()) {
                    isDouble = island.getUpNeighbour().getValue() != 1;
                    if (addBridges(island.getUpNeighbour(), island, isDouble, true, isDouble))
                        isFinishedChanged = true;
                }
                if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished()) {
                    isDouble = island.getRightNeighbour().getValue() != 1;
                    if (addBridges(island, island.getRightNeighbour(), isDouble, false, isDouble))
                        isFinishedChanged = true;
                }
                if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished()) {
                    isDouble = island.getLeftNeighbour().getValue() != 1;
                    if (addBridges(island.getLeftNeighbour(), island, isDouble, false, isDouble))
                        isFinishedChanged = true;
                }
            }
//                2. Islands with 3 in the corner, 5 on the side and 7 in the middle:
            else {
                if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished()) {
                    if (addBridges(island, island.getDownNeighbour(), false, true, false))
                        isFinishedChanged = true;
                }
                if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished()) {
                    if (addBridges(island.getUpNeighbour(), island, false, true, false))
                        isFinishedChanged = true;
                }
                if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished()) {
                    if (addBridges(island, island.getRightNeighbour(), false, false, false))
                        isFinishedChanged = true;
                }
                if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished()) {
                    if (addBridges(island.getLeftNeighbour(), island, false, false, false))
                        isFinishedChanged = true;
                }
            }
            points += 15;
        }
        return isFinishedChanged;
    }

    //4. Special case of 4 on the side:
    private static boolean fourOnTheSide(STIsland island) {
        boolean isDouble;
        boolean isFinishedChanged = false;

        if (island.getValue() == 4 && island.getNumberOfNeighbours() == 3 && island.numberOfNeighboursWithValueOne() == 2) {
            if (island.getDownNeighbour() != null) {
                isDouble = island.getDownNeighbour().getValue() != 1;
                if (addBridges(island, island.getDownNeighbour(), isDouble, true, isDouble))
                    isFinishedChanged = true;
            }
            if (island.getUpNeighbour() != null) {
                isDouble = island.getUpNeighbour().getValue() != 1;
                if (addBridges(island.getUpNeighbour(), island, isDouble, true, isDouble))
                    isFinishedChanged = true;
            }
            if (island.getRightNeighbour() != null) {
                isDouble = island.getRightNeighbour().getValue() != 1;
                if (addBridges(island, island.getRightNeighbour(), isDouble, false, isDouble))
                    isFinishedChanged = true;
            }
            if (island.getLeftNeighbour() != null) {
                isDouble = island.getLeftNeighbour().getValue() != 1;
                if (addBridges(island.getLeftNeighbour(), island, isDouble, false, isDouble))
                    isFinishedChanged = true;
            }
            points += 30;
        }

        return isFinishedChanged;
    }

    //5. Special case of 6 in the middle:
    private static boolean sixInTheMiddle(STIsland island) {
        boolean isDouble = false;
        boolean isFinishedChanged = false;
        if (island.getNumberOfNeighbours() == 4 && island.getValue() == 6 && island.numberOfNeighboursWithValueOne() == 1) {
            //isDouble = island.neighbourWithValueOne().isFinished();
            if (island.getDownNeighbour().getValue() != 1) {
                if (addBridges(island, island.getDownNeighbour(), isDouble, true, isDouble))
                    isFinishedChanged = true;
            }
            if (island.getUpNeighbour().getValue() != 1) {
                if (addBridges(island.getUpNeighbour(), island, isDouble, true, isDouble))
                    isFinishedChanged = true;
            }
            if (island.getRightNeighbour().getValue() != 1) {
                if (addBridges(island, island.getRightNeighbour(), isDouble, false, isDouble))
                    isFinishedChanged = true;
            }
            if (island.getLeftNeighbour().getValue() != 1) {
                if (addBridges(island.getLeftNeighbour(), island, isDouble, false, isDouble))
                    isFinishedChanged = true;
            }
            points += 20;
        }
        return isFinishedChanged;
    }

    private static boolean justEnoughDoubleNeighbours(STIsland island) {
        boolean isFinishedChanged = false;
        if (island.getRemainingValue() == island.getNumberOfNeighbours() * 2) {
            if (island.getDownNeighbour() != null) {
                if (addBridges(island, island.getDownNeighbour(), true, true, true))
                    isFinishedChanged = true;
            }
            if (island.getUpNeighbour() != null) {
                if (addBridges(island.getUpNeighbour(), island, true, true, true))
                    isFinishedChanged = true;
            }
            if (island.getRightNeighbour() != null) {
                if (addBridges(island, island.getRightNeighbour(), true, false, true))
                    isFinishedChanged = true;
            }
            if (island.getLeftNeighbour() != null) {
                if (addBridges(island.getLeftNeighbour(), island, true, false, true))
                    isFinishedChanged = true;
            }
            points += 10;
        }
        return isFinishedChanged;
    }

    private static boolean oneUnsolvedNeighbour(STIsland island) {
        boolean isFinishedChanged = false;
        if (island.numberOfUnfinishedNeighbours() == 1) {
            boolean isDouble = island.getRemainingValue() == 2;
            if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished()) {
                if (addBridges(island, island.getDownNeighbour(), isDouble, true, true))
                    isFinishedChanged = true;
            }
            if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished()) {
                if (addBridges(island.getUpNeighbour(), island, isDouble, true, true))
                    isFinishedChanged = true;
            }
            if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished()) {
                if (addBridges(island, island.getRightNeighbour(), isDouble, false, true))
                    isFinishedChanged = true;
            }
            if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished()) {
                if (addBridges(island.getLeftNeighbour(), island, isDouble, false, true))
                    isFinishedChanged = true;
            }
            points += 20;
        }
        return isFinishedChanged;
    }

    public static boolean isolationOfATwoIslandSegment(STIsland island){
        boolean isFinishedChanged = false;

        if(island.getValue() == 1 && island.numberOfNeighboursWithValueOne() >= 1
        || island.getValue() == 2 && island.numberOfNeighboursWithValueTwo() >= 1) {
            int value = island.getValue();
            if (island.getDownNeighbour() != null && island.getDownNeighbour().getValue() != value) {
                if (addBridges(island, island.getDownNeighbour(), false, true, false))
                    isFinishedChanged = true;
            }
            if (island.getUpNeighbour() != null && island.getUpNeighbour().getValue() != value) {
                if (addBridges(island.getUpNeighbour(), island, false, true, false))
                    isFinishedChanged = true;
            }
            if (island.getRightNeighbour() != null && island.getRightNeighbour().getValue() != value) {
                if (addBridges(island, island.getRightNeighbour(), false, false, false))
                    isFinishedChanged = true;
            }
            if (island.getLeftNeighbour() != null && island.getLeftNeighbour().getValue() != value) {
                if (addBridges(island.getLeftNeighbour(), island, false, false, false))
                    isFinishedChanged = true;
            }
        }
        return isFinishedChanged;
    }


    private static boolean addBridges(STIsland startIsland, STIsland endIsland, boolean isDouble, boolean isVertical, boolean canBeDouble) {
        if (!areBridgesIntersect(startIsland, endIsland)) {
            if (isVertical) {
                if (startIsland.getDownBridges() != null) {
                    if (canBeDouble) {
                        startIsland.getDownBridges().setDouble(true);
                    }
                } else {
                    STBridge bridge = new STBridge();
                    bridge.setStartIsland(startIsland);
                    bridge.setEndIsland(endIsland);
                    bridge.setDouble(isDouble);
                    bridge.setVertical(true);
                    startIsland.setDownBridges(bridge);
                    endIsland.setUpBridges(bridge);
                    stBoard.addBridge(bridge);
                }
            } else {
                if (startIsland.getRightBridges() != null) {
                    if (canBeDouble) {
                        startIsland.getRightBridges().setDouble(true);
                    }
                } else {
                    STBridge bridge = new STBridge();
                    bridge.setStartIsland(startIsland);
                    bridge.setEndIsland(endIsland);
                    bridge.setDouble(isDouble);
                    bridge.setVertical(false);
                    startIsland.setRightBridges(bridge);
                    endIsland.setLeftBridges(bridge);
                    stBoard.addBridge(bridge);
                }
            }
            if (startIsland.checkRemainingValue() == 0) {
                startIsland.setFinished(true);
                stBoard.getFinishedIslands().add(startIsland);
                stBoard.getUnfinishedIslands().remove(startIsland);
            }
            if (endIsland.checkRemainingValue() == 0) {
                endIsland.setFinished(true);
                stBoard.getFinishedIslands().add(endIsland);
                stBoard.getUnfinishedIslands().remove(endIsland);
            }
        } else {
            if (isVertical) {
                startIsland.setDownNeighbour(null);
                endIsland.setUpNeighbour(null);
            } else {
                startIsland.setRightBridges(null);
                endIsland.setLeftBridges(null);
            }

        }
        return startIsland.isFinished() || endIsland.isFinished();
    }

    private static boolean areBridgesIntersect(STIsland startIsland, STIsland endIsland) {
        Coordinates A, B, C, D;
        A = startIsland.getPosition();
        B = endIsland.getPosition();
        for (STBridge bridge : stBoard.getBridges()) {
            C = bridge.getStartIsland().getPosition();
            D = bridge.getEndIsland().getPosition();

            if (A.getX() == B.getX()) {
                if (C.getX() == D.getX() && A.getX() == C.getX()) {
                    if ((A.getY() < C.getY() && C.getY() < B.getY()) || (A.getY() < D.getY() && D.getY() < B.getY()))
                        return true;
                } else if (C.getY() == D.getY()) {
                    if (C.getX() < A.getX() && A.getX() < D.getX() && A.getY() < C.getY() && C.getY() < B.getY())
                        return true;
                }
            } else if (A.getY() == B.getY()) {
                if (C.getX() == D.getX()) {
                    if (C.getY() < A.getY() && A.getY() < D.getY() && A.getX() < C.getX() && C.getX() < B.getX())
                        return true;
                } else if (C.getY() == D.getY() && C.getY() == A.getY()) {
                    if ((A.getX() < C.getX() && C.getX() < B.getX()) || (A.getX() < D.getX() && D.getX() < B.getX()))
                        return true;
                }
            }
        }
        return false;
    }
}
