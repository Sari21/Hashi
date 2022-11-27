package main.solver.mathematical.converters;

import gurobi.*;
import main.models.Board;
import main.models.Coordinates;
import main.solver.mathematical.models.BridgePairs;
import main.solver.mathematical.models.LPModel;
import main.models.Bridge;

import java.util.ArrayList;

public class BoardAndSolverModelConverter {
    public static LPModel convertBoardToSolverModel(Board board) {
        LPModel model = new LPModel();

        // n -> szigetek száma
        model.setN(board.getIslands().size());
        board.sortIslands();
//        for (Island i : board.getIslands()) {
//            System.out.println("(" + i.getPosition().getX() + "," + i.getPosition().getY() + ")");
//        }

        //szomszédok
        model.setNeighbours(new int[model.getN()][model.getN()]);
        for (int i = 0; i <= model.getN(); i++) {
            for (int j = i + 1; j < model.getN(); j++) {
                if (i != j) {
                    if ((board.getIslands().get(i).getPosition().getX() == board.getIslands().get(j).getPosition().getX())
                            || (board.getIslands().get(i).getPosition().getY() == board.getIslands().get(j).getPosition().getY())) {
                        model.getNeighbours()[i][j] = 1;
                    } else
                        model.getNeighbours()[i][j] = 0;
                } else
                    model.getNeighbours()[i][j] = 0;
            }
        }

//        for (int i = 0; i < model.getN(); i++) {
////            System.out.println(board.getIslands().get(i).getValue() + " (" + board.getIslands().get(i).getPosition().getX() + "," + board.getIslands().get(i).getPosition().getY() + ")");
//        }

        //kiírás
        for (int i = 0; i < model.getN(); i++) {
            for (int j = 0; j < model.getN(); j++) {
//                    System.out.println(Y[i][j].get(GRB.StringAttr.VarName)
//                            + " " +Y[i][j].get(GRB.DoubleAttr.X));
//                System.out.print(" " + model.getNeighbours()[i][j]);
            }
//            System.out.println("");

        }

        //hidak száma szigetenként
        model.setD(new int[model.getN()]);
        for (int i = 1; i < model.getN(); i++) {
            model.getD()[i] = board.getIslands().get(i).getValue();
        }

        //metsző élek
//        System.out.println(model.getN());
        for (int i = 0; i < model.getN(); i++) {
            for (int j = i + 1; j < model.getN(); j++) {
                for (int k = 0; k < model.getN(); k++) {
                    for (int l = k + 1; l < model.getN(); l++) {
                        if (model.getNeighbours()[i][j] == 1 && model.getNeighbours()[k][l] == 1) {
                            if (areBridgesIntersect(board.getIslands().get(i).getPosition(), board.getIslands().get(j).getPosition(),
                                    board.getIslands().get(k).getPosition(), board.getIslands().get(l).getPosition())) {
                                model.getIntersectingBridges().add(new BridgePairs(i, j, k, l));
                            }
                        }
                    }
                }
            }
        }
        return model;
    }

    public static ArrayList<Bridge> convertSolvedModelToBridges(LPModel solverModel, GRBVar[][] X, Board board) throws GRBException {
        Board solutionBoard = new Board(board.getWidth(), board.getHeight());
        solutionBoard.setIslands(board.getIslands());

        for (int i = 0; i < solverModel.getN(); i++) {
            for (int j = 0; j < solverModel.getN(); j++) {
                if (X[i][j].get(GRB.DoubleAttr.X) == 1.0) {
                    Bridge bridge = new Bridge(solutionBoard.getIslands().get(i), solutionBoard.getIslands().get(j));
                    solutionBoard.addBridge(bridge);
                } else if (X[i][j].get(GRB.DoubleAttr.X) == 2.0) {
                    Bridge bridge = new Bridge(solutionBoard.getIslands().get(i), solutionBoard.getIslands().get(j));
                    bridge.setDouble(true);
                    solutionBoard.addBridge(bridge);
                }
            }
        }
        return solutionBoard.getSolutionBridges();
    }

    private static boolean areBridgesIntersect(Coordinates A, Coordinates B, Coordinates C, Coordinates D) {
        if (A.getX() == B.getX()) {
            if (C.getX() == D.getX() && A.getX() == C.getX()) {
                if ((A.getY() < C.getY() && C.getY() < B.getY()) || (A.getY() < D.getY() && D.getY() < B.getY()))
                    return true;
            } else if (C.getY() == D.getY()) {
                if (C.getX() < A.getX() && A.getX() < D.getX() && A.getY() < C.getY() && C.getY() < B.getY())
                    return true;
            }
        } else if (A.getY() == B.getY()) {
            if (C.getX() == D.getX()) {
                if (C.getY() < A.getY() && A.getY() < D.getY() && A.getX() < C.getX() && C.getX() < B.getX())
                    return true;
            } else if (C.getY() == D.getY() && C.getY() == A.getY()) {
                if ((A.getX() < C.getX() && C.getX() < B.getX()) || (A.getX() < D.getX() && D.getX() < B.getX()))
                    return true;
            }
        }
        return false;
    }
}
