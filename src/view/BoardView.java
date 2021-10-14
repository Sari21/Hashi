package view;

import fields.Board;
import fields.Bridge;
import fields.Island;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BoardView implements ViewElement{
    private Board board;
    private ArrayList<BridgeElement> bridgeViews = new ArrayList<>();
    private ArrayList<IslandElement> islandElements = new ArrayList<>();
    private int width, height;

    public BoardView(Board board) {
        this.height = board.getHeight() * FIELD_WIDTH;
        this.width = board.getWidth() * FIELD_WIDTH;
        this.board = board;
    }

    public void getBoardStage(){
        Stage boardStage = new Stage();
        Group root = new Group();
        for(Island island : this.board.getIslands()){
            IslandElement islandElement = new IslandElement(island);
            this.islandElements.add(islandElement);
            root.getChildren().addAll(islandElement.getCircle());
            root.getChildren().addAll(islandElement.getNumber());

        }
        for(Bridge bridge : this.board.getBridges()){
            BridgeElement bridgeView = new BridgeElement(bridge);
            this.bridgeViews.add(bridgeView);
            root.getChildren().addAll(bridgeView.getLine());
            if(bridgeView.getDoubleLine() != null){
                root.getChildren().addAll(bridgeView.getDoubleLine());
            }
        }
        boardStage.setScene(new Scene(root, this.width, this.height));
        boardStage.setTitle("Hashi");
        boardStage.show();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<BridgeElement> getBridgeViews() {
        return bridgeViews;
    }

    public void setBridgeViews(ArrayList<BridgeElement> bridgeViews) {
        this.bridgeViews = bridgeViews;
    }

    public ArrayList<IslandElement> getIslandElements() {
        return islandElements;
    }

    public void setIslandElements(ArrayList<IslandElement> islandElements) {
        this.islandElements = islandElements;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
