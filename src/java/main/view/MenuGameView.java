package main.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.controllers.MenuGameController;
import main.models.Level;

import java.awt.*;

public class MenuGameView implements ViewElement {
    private Desktop desktop = Desktop.getDesktop();
    private Stage stage;

    public MenuGameView() {
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

        Label sizeLabel = new javafx.scene.control.Label("Size:");
        Label levelLabel = new Label("Level:");
        final ComboBox<Integer> sizeComboBox = new ComboBox<>();
        sizeComboBox.getItems().addAll(
                7, 10, 15, 25);
        final ComboBox<Level> levelComboBox = new ComboBox<>();
        levelComboBox.getItems().addAll(
                 Level.EASY, Level.MEDIUM, Level.HARD);

        levelComboBox.setValue(Level.EASY);
        sizeComboBox.setValue(7);

        HBox sizeHB = new HBox();
        HBox levelHB = new HBox();
        sizeHB.getChildren().addAll(sizeLabel, sizeComboBox);
        levelHB.getChildren().addAll(levelLabel, levelComboBox);
        sizeHB.setSpacing(12);
        levelHB.setSpacing(12);
        Button startGameButton = new Button("Start game");
        GridPane.setConstraints(title, 0, 0);
        GridPane.setConstraints(sizeHB, 0, 1);
        GridPane.setConstraints(levelHB, 0, 2);
        GridPane.setConstraints(startGameButton, 0, 3);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        startGameButton.setMinWidth(140);
        gridPane.setMinWidth(startGameButton.getMinWidth());
        gridPane.getChildren().addAll(title, sizeHB, levelHB, startGameButton);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(gridPane);
        // rootGroup.getChildren().addAll(title);
        gridPane.setAlignment(Pos.CENTER);

        startGameButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                            try {
                                MenuGameController.openGame(levelComboBox.getValue(), sizeComboBox.getValue());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                    }
                });

        rootGroup.setPadding(new Insets(12, 12, 12, 12));
        stage.setScene(new Scene(rootGroup, 400, 350));
        stage.getScene().setFill(Color.CORAL);

    }
    public Stage getMenuStage() {
        return stage;
    }
}