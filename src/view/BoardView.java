package view;

import controllers.MenuController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.WindowEvent;
import models.Board;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Bridge;
import models.Island;

import java.util.ArrayList;

public class BoardView implements ViewElement {
    private Board board;
    private ArrayList<BridgeElement> bridgeElements = new ArrayList<>();
    private ArrayList<IslandElement> islandElements = new ArrayList<>();
    private int width, height;
    private Group root;
    private Stage boardStage;

    public BoardView(Board board) {
        this.height = board.getHeight() * FIELD_WIDTH;
        this.width = board.getWidth() * FIELD_WIDTH;
        this.board = board;



        root = new Group();
        for (Island island : board.getIslands()) {
            IslandElement islandElement = new IslandElement(island);
            islandElements.add(islandElement);
            root.getChildren().addAll(islandElement.getCircle());
            root.getChildren().addAll(islandElement.getNumber());

        }
        for (Bridge bridge : board.getBridges()) {
            BridgeElement bridgeView = new BridgeElement(bridge);
            bridgeElements.add(bridgeView);
            root.getChildren().addAll(bridgeView.getLine());
            if (bridgeView.getDoubleLine() != null) {
                root.getChildren().addAll(bridgeView.getDoubleLine());
            }
        }

        boardStage = new Stage();
        boardStage.setScene(new Scene(root, this.width, this.height));
        boardStage.setResizable(false);
        boardStage.setTitle("Hashi");

        boardStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                MenuController.showMenuStage();
              //  Platform.exit();
              //  System.exit(0);
            }
        });
    }

    public void refreshBridges() {
        for (BridgeElement b : bridgeElements) {
            root.getChildren().removeAll(b.getDoubleLine(), b.getLine());
        }
        bridgeElements.clear();

        for (Bridge bridge : board.getBridges()) {
            BridgeElement bridgeView = new BridgeElement(bridge);
            bridgeElements.add(bridgeView);
            root.getChildren().addAll(bridgeView.getLine());
            if (bridgeView.getDoubleLine() != null) {
                root.getChildren().addAll(bridgeView.getDoubleLine());
            }
        }
    }



    public BoardView() {
    }

    public Stage getBoardStage() {
        return boardStage;
    }

    public void setBoardView(Group root) {

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<BridgeElement> getBridgeElements() {
        return bridgeElements;
    }

    public void setBridgeElements(ArrayList<BridgeElement> bridgeElements) {
        this.bridgeElements = bridgeElements;
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

    public Group getRoot() {
        return root;
    }

    public void setRoot(Group root) {
        this.root = root;
    }
}
