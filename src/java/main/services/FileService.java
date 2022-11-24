package main.services;

import main.models.Board;
import main.models.Bridge;
import main.models.Island;
import main.services.interfaces.IFileService;
import main.models.Level;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

import static main.interfaces.CsvPrintable.CSV_SEPARATOR;

public class FileService implements IFileService {

    @Override
    public File saveNewBoard(Board board) {
        Date date = new Date();
        String fileName = board.getWidth() + "_" + board.getHeight() + "_" + board.getLevel().toString() + "_" + date.getTime() + ".txt";
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

    @Override
    public void writeDifficulty(String fileToWrite, String fileName, float features[], Level level) {
        StringBuilder results = new StringBuilder();
        results.append(fileName).append(CSV_SEPARATOR);
        for (int i = 0; i < features.length; i++) {
            results.append(features[i]).append(CSV_SEPARATOR);
        }
        results.append(level);
        Writer output;
        try {
            output = new BufferedWriter(new FileWriter(fileToWrite, true));
            output.append(results.toString());
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
            board.setBridgeAndIslandFields();
            int j = 0;
            int id = 1;
            for (int i = 0; i < splittedLine.length; i++) {
                if (Integer.parseInt(splittedLine[i]) != 0) {
                    Island island = new Island();
                    island.setId(id);
                    island.setValue(Integer.parseInt(splittedLine[i]));
                    island.getPosition().setX(i);
                    island.getPosition().setY(j);
                    id++;
                    board.addIsland(island);
                }
            }
            j++;
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                if (data.equals("bridges"))
                    break;
                splittedLine = data.split(",");
                for (int i = 0; i < splittedLine.length; i++) {
                    if (Integer.parseInt(splittedLine[i]) != 0) {
                        Island island = new Island();
                        island.setId(id);
                        island.setValue(Integer.parseInt(splittedLine[i]));
                        island.getPosition().setX(i);
                        island.getPosition().setY(j);
                        id++;
                        board.addIsland(island);
                    }
                }
                j++;
            }
            while (myReader.hasNextLine()) {
                splittedLine = myReader.nextLine().split(";");
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
            board.setBridgeAndIslandFields();
            int j = 0;
            int id = 1;
            for (int i = 0; i < splittedLine.length; i++) {
                if (Integer.parseInt(splittedLine[i]) != 0) {
                    Island island = new Island();
                    island.setId(id);
                    island.setValue(Integer.parseInt(splittedLine[i]));
                    island.getPosition().setX(i);
                    island.getPosition().setY(j);
                    id++;
                    board.addIsland(island);
                }
            }
            j++;
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                if (data.equals("bridges"))
                    break;
                splittedLine = data.split(",");
                for (int i = 0; i < splittedLine.length; i++) {
                    if (Integer.parseInt(splittedLine[i]) != 0) {
                        Island island = new Island();
                        island.setId(id);
                        island.setValue(Integer.parseInt(splittedLine[i]));
                        island.getPosition().setX(i);
                        island.getPosition().setY(j);
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
                board.setLevel(Level.PRETTY_EASY);
                break;
            case "pretty_easy":
                board.setLevel(Level.PRETTY_EASY);
                break;
            case "easy":
                board.setLevel(Level.EASY);
                break;
            case "medium":
                board.setLevel(Level.MEDIUM);
                break;
            case "hard":
                board.setLevel(Level.HARD);
                break;
            case "very_hard":
                board.setLevel(Level.VERY_HARD);
                break;
            case "super_hard":
                board.setLevel(Level.SUPER_HARD);
                break;
            default:
                break;
        }
    }

}
