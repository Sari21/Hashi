package controllers;

import models.Board;
import services.FileService;
import view.MenuView;

import java.io.File;

public class MenuController {

    private static MenuView menuView = new MenuView();

    public static void openGame(File file){
        Board board = FileService.ReadFile(file);
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
