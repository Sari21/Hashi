package controllers;

import fields.Board;
import fields.Bridge;
import fields.Island;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.BoardView;
import view.BridgeElement;
import view.IslandElement;

public class BoardController {
    private static Board board;
    private static BoardView boardView;
    private static Stage boardStage = new Stage();

    public static void setBoardStage(){
        Group root = new Group();
        for(Island island : board.getIslands()){
            IslandElement islandElement = new IslandElement(island);
            boardView.getIslandElements().add(islandElement);
            root.getChildren().addAll(islandElement.getCircle());
            root.getChildren().addAll(islandElement.getNumber());

        }
        for(Bridge bridge : board.getBridges()){
            BridgeElement bridgeView = new BridgeElement(bridge);
            boardView.getBridgeViews().add(bridgeView);
            root.getChildren().addAll(bridgeView.getLine());
            if(bridgeView.getDoubleLine() != null){
                root.getChildren().addAll(bridgeView.getDoubleLine());
            }
        }
//        boardStage.setScene(new Scene(root, this.width, this.height));
//        boardStage.setTitle("Hashi");
//        boardStage.show();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public BoardView getBoardView() {
        return boardView;
    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }
}
