package controllers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import models.Board;
import models.Bridge;
import view.BoardView;
import view.BridgeElement;
import view.IslandElement;

public class BoardController {
    private static Board board;
    private static BoardView boardView;
    private static IslandElement startIsland, endIsland;

    private static void setBoardStage() {
        boardView = new BoardView(board);
        setEventHandlersForIslands();
        setEventHandlersForBridges();
    }

    public static void openGame(Board board) {
        setBoard(board);
        boardView = new BoardView();
        setBoardStage();
        showBoardStage();
    }

    public static void showBoardStage() {
        setBoardStage();
        boardView.getBoardStage().show();
    }

    public static void closeBoardStage() {
        boardView.getBoardStage().close();
    }

    private static void setEventHandlersForIslands() {
        for (IslandElement islandElement : boardView.getIslandElements()) {
            islandElement.getCircle().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent e) {
                    if (e.getButton().name().equals("PRIMARY")) {
                        if (startIsland == null) {
                            startIsland = islandElement;
                            islandElement.getCircle().setStroke(Color.BURLYWOOD);
                        }
                        else if(endIsland == null && startIsland == islandElement){
                            startIsland.getCircle().setStroke(null);
                            endIsland = null;
                            startIsland = null;
                        }
                        else if (endIsland == null && startIsland != islandElement) {
                            endIsland = islandElement;
                            if ((startIsland.getIsland().getPosition().getX() == endIsland.getIsland().getPosition().getX() ||
                                    startIsland.getIsland().getPosition().getY() == endIsland.getIsland().getPosition().getY())) {
                                startIsland.getCircle().setStroke(null);
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
                                startIsland.getCircle().setStroke(null);
                                endIsland = null;
                                startIsland = null;
                            }
                        }
                    }
                    else if(e.getButton().name().equals("SECONDARY")) {
                        islandElement.mark();
                    }
                }
            });
        }
    }

    private static void setEventHandlersForBridges() {
        for (BridgeElement bridgeElement : boardView.getBridgeElements()) {
            bridgeElement.getLine().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent e) {

                        System.out.println("katt");
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

    public static Board getBoard() {
        return board;
    }

    public static void setBoard(Board board) {
        BoardController.board = board;
    }

    public static BoardView getBoardView() {
        return boardView;
    }

    public static void setBoardView(BoardView boardView) {
        BoardController.boardView = boardView;
    }
}
