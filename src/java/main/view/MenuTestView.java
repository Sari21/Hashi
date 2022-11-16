package main.view;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import main.controllers.MenuTestController;
import gurobi.GRBException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.geometry.*;

public class MenuTestView implements ViewElement {
    private Desktop desktop = Desktop.getDesktop();
    private FileChooser fileChooser = new FileChooser();
    private Stage stage;

    public MenuTestView() {
        final GridPane gridPane = new GridPane();
        stage = new Stage();
        stage.setTitle("Menu");

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.GRAY);

        Text title = new Text("HASHI");
        title.setEffect(ds);
        title.setFont(Font.font("Veranda", FontWeight.LIGHT, 30));
        title.setTextAlignment(TextAlignment.CENTER);
        TextField widthTextField = new TextField();
        TextField heightTextField = new TextField();
        TextField islandsTextField = new TextField();

        Label widthLabel = new Label("Width:");
        Label heightLabel = new Label("Height:");
        Label islandsLabel = new Label("Islands:");
        HBox widthHB = new HBox();
        HBox heightHB = new HBox();
        HBox islandsHB = new HBox();
        widthHB.getChildren().addAll(widthLabel, widthTextField);
        heightHB.getChildren().addAll(heightLabel, heightTextField);
        islandsHB.getChildren().addAll(islandsLabel, islandsTextField);
        widthHB.setSpacing(12);
        heightHB.setSpacing(12);
        islandsHB.setSpacing(12);

        Button openSolution = new Button("Open solution");
        Button generateNewGame = new Button("Generate new game");
        Button generateNewGameWithSolution = new Button("Generate new game with solution");
        Button openGame = new Button("Open game");
        Button solveGameLP = new Button("Solve game LP");
        Button solveGameST = new Button("Solve game ST");
        Button solveMultipleGamesST = new Button("Solve multiple games ST");
        Button solveMultipleGamesLP = new Button("Solve multiple games LP");
        Button predictDifficultyRF = new Button("Predict difficulty RF");
        Button predictDifficultyTF = new Button("Predict difficulty TF");

        GridPane.setConstraints(title, 0, 0);
        GridPane.setConstraints(openGame, 1, 1);
        GridPane.setConstraints(openSolution, 1, 2);
        GridPane.setConstraints(widthHB, 0, 1);
        GridPane.setConstraints(heightHB, 0, 2);
        GridPane.setConstraints(islandsHB, 0, 3);
        GridPane.setConstraints(generateNewGame, 0, 4);
        GridPane.setConstraints(generateNewGameWithSolution, 0, 5);
        GridPane.setConstraints(solveGameLP, 1, 3);
        GridPane.setConstraints(solveGameST, 1, 4);
        GridPane.setConstraints(solveMultipleGamesST, 1, 5);
        GridPane.setConstraints(solveMultipleGamesLP, 1, 6);
        GridPane.setConstraints(predictDifficultyRF, 1, 7);
        GridPane.setConstraints(predictDifficultyTF, 1, 8);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        generateNewGame.setMinWidth(140);
        gridPane.setMinWidth(generateNewGame.getMinWidth());
        gridPane.getChildren().addAll(title, openGame, openSolution, generateNewGame, generateNewGameWithSolution,
                widthHB, heightHB, islandsHB, solveGameLP, solveGameST, solveMultipleGamesST, solveMultipleGamesLP,
                predictDifficultyRF, predictDifficultyTF);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(gridPane);
        // rootGroup.getChildren().addAll(title);
        gridPane.setAlignment(Pos.CENTER);


        rootGroup.setPadding(new Insets(12, 12, 12, 12));


        openSolution.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            MenuTestController.openSolution(file);
                        }
                    }
                });
        openGame.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            try {
                                MenuTestController.openGame(file);
                            } catch (GRBException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
        solveGameLP.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            try {
                                MenuTestController.solveGameLP(file);
                            } catch (GRBException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
        solveGameST.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            try {
                                MenuTestController.solveGameST(file);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
        solveMultipleGamesST.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        List<File> files = new ArrayList<>();
                        files = fileChooser.showOpenMultipleDialog(stage);
                        if (files != null && !files.isEmpty()) {
                            try {
                                MenuTestController.solveMultipleGamesST(files);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
        solveMultipleGamesLP.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        List<File> files = new ArrayList<>();
                        files = fileChooser.showOpenMultipleDialog(stage);
                        if (files != null && !files.isEmpty()) {
                            try {
                                MenuTestController.solveMultipleGamesLP(files);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });

        generateNewGame.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        int w = widthTextField.getCharacters().toString().equals("") ? 6 : Integer.parseInt(widthTextField.getCharacters().toString());
                        int h = heightTextField.getCharacters().toString().equals("") ? 6 : Integer.parseInt(heightTextField.getCharacters().toString());
                        int i = islandsTextField.getCharacters().toString().equals("") ? 6 : Integer.parseInt(islandsTextField.getCharacters().toString());
                        try {
                            MenuTestController.generatePuzzle(w, h, i, false);

                        } catch (GRBException | InterruptedException | ExecutionException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        generateNewGameWithSolution.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        int w = widthTextField.getCharacters().toString().equals("") ? 6 : Integer.parseInt(widthTextField.getCharacters().toString());
                        int h = heightTextField.getCharacters().toString().equals("") ? 6 : Integer.parseInt(heightTextField.getCharacters().toString());
                        int i = islandsTextField.getCharacters().toString().equals("") ? 6 : Integer.parseInt(islandsTextField.getCharacters().toString());
                        try {
                            MenuTestController.generatePuzzle(w, h, i, true);
                        } catch (GRBException ex) {
                            ex.printStackTrace();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        } catch (ExecutionException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        predictDifficultyRF.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            try {
                                MenuTestController.predictRF(file);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
        predictDifficultyTF.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            try {
                                MenuTestController.predictFromFileTF(file);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });

        stage.setScene(new Scene(rootGroup, 400, 350));
        stage.getScene().setFill(Color.CORAL);
    }

    public Stage getMenuStage() {
        return stage;
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
