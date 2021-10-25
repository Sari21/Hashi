package main;

import controllers.MenuController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.BoardView;


public class Main extends Application {

      // static Board board;
       private static BoardView boardView;

    @Override
    public void start(Stage primaryStage) throws Exception{
        MenuController.showMenuStage();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
