package main.controllers;

import gurobi.GRBException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.models.Board;
import main.models.Bridge;
import main.models.Island;
import main.solver.mathematical.LPSolver;
import main.view.BridgeElement;
import main.view.IslandElement;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class BoardController {
    public Pane islandsPane;
    public Pane bridgesPane;
    private Board board;
    private ArrayList<BridgeElement> bridgeElements = new ArrayList<>();
    private ArrayList<IslandElement> islandElements = new ArrayList<>();
    public Pane gameMenuPane;
    private IslandElement startIsland, endIsland;
    private String SECONDARY = "SECONDARY";
    private String PRIMARY = "PRIMARY";

    private void setEventHandlersForIslands() {
        for (IslandElement islandElement : islandElements) {
            islandElement.getCircle().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent e) {
                    if (e.getButton().name().equals(PRIMARY)) {
                        if (startIsland == null) {
                            startIsland = islandElement;
                            islandElement.addStroke();
                        } else if (endIsland == null && startIsland == islandElement) {
                            clearSelectedIslands();
                        } else if (endIsland == null && startIsland != islandElement) {
                            endIsland = islandElement;
                            if ((startIsland.getIsland().getPosition().getX() == endIsland.getIsland().getPosition().getX() ||
                                    startIsland.getIsland().getPosition().getY() == endIsland.getIsland().getPosition().getY())) {
                                startIsland.removeStroke();
                                boolean done = false;
                                for (Bridge b : board.getBridges()) {
                                    if ((b.getStartIsland().equals(startIsland.getIsland()) && b.getEndIsland().equals(endIsland.getIsland()))
                                            || (b.getStartIsland().equals(endIsland.getIsland()) && b.getEndIsland().equals(startIsland.getIsland()))) {
                                        b.setDouble(true);
                                        done = true;
                                        break;
                                    }
                                }
                                if (!done) {
                                    Bridge newBridge = new Bridge(startIsland.getIsland(), endIsland.getIsland());
                                    board.addBridge(newBridge);
                                }
                                refreshBridges(board.getBridges());
                                setEventHandlersForBridges();
                                clearSelectedIslands();
                            } else {
                                clearSelectedIslands();
                            }
                        }
                    } else if (e.getButton().name().equals(SECONDARY)) {
                        islandElement.mark();
                    }
                }
            });
        }
    }

    private void setEventHandlersForBridges() {
        for (BridgeElement bridgeElement : this.bridgeElements) {
            bridgeElement.getLine().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent e) {
                    if (!bridgeElement.getBridge().isDouble()) {
                        board.getBridges().remove(bridgeElement.getBridge());
                    } else {
                        bridgeElement.getBridge().setDouble(false);
                    }
                    refreshBridges(board.getBridges());
                    setEventHandlersForBridges();
                    clearSelectedIslands();
                }
            });
            bridgeElement.getDoubleLine().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent e) {
                    bridgeElement.getBridge().setDouble(false);
                    refreshBridges(board.getBridges());
                    setEventHandlersForBridges();
                    clearSelectedIslands();

                }
            });
        }
    }

    private void clearSelectedIslands() {
        if (startIsland != null) {
            startIsland.removeStroke();
            startIsland = null;
        }
        if (endIsland != null) {
            endIsland.removeStroke();
            endIsland = null;
        }
    }

    @FXML
    public void checkSolution() throws GRBException {
        if (board.getSolutionBridges().isEmpty()) {
            board.setBridges(LPSolver.solveAndGetBridges(board));
        }
        Collections.sort(board.getSolutionBridges());
        Collections.sort(board.getBridges());

        boolean isCorrect = board.getSolutionBridges().equals(board.getBridges());
        String text;
        Image img;
        if(isCorrect){
            text = "Correct solution!";
            img = new Image("/images/happy.png");
        }
        else{
            text = "Incorrect solution!";
            img = new Image("/images/sad.png");
        }
        ImageView iv = new ImageView(img);
        iv.setFitHeight(50);
        iv.setFitWidth(50);
        Notifications notification = Notifications.create()
                .title(text)
                .graphic(iv)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT);
        notification.show();

    }

    public Board getBoard() {
        return board;
    }

    public void setGame(Board board) {
        this.board = board;
        for (Bridge bridge : board.getBridges()) {
            BridgeElement bridgeView = new BridgeElement(bridge, board.getWidth());
            bridgeElements.add(bridgeView);
            bridgesPane.getChildren().add(bridgeView.getLine());
//            root.getChildren().addAll(bridgeView.getLine());
            if (bridgeView.getDoubleLine() != null) {
                islandsPane.getChildren().add(bridgeView.getDoubleLine());
//                root.getChildren().addAll(bridgeView.getDoubleLine());
            }
        }
        for (Island island : board.getIslands()) {
            IslandElement islandElement = new IslandElement(island, board.getWidth());
            islandElements.add(islandElement);
            islandsPane.getChildren().add(islandElement.getCircle());
            islandsPane.getChildren().add(islandElement.getNumber());
        }
        setEventHandlersForIslands();
        setEventHandlersForBridges();

    }

    private void refreshBridges(ArrayList<Bridge> bridges) {
        for (BridgeElement b : bridgeElements) {
            bridgesPane.getChildren().removeAll(b.getDoubleLine(), b.getLine());
        }
        bridgeElements.clear();
        for (Bridge bridge : bridges) {
            BridgeElement bridgeView = new BridgeElement(bridge, board.getWidth());
            bridgeElements.add(bridgeView);
            bridgesPane.getChildren().addAll(bridgeView.getLine());
            if (bridgeView.getDoubleLine() != null) {
                bridgesPane.getChildren().addAll(bridgeView.getDoubleLine());
            }
        }
    }

    public void retry(ActionEvent actionEvent) {
        board.getBridges().clear();
        refreshBridges(board.getBridges());
    }

    public void showSolution(ActionEvent actionEvent) {
        refreshBridges(board.getSolutionBridges());
    }

    @FXML
    public void exitApplication(ActionEvent event) throws IOException {
        Platform.exit();
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/views/menu_game.fxml"));
        Parent rootNode = fxmlLoader.load();
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Hashi");
        stage.setResizable(false);
        stage.show();
    }


}
