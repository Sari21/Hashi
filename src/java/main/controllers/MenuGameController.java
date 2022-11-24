package main.controllers;

import com.google.cloud.firestore.Query;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import main.database.BoardModelDTOConverter;
import main.database.ManageData;
import main.database.QueryData;
import main.models.Board;
import main.models.Level;
import main.view.MenuGameView;

import java.util.Map;

public class MenuGameController {
    private static MenuGameView menuGameView = new MenuGameView();
    @Inject
    private static ManageData manageData = new ManageData();
    @Inject
    private static QueryData queryData = new QueryData();
//    @FXML
//    private ComboBox<Integer> sizeCombo;
//    @FXML
//    private ComboBox<Level> levelCombo;
//

    public static void openGame(Level level, int size) throws Exception {
//        Board board = fileService.readGame(file);
//        if(board.getBridges().isEmpty()){
//            board.setBridges(LPSolver.solveAndGetBridges(board));
//        }
//        File newFile = fileService.saveNewBoard(board);
//        board = fileService.readGame(newFile);
        Query query = queryData.createQuery(level, size);
        Map<String, Object> data = query.get().get().getDocuments().get(0).getData();
        Board board = BoardModelDTOConverter.mapToModelConverter(data, level, size);

        closeMenuStage();
        BoardController.openGame(board);
    }

    public static void showMenuStage() {
        menuGameView.getMenuStage().show();
    }

    public static void closeMenuStage() {
        menuGameView.getMenuStage().close();
    }

}
