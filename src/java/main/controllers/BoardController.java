package main.controllers;

import gurobi.GRBException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.models.Board;
import main.models.Bridge;
import main.models.Island;
import main.solver.mathematical.LPSolver;
import main.view.BridgeElement;
import main.view.IslandElement;
import main.view.NotificationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class BoardController {
    private Board board;
    private ArrayList<BridgeElement> bridgeElements = new ArrayList<>();
    private ArrayList<IslandElement> islandElements = new ArrayList<>();
    private IslandElement startIsland, endIsland;

    @FXML
    private Pane islandsPane;
    @FXML
    private Pane bridgesPane;
    @FXML
    private Pane valuePane;
    private String SECONDARY = "SECONDARY";
    private String PRIMARY = "PRIMARY";
    private boolean solution = false;

    private void setEventHandlersForIslands() {
        for (IslandElement islandElement : islandElements) {
            islandElement.getCircle().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent e) {
                    if (e.getButton().name().equals(PRIMARY) && !solution) {
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
                                //todo ezt a modell végezze
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
                                refreshBridges();
                                setEventHandlersForBridges();
                                clearSelectedIslands();
                            } else {
                                clearSelectedIslands();
                            }
                        }
                    } else if (e.getButton().name().equals(SECONDARY) && !solution) {
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
                    if (!bridgeElement.getBridge().isDouble() && !solution) {
                        board.getBridges().remove(bridgeElement.getBridge());
                    } else if (!solution) {
                        bridgeElement.getBridge().setDouble(false);
                    }
                    refreshBridges();
                    setEventHandlersForBridges();
                    clearSelectedIslands();
                }
            });
            bridgeElement.getDoubleLine().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent e) {
                    if (!solution) {
                        bridgeElement.getBridge().setDouble(false);
                        refreshBridges();
                        setEventHandlersForBridges();
                        clearSelectedIslands();
                    }

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
            LPSolver lpSolver = new LPSolver();

            board.setSolutionBridges(lpSolver.solveAndGetBridges(board));
        }
        Collections.sort(board.getSolutionBridges());
        Collections.sort(board.getBridges());

        boolean isCorrect = board.getSolutionBridges().equals(board.getBridges());
        String text;
        if (isCorrect) {
            NotificationView.showNotification("Correct solution!", "Great job!", false);

        } else {
            NotificationView.showNotification("Incorrect solution!", "Don't give up!", true);

        }

    }

    public Board getBoard() {
        return board;
    }

    public void setGame(Board board) {
        this.board = board;
        refreshBridges();
        for (Island island : board.getIslands()) {
            IslandElement islandElement = new IslandElement(island, board.getWidth());
            islandElements.add(islandElement);
            islandsPane.getChildren().add(islandElement.getCircle());
            valuePane.getChildren().add(islandElement.getNumber());
        }
        setEventHandlersForIslands();
        setEventHandlersForBridges();

    }

    private void refreshBridges() {
        for (BridgeElement b : bridgeElements) {
            bridgesPane.getChildren().removeAll(b.getDoubleLine(), b.getLine());
        }
        bridgeElements.clear();
        ArrayList<Bridge> bridges = solution ? board.getSolutionBridges() : board.getBridges();
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
        solution = false;
        refreshBridges();

    }

    public void showSolution(ActionEvent actionEvent) throws GRBException {
        if (board.getSolutionBridges().isEmpty()) {
            LPSolver lpSolver = new LPSolver();
            board.setSolutionBridges(lpSolver.solveAndGetBridges(board));
        }
        solution = !solution;
        refreshBridges();
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

    public boolean isSolution() {
        return solution;
    }

    public void setSolution(boolean solution) {
        this.solution = solution;
    }
}
