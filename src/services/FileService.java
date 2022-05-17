package services;

import models.Board;
import models.Bridge;
import models.Island;
import services.interfaces.IFileService;
import solver.solvingTechniques.Levels;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

public class FileService implements IFileService {

    @Override
    public File saveNewBoard(Board board, Levels level) {
        Date date = new Date();
        String fileName = board.getWidth() + "_" + board.getHeight() + "_" + level.toString() + "_" + date.getTime() + ".txt";
        board.setFileName(fileName);
        try {
            File myObj = new File(PATH + fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        writeFile(PATH + fileName, board.printCsv());
        return new File(PATH + fileName);
    }

    private void writeFile(String filename, String textToWrite) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(textToWrite);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeDifficulty(String filename, String textToWrite) {

        Writer output;
        try {
            output = new BufferedWriter(new FileWriter(filename, true));
            output.append(textToWrite);
            output.append("\n");
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Board readSolution(File file) {
        Board board = new Board();
        String data = "";

        try {
            //File myObj = new File(path + fileName);
            Scanner myReader = new Scanner(file);
            if (myReader.hasNextLine() && !myReader.nextLine().equals("board")) {
                return null;
            }
            String[] splittedLine;
            data = myReader.nextLine();
            splittedLine = data.split(",");
            board.setWidth(Integer.parseInt(splittedLine[0]));
            board.setHeight(Integer.parseInt(splittedLine[1]));
            board.setFileName(file.getName());
            data = myReader.nextLine();

            this.setLevel(data, board);

            myReader.nextLine();

            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                if (data.equals("bridges")) {
                    break;
                }
                Island island = new Island();
                splittedLine = data.split(",");
                island.setId(Integer.parseInt(splittedLine[0]));
                island.setValue(Integer.parseInt(splittedLine[1]));
                island.getPosition().setX(Integer.parseInt(splittedLine[2]));
                island.getPosition().setY(Integer.parseInt(splittedLine[3]));
                board.addIsland(island);
            }
            while (myReader.hasNextLine()) {
                splittedLine = myReader.nextLine().split(",");
                Bridge bridge = new Bridge();
                String[] finalSplittedLine = splittedLine;
                Island startIsland = board.getIslands().stream().filter(isl -> isl.getId() == Integer.parseInt(finalSplittedLine[0])).findFirst().orElse(null);
                Island endIsland = board.getIslands().stream().filter(isl -> isl.getId() == Integer.parseInt(finalSplittedLine[1])).findFirst().orElse(null);
                bridge.setStartIsland(startIsland);
                bridge.setEndIsland(endIsland);
                bridge.setDouble(splittedLine[2].equals("double"));
                bridge.setVertical(splittedLine[3].equals("vertical"));
                board.addBridge(bridge);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return board;
    }

    @Override
    public Board readSolution(Board board) {
        return this.readSolution(new File(this.PATH + board.getFileName()));
    }

    @Override
    public Board readGame(File file) {
        Board board = new Board();
        String data = "";
        try {
            Scanner myReader = new Scanner(file);
            String level = myReader.nextLine();
            this.setLevel(level, board);
//            System.out.println(level);
            board.setFileName(file.getName());

            String[] splittedLine;
            data = myReader.nextLine();
            splittedLine = data.split(",");
            int size = Integer.parseInt(String.valueOf(splittedLine.length));
            board.setWidth(size);
            board.setHeight(size);
            int j = 0;
            int id = 0;
            for (int i = 0; i < splittedLine.length; i++) {
                if (Integer.parseInt(splittedLine[i]) != 0) {
                    Island island = new Island();
                    island.setId(id);
                    island.setValue(Integer.parseInt(splittedLine[i]));
                    island.getPosition().setX(i + 1);
                    island.getPosition().setY(j + 1);
                    id++;
                    board.addIsland(island);
                }
            }
            j++;
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                splittedLine = data.split(",");
                for (int i = 0; i < splittedLine.length; i++) {
                    if (Integer.parseInt(splittedLine[i]) != 0) {
                        Island island = new Island();
                        island.setId(id);
                        island.setValue(Integer.parseInt(splittedLine[i]));
                        island.getPosition().setX(i + 1);
                        island.getPosition().setY(j + 1);
                        id++;
                        board.addIsland(island);
                    }
                }
                j++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return board;
    }

    private void setLevel(String data, Board board) {
        data = data.toLowerCase();
        switch (data) {
            case "very_easy":
                board.setLevel(Levels.PRETTY_EASY);
                break;
            case "pretty_easy":
                board.setLevel(Levels.PRETTY_EASY);
                break;
            case "easy":
                board.setLevel(Levels.EASY);
                break;
            case "medium":
                board.setLevel(Levels.MEDIUM);
                break;
            case "hard":
                board.setLevel(Levels.HARD);
                break;
            case "very_hard":
                board.setLevel(Levels.VERY_HARD);
                break;
            case "super_hard":
                board.setLevel(Levels.SUPER_HARD);
                break;
            default:
                break;
        }
    }

}
