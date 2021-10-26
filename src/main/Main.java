package main;

import controllers.MenuController;
import javafx.application.Application;
import javafx.stage.Stage;
import models.Board;
import models.Coordinates;
import models.Island;
import solver.Solver;
import view.BoardView;


public class Main extends Application {

      // static Board board;
       private static BoardView boardView;

    @Override
    public void start(Stage primaryStage) throws Exception{
        MenuController.showMenuStage();
//        Board board = new Board();
//        board.addIsland(new Island(new Coordinates(1,1), 1, 2));
//        board.addIsland(new Island(new Coordinates(3,1), 2, 3));
//        board.addIsland(new Island(new Coordinates(2,4), 3, 3));
//        board.addIsland(new Island(new Coordinates(4,1), 4, 4));
//        board.addIsland(new Island(new Coordinates(4,4), 5, 1));
//        board.addIsland(new Island(new Coordinates(5,3), 6, 1));
//        Solver.solve(board);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
