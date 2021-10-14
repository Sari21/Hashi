package view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fields.Board;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.FileService;

public class MenuView implements ViewElement {
        private static Desktop desktop = Desktop.getDesktop();

        public static Stage getMenuView() {
            Stage stage = new Stage();
            stage.setTitle("Menu");

            final FileChooser fileChooser = new FileChooser();
            final Button openButton = new Button("Open");
            openButton.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(final ActionEvent e) {
                            File file = fileChooser.showOpenDialog(stage);
                            if (file != null) {
                                Board board = FileService.ReadFile(file);
                                BoardView boardView = new BoardView(board);
                                boardView.getBoardStage();
                            }
                        }
                    });

            final GridPane inputGridPane = new GridPane();

            GridPane.setConstraints(openButton, 0, 0);
            inputGridPane.setHgap(6);
            inputGridPane.setVgap(6);
            inputGridPane.getChildren().addAll(openButton);

            final Pane rootGroup = new VBox(12);
            rootGroup.getChildren().addAll(inputGridPane);
            rootGroup.setPadding(new Insets(12, 12, 12, 12));

            stage.setScene(new Scene(rootGroup));
            return stage;
        }

        public static void main(String[] args) {
            Application.launch(args);
        }

        private static void openFile(File file) {
            try {
                desktop.open(file);
            } catch (IOException ex) {
            }
        }
}
