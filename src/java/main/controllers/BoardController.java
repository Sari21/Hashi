package main.controllers;

import gurobi.GRBException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.models.Board;
import main.models.Bridge;
import main.services.FileService;
import main.services.interfaces.IFileService;
import main.solver.mathematical.LPSolver;
import main.view.BoardView;
import main.view.BridgeElement;
import main.view.IslandElement;

import java.net.URL;
import java.util.ResourceBundle;


public class BoardController implements Initializable {
    public Pane gameMenuPane;
    public Pane gamePane;
    private Board board;
    private BoardView boardView;
    private IslandElement startIsland, endIsland;
    private IFileService fileService = new FileService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        boardView = new BoardView(board);

    }
    private  void setBoardStage() {
//        boardView = new BoardView(board, true);

    }

    public  void openGame(Board board) {

    }

    public  void showBoardStage() {
        setBoardStage();
        boardView.getBoardStage().show();
    }

    public  void closeBoardStage() {
        boardView.getBoardStage().close();
    }

    private  void setEventHandlersForIslands() {
        for (IslandElement islandElement : boardView.getIslandElements()) {
            islandElement.getCircle().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent e) {
                    if (e.getButton().name().equals("PRIMARY")) {
                        if (startIsland == null) {
                            startIsland = islandElement;
                            islandElement.addStroke();
                        } else if (endIsland == null && startIsland == islandElement) {
                            startIsland.removeStroke();
                            endIsland = null;
                            startIsland = null;
                        }
//                        else if (endIsland == null &&
//                                (Math.abs(startIsland.getIsland().getPosition().getX() - islandElement.getIsland().getPosition().getX()) == 1
//                                        || Math.abs(startIsland.getIsland().getPosition().getY() - islandElement.getIsland().getPosition().getY()) == 1)) {
//                            startIsland.removeStroke();
//                            endIsland = null;
//                            startIsland = null;
//                        }
                        else if (endIsland == null && startIsland != islandElement) {
                            endIsland = islandElement;
                            if ((startIsland.getIsland().getPosition().getX() == endIsland.getIsland().getPosition().getX() ||
                                    startIsland.getIsland().getPosition().getY() == endIsland.getIsland().getPosition().getY())) {
                                startIsland.removeStroke();
                                Bridge newBridge = null;
                                boolean done = false;
                                for (Bridge b : board.getBridges()) {
                                    newBridge = null;
                                    if ((b.getStartIsland().equals(startIsland.getIsland()) && b.getEndIsland().equals(endIsland.getIsland()))
                                            || (b.getStartIsland().equals(endIsland.getIsland()) && b.getEndIsland().equals(startIsland.getIsland()))) {
                                        if (!b.isDouble()) {
                                            b.setDouble(true);
                                        }
                                        done = true;
                                        break;
                                    }
                                }
                                if (!done) {
                                    newBridge = new Bridge(startIsland.getIsland(), endIsland.getIsland());
                                }
                                if (newBridge != null) {
                                    board.addBridge(newBridge);
                                }
                                boardView.refreshBridges();
                                setEventHandlersForBridges();
                                endIsland = null;
                                startIsland = null;
                            } else {
                                startIsland.removeStroke();
                                endIsland = null;
                                startIsland = null;
                            }
                        }
                    } else if (e.getButton().name().equals("SECONDARY")) {
                        islandElement.mark();
                    }
                }
            });
        }
    }

    private void setEventHandlersForBridges() {
        for (BridgeElement bridgeElement : this.boardView.getBridgeElements()) {
            bridgeElement.getLine().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent e) {
                    if (!bridgeElement.getBridge().isDouble()) {
                        board.getBridges().remove(bridgeElement.getBridge());
                    } else {
                        bridgeElement.getBridge().setDouble(false);
                    }
                    boardView.refreshBridges();
                    setEventHandlersForBridges();
                    startIsland = null;
                    endIsland = null;
                }
            });
            bridgeElement.getDoubleLine().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent e) {
                    bridgeElement.getBridge().setDouble(false);
                    boardView.refreshBridges();
                    setEventHandlersForBridges();
                    startIsland = null;
                    endIsland = null;
                }
            });
        }
    }
    private void setEventListenerForCheckSolutionButton(){
        this.boardView.getCheckSolutionButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        try {
                            if (checkSolution(board)) {
//                                result.setText("Perfect!");
                            }
                            else{
//                                result.setText(":(");
                            }
                        } catch (GRBException ex) {
                            ex.printStackTrace();
                        }


                    }
                });

    }

    //todo
    public  boolean checkSolution(Board board) throws GRBException {
        Board solution = fileService.readSolution(board);
//        if(board.getBridges().isEmpty()){
//            board.setBridges(LPSolver.solveAndGetBridges(board));
//        }
        return board.equals(solution);
    }

    public  Board getBoard() {
        return board;
    }

    public  void setBoard(Board board) {
        this.board = board;
        boardView = new BoardView(board);
        gamePane.getChildren().add(boardView.getRoot());
        setEventHandlersForIslands();
        setEventHandlersForBridges();

    }

    public  BoardView getBoardView() {
        return boardView;
    }

    public  void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }


}
