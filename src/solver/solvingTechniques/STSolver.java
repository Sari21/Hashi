package solver.solvingTechniques;

import models.Board;
import models.Bridge;
import models.Coordinates;
import models.Island;
import services.FileService;
import solver.solvingTechniques.models.STBoard;
import solver.solvingTechniques.models.STBridge;
import solver.solvingTechniques.models.STIsland;

import java.util.HashSet;
import java.util.Random;

import static interfaces.CsvPrintable.CSV_SEPARATOR;
import static solver.solvingTechniques.models.Direction.*;


public class STSolver {
    private static STBoard stBoard;
    private static STBoard stBoardBeforeRandom;
    private static int points = 0;
    private static double[] features;

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private static int leftoverTechniques, justEnoughDoubleNeighbours, oneUnsolvedNeighbour, islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue, fourOnTheSide = 0;
    private static int sixInTheMiddle, threeInTheCornerFiveOnTheSideAndSevenInTheMiddle, islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges, isolationOfATwoIslandSegment = 0;
    private static int isolationOfASegment,  isolationWhenASegmentConnectsToAnotherSegment, addRandomBridge = 0;

    public static Levels calculateGameLevel(Board board) {
        solve(board);
        if (points < 10)
            return Levels.EASY;
        else if (points < 15)
            return Levels.MEDIUM;
        else
            return Levels.HARD;
    }

    public static Board solve(Board board) {
        System.out.println("-------------------1");
        leftoverTechniques = 0;
        justEnoughDoubleNeighbours = 0;
        oneUnsolvedNeighbour = 0;
        islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue = 0;
        fourOnTheSide = 0;
        sixInTheMiddle = 0;
        threeInTheCornerFiveOnTheSideAndSevenInTheMiddle = 0;
        islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges = 0;
        isolationOfATwoIslandSegment = 0;
        isolationOfASegment = 0;
        isolationWhenASegmentConnectsToAnotherSegment = 0;
        addRandomBridge = 0;
        int sumValues = 0;
//        for (Island i : board.getIslands()) {
//            System.out.println(i.getId() + " " + i.getValue() + " " + i.getPosition());
//            sumValues += i.getValue();
//        }
        board.sortIslands();
        stBoard = STBoardConverter.convertBoardToSTBoard(board);
        stBoard.setLevel(board.getLevel());
        long startTime = System.currentTimeMillis(); //fetch starting time
        int retry = 0;
        System.out.println("-------------------2");
        while ((stBoard.getUnfinishedIslands().size() > 0) && ((System.currentTimeMillis() - startTime) < 3000)) {
            callSolverFunctions();
            if (stBoardBeforeRandom != null && !stBoard.checkFinishedIslands()) {
                stBoard = stBoardBeforeRandom;
                retry++;
                if(retry >= 10){
                    break;
                }
            }

        }
        System.out.println("-------------------3");
        long endTime = System.currentTimeMillis();
        //points = points + (stBoard.getUnfinishedIslands().size() * 50);
        int numberOfIslands = board.getIslands().size();
        double level = (double) points / (double) numberOfIslands;
//        System.out.println(points);
//        points = 0;
//        System.out.println(level);
//        return STBoardConverter.convertSTBoardNeighboursToBoard(stBoard);
        int remainingValue = 0;
        int neighboursWithBridges = 0;
        for (STIsland i : stBoard.getUnfinishedIslands()) {
            remainingValue += i.getRemainingValue();
            neighboursWithBridges += i.getNumberOfNeighboursWithBridges();
        }
        for (STIsland i : stBoard.getFinishedIslands()) {
            remainingValue += i.getRemainingValue();
            neighboursWithBridges += i.getNumberOfNeighboursWithBridges();
        }
        int sumBridgeLength = 0;
        for (STBridge b : stBoard.getBridges()) {
            sumBridgeLength += b.getLength();
        }
        double averageBridgeLength = (double) sumBridgeLength / (double) stBoard.getBridges().size();
        double averageNumberOfNeighbours = (double) neighboursWithBridges / (double) numberOfIslands;
        System.out.println("-------------------4");

       features = new double[21];
        features[0] = board.getWidth() * board.getHeight() ;
        features[1] =  leftoverTechniques;
        features[2] = justEnoughDoubleNeighbours ;
        features[3] = oneUnsolvedNeighbour ;
        features[4] = islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue ;
        features[5] = fourOnTheSide ;
        features[6] = sixInTheMiddle ;
        features[7] = threeInTheCornerFiveOnTheSideAndSevenInTheMiddle ;
        features[8] = islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges ;
        features[9] = isolationOfATwoIslandSegment ;
        features[10] = isolationOfASegment ;
        features[11] = isolationWhenASegmentConnectsToAnotherSegment ;
        features[12] = addRandomBridge ;
        features[13] = stBoard.getUnfinishedIslands().size() ;
        features[14] = remainingValue ;
        features[15] = sumValues ;
        features[16] = retry ;
        features[17] = stBoard.checkFinishedIslands() ? 1 : 0;
        features[18] = averageBridgeLength ;
        features[19] = averageNumberOfNeighbours ;
        features[20] = endTime - startTime ;

//        StringBuilder results = new StringBuilder()
//                .append(stBoard.getFilename()).append(CSV_SEPARATOR)
//                .append(board.getWidth() * board.getHeight()).append(CSV_SEPARATOR)
//                .append(leftoverTechniques).append(CSV_SEPARATOR)
//                .append(justEnoughDoubleNeighbours).append(CSV_SEPARATOR)
//                .append(oneUnsolvedNeighbour).append(CSV_SEPARATOR)
//                .append(islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue).append(CSV_SEPARATOR)
//                .append(fourOnTheSide).append(CSV_SEPARATOR)
//                .append(sixInTheMiddle).append(CSV_SEPARATOR)
//                .append(threeInTheCornerFiveOnTheSideAndSevenInTheMiddle).append(CSV_SEPARATOR)
//                .append(islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges).append(CSV_SEPARATOR)
//                .append(isolationOfATwoIslandSegment).append(CSV_SEPARATOR)
//                .append(isolationOfASegment).append(CSV_SEPARATOR)
//                .append(isolationWhenASegmentConnectsToAnotherSegment).append(CSV_SEPARATOR)
//                .append(addRandomBridge).append(CSV_SEPARATOR)
//                .append(stBoard.getUnfinishedIslands().size()).append(CSV_SEPARATOR)
//                .append(remainingValue).append(CSV_SEPARATOR)
//                .append(sumValues).append(CSV_SEPARATOR)
//                .append(retry).append(CSV_SEPARATOR)
//                .append(stBoard.checkFinishedIslands()).append(CSV_SEPARATOR)
//                .append(averageBridgeLength).append(CSV_SEPARATOR)
//                .append(averageNumberOfNeighbours).append(CSV_SEPARATOR)
//                .append(endTime - startTime).append(CSV_SEPARATOR)
//                .append(board.getLevel());
        System.out.println("-------------------5");

//        char[] tmp = new char[10];
//        results.getChars('a', 'b', tmp, 1);
//        fileService.writeDifficulty("Difficulty_ST.csv", results.toString());
        System.out.println("-------------------6");

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
                islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue++;
            }
            if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished()) {
                if (addBridges(island.getUpNeighbour(), island, island.getUpNeighbour().getRemainingValue() == 2, true))
                    isFinishedChanged = true;
                islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue++;
            }
            if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished()) {
                if (addBridges(island, island.getRightNeighbour(), island.getRightNeighbour().getRemainingValue() == 2, true))
                    isFinishedChanged = true;
                islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue++;
            }
            if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished()) {
                if (addBridges(island.getLeftNeighbour(), island, island.getLeftNeighbour().getRemainingValue() == 2, true))
                    isFinishedChanged = true;
                islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue++;
            }
        }
        return isFinishedChanged;
    }

    private static boolean islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges(STIsland island) {
        boolean isFinishedChanged = false;
        int n = 0;
        if (island.getNumberOfRemainingBridgesOfUnfinishedNeighbours() == island.getRemainingValue()) {
            if (island.getDownNeighbour() != null && island.getRemainingBridges(DOWN) != 0) {
                if (addBridges(island, island.getDownNeighbour(), island.getRemainingBridges(DOWN) == 2, true))
                    isFinishedChanged = true;
                islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges++;
            }
            if (island.getUpNeighbour() != null && island.getRemainingBridges(UP) != 0) {
                if (addBridges(island.getUpNeighbour(), island, island.getRemainingBridges(UP) == 2, true))
                    isFinishedChanged = true;
                islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges++;
            }
            if (island.getRightNeighbour() != null && island.getRemainingBridges(RIGHT) != 0) {
                if (addBridges(island, island.getRightNeighbour(), island.getRemainingBridges(RIGHT) == 2, true))
                    isFinishedChanged = true;
                islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges++;
            }
            if (island.getLeftNeighbour() != null && island.getRemainingBridges(LEFT) != 0) {
                if (addBridges(island.getLeftNeighbour(), island, island.getRemainingBridges(LEFT) == 2, true))
                    isFinishedChanged = true;
                islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges++;
            }
        }
        return isFinishedChanged;
    }

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
                    threeInTheCornerFiveOnTheSideAndSevenInTheMiddle++;
                }
                if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished()) {
                    isDouble = island.getUpNeighbour().getValue() != 1;
                    if (addBridges(island.getUpNeighbour(), island, isDouble, isDouble))
                        isFinishedChanged = true;
                    threeInTheCornerFiveOnTheSideAndSevenInTheMiddle++;
                }
                if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished()) {
                    isDouble = island.getRightNeighbour().getValue() != 1;
                    if (addBridges(island, island.getRightNeighbour(), isDouble, isDouble))
                        isFinishedChanged = true;
                    threeInTheCornerFiveOnTheSideAndSevenInTheMiddle++;
                }
                if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished()) {
                    isDouble = island.getLeftNeighbour().getValue() != 1;
                    if (addBridges(island.getLeftNeighbour(), island, isDouble, isDouble))
                        isFinishedChanged = true;
                    threeInTheCornerFiveOnTheSideAndSevenInTheMiddle++;
                }
            }
//                2. Islands with 3 in the corner, 5 on the side and 7 in the middle:
            else {
                if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished()) {
                    if (addBridges(island, island.getDownNeighbour(), false, false))
                        isFinishedChanged = true;
                    threeInTheCornerFiveOnTheSideAndSevenInTheMiddle++;
                }
                if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished()) {
                    if (addBridges(island.getUpNeighbour(), island, false, false))
                        isFinishedChanged = true;
                    threeInTheCornerFiveOnTheSideAndSevenInTheMiddle++;
                }
                if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished()) {
                    if (addBridges(island, island.getRightNeighbour(), false, false))
                        isFinishedChanged = true;
                    threeInTheCornerFiveOnTheSideAndSevenInTheMiddle++;
                }
                if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished()) {
                    if (addBridges(island.getLeftNeighbour(), island, false, false))
                        isFinishedChanged = true;
                    threeInTheCornerFiveOnTheSideAndSevenInTheMiddle++;
                }
            }
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
                fourOnTheSide++;
            }
            if (island.getUpNeighbour() != null) {
                isDouble = island.getUpNeighbour().getValue() != 1;
                if (addBridges(island.getUpNeighbour(), island, isDouble, isDouble))
                    isFinishedChanged = true;
                fourOnTheSide++;
            }
            if (island.getRightNeighbour() != null) {
                isDouble = island.getRightNeighbour().getValue() != 1;
                if (addBridges(island, island.getRightNeighbour(), isDouble, isDouble))
                    isFinishedChanged = true;
                fourOnTheSide++;
            }
            if (island.getLeftNeighbour() != null) {
                isDouble = island.getLeftNeighbour().getValue() != 1;
                if (addBridges(island.getLeftNeighbour(), island, isDouble, isDouble))
                    isFinishedChanged = true;
                fourOnTheSide++;
            }
        }

        return isFinishedChanged;
    }

    //5. Special case of 6 in the middle:
    private static boolean sixInTheMiddle(STIsland island) {
        boolean isDouble = false;
        boolean isFinishedChanged = false;
        if (island.getNumberOfNeighbours() == 4 && island.getValue() == 6 && island.getRemainingValue() == 6 && island.numberOfUnfinishedNeighboursWithValueOne() == 1) {
            //isDouble = island.neighbourWithValueOne().isFinished();
            if (island.getDownNeighbour().getValue() != 1) {
                if (addBridges(island, island.getDownNeighbour(), isDouble, isDouble))
                    isFinishedChanged = true;
                sixInTheMiddle++;
            }
            if (island.getUpNeighbour().getValue() != 1) {
                if (addBridges(island.getUpNeighbour(), island, isDouble, isDouble))
                    isFinishedChanged = true;
                sixInTheMiddle++;
            }
            if (island.getRightNeighbour().getValue() != 1) {
                if (addBridges(island, island.getRightNeighbour(), isDouble, isDouble))
                    isFinishedChanged = true;
                sixInTheMiddle++;
            }
            if (island.getLeftNeighbour().getValue() != 1) {
                if (addBridges(island.getLeftNeighbour(), island, isDouble, isDouble))
                    isFinishedChanged = true;
                sixInTheMiddle++;
            }
//            System.out.println("sixInTheMiddle");
        }
        return isFinishedChanged;
    }

    private static boolean justEnoughDoubleNeighbours(STIsland island) {
        boolean isFinishedChanged = false;
        if (island.getValue() == (island.getNumberOfNeighbours() * 2)) {
            if (island.getDownNeighbour() != null) {
                if (addBridges(island, island.getDownNeighbour(), true, true))
                    isFinishedChanged = true;
                justEnoughDoubleNeighbours++;
            }
            if (island.getUpNeighbour() != null) {
                if (addBridges(island.getUpNeighbour(), island, true, true))
                    isFinishedChanged = true;
                justEnoughDoubleNeighbours++;
            }
            if (island.getRightNeighbour() != null) {
                if (addBridges(island, island.getRightNeighbour(), true, true))
                    isFinishedChanged = true;
                justEnoughDoubleNeighbours++;
            }
            if (island.getLeftNeighbour() != null) {
                if (addBridges(island.getLeftNeighbour(), island, true, true))
                    isFinishedChanged = true;
                justEnoughDoubleNeighbours++;
            }
//            System.out.println("justEnoughDoubleNeighbours");
        }
        return isFinishedChanged;
    }

    private static boolean oneUnsolvedNeighbour(STIsland island) {
        boolean isFinishedChanged = false;
        if (island.numberOfUnfinishedNeighboursWithFreeBridges() == 1) {
            boolean isDouble = island.getRemainingValue() == 2;
            if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished() && island.getRemainingBridges(DOWN) > 0) {
                if (addBridges(island, island.getDownNeighbour(), isDouble, true))
                    isFinishedChanged = true;
                oneUnsolvedNeighbour++;
            }
            if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished() && island.getRemainingBridges(UP) > 0) {
                if (addBridges(island.getUpNeighbour(), island, isDouble, true))
                    isFinishedChanged = true;
                oneUnsolvedNeighbour++;
            }
            if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished() && island.getRemainingBridges(RIGHT) > 0) {
                if (addBridges(island, island.getRightNeighbour(), isDouble, true))
                    isFinishedChanged = true;
                oneUnsolvedNeighbour++;
            }
            if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished() && island.getRemainingBridges(LEFT) > 0) {
                if (addBridges(island.getLeftNeighbour(), island, isDouble, true))
                    isFinishedChanged = true;
                oneUnsolvedNeighbour++;
            }
//            System.out.println("oneUnsolvedNeighbour");
        }
        return isFinishedChanged;
    }


    private static boolean leftoverTechniques(STIsland island) {
        boolean isFinishedChanged = false;
        int n = 0;
        if (island.getNumberOfUnfinishedNeighbours() == island.getRemainingValue() && island.numberOfUnfinishedNeighboursWithValue(1) == island.getRemainingValue() - 1) {
            if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished() && island.getDownNeighbour().getRemainingValue() != 1) {
                if (addBridges(island, island.getDownNeighbour(), false, true))
                    isFinishedChanged = true;
                leftoverTechniques++;
            }
            if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished() && island.getUpNeighbour().getRemainingValue() != 1) {
                if (addBridges(island.getUpNeighbour(), island, false, true))
                    isFinishedChanged = true;
                leftoverTechniques++;
            }
            if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished() && island.getRightNeighbour().getRemainingValue() != 1) {
                if (addBridges(island, island.getRightNeighbour(), false, true))
                    isFinishedChanged = true;
                leftoverTechniques++;
            }
            if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished() && island.getLeftNeighbour().getRemainingValue() != 1) {
                if (addBridges(island.getLeftNeighbour(), island, false, true))
                    isFinishedChanged = true;
                leftoverTechniques++;
            }
//            System.out.println("leftoverTechniques");
        }
        return isFinishedChanged;
    }

//    //todo: ez nem jó
//    private static boolean isolationOfASegment(STIsland island) {
//        boolean isFinishedChanged = false;
//        if (island.getNumberOfNeighboursWithoutBridges() == island.getNumberOfNeighbours()) {
//
//
//            ArrayList<STIsland> neighboursWithoutBridges = island.getNeighboursWithoutBridges();
//            if (neighboursWithoutBridges.size() > 0) {
//                int sumOfValues = 0;
//                for (STIsland i : neighboursWithoutBridges) {
//                    sumOfValues += Math.min(i.getValue(), 2);
//                }
//                for (STIsland i : neighboursWithoutBridges) {
//                    if ((sumOfValues - i.getValue()) == island.getRemainingValue()) {
//                        isFinishedChanged = addBridges(island, i, false, false);
//                    }
//                }
//            }
//        }
//        return isFinishedChanged;
//    }

    private static boolean isolationOfATwoIslandSegment(STIsland island) {
        boolean isFinishedChanged = false;
        if (island.getValue() == 1 && island.getRemainingValue() == 1 && (island.numberOfUnfinishedNeighboursWithValue(1) + 1 == island.getNumberOfNeighbours())) {
            int value = island.getValue();
            if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished() && island.getDownNeighbour().getValue() != 1) {
                if (addBridges(island, island.getDownNeighbour(), false, false))
                    isFinishedChanged = true;
                isolationOfATwoIslandSegment++;
            }
            if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished() && island.getUpNeighbour().getValue() != 1) {
                if (addBridges(island.getUpNeighbour(), island, false, false))
                    isFinishedChanged = true;
                isolationOfATwoIslandSegment++;
            }
            if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished() && island.getRightNeighbour().getValue() != 1) {
                if (addBridges(island, island.getRightNeighbour(), false, false))
                    isFinishedChanged = true;
                isolationOfATwoIslandSegment++;
            }
            if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished() && island.getLeftNeighbour().getValue() != 1) {
                if (addBridges(island.getLeftNeighbour(), island, false, false))
                    isFinishedChanged = true;
                isolationOfATwoIslandSegment++;
            }
        } else if (island.getValue() == 2 && island.getRemainingValue() == 2 && island.numberOfUnfinishedNeighboursWithValue(2) == 2 && island.getNumberOfNeighbours() == 2) {
            int value = island.getValue();
            if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished() && island.getDownNeighbour().getValue() == 2) {
                if (addBridges(island, island.getDownNeighbour(), false, false))
                    isFinishedChanged = true;
                isolationOfATwoIslandSegment++;
            }
            if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished() && island.getUpNeighbour().getValue() == 2) {
                if (addBridges(island.getUpNeighbour(), island, false, false))
                    isFinishedChanged = true;
                isolationOfATwoIslandSegment++;
            }
            if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished() && island.getRightNeighbour().getValue() == 2) {
                if (addBridges(island, island.getRightNeighbour(), false, false))
                    isFinishedChanged = true;
                isolationOfATwoIslandSegment++;
            }
            if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished() && island.getLeftNeighbour().getValue() == 2) {
                if (addBridges(island.getLeftNeighbour(), island, false, false))
                    isFinishedChanged = true;
                isolationOfATwoIslandSegment++;
            }
        } else if (island.getValue() == 2 && island.getRemainingValue() == 2 && island.numberOfUnfinishedNeighboursWithValue(2) == 1 && island.getNumberOfNeighbours() == 2) {
            int value = island.getValue();
            if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished() && island.getDownNeighbour().getValue() != 2) {
                if (addBridges(island, island.getDownNeighbour(), false, false))
                    isFinishedChanged = true;
                isolationOfATwoIslandSegment++;
            }
            if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished() && island.getUpNeighbour().getValue() != 2) {
                if (addBridges(island.getUpNeighbour(), island, false, false))
                    isFinishedChanged = true;
                isolationOfATwoIslandSegment++;
            }
            if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished() && island.getRightNeighbour().getValue() != 2) {
                if (addBridges(island, island.getRightNeighbour(), false, false))
                    isFinishedChanged = true;
                isolationOfATwoIslandSegment++;
            }
            if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished() && island.getLeftNeighbour().getValue() != 2) {
                if (addBridges(island.getLeftNeighbour(), island, false, false))
                    isFinishedChanged = true;
                isolationOfATwoIslandSegment++;
            }
        }
        return isFinishedChanged;
    }
//
//    private static boolean isolationWhenASegmentConnectsToAnIsland(STIsland island) {
//        boolean isFinishedChanged = false;
//        if ((island.getRemainingValue() == 1) && (island.numberOfUnfinishedNeighboursWithFreeBridges() - island.numberOfNeighboursWithValue(1) == 1)) {
//            HashSet<STIsland> segment = getSegment(island, new HashSet<>());
//            int remainingValue = 0;
//            for (STIsland i : segment) {
//                remainingValue += i.getRemainingValue();
//            }
//            if (remainingValue == 1) {
//                if (island.getDownNeighbour() != null && island.getDownNeighbour().getValue() != 1) {
//                    isFinishedChanged = addBridges(island, island.getDownNeighbour(), false, false);
//                    isolationWhenASegmentConnectsToAnIsland++;
//                } else if (island.getUpNeighbour() != null && island.getUpNeighbour().getValue() != 1) {
//                    isFinishedChanged = addBridges(island, island.getUpNeighbour(), false, false);
//                    isolationWhenASegmentConnectsToAnIsland++;
//                } else if (island.getRightNeighbour() != null && island.getRightNeighbour().getValue() != 1) {
//                    isFinishedChanged = addBridges(island, island.getRightNeighbour(), false, false);
//                    isolationWhenASegmentConnectsToAnIsland++;
//                } else if (island.getLeftNeighbour() != null && island.getLeftNeighbour().getValue() != 1) {
//                    isFinishedChanged = addBridges(island, island.getLeftNeighbour(), false, false);
//                    isolationWhenASegmentConnectsToAnIsland++;
//                }
//            }
//        }
//        return isFinishedChanged;
//    }

    private static boolean isolationWhenASegmentConnectsToAnotherSegment(STIsland island) {
        boolean isFinishedChanged = false;
        if (island.getNumberOfUnfinishedNeighbours() == 2 && island.getRemainingValue() == 1) {
            HashSet<STIsland> islandSegment = getSegment(island, new HashSet<>());
            int unfinishedIslands = 0;
            for (STIsland i : islandSegment) {
                if (!i.isFinished())
                    unfinishedIslands++;
            }
            if (unfinishedIslands > 1)
                return false;
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
                secondNeighbour = island.getLeftNeighbour();
                secondNeighbourSegment = getSegment(island.getLeftNeighbour(), new HashSet<>());
            }
            for (STIsland i : firstNeighbourSegment) {
                firstRemainingValue += i.getRemainingValue();
            }
            for (STIsland i : secondNeighbourSegment) {
                secondRemainingValue += i.getRemainingValue();
            }
            if (1 == secondRemainingValue) {
                isFinishedChanged = addBridges(island, firstNeighbour, false, false);
                isolationWhenASegmentConnectsToAnotherSegment++;
            }
            if (1 == firstRemainingValue) {
                isFinishedChanged = addBridges(island, secondNeighbour, false, false);
                isolationWhenASegmentConnectsToAnotherSegment++;
            }
        }
        return isFinishedChanged;
    }


    private static void addRandomBridge() {
        if(stBoard.getUnfinishedIslands().size() == 0)
            return;
        if (stBoardBeforeRandom == null) {
            try {
                stBoardBeforeRandom = (STBoard) stBoard.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        Random rand = new Random();
        int randomNumber = rand.nextInt(stBoard.getUnfinishedIslands().size() - 1);
        STIsland island = stBoard.getUnfinishedIslands().get(randomNumber);
        int randomDirection = rand.nextInt(3);

        switch (randomDirection) {
            case 0:
                if (island.getDownNeighbour() != null && !island.getDownNeighbour().isFinished() && island.getDownBridges() == null) {
                    if(addBridges(island, island.getDownNeighbour(), false, false)){
                        addRandomBridge++;
                    }
                }
                break;
            case 1:
                if (island.getUpNeighbour() != null && !island.getUpNeighbour().isFinished() && island.getUpBridges() == null) {
                    if(addBridges(island, island.getUpNeighbour(), false, false)){
                        addRandomBridge++;
                    }
                }
                break;
            case 2:
                if (island.getRightNeighbour() != null && !island.getRightNeighbour().isFinished() && island.getRightBridges() == null) {
                    if(addBridges(island, island.getRightNeighbour(), false, false)){
                        addRandomBridge++;
                    }
                }
                break;
            case 3:
                if (island.getLeftNeighbour() != null && !island.getLeftNeighbour().isFinished() && island.getLeftBridges() == null) {
                    if(addBridges(island, island.getLeftNeighbour(), false, false)){
                        addRandomBridge++;
                    }
                }
                break;
        }

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
        int numberOfBridges = stBoard.getBridges().size();
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
        return numberOfBridges < stBoard.getBridges().size();
//        return startIsland.isFinished() || endIsland.isFinished();
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

    private static void callSolverFunctions() {

        boolean changed = false;

        checkIntersectingBridges();

        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = leftoverTechniques(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                points += 5;
//                System.out.println("leftoverTechniques " + stBoard.getUnfinishedIslands().get(i).getId() + "(" + stBoard.getUnfinishedIslands().get(i).getPosition().toString() + ")" );
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = justEnoughDoubleNeighbours(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                points += 10;
//                System.out.println("justEnoughDoubleNeighbours " + stBoard.getUnfinishedIslands().get(i).getValue() + "(" + stBoard.getUnfinishedIslands().get(i).getPosition().toString() + ")" );
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = oneUnsolvedNeighbour(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                points += 5;
//                System.out.println("oneUnsolvedNeighbour " + stBoard.getUnfinishedIslands().get(i).getId() + "(" + stBoard.getUnfinishedIslands().get(i).getPosition().toString() + ")" );
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                points += 10;
//                System.out.println("islandRemainingValueEqualsToUnfinishedNeighboursRemainingValue " + stBoard.getUnfinishedIslands().get(i).getId() + "(" + stBoard.getUnfinishedIslands().get(i).getPosition().toString() + ")" );
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = fourOnTheSide(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                points += 20;
//                System.out.println("fourOnTheSide " + stBoard.getUnfinishedIslands().get(i).getId() + "(" + stBoard.getUnfinishedIslands().get(i).getPosition().toString() + ")" );
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = sixInTheMiddle(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                points += 20;
//                System.out.println("sixInTheMiddle " + stBoard.getUnfinishedIslands().get(i).getId() + "(" + stBoard.getUnfinishedIslands().get(i).getPosition().toString() + ")" );
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = threeInTheCornerFiveOnTheSideAndSevenInTheMiddle(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                points += 20;
//                System.out.println("threeInTheCornerFiveOnTheSideAndSevenInTheMiddle " + stBoard.getUnfinishedIslands().get(i).getId() + "(" + stBoard.getUnfinishedIslands().get(i).getPosition().toString() + ")" );
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                points += 20;
//                System.out.println("islandRemainingValueEqualsToUnfinishedNeighboursRemainingBridges " + stBoard.getUnfinishedIslands().get(i).getId() + "(" + stBoard.getUnfinishedIslands().get(i).getPosition().toString() + ")" );
                return;
            }
        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = isolationOfATwoIslandSegment(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                points += 50;
//                System.out.println("isolationOfATwoIslandSegment " + stBoard.getUnfinishedIslands().get(i).getId() + "(" + stBoard.getUnfinishedIslands().get(i).getPosition().toString() + ")" );
                return;
            }
        }
//        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
//            changed = isolationWhenASegmentConnectsToAnIsland(stBoard.getUnfinishedIslands().get(i));
//            if (changed) {
//                points += 60;
////                System.out.println("isolationWhenASegmentConnectsToAnIsland " + stBoard.getUnfinishedIslands().get(i).getId() + "(" + stBoard.getUnfinishedIslands().get(i).getPosition().toString() + ")" );
//                return;
//            }
//        }
        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
            changed = isolationWhenASegmentConnectsToAnotherSegment(stBoard.getUnfinishedIslands().get(i));
            if (changed) {
                points += 60;
//                System.out.println("isolationWhenASegmentConnectsToAnotherSegment " + stBoard.getUnfinishedIslands().get(i).getId() + "(" + stBoard.getUnfinishedIslands().get(i).getPosition().toString() + ")" );
                return;
            }
        }
        addRandomBridge();


//        for (int i = 0; i < stBoard.getUnfinishedIslands().size(); i++) {
//            changed = isolationOfASegment(stBoard.getUnfinishedIslands().get(i));
//            if (changed) {
//                points += 50;
//                System.out.println("isolationOfASegment " + stBoard.getUnfinishedIslands().get(i).getId() + "(" + stBoard.getUnfinishedIslands().get(i).getPosition().toString() + ")" );
//                return;
//            }
//        }

    }
    public static double[] getFeatures(){
        return features;
    }
 }
