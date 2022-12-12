package main.controllers;

import com.google.cloud.firestore.Query;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.database.BoardModelDTOConverter;
import main.database.QueryData;
import main.models.Board;
import main.models.Level;
import main.view.NotificationView;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class MenuGameController implements Initializable {
    @Inject
    private static QueryData queryData = new QueryData();

    public Button startButton;
    public ComboBox<Level> levelCombo;
    public ComboBox<Integer> sizeCombo;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        ObservableList<Integer> sizeList = FXCollections.observableArrayList(7, 10, 15, 25);
        sizeCombo.getItems().addAll(sizeList);
        sizeCombo.setValue(7);
        ObservableList<Level> levelList = FXCollections.observableArrayList(Level.EASY, Level.MEDIUM, Level.HARD);
        levelCombo.setItems(levelList);
        levelCombo.setValue(Level.EASY);

    }
    public static void showMenuStage() {
//        @FXML
//
//        menuGameView.getMenuStage().show();
    }

    public void openGame(ActionEvent actionEvent) {
        try {

            Level level = levelCombo.getValue();
            Integer size = sizeCombo.getValue();
            Query query = queryData.createQuery(level, size);
            Map<String, Object> data = query.get().get().getDocuments().get(0).getData();
            Board board = BoardModelDTOConverter.mapToModelConverter(data, level, size);

//        BoardView boardView = new BoardView(true);
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/views/game.fxml"));
            Parent rootNode = fxmlLoader.load();
            ((BoardController) fxmlLoader.getController()).setGame(board);
            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Hashi");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            NotificationView.showNotification("Error", "Error opening puzzle", true);

        }
    }
}
