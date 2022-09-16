package main.controllers;

import gurobi.GRBException;
import main.classifier.TensorflowClassifier;
import main.models.Board;
import main.services.FileService;
import main.services.PuzzleGeneratorService;
import main.services.interfaces.IFileService;
import main.solver.mathematical.LPSolver;
import main.solver.solvingTechniques.Levels;
import main.solver.solvingTechniques.STSolver;
import main.view.MenuView;
import main.classifier.RandomForestClassifier;

import java.io.File;
import java.util.List;


public class MenuController {

    private static MenuView menuView = new MenuView();
    private static IFileService fileService = new FileService();

    public static void openGame(File file) {
        Board board = fileService.readGame(file);
        closeMenuStage();
        BoardController.openGame(board);
    }

    public static void solveGameLP(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();
        Board board2 = LPSolver.solve(board);
        BoardController.openGame(board2);
    }

    public static void solveGameST(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();
        Board board2 = STSolver.solve(board);
        BoardController.openGame(board2);
    }

    public static void openSolution(File file) {
        Board board = fileService.readSolution(file);
        closeMenuStage();
        BoardController.openGame(board);
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
        BoardController.openGame(boardLP);
    }

    public static void predictFromFileTF(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();

        Levels level = predictTF(board);
        System.out.println("predicted level: " + level);
        System.out.println("original level: " + board.getLevel());
//        for (float d : features) {
//            System.out.print(d);
//            System.out.print(", ");
//        }
        BoardController.openGame(board);
    }
    private static Levels predictTF(Board board) throws GRBException {
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
    public static File generatePuzzle(int w, int h, int i) throws GRBException {
        Board board = PuzzleGeneratorService.generatePuzzle(w, h, i);
        Levels level = predictTF(board);
        board.setLevel(level);
        FileService fileService = new FileService();
        File file = fileService.saveNewBoard(board);
        System.out.println(board.getLevel().toString());
        return file;
    }

    public static void showMenuStage() {
        menuView.getMenuStage().show();
    }

    public static void closeMenuStage() {
        menuView.getMenuStage().close();
    }
}
