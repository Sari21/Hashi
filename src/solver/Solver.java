package solver;

import gurobi.*;
import models.Board;
import solver.converters.BoardAndSolverModelConverter;
import solver.models.BridgePairs;
import solver.models.SolverModel;

public class Solver {
    public static Board solve(Board board) throws GRBException {
        try {

            //TODO index / id?

            // Create empty environment, set options, and start
            GRBEnv env = new GRBEnv(true);
            env.set("logFile", "mip1.log");
            env.start();

            // Create empty model
            GRBModel model = new GRBModel(env);

            SolverModel solverModel = BoardAndSolverModelConverter.convertBoardToSolverModel(board);


            //xij integer - hidak száma i és j között
            GRBVar[][] X = new GRBVar[solverModel.getN()][solverModel.getN()];

            for (int i = 0; i < solverModel.getN(); i++) {
                for (int j = 0; j < solverModel.getN(); j++) {
                    String st = "X_" + String.valueOf(i) + "_" + String.valueOf(j);
                    X[i][j] = model.addVar(0.0, 2.0, 0.0, GRB.INTEGER, st);
                }
            }
//            yij bináris változó  - i és j össze van-e kötve
            GRBVar[][] Y = new GRBVar[solverModel.getN()][solverModel.getN()];
//
            for (int i = 0; i < solverModel.getN(); i++) {
                for (int j = 0; j < solverModel.getN(); j++) {
                    String st = "Y_" + String.valueOf(i) + "_" + String.valueOf(j);
                    Y[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, st);
                }
            }


            // hidak száma szigetenként (1)
            GRBLinExpr expr;
            // nem használt fele a mátrixnak 0
            for (int i = 0; i < solverModel.getN(); i++) {
                expr = new GRBLinExpr();
                for (int j = 0; j < i; j++) {
                    expr.addTerm(1.0, X[i][j]);
                }

                model.addConstr(expr, GRB.EQUAL, 0.0, "c0");
            }
            // hidak száma
//            for (int i = 0; i < solverModel.getN(); i++) {
//                expr = new GRBLinExpr();
//                for (int j = 0; j <= i; j++) {
////                    if (neighbours.contains(new Pair(i, j)))
////                    if(neighbours[i][j] == 1)
//                    expr.addTerm(1.0, X[i][j]);
//                }
//                for (int j = i; j < solverModel.getN(); j++) {
////                    if (neighbours.contains(new Pair(i, j)))
////                    if(neighbours[j][i] == 1)
//                    expr.addTerm(1.0, X[j][i]);
//                }
            //TODO ide jön a d

//          todo ez jó
            for (int k = 0; k < solverModel.getN(); k++) {
                expr = new GRBLinExpr();

                for (int i = 0; i < k; i++) {
                    expr.addTerm(1.0, X[i][k]);
                }
                for (int j = k; j < solverModel.getN(); j++) {
                    expr.addTerm(1.0, X[k][j]);
                }
                model.addConstr(expr, GRB.EQUAL, board.getIslands().get(k).getValue(), "c0");

            }


            //nincsenek hurokélek todo pipa
            for (int i = 0; i < solverModel.getN(); i++) {
                expr = new GRBLinExpr();
                expr.addTerm(1.0, X[i][i]);
                model.addConstr(expr, GRB.EQUAL, 0.0, "c0");
            }

            // (2) todo pipa
            for (int i = 0; i < solverModel.getN(); i++) {
                for (int j = i + 1; j < solverModel.getN(); j++) {
                    GRBLinExpr exprY = new GRBLinExpr();
                    GRBLinExpr expr2Y = new GRBLinExpr();
                    GRBLinExpr exprX = new GRBLinExpr();
                    exprX.addTerm(1.0, X[i][j]);
                    exprY.addTerm(1.0, Y[i][j]);
                    model.addConstr(exprY, GRB.LESS_EQUAL, exprX, "c0");
                    expr2Y.addTerm(2.0, Y[i][j]);
                    model.addConstr(exprX, GRB.LESS_EQUAL, expr2Y, "c0");
                }
            }

//            // (3) keresztező élek
            for (int i = 0; i < solverModel.getIntersectingBridges().size(); i++) {
                int startIdx1 = solverModel.getIntersectingBridges().get(i).getStartIdx1();
                int startIdx2 = solverModel.getIntersectingBridges().get(i).getStartIdx2();
                int endIdx1 = solverModel.getIntersectingBridges().get(i).getEndIdx1();
                int endIdx2 = solverModel.getIntersectingBridges().get(i).getEndIdx2();
                GRBLinExpr expr1 = new GRBLinExpr();
                expr1.addTerm(1.0, Y[startIdx1][endIdx1]);
                expr1.addTerm(1.0, Y[startIdx2][endIdx2]);
//                    GRBVar var = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "x");
                model.addConstr(expr1, GRB.LESS_EQUAL, 1.0, "int");
                System.out.println(startIdx1 + " " + endIdx1 + " " + startIdx2 + " " + endIdx2);
            }
            //szomszédok todo pipa
            for (int i = 0; i < solverModel.getN(); i++) {
                for (int j = i; j < solverModel.getN(); j++) {
                    model.addConstr(Y[i][j], GRB.LESS_EQUAL, solverModel.getNeighbours()[i][j], "int");
                }

            }

            model.optimize();

//            hidak hozzáadása a táblához

            board = BoardAndSolverModelConverter.convertSolvedGameToBoard(solverModel, X, board);

//            for (int i = 0; i < solverModel.getN(); i++) {
//                for (int j = 0; j < solverModel.getN(); j++) {
////                    System.out.println(Y[i][j].get(GRB.StringAttr.VarName)
////                            + " " +Y[i][j].get(GRB.DoubleAttr.X));
//                    System.out.print(" " + Y[i][j].get(GRB.DoubleAttr.X));
//                }
//                System.out.println("");
//
//            }

            for (int i = 0; i < solverModel.getN(); i++) {
                for (int j = 0; j < solverModel.getN(); j++) {
//                    System.out.println(Y[i][j].get(GRB.StringAttr.VarName)
//                            + " " +Y[i][j].get(GRB.DoubleAttr.X));
                    System.out.print(" " + X[i][j].get(GRB.DoubleAttr.X));
                }
                System.out.println("");

            }
            model.dispose();
            env.dispose();

        } catch (
                GRBException ex) {
            ex.printStackTrace();
        }

        return board;
    }

}
