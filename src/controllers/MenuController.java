package controllers;

import models.Board;
import services.FileService;
import services.interfaces.IFileService;
import solver.Solver;
import view.MenuView;

import java.io.File;

public class MenuController {

    private static MenuView menuView = new MenuView();
    private static IFileService fileService = new FileService();;

    public static void openGame(File file){
        Board board = fileService.ReadGame(file);
        closeMenuStage();
        BoardController.openGame(board);
    }
    public static void solveGame(File file){
        Board board = fileService.ReadGame(file);
        closeMenuStage();
        Board board2 = Solver.solve(board);
        BoardController.openGame(board2);
    }
    public static void openSolution(File file){
        Board board = fileService.ReadSolution(file);
        closeMenuStage();
        BoardController.openGame(board);
    }

    public static void showMenuStage(){
        menuView.getMenuStage().show();
    }
    public static void closeMenuStage(){
        menuView.getMenuStage().close();
    }
}
