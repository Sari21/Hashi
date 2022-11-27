package main.controllers;

import com.google.cloud.firestore.Query;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.database.BoardModelDTOConverter;
import main.database.ManageData;
import main.database.QueryData;
import main.models.Board;
import main.models.Level;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class MenuGameController implements Initializable {
    @Inject
    private static ManageData manageData = new ManageData();
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
            Image img = new Image("/images/sad.png");
            ImageView iv = new ImageView(img);
            iv.setFitHeight(50);
            iv.setFitWidth(50);
            Notifications notification = Notifications.create()
                    .title("Hiba")
                    .text("Nagy a baj")
                    .graphic(iv)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);
            notification.show();
        }


//        setBoardStage();
//        BoardController boardController = new BoardController(board);
//        boardView.getBoardStage().show();

    }
}
