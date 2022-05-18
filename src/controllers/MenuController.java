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

import classifier.RandomForestClassifier;

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
        System.out.println("done");
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
            double features[] = STSolver.getFeatures();
            fileService.writeDifficulty("Difficulty_ST.csv", file.getName(), features, board.getLevel());

        }

        showMenuStage();
    }

    public static void solveMultipleGamesLP(List<File> files) throws GRBException {
        closeMenuStage();
        for (File file : files) {
            Board board = fileService.readGame(file);
            double features[] = LPSolver.getFeatures();
            fileService.writeDifficulty("Difficulty_LP.csv", file.getName(), features, board.getLevel());
        }
        showMenuStage();
    }

    public static void predict(File file) throws GRBException {
        Board board = fileService.readGame(file);
        closeMenuStage();

        double[] featuresLP;
        double[] featuresST;
        Board boardLP = LPSolver.solve(board);
        featuresLP = LPSolver.getFeatures();
        STSolver.solve(board);
        featuresST = STSolver.getFeatures();
        double[] features = new double[featuresLP.length + featuresST.length];
        System.arraycopy(featuresLP, 0, features, 0, featuresLP.length);
        System.arraycopy(featuresST, 0, features,  featuresLP.length, featuresST.length);

        int prediction = RandomForestClassifier.predict(features);
        System.out.println(prediction);
        System.out.println(board.getLevel());
        for(double d :features){
            System.out.print(d);
            System.out.print(", ");
        }
        BoardController.openGame(boardLP);
    }


    public static void showMenuStage() {
        menuView.getMenuStage().show();
    }

    public static void closeMenuStage() {
        menuView.getMenuStage().close();
    }
}
