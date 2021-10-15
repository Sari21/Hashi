package view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import controllers.MenuController;
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

public class MenuView implements ViewElement {
        private Desktop desktop = Desktop.getDesktop();
        private FileChooser fileChooser = new FileChooser();
        private Button openButton = new Button("Open");
        private Stage stage;

        public MenuView(){
            stage = new Stage();
            stage.setTitle("Menu");

            openButton.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(final ActionEvent e) {
                            File file = fileChooser.showOpenDialog(stage);
                            if (file != null) {
                                MenuController.openGame(file);
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
