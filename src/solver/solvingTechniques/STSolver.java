package solver.solvingTechniques;

import models.Board;
import models.Coordinates;
import solver.solvingTechniques.models.STBoard;
import solver.solvingTechniques.models.STBridge;
import solver.solvingTechniques.models.STIsland;

import java.util.ArrayList;
import java.util.HashSet;


public class STSolver {
    private static STBoard stBoard;
    private static int points = 0;

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public static Levels calculateGameLevel(Board board) {
        solve(board);
        if (points < 10)
            return Levels.EASY;
        else if (points < 15)
            return Levels.NORMAL;
        else
            return Levels.DIFFICULT;
    }

    public static Board solve(Board board) {
        board.sortIslands();
        stBoard = STBoardConverter.convertBoardToSTBoard(board);
        long startTime = System.currentTimeMillis(); //fetch starting time
        while ((stBoard.getUnfinishedIslands().size() > 0) && ((System.currentTimeMillis() - startTime) < 2500)) {
            callSolverFunction();
        }
        //points = points + (stBoard.getUnfinishedIslands().size() * 40);
//        int numberOfBridges = stBoard.getUnfinishedIslands().size() + stBoard.getFinishedIslands().size();
//        double level = (double) points / (double) numberOfBridges;
//        points = 0;
//        System.out.println(level);
//        return STBoardConverter.convertSTBoardNeighboursToBoard(stBoard);
        return STBoardConverter.convertSTBoardToBoard(stBoard);

    }

//    private static boolean isFinished(STIsland island, Direction direction) {
//        if (direction == Direction.UP) {
//
//        }
//        if (direction == Direction.DOWN) {
//            return island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished();
//        }
//        if (direction == Direction.RIGHT) {
//
//        }
//        if (direction == Direction.LEFT) {
//
//        }
//    }

    //1. Islands with a single neighbor:
    private static boolean islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue(STIsland island) {
        boolean isFinishedChanged = false;
        int n = 0;
        if (island.getRemainingValueOfUnfinishedNeighbours() == island.getRemainingValue()) {
            if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished()) {
                if (addBridges(island, island.getDownNeighbour(), island.getDownNeighbour().getRemainingValue() == 2, true))
                    isFinishedChanged = true;
                n++;
            }
            if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished()) {
                if (addBridges(island.getUpNeighbour(), island, island.getUpNeighbour().getRemainingValue() == 2, true))
                    isFinishedChanged = true;
                n++;
            }
            if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished()) {
                if (addBridges(island, island.getRightNeighbour(), island.getRightNeighbour().getRemainingValue() == 2, true))
                    isFinishedChanged = true;
                n++;
            }
            if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished()) {
                if (addBridges(island.getLeftNeighbour(), island, island.getLeftNeighbour().getRemainingValue() == 2, true))
                    isFinishedChanged = true;
                n++;
            }
            points += 25 * n;
        }
        return isFinishedChanged;
    }

    private static boolean islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges(STIsland island) {
        boolean isFinishedChanged = false;
        int n = 0;
        if (island.getNumberOfRemainingBridgesOfUnfinishedNeighbours() == island.getRemainingValue()) {
            if (island.getDownNeighbour() != null && island.getRemainingDownBridges() != 0) {
                if (addBridges(island, island.getDownNeighbour(), island.getRemainingDownBridges() == 2, true))
                    isFinishedChanged = true;
                n++;
            }
            if (island.getUpNeighbour() != null && island.getRemainingUpBridges() != 0) {
                if (addBridges(island.getUpNeighbour(), island, island.getRemainingUpBridges() == 2, true))
                    isFinishedChanged = true;
                n++;
            }
            if (island.getRightNeighbour() != null && island.getRemainingRightBridges() != 0) {
                if (addBridges(island, island.getRightNeighbour(), island.getRemainingRightBridges() == 2, true))
                    isFinishedChanged = true;
                n++;
            }
            if (island.getLeftNeighbour() != null && island.getRemainingLeftBridges() != 0) {
                if (addBridges(island.getLeftNeighbour(), island, island.getRemainingLeftBridges() == 2, true))
                    isFinishedChanged = true;
                n++;
            }
            points += 25 * n;
        }
        return isFinishedChanged;
    }

    //todo: pipa
    private static boolean threeInTheCornerFiveOnTheSideAndSevenInTheMiddle(STIsland island) {
        boolean isFinishedChanged = false;
        int n = 0;
        if ((island.getNumberOfUnfinishedNeighbours() == 2 && island.getRemainingValue() == 3)
                || (island.getNumberOfUnfinishedNeighbours() == 3 && island.getRemainingValue() == 5)
                || (island.getNumberOfUnfinishedNeighbours() == 4 && island.getRemainingValue() == 7)) {
//                3. Special cases of 3 in the corner, 5 on the side and 7 in the middle
            if (island.numberOfUnfinishedNeighboursWithValueOne() == 1) {
                boolean isDouble = false;
                if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished()) {
                    isDouble = island.getDownNeighbour().getValue() != 1;
                    if (addBridges(island, island.getDownNeighbour(), isDouble, isDouble))
                        isFinishedChanged = true;
                    n++;
                }
                if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished()) {
                    isDouble = island.getUpNeighbour().getValue() != 1;
                    if (addBridges(island.getUpNeighbour(), island, isDouble, isDouble))
                        isFinishedChanged = true;
                    n++;
                }
                if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished()) {
                    isDouble = island.getRightNeighbour().getValue() != 1;
                    if (addBridges(island, island.getRightNeighbour(), isDouble, isDouble))
                        isFinishedChanged = true;
                    n++;
                }
                if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished()) {
                    isDouble = island.getLeftNeighbour().getValue() != 1;
                    if (addBridges(island.getLeftNeighbour(), island, isDouble, isDouble))
                        isFinishedChanged = true;
                    n++;
                }
            }
//                2. Islands with 3 in the corner, 5 on the side and 7 in the middle:
            else {
                if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished()) {
                    if (addBridges(island, island.getDownNeighbour(), false, false))
                        isFinishedChanged = true;
                    n++;
                }
                if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished()) {
                    if (addBridges(island.getUpNeighbour(), island, false, false))
                        isFinishedChanged = true;
                    n++;
                }
                if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished()) {
                    if (addBridges(island, island.getRightNeighbour(), false, false))
                        isFinishedChanged = true;
                    n++;
                }
                if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished()) {
                    if (addBridges(island.getLeftNeighbour(), island, false, false))
                        isFinishedChanged = true;
                    n++;
                }
            }
            points += 10 * n;
        }
        return isFinishedChanged;
    }

    //4. Special case of 4 on the side:
    private static boolean fourOnTheSide(STIsland island) {
        boolean isDouble;
        boolean isFinishedChanged = false;

        if (island.getValue() == 4 && island.getNumberOfNeighbours() == 3 && island.numberOfUnfinishedNeighboursWithValueOne() == 2) {
            if (island.getDownNeighbour() != null) {
                isDouble = island.getDownNeighbour().getValue() != 1;
                if (addBridges(island, island.getDownNeighbour(), isDouble, isDouble))
                    isFinishedChanged = true;
            }
            if (island.getUpNeighbour() != null) {
                isDouble = island.getUpNeighbour().getValue() != 1;
                if (addBridges(island.getUpNeighbour(), island, isDouble, isDouble))
                    isFinishedChanged = true;
            }
            if (island.getRightNeighbour() != null) {
                isDouble = island.getRightNeighbour().getValue() != 1;
                if (addBridges(island, island.getRightNeighbour(), isDouble, isDouble))
                    isFinishedChanged = true;
            }
            if (island.getLeftNeighbour() != null) {
                isDouble = island.getLeftNeighbour().getValue() != 1;
                if (addBridges(island.getLeftNeighbour(), island, isDouble, isDouble))
                    isFinishedChanged = true;
            }
//            System.out.println("fourOnTheSide");
            points += 30 * 4;
        }

        return isFinishedChanged;
    }

    //5. Special case of 6 in the middle:
    private static boolean sixInTheMiddle(STIsland island) {
        boolean isDouble = false;
        boolean isFinishedChanged = false;
        if (island.getNumberOfNeighbours() == 4 && island.getValue() == 6 && island.numberOfUnfinishedNeighboursWithValueOne() == 1) {
            //isDouble = island.neighbourWithValueOne().isFinished();
            if (island.getDownNeighbour().getValue() != 1) {
                if (addBridges(island, island.getDownNeighbour(), isDouble, isDouble))
                    isFinishedChanged = true;
            }
            if (island.getUpNeighbour().getValue() != 1) {
                if (addBridges(island.getUpNeighbour(), island, isDouble, isDouble))
                    isFinishedChanged = true;
            }
            if (island.getRightNeighbour().getValue() != 1) {
                if (addBridges(island, island.getRightNeighbour(), isDouble, isDouble))
                    isFinishedChanged = true;
            }
            if (island.getLeftNeighbour().getValue() != 1) {
                if (addBridges(island.getLeftNeighbour(), island, isDouble, isDouble))
                    isFinishedChanged = true;
            }
//            System.out.println("sixInTheMiddle");
            points += 20 * 3;
        }
        return isFinishedChanged;
    }

    private static boolean justEnoughDoubleNeighbours(STIsland island) {
        boolean isFinishedChanged = false;
        int n = 0;
        if (island.getValue() == island.getNumberOfNeighbours() * 2) {
            if (island.getDownNeighbour() != null) {
                if (addBridges(island, island.getDownNeighbour(), true, true))
                    isFinishedChanged = true;
                n++;
            }
            if (island.getUpNeighbour() != null) {
                if (addBridges(island.getUpNeighbour(), island, true, true))
                    isFinishedChanged = true;
                n++;
            }
            if (island.getRightNeighbour() != null) {
                if (addBridges(island, island.getRightNeighbour(), true, true))
                    isFinishedChanged = true;
                n++;
            }
            if (island.getLeftNeighbour() != null) {
                if (addBridges(island.getLeftNeighbour(), island, true, true))
                    isFinishedChanged = true;
                n++;
            }
//            System.out.println("justEnoughDoubleNeighbours");
            points += 10 * n;
        }
        return isFinishedChanged;
    }

    private static boolean oneUnsolvedNeighbour(STIsland island) {
        boolean isFinishedChanged = false;
        if (island.numberOfUnfinishedNeighboursWithFreeBridges() == 1) {
            boolean isDouble = island.getRemainingValue() == 2;
            if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished() && island.getRemainingDownBridges() > 0) {
                if (addBridges(island, island.getDownNeighbour(), isDouble, true))
                    isFinishedChanged = true;
            }
            if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished() && island.getRemainingUpBridges() > 0) {
                if (addBridges(island.getUpNeighbour(), island, isDouble, true))
                    isFinishedChanged = true;
            }
            if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished()) {
                if (addBridges(island, island.getRightNeighbour(), isDouble, true))
                    isFinishedChanged = true;
            }
            if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished()) {
                if (addBridges(island.getLeftNeighbour(), island, isDouble, true))
                    isFinishedChanged = true;
            }
//            System.out.println("oneUnsolvedNeighbour");
            points += 10;
        }
        return isFinishedChanged;
    }

    private static boolean isolationOfATwoIslandSegment(STIsland island) {
        boolean isFinishedChanged = false;

        if (island.getValue() == 1 && island.numberOfUnfinishedNeighboursWithValueOne() >= 1
                || island.getValue() == 2 && island.numberOfNeighboursWithValueTwo() >= 1) {
            int value = island.getValue();
            if (island.getDownNeighbour() != null && island.getDownNeighbour().getValue() != value) {
                if (addBridges(island, island.getDownNeighbour(), false, false))
                    isFinishedChanged = true;
            }
            if (island.getUpNeighbour() != null && island.getUpNeighbour().getValue() != value) {
                if (addBridges(island.getUpNeighbour(), island, false, false))
                    isFinishedChanged = true;
            }
            if (island.getRightNeighbour() != null && island.getRightNeighbour().getValue() != value) {
                if (addBridges(island, island.getRightNeighbour(), false, false))
                    isFinishedChanged = true;
            }
            if (island.getLeftNeighbour() != null && island.getLeftNeighbour().getValue() != value) {
                if (addBridges(island.getLeftNeighbour(), island, false, false))
                    isFinishedChanged = true;
            }
//            System.out.println("isolationOfATwoIslandSegment");
            points += 60;
        }
        return isFinishedChanged;
    }

    private static boolean isolationOfASegment(STIsland island) {
        boolean isFinishedChanged = false;

        ArrayList<STIsland> neighboursWithoutBridges = island.getNeighboursWithoutBridges();
        if (neighboursWithoutBridges.size() > 0) {
            int sumOfValues = 0;
            for (STIsland i : neighboursWithoutBridges) {
                sumOfValues += i.getValue();
            }
            for (STIsland i : neighboursWithoutBridges) {
                if ((sumOfValues - i.getValue()) == island.getValue()) {
                    isFinishedChanged = addBridges(island, i, false, false);
                }
            }
        }
        return isFinishedChanged;
    }

    private static boolean leftoverTechniques(STIsland island) {
        boolean isFinishedChanged = false;
        int n = 0;
        if (island.getNumberOfUnfinishedNeighbours() == island.getRemainingValue() && island.numberOfNeighboursWithRemainingValueOne() == island.getRemainingValue() - 1) {
            if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished() && island.getDownNeighbour().getRemainingValue() != 1) {
                if (addBridges(island, island.getDownNeighbour(), false, true))
                    isFinishedChanged = true;
                n++;
            }
            if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished() && island.getUpNeighbour().getRemainingValue() != 1) {
                if (addBridges(island.getUpNeighbour(), island, false, true))
                    isFinishedChanged = true;
                n++;
            }
            if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished() && island.getRightNeighbour().getRemainingValue() != 1) {
                if (addBridges(island, island.getRightNeighbour(), false, true))
                    isFinishedChanged = true;
                n++;
            }
            if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished() && island.getLeftNeighbour().getRemainingValue() != 1) {
                if (addBridges(island.getLeftNeighbour(), island, false, true))
                    isFinishedChanged = true;
                n++;
            }
            points += 25 * n;
//            System.out.println("leftoverTechniques");
        }
        return isFinishedChanged;
    }

    private static boolean isolationWhenASegmentConnectsToAnIsland(STIsland island) {
        boolean isFinishedChanged = false;
        if ((island.getRemainingValue() == 1) && (island.numberOfUnfinishedNeighboursWithFreeBridges() - island.numberOfNeighboursWithValue(1) == 1)) {
            HashSet<STIsland> segment = getSegment(island, new HashSet<>());
            int remainingValue = 0;
            for (STIsland i : segment) {
                remainingValue += i.getRemainingValue();
            }
            if (remainingValue == 1) {

                if (island.getDownNeighbour() != null && island.getDownNeighbour().getValue() != 1) {
                    isFinishedChanged = addBridges(island, island.getDownNeighbour(), false, false);
                } else if (island.getUpNeighbour() != null && island.getUpNeighbour().getValue() != 1) {
                    isFinishedChanged = addBridges(island, island.getUpNeighbour(), false, false);
                } else if (island.getRightNeighbour() != null && island.getRightNeighbour().getValue() != 1) {
                    isFinishedChanged = addBridges(island, island.getRightNeighbour(), false, false);
                } else if (island.getLeftNeighbour() != null && island.getLeftNeighbour().getValue() != 1) {
                    isFinishedChanged = addBridges(island, island.getLeftNeighbour(), false, false);
                }
            }
        }
        return isFinishedChanged;
    }

    private static boolean isolationWhenASegmentConnectsToAnotherSegment(STIsland island) {
        boolean isFinishedChanged = false;
        if (island.getNumberOfUnfinishedNeighbours() == 2) {
            HashSet<STIsland> islandSegment = getSegment(island, new HashSet<>());
            int remainingValue = 0;
            for (STIsland i : islandSegment) {
                remainingValue += i.getRemainingValue();
            }
            if (remainingValue != island.getRemainingValue()) {
                return false;
            }
            HashSet<STIsland> firstNeighbourSegment = new HashSet<>();
            HashSet<STIsland> secondNeighbourSegment = new HashSet<>();
            STIsland firstNeighbour = null;
            STIsland secondNeighbour = null;
            int firstRemainingValue = 0;
            int secondRemainingValue = 0;

            if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished()) {
                firstNeighbour = island.getDownNeighbour();
                firstNeighbourSegment = getSegment(island.getDownNeighbour(), new HashSet<>());
            }
            if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished()) {
                if (firstNeighbour != null) {
                    secondNeighbour = island.getUpNeighbour();
                    secondNeighbourSegment = getSegment(island.getUpNeighbour(), new HashSet<>());
                } else {
                    firstNeighbour = island.getUpNeighbour();
                    firstNeighbourSegment = getSegment(island.getUpNeighbour(), new HashSet<>());
                }
            }
            if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished()) {
                if (firstNeighbour != null) {
                    secondNeighbour = island.getRightNeighbour();
                    secondNeighbourSegment = getSegment(island.getRightNeighbour(), new HashSet<>());
                } else {
                    firstNeighbour = island.getRightNeighbour();
                    firstNeighbourSegment = getSegment(island.getRightNeighbour(), new HashSet<>());
                }
            }
            if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished()) {
                secondNeighbour = island.getRightNeighbour();
                secondNeighbourSegment = getSegment(island.getRightNeighbour(), new HashSet<>());
            }
            for (STIsland i : firstNeighbourSegment) {
                firstRemainingValue += i.getRemainingValue();
            }
            for (STIsland i : secondNeighbourSegment) {
                secondRemainingValue += i.getRemainingValue();
            }
            if (remainingValue == secondRemainingValue) {
                isFinishedChanged = addBridges(island, firstNeighbour, false, false);
            }
            if (remainingValue == firstRemainingValue) {
                isFinishedChanged = addBridges(island, secondNeighbour, false, false);
            }
        }
        return isFinishedChanged;
    }

    private static void checkIntersectingBridges() {
        for (STIsland island : stBoard.getUnfinishedIslands()) {
            if (island.getDownNeighbour() != null)
                if (areBridgesIntersect(island, island.getDownNeighbour())) {
                    island.getDownNeighbour().setUpNeighbour(null);
                    island.setDownNeighbour(null);
                }
            if (island.getRightNeighbour() != null)
                if (areBridgesIntersect(island, island.getRightNeighbour())) {
                    island.getRightNeighbour().setLeftNeighbour(null);
                    island.setRightNeighbour(null);
                }
        }
    }

    private static boolean addBridges(STIsland startIsland, STIsland endIsland, boolean isDouble, boolean canBeDouble) {
        boolean isVertical = startIsland.getPosition().getX() == endIsland.getPosition().getX();
        if ((startIsland.getPosition().getY() == endIsland.getPosition().getY()) && (startIsland.getPosition().getX() > endIsland.getPosition().getX())) {
            STIsland tmp = startIsland;
            startIsland = endIsland;
            endIsland = tmp;
        } else if ((startIsland.getPosition().getX() == endIsland.getPosition().getX()) && (startIsland.getPosition().getY() > endIsland.getPosition().getY())) {
            STIsland tmp = startIsland;
            startIsland = endIsland;
            endIsland = tmp;
        }

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

    private static HashSet<STIsland> getSegment(STIsland island, HashSet<STIsland> segment) {
        if (!segment.contains(island)) {
            segment.add(island);
            if (island.getDownNeighbour() != null) {
                getSegment(island.getDownNeighbour(), segment);
            }
            if (island.getUpNeighbour() != null) {
                getSegment(island.getUpNeighbour(), segment);
            }
            if (island.getRightNeighbour() != null) {
                getSegment(island.getRightNeighbour(), segment);
            }
            if (island.getLeftNeighbour() != null) {
                getSegment(island.getLeftNeighbour(), segment);
            }
        }
        return segment;
    }


    private static void callSolverFunction() {
        boolean changed = false;

        checkIntersectingBridges();
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = leftoverTechniques(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                System.out.println("leftoverTechniques");
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = justEnoughDoubleNeighbours(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                System.out.println("justEnoughDoubleNeighbours");
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = oneUnsolvedNeighbour(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                System.out.println("oneUnsolvedNeighbour");
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                System.out.println("islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue");
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = fourOnTheSide(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                System.out.println("fourOnTheSide");
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = sixInTheMiddle(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                System.out.println("sixInTheMiddle");
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = threeInTheCornerFiveOnTheSideAndSevenInTheMiddle(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                System.out.println("threeInTheCornerFiveOnTheSideAndSevenInTheMiddle");
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                System.out.println("islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges");
                return;
            }
        }
//
//
//        i = 0;
//        idx = 0;
//        while (i < stBoard.getUnfinishedIslands().size()) {
//            for (i = idx; i < stBoard.getUnfinishedIslands().size(); i++) {
//                changed = isolationOfATwoIslandSegment(stBoard.getUnfinishedIslands().get(i));
//                if (changed) {
//                    idx = i;
//                    System.out.println("isolationOfATwoIslandSegment");
//                    return;
//                }
//            }
//        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = isolationOfASegment(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                System.out.println("isolationOfASegment");
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = isolationWhenASegmentConnectsToAnIsland(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                System.out.println("isolationWhenASegmentConnectsToAnIsland");
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = isolationWhenASegmentConnectsToAnotherSegment(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                System.out.println("isolationWhenASegmentConnectsToAnotherSegment");
                return;
            }
        }
    }
}
