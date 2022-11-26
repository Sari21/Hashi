package main.view;

import main.controllers.MenuTestController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.WindowEvent;
import main.models.Board;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.models.Bridge;
import main.models.Island;

import java.util.ArrayList;

public class BoardView implements ViewElement {
    private Board board;
    private ArrayList<BridgeElement> bridgeElements = new ArrayList<>();
    private ArrayList<IslandElement> islandElements = new ArrayList<>();
    private int width, height;
    private Group root;
    private Stage boardStage;
    private Button checkSolutionButton;


public BoardView(Board board) {
        this.height = board.getHeight() * FIELD_WIDTH + 50;
        this.width = board.getWidth() * FIELD_WIDTH;
        this.board = board;

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

//            checkSolutionButton = new Button("Check solution");
//            checkSolutionButton.setAlignment(Pos.BOTTOM_CENTER);
//            checkSolutionButton.setLayoutX(10);
//            checkSolutionButton.setLayoutY(height - 40);
//            root.getChildren().add(checkSolutionButton);
//            Label result = new Label();
//            result.setText("");
//            result.setAlignment(Pos.BOTTOM_CENTER);
//            result.setLayoutX(130);
//            result.setLayoutY(height - 40);
//            result.setFont(Font.font("Veranda", FontWeight.LIGHT, 12));
//            root.getChildren().add(result);

        boardStage = new Stage();
        boardStage.setScene(new Scene(root, this.width, this.height));
        boardStage.setResizable(false);
        boardStage.setTitle("Hashi");

        //todo

        boardStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                MenuTestController.showMenuStage();
                //  Platform.exit();
                //  System.exit(0);
            }
        });
    }

    //todo
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

    public Button getCheckSolutionButton() {
        return checkSolutionButton;
    }

    public void setCheckSolutionButton(Button checkSolutionButton) {
        this.checkSolutionButton = checkSolutionButton;
    }
}
