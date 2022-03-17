package view;

import controllers.BoardController;
import controllers.MenuController;
import gurobi.GRBException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.WindowEvent;
import models.Board;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Bridge;
import models.Island;

import java.io.File;
import java.util.ArrayList;

public class BoardView implements ViewElement {
    private Board board;
    private ArrayList<BridgeElement> bridgeElements = new ArrayList<>();
    private ArrayList<IslandElement> islandElements = new ArrayList<>();
    private int width, height;
    private Group root;
    private Stage boardStage;
    private boolean gameMode;

    public BoardView(Board board, boolean gameMode) {
        this.height = board.getHeight() * FIELD_WIDTH + 50;
        this.width = board.getWidth() * FIELD_WIDTH;
        this.board = board;
        this.gameMode = gameMode;

        root = new Group();
        for (Bridge bridge : board.getBridges()) {
            BridgeElement bridgeView = new BridgeElement(bridge);
            bridgeElements.add(bridgeView);
            root.getChildren().addAll(bridgeView.getLine());
            if (bridgeView.getDoubleLine() != null) {
                root.getChildren().addAll(bridgeView.getDoubleLine());
            }
        }
        for (Island island : board.getIslands()) {
            IslandElement islandElement = new IslandElement(island);
            islandElements.add(islandElement);
            root.getChildren().addAll(islandElement.getCircle());
            root.getChildren().addAll(islandElement.getNumber());
        }
//        if (gameMode) {
        if(true){
            Button checkSolutionButton = new Button("Check solution");
            checkSolutionButton.setAlignment(Pos.BOTTOM_CENTER);
            checkSolutionButton.setLayoutX(10);
            checkSolutionButton.setLayoutY(height - 40);
            root.getChildren().add(checkSolutionButton);
            Label result = new Label();
            result.setText("");
            result.setAlignment(Pos.BOTTOM_CENTER);
            result.setLayoutX(130);
            result.setLayoutY(height - 40);
            result.setFont(Font.font("Veranda", FontWeight.LIGHT, 12));
            root.getChildren().add(result);

            checkSolutionButton.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(final ActionEvent e) {
                            try {
                                if (BoardController.checkSolution(board)) {
                                    result.setText("Perfect!");
                                }
                                else{
                                    result.setText(":(");
                                }
                            } catch (GRBException ex) {
                                ex.printStackTrace();
                            }


                        }
                    });
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


    public BoardView(boolean gameMode) {
        this.gameMode = gameMode;
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
