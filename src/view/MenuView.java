package view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import controllers.MenuController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

            final GridPane gridPane = new GridPane();
            Text text2 = new Text("HASHI");

            GridPane.setConstraints(openButton, 0, 0);
            gridPane.setHgap(6);
            gridPane.setVgap(6);
            gridPane.getChildren().addAll(openButton);

            final Pane rootGroup = new VBox(12);
            rootGroup.getChildren().addAll(gridPane);
            rootGroup.getChildren().addAll(text2);
            rootGroup.setPadding(new Insets(12, 12, 12, 12));

            stage.setScene(new Scene(rootGroup, 200, 200));
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
