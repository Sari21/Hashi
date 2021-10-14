package main;

import fields.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.BoardView;
import view.MenuView;

public class Main extends Application {

      // static Board board;
       private static BoardView boardView;

    @Override
    public void start(Stage primaryStage) throws Exception{
       /* Board board = FileService.ReadFile();
        if (board != null) {
            BoardView boardView = new BoardView(board);
            boardView.getBoardStage().show();

        */
        MenuView.getMenuView().show();

       /* Group root = new Group();
        for(Island island : boardView.getBoard().getIslands()){
            IslandView islandView = new IslandView(island);
            boardView.getIslandViews().add(islandView);
            root.getChildren().addAll(islandView.getCircle());
            root.getChildren().addAll(islandView.getNumber());

        }
        for(Bridge bridge : boardView.getBoard().getBridges()){
            BridgeView bridgeView = new BridgeView(bridge);
            boardView.getBridgeViews().add(bridgeView);
            root.getChildren().addAll(bridgeView.getLine());
            if(bridgeView.getDoubleLine() != null){
                root.getChildren().addAll(bridgeView.getDoubleLine());
            }
        }
        primaryStage.setScene(new Scene(root, boardView.getWidth(), boardView.getHeight()));
        primaryStage.setTitle("Hashi");
        primaryStage.show();

        */
    }


    public static void main(String[] args) {
      /*  ArrayList<Island> islands = new ArrayList<>();
        ArrayList<Bridge> bridges = new ArrayList<>();
        Island i1 = new Island(2, new Coordinates(2,1), 1);
        Island i2 = new Island(2, new Coordinates(5, 1), 2);
        Bridge b1 = new Bridge(i1, i2, true, false);

        islands.add(i1);
        islands.add(i2);
        bridges.add(b1);

        Island i3 = new Island(1, new Coordinates(3,2), 3);
        Island i4 = new Island(1, new Coordinates(3, 5), 4);
        Bridge b2 = new Bridge(i3, i4, false, true);

        islands.add(i3);
        islands.add(i4);
        bridges.add(b2);

        Board board = new Board(5, 5, islands, bridges);
        boardView = new BoardView(board);
        FileService.printBoardToCsv(board);*/


        launch(args);
    }
    public Scene createScene(Board board){

        return null;

    }
}
