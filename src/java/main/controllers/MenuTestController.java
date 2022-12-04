package main.controllers;

import com.google.cloud.firestore.Query;
import com.google.inject.Inject;
import gurobi.GRBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.classifier.TensorflowClassifier;
import main.database.BoardModelDTOConverter;
import main.database.ManageData;
import main.database.QueryData;
import main.models.Board;
import main.services.FileService;
import main.services.PuzzleGeneratorService;
import main.services.interfaces.IFileService;
import main.solver.mathematical.LPSolver;
import main.models.Level;
import main.solver.solvingTechniques.STSolver;
import main.view.MenuTestView;
import main.classifier.RandomForestClassifier;
import main.view.NotificationView;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;


public class MenuTestController implements Initializable {

    private static MenuTestView menuTestView = new MenuTestView();
    private FileChooser fileChooser;
    private static IFileService fileService = new FileService();
    @Inject
    private static ManageData manageData = new ManageData();
    @Inject
    private static QueryData queryData = new QueryData();
    public AnchorPane testPane;
    public ComboBox<Integer> sizeCombo;
    public ComboBox<Level> levelCombo;
    public TextField numOfIslandsField;
    public TextField numOfPuzzlesField;
    public Button newGameButton;
    public Button startButton1;

    @FXML
    public void openGameFromFile(ActionEvent actionEvent) throws GRBException {
        Stage stage = new Stage();
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                Board board = fileService.readGame(file);
                if (board.getSolutionBridges().isEmpty()) {
                    LPSolver lpSolver = new LPSolver();
                    board.setSolutionBridges(lpSolver.solveAndGetBridges(board));
                }
                File newFile = fileService.saveNewBoard(board);
                board = fileService.readGame(newFile);
//                closeMenuStage();
                showBoard(board, false);
            } catch (GRBException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void solveGameLP(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();
        LPSolver lpSolver = new LPSolver();
        Board board2 = lpSolver.solve(board);
//        BoardController.openGame(board2);
    }

    public static void solveGameST(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();
        STSolver stSolver = new STSolver();
        Board board2 = stSolver.solve(board);
//        BoardController.openGame(board2);
    }

    public static void openSolution(File file) {
        Board board = fileService.readSolution(file);
        closeMenuStage();
//        BoardController.openGame(board);
    }

    @FXML
    public void solveMultipleGamesST(ActionEvent actionEvent) {
//        closeMenuStage();
        Stage stage = new Stage();
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        List<File> files = fileChooser.showOpenMultipleDialog(stage);
        if (!files.isEmpty()) {
            for (File file : files) {
                Board board = fileService.readGame(file);
                STSolver stSolver = new STSolver();
                Board board2 = stSolver.solve(board);
                float[] features = stSolver.getFeatures();
                fileService.writeDifficulty("Difficulty_ST_data.csv", file.getName(), features, board.getLevel());
                System.out.println(file.getName());

            }
            NotificationView.showNotification("Done!", "Puzzles solved!", false);
            System.out.println("Done");
        }
    }

    @FXML
    public void solveMultipleGamesLP(ActionEvent actionEvent) throws GRBException {
        Stage stage = new Stage();
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        List<File> files = fileChooser.showOpenMultipleDialog(stage);
        if (!files.isEmpty()) {
            for (File file : files) {
                Board board = fileService.readGame(file);
                LPSolver lpSolver = new LPSolver();
                float[] features = lpSolver.solveBoardAndGetFeatures(board);
                fileService.writeDifficulty("Difficulty_LP_data.csv", file.getName(), features, board.getLevel());
            }
        }
        showMenuStage();
    }

    public static void predictRF(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();

        float[] featuresLP;
        float[] featuresST;
        LPSolver lpSolver = new LPSolver();
        featuresLP = lpSolver.solveBoardAndGetFeatures(board);
        STSolver stSolver = new STSolver();
        stSolver.solve(board);
        featuresST = stSolver.getFeatures();
        double[] features = new double[featuresLP.length + featuresST.length];
        System.arraycopy(featuresLP, 0, features, 0, featuresLP.length);
        System.arraycopy(featuresST, 0, features, featuresLP.length, featuresST.length);

        int prediction = RandomForestClassifier.predict(features);
        System.out.println(prediction);
        System.out.println(board.getLevel());
//        for (double d : features) {
//            System.out.print(d);
//            System.out.print(", ");
//        }
//        BoardController.openGame(boardLP);
    }

    public static void predictFromFileTF(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();

        Level level = predictTF(board);
        System.out.println("predicted level: " + level);
        System.out.println("original level: " + board.getLevel());
//        for (float d : features) {
//            System.out.print(d);
//            System.out.print(", ");
//        }
//        BoardController.openGame(board);
    }

    private static Level predictTF(Board board) throws GRBException {
        float[] featuresLP;
        float[] featuresST;
        LPSolver lpSolver = new LPSolver();
        featuresLP = lpSolver.solveBoardAndGetFeatures(board);
        STSolver stSolver = new STSolver();
        stSolver.solve(board);
        featuresST = stSolver.getFeatures();
        float[] features = new float[featuresLP.length + featuresST.length];
        System.arraycopy(featuresLP, 0, features, 0, featuresLP.length);
        System.arraycopy(featuresST, 0, features, featuresLP.length, featuresST.length);

        return TensorflowClassifier.predict(features);

    }

    public static void generatePuzzle(int w, int h, int i, boolean withSolution) throws
            GRBException, ExecutionException, InterruptedException {
        Board board = PuzzleGeneratorService.generatePuzzle(w, h, i);
        LPSolver lpSolver = new LPSolver();

        while (lpSolver.solve(board) == null) {
            board = PuzzleGeneratorService.generatePuzzle(w, h, i);

        }
//        while (LPSolver.hasMultipleSolutions()) {
//            board = PuzzleGeneratorService.generatePuzzle(w, h, i);
//            LPSolver.solve(board);
//        }
        Level level = predictTF(board);
        board.setLevel(level);
        manageData.addSimpleDocumentAsEntity(board);


//        FileService fileService = new FileService();
//        File file = fileService.saveNewBoard(board);
//        if (withSolution)
//            openSolution(file);
//        else
//            openGame(file);
//        System.out.println(board.getLevel().toString());
    }

    public static void showMenuStage() {
        menuTestView.getMenuStage().show();
    }

    public static void closeMenuStage() {
        menuTestView.getMenuStage().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> sizeList = FXCollections.observableArrayList(7, 10, 15, 25);
        sizeCombo.getItems().addAll(sizeList);
        sizeCombo.setValue(7);
        ObservableList<Level> levelList = FXCollections.observableArrayList(Level.EASY, Level.MEDIUM, Level.HARD);
        levelCombo.setItems(levelList);
        levelCombo.setValue(Level.EASY);

    }

    public void generateNewGame(ActionEvent actionEvent) {
    }

    public void solveGameLP(ActionEvent actionEvent) {
    }

    public void solveGameST(ActionEvent actionEvent) {
        Stage stage = new Stage();
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Board board = fileService.readGame(file);
            closeMenuStage();
            STSolver stSolver = new STSolver();
            Board board2 = stSolver.solve(board);
            showBoard(board2, true);

        }
    }


    public void predictDifficultyRF(ActionEvent actionEvent) {
    }


    public void predictDifficultyTF(ActionEvent actionEvent) {
    }

    @FXML
    public void openGame(ActionEvent actionEvent) {
        try {
            Level level = levelCombo.getValue();
            Integer size = sizeCombo.getValue();
            Query query = queryData.createQuery(level, size);
            Map<String, Object> data = query.get().get().getDocuments().get(0).getData();
            Board board = BoardModelDTOConverter.mapToModelConverter(data, level, size);
            showBoard(board, false);
        } catch (Exception e) {
            NotificationView.showNotification("Error", "Error opening puzzle", false);
        }
    }

    private void showBoard(Board board, boolean showSolution) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/views/game.fxml"));
            Parent rootNode = fxmlLoader.load();
           BoardController boardController =  ((BoardController) fxmlLoader.getController());
            boardController.setGame(board);
            boardController.setSolution(showSolution);
            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Hashi");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            NotificationView.showNotification("Error", "Error opening puzzle", false);
        }
    }


}
