package services;

import models.Board;
import models.Bridge;
import models.Coordinates;
import models.Island;
import services.interfaces.IFileService;

import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PuzzleGeneratorService {
    private static Board board;
    private static IFileService fileService = new FileService();


    public static File generatePuzzle(int width, int height, int numberOfIslands) {
        board = new Board(width, height);
        Random random = new Random();

        int randomX = random.nextInt(width - 1) + 1;
        int randomY = random.nextInt(height - 1) + 1;
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
                    if (checkIfFieldIsFree(x, y) && checkIfFieldIsFree(++x, y)) {
                        while (random.nextBoolean()) {
                            x++;
                            if (!checkIfFieldIsFree(x, y)) {
                                break;
                            }
                        }
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
                    if (checkIfFieldIsFree(x, y) && checkIfFieldIsFree(x, ++y)) {
                        while (random.nextBoolean()) {
                            y++;
                            if (!checkIfFieldIsFree(x, y)) {
                                break;
                            }
                        }
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
                    if (checkIfFieldIsFree(x, y) && checkIfFieldIsFree(--x, y)) {
                        while (random.nextBoolean()) {
                            x--;
                            if (!checkIfFieldIsFree(x, y)) {
                                break;
                            }
                        }
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
                    if (checkIfFieldIsFree(x, y) && checkIfFieldIsFree(x, --y)) {
                        while (random.nextBoolean()) {
                            y--;
                            if (!checkIfFieldIsFree(x, y)) {
                                break;
                            }
                        }
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
        return fileService.printBoardToCsv(board);
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
        for (Coordinates field : board.getFields()) {
            if (field.getX() == x && field.getY() == y) {
                return false;
            }
        }

        return  (x >= 1 && x <= board.getWidth() && y >= 1 && y <= board.getHeight());
    }
}
