package controllers;

import gurobi.GRBException;
import models.Board;
import services.FileService;
import services.interfaces.IFileService;
import solver.mathematical.LPSolver;
import solver.solvingTechniques.STSolver;
import view.MenuView;
import java.io.File;
import java.util.List;
import classif.RandomForestClassifier;

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

    public static void solveGameWithSolvingTechniques(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();
        Board board2 = STSolver.solve(board);
        BoardController.openGame(board2);
        System.out.println("done");
    }

    public static void openSolution(File file) {
        Board board = fileService.readSolution(file);
        closeMenuStage();
        BoardController.openGame(board);
    }

    public static void solveMultipleGameWithSolvingTechniques(List<File> files) {
        closeMenuStage();
        for (File file : files) {
            Board board = fileService.readGame(file);
            STSolver.solve(board);
        }
        showMenuStage();
        System.out.println("done");
    }

    public static void solveMultipleGameLP(List<File> files) throws GRBException {
        closeMenuStage();
        for (File file : files) {
            Board board = fileService.readGame(file);
            LPSolver.solve(board);
        }
        showMenuStage();
    }

    public static void predict(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();
        Board boardLP = LPSolver.solve(board);
        BoardController.openGame(boardLP);

        // Features:
        double[] features = {0.0, 0.0, 0.012002944946289062, 2096477230, 3362, 0.0, 1, 2.0, 1.0, 0.0, 0.0, 40.0, 1.0, 121, 19, 1, 9, 6, 0, 0, 8, 2, 3, 0, 0, 0, 0, 0, 0, 0, 1.0, 1.3902439024390243, 2.0, 6};
//                double[] features = new double[args.length];
//                for (int i = 0, l = args.length; i < l; i++) {
//                    features[i] = Double.parseDouble(args[i]);
//                }

        // Prediction:


        int prediction = RandomForestClassifier.predict(features);
        System.out.println(prediction);


    }


    public static void showMenuStage() {
        menuView.getMenuStage().show();
    }

    public static void closeMenuStage() {
        menuView.getMenuStage().close();
    }
}
