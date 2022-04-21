package controllers;

import gurobi.GRBException;
import models.Board;
import services.FileService;
import services.interfaces.IFileService;
import solver.mathematical.LPSolver;
import solver.solvingTechniques.STSolver;
import view.MenuView;

import java.io.File;
import java.util.List;

public class MenuController {

    private static MenuView menuView = new MenuView();
    private static IFileService fileService = new FileService();

    public static void openGame(File file){
        Board board = fileService.readGame(file);
        closeMenuStage();
        BoardController.openGame(board);
    }
    public static void solveGame(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();
        Board board2 = LPSolver.solve(board);
        BoardController.openGame(board2);
    }
    public static void solveGameWithSolvingTechniques(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();
        Board board2 = STSolver.solve(board);
        BoardController.openGame(board2);
    }
    public static void openSolution(File file){
        Board board = fileService.readSolution(file);
        closeMenuStage();
        BoardController.openGame(board);
    }
    public static void solveMultipleGameWithSolvingTechniques(List<File> files){
        closeMenuStage();
        for(File file : files){
            Board board = fileService.readGame(file);
            STSolver.solve(board);
        }
        showMenuStage();
        System.out.println("done");
    }

    public static void showMenuStage(){
        menuView.getMenuStage().show();
    }
    public static void closeMenuStage(){
        menuView.getMenuStage().close();
    }
}
