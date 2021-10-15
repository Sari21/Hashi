package services;

import models.Board;
import models.Bridge;
import models.Coordinates;
import models.Island;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PuzzleGenerator {
    private static Board board;

    public static void generatePuzzle(int width, int height, int numberOfIslands) {
        board = new Board(width, height);
        Random random = new Random();

        int randomX = random.nextInt(width);
        int randomY = random.nextInt(height);
        int id = 1;
        Island firstIsland = new Island(new Coordinates(randomX, randomY), id++);
        board.addIsland(firstIsland);

        while (board.getIslands().size() < numberOfIslands) {
            Island randomIsland = chooseARandomIsland();
            int x = randomIsland.getPosition().getX();
            int y = randomIsland.getPosition().getY();
            int direction = random.nextInt(4);
            switch (direction) {
                case 0:
                    x++;
                    if (checkIfFieldIsFree(x, y)) {
                        while (random.nextBoolean()) {
                            x++;
                            if (!checkIfFieldIsFree(x, y)) {
                                break;
                            }
                        }
                        x--;
                        if(checkIfFieldIsFree(x, y)){
                            Island newIsland = new Island(new Coordinates(x, y), id++);
                            Bridge newBridge = new Bridge(randomIsland, newIsland, random.nextBoolean());
                            if (newBridge.isDouble()) {
                                randomIsland.setValue(randomIsland.getValue() + 2);
                                newIsland.setValue(newIsland.getValue() + 2);
                            } else {
                                randomIsland.setValue(randomIsland.getValue() + 1);
                                newIsland.setValue(newIsland.getValue() + 1);
                            }
                            board.addIsland(newIsland);
                            board.addBridge(newBridge);
                        }

                    }
                    break;
                case 1:
                    y++;
                    if (checkIfFieldIsFree(x, y)) {
                        while (random.nextBoolean()) {
                            y++;
                            if (!checkIfFieldIsFree(x, y)) {
                                break;
                            }
                        }
                        y--;
                        if(checkIfFieldIsFree(x, y)){
                            Island newIsland = new Island(new Coordinates(x, y), id++);
                            Bridge newBridge = new Bridge(randomIsland, newIsland, random.nextBoolean());
                            if (newBridge.isDouble()) {
                                randomIsland.setValue(randomIsland.getValue() + 2);
                                newIsland.setValue(newIsland.getValue() + 2);
                            } else {
                                randomIsland.setValue(randomIsland.getValue() + 1);
                                newIsland.setValue(newIsland.getValue() + 1);
                            }
                            board.addIsland(newIsland);
                            board.addBridge(newBridge);
                        }
                    }
                    break;
                case 2:
                    x--;
                    if (checkIfFieldIsFree(x, y)) {
                        while (random.nextBoolean()) {
                            x--;
                            if (!checkIfFieldIsFree(x, y)) {
                                break;
                            }
                        }
                        x++;
                        if (checkIfFieldIsFree(x, y)) {
                            Island newIsland = new Island(new Coordinates(x, y), id++);
                            Bridge newBridge = new Bridge(randomIsland, newIsland, random.nextBoolean());
                            if (newBridge.isDouble()) {
                                randomIsland.setValue(randomIsland.getValue() + 2);
                                newIsland.setValue(newIsland.getValue() + 2);
                            } else {
                                randomIsland.setValue(randomIsland.getValue() + 1);
                                newIsland.setValue(newIsland.getValue() + 1);
                            }
                            board.addIsland(newIsland);
                            board.addBridge(newBridge);
                        }
                    }
                    break;
                case 3:
                    y--;
                    if (checkIfFieldIsFree(x, y)) {
                        while (random.nextBoolean()) {
                            y--;
                            if (!checkIfFieldIsFree(x, y)) {
                                break;
                            }
                        }
                        y++;
                        if (checkIfFieldIsFree(x, y)) {
                            Island newIsland = new Island(new Coordinates(x, y), id++);
                            Bridge newBridge = new Bridge(randomIsland, newIsland, random.nextBoolean());
                            if (newBridge.isDouble()) {
                                randomIsland.setValue(randomIsland.getValue() + 2);
                                newIsland.setValue(newIsland.getValue() + 2);
                            } else {
                                randomIsland.setValue(randomIsland.getValue() + 1);
                                newIsland.setValue(newIsland.getValue() + 1);
                            }
                            board.addIsland(newIsland);
                            board.addBridge(newBridge);
                        }
                    }
                    break;
            }
        }
        FileService.printBoardToCsv(board);
    }

    private static Island chooseARandomIsland() {
        Random random = new Random();
        return board.getIslands().get(random.nextInt(board.getIslands().size()));
    }

    private static boolean checkIfFieldIsFree(Coordinates newField) {
        for (Coordinates field : board.getFields()) {
            if (field.getX() == newField.getX() && field.getY() == newField.getY()) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkIfFieldIsFree(int x, int y) {
        boolean free = true;
        for (Coordinates field : board.getFields()) {
            if (field.getX() == x && field.getY() == y) {
                free =  false;
                break;
            }
        }
        boolean isInTheBoard = (x <= board.getWidth() && y <= board.getHeight());
        return free && isInTheBoard;
    }
}
