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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.classifier.RandomForestClassifier;
import main.classifier.TensorflowClassifier;
import main.database.BoardModelDTOConverter;
import main.database.ManageData;
import main.database.QueryData;
import main.models.Board;
import main.models.Bridge;
import main.models.Level;
import main.services.FileService;
import main.services.PuzzleGeneratorService;
import main.services.interfaces.IFileService;
import main.solver.mathematical.LPSolver;
import main.solver.solvingTechniques.STSolver;
import main.view.NotificationView;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class MenuTestController implements Initializable {
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
    }

    public static void predictFromFileTF(File file) throws GRBException {
        Board board = fileService.readGame(file);
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

    @FXML
    public void generateNewGame(ActionEvent actionEvent) {
        try {
            Board board = PuzzleGeneratorService.generatePuzzle(sizeCombo.getValue(), sizeCombo.getValue(), Integer.parseInt(numOfIslandsField.getText()));
            if (board == null) {
                NotificationView.showNotification("Error", "It is not possible to create a puzzle with these parameters", true);
            }
            LPSolver lpSolver = new LPSolver();

            ArrayList<Bridge> bridges = lpSolver.solveAndGetBridges(board);
            while (bridges.isEmpty() || board == null) {
                board = PuzzleGeneratorService.generatePuzzle(sizeCombo.getValue(), sizeCombo.getValue(), Integer.parseInt(numOfIslandsField.getText()));
                bridges = lpSolver.solveAndGetBridges(board);
            }
            board.setSolutionBridges(bridges);
//        while (LPSolver.hasMultipleSolutions()) {
//            board = PuzzleGeneratorService.generatePuzzle(w, h, i);
//            LPSolver.solve(board);
//        }
            Level level = predictTF(board);
            board.setLevel(level);
            manageData.addSimpleDocumentAsEntity(board);
        } catch (Exception e) {
            NotificationView.showNotification("Error", "It is not possible to create a puzzle with these parameters", true);

        }
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

    @FXML
    public void solveGameLP(ActionEvent actionEvent) {
        Stage stage = new Stage();
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Board board = fileService.readGame(file);
            LPSolver lpSolver = new LPSolver();
            try {
                board.setSolutionBridges(lpSolver.solveAndGetBridges(board));
            } catch (GRBException e) {
                NotificationView.showNotification("Error", "Can not solve board", true);
            }
            showBoard(board, true);

        }
    }

    @FXML
    public void solveGameST(ActionEvent actionEvent) {
        Stage stage = new Stage();
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Board board = fileService.readGame(file);
            STSolver stSolver = new STSolver();
            Board board2 = stSolver.solve(board);
            showBoard(board2, true);

        }
    }

    @FXML
    public void predictDifficultyRF(ActionEvent actionEvent) {
        Stage stage = new Stage();
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                Board board = fileService.readGame(file);
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
                double[] featuresDouble = new double[features.length];
                for (int i = 0; i < features.length; i++) {
                    featuresDouble[i] = features[i];
                }
//todo
                int prediction = RandomForestClassifier.predict(featuresDouble);
                Level predictedLevel;
                switch (prediction) {
                    case 0:
                        predictedLevel = Level.EASY;
                        break;
                    case 1:
                        predictedLevel = Level.MEDIUM;
                        break;
                    case 2:
                        predictedLevel = Level.HARD;
                        break;
                    default:
                        predictedLevel = Level.EASY;
                }


                NotificationView.showNotification("Random forest prediction", "Prediction: " + predictedLevel.toString() + "\nOriginal level: " + board.getLevel(), false);
            } catch (Exception e) {
                NotificationView.showNotification("Error", "Prediction error", true);
            }
        }
    }

    @FXML
    public void predictDifficultyTF(ActionEvent actionEvent) {
        Stage stage = new Stage();
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                Board board = fileService.readGame(file);
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
                double[] featuresDouble = new double[features.length];
                for (int i = 0; i < features.length; i++) {
                    featuresDouble[i] = features[i];
                }
                Level level = TensorflowClassifier.predict(features);


                NotificationView.showNotification("Random forest prediction", "Prediction: " + level.toString() + "\nOriginal level: " + board.getLevel(), false);
            } catch (Exception e) {
                NotificationView.showNotification("Error", "Prediction error", true);
            }
        }
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
            BoardController boardController = ((BoardController) fxmlLoader.getController());
            boardController.setSolution(showSolution);
            boardController.setGame(board);
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
