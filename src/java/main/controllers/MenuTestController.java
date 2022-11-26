package main.controllers;

import com.google.inject.Inject;
import gurobi.GRBException;
import main.classifier.TensorflowClassifier;
import main.database.ManageData;
import main.models.Board;
import main.services.FileService;
import main.services.PuzzleGeneratorService;
import main.services.interfaces.IFileService;
import main.solver.mathematical.LPSolver;
import main.models.Level;
import main.solver.solvingTechniques.STSolver;
import main.view.MenuTestView;
import main.classifier.RandomForestClassifier;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MenuTestController {

    private static MenuTestView menuTestView = new MenuTestView();
    private static IFileService fileService = new FileService();
    @Inject private static ManageData manageData = new ManageData();


    public static void openGame(File file) throws GRBException {
        Board board = fileService.readGame(file);
        if(board.getBridges().isEmpty()){
            board.setBridges(LPSolver.solveAndGetBridges(board));
        }
        File newFile = fileService.saveNewBoard(board);
        board = fileService.readGame(newFile);
        closeMenuStage();
//        BoardController.openGame(board);
    }

    public static void solveGameLP(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();
        Board board2 = LPSolver.solve(board);
//        BoardController.openGame(board2);
    }

    public static void solveGameST(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();
        Board board2 = STSolver.solve(board);
//        BoardController.openGame(board2);
    }

    public static void openSolution(File file) {
        Board board = fileService.readSolution(file);
        closeMenuStage();
//        BoardController.openGame(board);
    }

    public static void solveMultipleGamesST(List<File> files) {
        closeMenuStage();
        for (File file : files) {
            Board board = fileService.readGame(file);
            STSolver.solve(board);
            float features[] = STSolver.getFeatures();
            fileService.writeDifficulty("Difficulty_ST.csv", file.getName(), features, board.getLevel());

        }

        showMenuStage();
    }

    public static void solveMultipleGamesLP(List<File> files) throws GRBException {
        closeMenuStage();
        for (File file : files) {
            Board board = fileService.readGame(file);
            float features[] = LPSolver.getFeatures();
            fileService.writeDifficulty("Difficulty_LP.csv", file.getName(), features, board.getLevel());
        }
        showMenuStage();
    }

    public static void predictRF(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();

        float[] featuresLP;
        float[] featuresST;
        Board boardLP = LPSolver.solve(board);
        featuresLP = LPSolver.getFeatures();
        STSolver.solve(board);
        featuresST = STSolver.getFeatures();
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
        LPSolver.solve(board);
        featuresLP = LPSolver.getFeatures();
        STSolver.solve(board);
        featuresST = STSolver.getFeatures();
        float[] features = new float[featuresLP.length + featuresST.length];
        System.arraycopy(featuresLP, 0, features, 0, featuresLP.length);
        System.arraycopy(featuresST, 0, features, featuresLP.length, featuresST.length);

        return TensorflowClassifier.predict(features);

    }

    public static void generatePuzzle(int w, int h, int i, boolean withSolution) throws GRBException, ExecutionException, InterruptedException {
        Board board = PuzzleGeneratorService.generatePuzzle(w, h, i);
        while(LPSolver.solve(board) == null){
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
}
