package services;

import models.Board;
import models.Bridge;
import models.Island;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class  FileService {
    public static void printBoardToCsv(Board board){
            String path = "src\\resources\\hashiBoards\\";
            Date date = new Date();
            String fileName = date.getTime() + ".txt";
        try {
            File myObj = new File(path + fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        FileWriter(path+fileName, board.printCsv());

    }
    private static void FileWriter(String filename, String textToWrite){
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
     public static Board ReadFile(File file) {
         Board board = new Board();
         try {
             //File myObj = new File(path + fileName);
             Scanner myReader = new Scanner(file);
             if (myReader.hasNextLine() && !myReader.nextLine().equals("board")) {
                 return null;
             }
             String[] splittedLine;
             while (myReader.hasNextLine()) {
                 String data = myReader.nextLine();
                 if (data.equals("islands")) {
                     break;
                 }
                 splittedLine = data.split(";");
                 board.setWidth(Integer.parseInt(splittedLine[0]));
                 board.setHeight(Integer.parseInt(splittedLine[1]));
             }
             while (myReader.hasNextLine()) {
                 String data = myReader.nextLine();
                 if (data.equals("bridges")) {
                     break;
                 }
                 Island island = new Island();
                 splittedLine = data.split(";");
                 island.setId(Integer.parseInt(splittedLine[0]));
                 island.setValue(Integer.parseInt(splittedLine[1]));
                 island.getPosition().setX(Integer.parseInt(splittedLine[2]));
                 island.getPosition().setY(Integer.parseInt(splittedLine[3]));
                 board.addIsland(island);
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
             System.out.println(board.printCsv());

         } catch (FileNotFoundException e) {
             System.out.println("An error occurred.");
             e.printStackTrace();
         }

         return board;

    }
}
