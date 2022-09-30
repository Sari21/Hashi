package main.services;

import gurobi.GRBException;
import main.models.Board;
import main.models.Bridge;
import main.models.Coordinates;
import main.models.Island;
import main.services.interfaces.IFileService;
import main.services.interfaces.IPuzzleGeneratorService;
import main.solver.mathematical.LPSolver;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class PuzzleGeneratorService implements IPuzzleGeneratorService {
    private static Board board;
    private static IFileService fileService = new FileService();

    public static Board generatePuzzle(int width, int height, int numberOfIslands) throws GRBException {
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
                    x++;
                    if (checkIfFieldIsFree(x - 1, y) && checkIfFieldIsFree(x, y) && checkIfFieldIsFreeOrBridgeOrEdge(x + 1, y)) {
                        while (random.nextBoolean()) {
                            x++;
                            if (!checkIfFieldIsFree(x, y) || !checkIfFieldIsFreeOrBridgeOrEdge(x + 1, y)) {
                                break;
                            }
                        }
                        createNewIsland(randomIsland, x, y, id++);
                    }
                    break;
                case 1:
                    y++;
                    y++;
                    if (checkIfFieldIsFree(x, y - 1) && checkIfFieldIsFree(x, y) && checkIfFieldIsFreeOrBridgeOrEdge(x, y + 1)) {
                        while (random.nextBoolean()) {
                            y++;
                            if (!checkIfFieldIsFree(x, y) || !checkIfFieldIsFreeOrBridgeOrEdge(x, y + 1)) {
                                break;
                            }
                        }
                        createNewIsland(randomIsland, x, y, id++);
                    }
                    break;
                case 2:
                    x--;
                    x--;
                    if (checkIfFieldIsFreeOrBridgeOrEdge(x - 1, y) && checkIfFieldIsFree(x, y) && checkIfFieldIsFree(x + 1, y)) {
                        while (random.nextBoolean()) {
                            x--;
                            if (!checkIfFieldIsFreeOrBridgeOrEdge(x - 1, y) || !checkIfFieldIsFree(x, y)) {
                                break;
                            }
                        }
                        createNewIsland(randomIsland, x, y, id++);
                    }
                    break;
                case 3:
                    y--;
                    y--;
                    if (checkIfFieldIsFreeOrBridgeOrEdge(x, y - 1) && checkIfFieldIsFree(x, y) && checkIfFieldIsFree(x, y + 1)) {
                        while (random.nextBoolean()) {
                            y--;
                            if (!checkIfFieldIsFreeOrBridgeOrEdge(x, y - 1) || !checkIfFieldIsFree(x, y)) {
                                break;
                            }
                        }
                        createNewIsland(randomIsland, x, y, id++);
                    }
                    break;
            }
        }
        Collections.sort(board.getIslands());
        int newId = 1;
        for(Island i : board.getIslands()){
            i.setId(newId);
            newId++;
        }
        return board;
    }

    private static void createNewIsland(Island randomIsland, int x, int y, int id) {
        Random random = new Random();
        Island newIsland = new Island(new Coordinates(x, y), id);
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

    private static Island chooseARandomIsland() {
        Random random = new Random();
        return board.getIslands().get(random.nextInt(board.getIslands().size()));
    }

    private static boolean checkIfFieldIsFreeOrBridgeOrEdge(int x, int y) {
        if (x <= -1 || x >= board.getWidth() || y <= -1 || y >= board.getHeight())
            return false;
        return !board.getIslandFields()[x][y];
    }

    private static boolean checkIfFieldIsFree(int x, int y) {
        if (x < 0 || x >= board.getWidth() || y < 0 || y >= board.getHeight())
            return false;

        return !board.getIslandFields()[x][y] && !board.getBridgeFields()[x][y];
    }
}
