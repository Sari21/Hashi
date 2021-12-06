package solver.solvingTechniques;

import models.Board;
import models.Coordinates;
import solver.solvingTechniques.models.STBoard;
import solver.solvingTechniques.models.STBridge;
import solver.solvingTechniques.models.STIsland;

public class STSolver {
    private static STBoard stBoard;

    public static Board solve(Board board) {
        board.sortIslands();
        stBoard = STBoardConverter.convertBoardToSTBoard(board);
        justEnoughNeighbour();
        oneUnsolvedNeighbour();
        islandsWithASingleNeighbor();
        threeInTheCornerFiveOnTheSideAndSevenInTheMiddle();
        fourOnTheSide();
        sixInTheMiddle();
        justEnoughNeighbour();
        oneUnsolvedNeighbour();
        islandsWithASingleNeighbor();
        threeInTheCornerFiveOnTheSideAndSevenInTheMiddle();
        fourOnTheSide();
        sixInTheMiddle();
//        return STBoardConverter.convertSTBoardNeighboursToBoard(stBoard);
        return STBoardConverter.convertSTBoardToBoard(stBoard);

    }

    //1. Islands with a single neighbor: todo: ezt lefedi a oneUnfinishedNeighbour
    private static void islandsWithASingleNeighbor() {
        for (STIsland island : stBoard.getUnfinishedIslands()) {
            if (island.numberOfNeighbours() == 1) {
                if (island.getDownNeighbour() != null) {
                    addBridges(island, island.getDownNeighbour(), island.getValue() == 2, true, false);

                } else if (island.getUpNeighbour() != null) {
                    addBridges(island.getUpNeighbour(), island, island.getValue() == 2, true, false);
                } else if (island.getRightNeighbour() != null) {
                    addBridges(island, island.getRightNeighbour(), island.getValue() == 2, false, false);
                } else if (island.getLeftNeighbour() != null) {
                    addBridges(island.getLeftNeighbour(), island, island.getValue() == 2, false, false);
                }
            }
        }
    }

    private static void threeInTheCornerFiveOnTheSideAndSevenInTheMiddle() {
        for (STIsland island : stBoard.getUnfinishedIslands()) {
            if ((island.numberOfNeighbours() == 2 && island.getValue() == 3)
                    || (island.numberOfNeighbours() == 3 && island.getValue() == 5)
                    || (island.numberOfNeighbours() == 4 && island.getValue() == 7)) {
//                3. Special cases of 3 in the corner, 5 on the side and 7 in the middle
                if (island.numberOfNeighboursWithValueOne() == 1) {
                    boolean isDouble = false;
                    if (island.getDownNeighbour() != null) {
                        isDouble = island.getDownNeighbour().getValue() != 1;
                        addBridges(island, island.getDownNeighbour(), isDouble, true, isDouble);
                    }
                    if (island.getUpNeighbour() != null) {
                        isDouble = island.getUpNeighbour().getValue() != 1;
                        addBridges(island.getUpNeighbour(), island, isDouble, true, isDouble);
                    }
                    if (island.getRightNeighbour() != null) {
                        isDouble = island.getRightNeighbour().getValue() != 1;
                        addBridges(island, island.getRightNeighbour(), isDouble, false, isDouble);
                    }
                    if (island.getLeftNeighbour() != null) {
                        isDouble = island.getLeftNeighbour().getValue() != 1;
                        addBridges(island.getLeftNeighbour(), island, isDouble, false, isDouble);
                    }
                }
//                2. Islands with 3 in the corner, 5 on the side and 7 in the middle:
                else {
                    if (island.getDownNeighbour() != null) {
                        addBridges(island, island.getDownNeighbour(), false, true, false);
                    }
                    if (island.getUpNeighbour() != null) {
                        addBridges(island.getUpNeighbour(), island, false, true, false);
                    }
                    if (island.getRightNeighbour() != null) {
                        addBridges(island, island.getRightNeighbour(), false, false, false);
                    }
                    if (island.getLeftNeighbour() != null) {
                        addBridges(island.getLeftNeighbour(), island, false, false, false);
                    }
                }
            }
        }
    }

    //4. Special case of 4 on the side:
    private static void fourOnTheSide() {
        boolean isDouble;
        for (STIsland island : stBoard.getUnfinishedIslands()) {
            if (island.numberOfNeighbours() == 3 && island.getValue() == 4 && island.numberOfNeighboursWithValueOne() == 2) {
                if (island.getDownNeighbour() != null) {
                    isDouble = island.getDownNeighbour().getValue() != 1;
                    addBridges(island, island.getDownNeighbour(), isDouble, true, isDouble);
                }
                if (island.getUpNeighbour() != null) {
                    isDouble = island.getUpNeighbour().getValue() != 1;
                    addBridges(island.getUpNeighbour(), island, isDouble, true, isDouble);
                }
                if (island.getRightNeighbour() != null) {
                    isDouble = island.getRightNeighbour().getValue() != 1;
                    addBridges(island, island.getRightNeighbour(), isDouble, false, isDouble);
                }
                if (island.getLeftNeighbour() != null) {
                    isDouble = island.getLeftNeighbour().getValue() != 1;
                    addBridges(island.getLeftNeighbour(), island, isDouble, false, isDouble);
                }

            }
        }
    }

    //5. Special case of 6 in the middle:
    private static void sixInTheMiddle() {
        boolean isDouble = false;
        for (STIsland island : stBoard.getUnfinishedIslands()) {
            if (island.numberOfNeighbours() == 4 && island.getValue() == 6 && island.numberOfNeighboursWithValueOne() == 1) {
                //isDouble = island.neighbourWithValueOne().isFinished();
                if (island.getDownNeighbour().getValue() != 1) {
                    addBridges(island, island.getDownNeighbour(), isDouble, true, isDouble);
                }
                if (island.getUpNeighbour().getValue() != 1) {
                    addBridges(island.getUpNeighbour(), island, isDouble, true, isDouble);
                }
                if (island.getRightNeighbour().getValue() != 1) {
                    addBridges(island, island.getRightNeighbour(), isDouble, false, isDouble);
                }
                if (island.getLeftNeighbour().getValue() != 1) {
                    addBridges(island.getLeftNeighbour(), island, isDouble, false, isDouble);
                }

            }
        }
    }

    private static void justEnoughNeighbour() {
        for (STIsland island : stBoard.getUnfinishedIslands()) {
            if (island.getValue() == island.numberOfNeighbours() * 2) {
                if (island.getDownNeighbour() != null) {
                    addBridges(island, island.getDownNeighbour(), true, true, true);
                }
                if (island.getUpNeighbour() != null) {
                    addBridges(island.getUpNeighbour(), island, true, true, true);
                }
                if (island.getRightNeighbour() != null) {
                    addBridges(island, island.getRightNeighbour(), true, false, true);
                }
                if (island.getLeftNeighbour() != null) {
                    addBridges(island.getLeftNeighbour(), island, true, false, true);
                }
            }
        }

    }

    private static void oneUnsolvedNeighbour() {
        for (STIsland island : stBoard.getUnfinishedIslands()) {
            if (island.numberOfUnfinishedNeighbours() == 1) {
                boolean isDouble = island.getValue() - island.numberOfBridges() == 2;
                if (island.getDownNeighbour() != null) {
                    addBridges(island, island.getDownNeighbour(), isDouble, true, true);
                }
                if (island.getUpNeighbour() != null) {
                    addBridges(island.getUpNeighbour(), island, isDouble, true, true);
                }
                if (island.getRightNeighbour() != null) {
                    addBridges(island, island.getRightNeighbour(), isDouble, false, true);
                }
                if (island.getLeftNeighbour() != null) {
                    addBridges(island.getLeftNeighbour(), island, isDouble, false, true);
                }
            }
        }
    }

    private static void addBridges(STIsland startIsland, STIsland endIsland, boolean isDouble, boolean isVertical, boolean canBeDouble) {
        if (!areBridgesIntersect(startIsland.getPosition(), endIsland.getPosition())) {
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
            if (startIsland.checkNumberOfBridges()) {
//                stBoard.getFinishedIslands().add(startIsland);
//                stBoard.getUnfinishedIslands().remove(startIsland);
            }
            if (endIsland.checkNumberOfBridges()) {
//                stBoard.getFinishedIslands().add(endIsland);
//                stBoard.getUnfinishedIslands().remove(endIsland);
            }

        }
    }

    private static boolean areBridgesIntersect(Coordinates A, Coordinates B) {
        Coordinates C, D;
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
