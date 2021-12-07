package view;



import java.awt.*;
import java.io.File;
import java.io.IOException;

import controllers.MenuController;
import gurobi.GRBException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.PuzzleGeneratorService;

public class MenuView implements ViewElement {
        private Desktop desktop = Desktop.getDesktop();
        private FileChooser fileChooser = new FileChooser();
    private Stage stage;

        public MenuView(){
            stage = new Stage();
            stage.setTitle("Menu");


            final GridPane gridPane = new GridPane();
            Text title = new Text("HASHI");
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
            widthHB.setSpacing(10);
            heightHB.setSpacing(10);
            islandsHB.setSpacing(10);

            Button openSolution = new Button("Open solution");
            Button generateNewGame = new Button("Generate new game");
            Button generateNewGameWithSolution = new Button("Generate new game with solution");
            Button openGame = new Button ("Open game");
            Button solveGameLP = new Button ("Solve game LP");
            Button solveGameST = new Button ("Solve game ST");

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
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            generateNewGame.setMinWidth(140);
            gridPane.setMinWidth(generateNewGame.getMinWidth());
            gridPane.getChildren().addAll(title, openGame, openSolution, generateNewGame, generateNewGameWithSolution,
                    widthHB, heightHB, islandsHB, solveGameLP, solveGameST);

            final Pane rootGroup = new VBox(12);
            rootGroup.getChildren().addAll(gridPane);
           // rootGroup.getChildren().addAll(title);
            rootGroup.setPadding(new Insets(12, 12, 12, 12));


            openSolution.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(final ActionEvent e) {
                            File file = fileChooser.showOpenDialog(stage);
                            if (file != null) {
                                MenuController.openSolution(file);
                            }
                        }
                    });
            openGame.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(final ActionEvent e) {
                            File file = fileChooser.showOpenDialog(stage);
                            if (file != null) {
                                MenuController.openGame(file);
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
                                    MenuController.solveGame(file);
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
                                    MenuController.solveGameWithSolvingTechniques(file);
                                } catch (GRBException ex) {
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
                            int h = heightTextField.getCharacters().toString().equals("") ? 6 :Integer.parseInt(heightTextField.getCharacters().toString());
                            int i = islandsTextField.getCharacters().toString().equals("") ? 6 :Integer.parseInt(islandsTextField.getCharacters().toString());

                            File file =   PuzzleGeneratorService.generatePuzzle( w, h, i);
                            MenuController.openGame(file);
                        }
                    });
            generateNewGameWithSolution.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(final ActionEvent e) {
                            int w = widthTextField.getCharacters().toString().equals("") ? 6 : Integer.parseInt(widthTextField.getCharacters().toString());
                            int h = heightTextField.getCharacters().toString().equals("") ? 6 :Integer.parseInt(heightTextField.getCharacters().toString());
                            int i = islandsTextField.getCharacters().toString().equals("") ? 6 :Integer.parseInt(islandsTextField.getCharacters().toString());
                            File file = PuzzleGeneratorService.generatePuzzle(w, h, i);
                            MenuController.openSolution(file);
                        }
                    });

            stage.setScene(new Scene(rootGroup, 400, 250));
        }
        public Stage getMenuStage(){
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
