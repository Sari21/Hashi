package solver.mathematical;

import gurobi.*;
import models.Board;
import services.FileService;
import solver.mathematical.converters.BoardAndSolverModelConverter;
import solver.mathematical.models.LPModel;

import static interfaces.CsvPrintable.CSV_SEPARATOR;

public class LPSolver {
    private static double[] features;

    public static Board solve(Board board) throws GRBException {
        try {
            // Create empty environment, set options, and start
            GRBEnv env = new GRBEnv(true);
            //  env.set("logFile", "mip1.log");
            env.start();

            // Create empty model
            GRBModel model = new GRBModel(env);
            LPModel LPModel = BoardAndSolverModelConverter.convertBoardToSolverModel(board);
            //xij integer - hidak száma i és j között
            GRBVar[][] X = new GRBVar[LPModel.getN()][LPModel.getN()];

            for (int i = 0; i < LPModel.getN(); i++) {
                for (int j = 0; j < LPModel.getN(); j++) {
                    String st = "X_" + String.valueOf(i) + "_" + String.valueOf(j);
                    X[i][j] = model.addVar(0.0, 2.0, 0.0, GRB.INTEGER, st);
                }
            }
//            yij bináris változó  - i és j össze van-e kötve
            GRBVar[][] Y = new GRBVar[LPModel.getN()][LPModel.getN()];
//
            for (int i = 0; i < LPModel.getN(); i++) {
                for (int j = 0; j < LPModel.getN(); j++) {
                    String st = "Y_" + String.valueOf(i) + "_" + String.valueOf(j);
                    Y[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, st);
                }
            }

            // hidak száma szigetenként (1)
            GRBLinExpr expr;
            // nem használt fele a mátrixnak 0
            for (int i = 0; i < LPModel.getN(); i++) {
                expr = new GRBLinExpr();
                for (int j = 0; j < i; j++) {
                    expr.addTerm(1.0, X[i][j]);
                }

                model.addConstr(expr, GRB.EQUAL, 0.0, "c0");
            }

            for (int k = 0; k < LPModel.getN(); k++) {
                expr = new GRBLinExpr();

                for (int i = 0; i < k; i++) {
                    expr.addTerm(1.0, X[i][k]);
                }
                for (int j = k; j < LPModel.getN(); j++) {
                    expr.addTerm(1.0, X[k][j]);
                }
                model.addConstr(expr, GRB.EQUAL, board.getIslands().get(k).getValue(), "c0");

            }

            //nincsenek hurokélek todo pipa
            for (int i = 0; i < LPModel.getN(); i++) {
                expr = new GRBLinExpr();
                expr.addTerm(1.0, X[i][i]);
                model.addConstr(expr, GRB.EQUAL, 0.0, "c0");
            }

            // (2) todo pipa
            for (int i = 0; i < LPModel.getN(); i++) {
                for (int j = i + 1; j < LPModel.getN(); j++) {
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
            for (int i = 0; i < LPModel.getIntersectingBridges().size(); i++) {
                int startIdx1 = LPModel.getIntersectingBridges().get(i).getStartIdx1();
                int startIdx2 = LPModel.getIntersectingBridges().get(i).getStartIdx2();
                int endIdx1 = LPModel.getIntersectingBridges().get(i).getEndIdx1();
                int endIdx2 = LPModel.getIntersectingBridges().get(i).getEndIdx2();
                GRBLinExpr expr1 = new GRBLinExpr();
                expr1.addTerm(1.0, Y[startIdx1][endIdx1]);
                expr1.addTerm(1.0, Y[startIdx2][endIdx2]);
//                    GRBVar var = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "x");
                model.addConstr(expr1, GRB.LESS_EQUAL, 1.0, "int");
//                System.out.println(startIdx1 + " " + endIdx1 + " " + startIdx2 + " " + endIdx2);
            }
            //szomszédok todo pipa
            for (int i = 0; i < LPModel.getN(); i++) {
                for (int j = i; j < LPModel.getN(); j++) {
                    model.addConstr(Y[i][j], GRB.LESS_EQUAL, LPModel.getNeighbours()[i][j], "int");
                }
            }
            GRBLinExpr sumY = new GRBLinExpr();
            //feszítőfa - összefüggő gráf
            for (int i = 0; i < LPModel.getN(); i++) {
                for (int j = i; j < LPModel.getN(); j++) {
                    sumY.addTerm(1.0, Y[i][j]);
                }
            }
            model.addConstr(sumY, GRB.GREATER_EQUAL, LPModel.getN() - 1, "spanning tree");
            model.optimize();

            features = new double[13];

            features[0] = model.get(GRB.DoubleAttr.IterCount);
            features[1] = model.get(GRB.IntAttr.BarIterCount);
            features[2] = model.get(GRB.DoubleAttr.Runtime);
            features[3] = model.get(GRB.IntAttr.Fingerprint);
            features[4] = model.get(GRB.IntAttr.NumVars);
            features[5] = model.get(GRB.DoubleAttr.NodeCount);
            features[6] = model.get(GRB.IntAttr.SolCount);
            features[7] = model.get(GRB.DoubleAttr.MaxBound);
            features[8] = model.get(GRB.DoubleAttr.MinBound);
            features[9] = model.get(GRB.DoubleAttr.MaxObjCoeff);
            features[10] = model.get(GRB.DoubleAttr.MinObjCoeff);
            features[11] = model.get(GRB.DoubleAttr.MaxRHS);
            features[12] = model.get(GRB.DoubleAttr.MinRHS);
            FileService fileService = new FileService();
            StringBuilder results = new StringBuilder();

//            hidak hozzáadása a táblához
            board = BoardAndSolverModelConverter.convertSolvedGameToBoard(LPModel, X, board);
//            fileService.writeDifficulty("Difficulty_lp.csv", results.toString());
            model.dispose();
            env.dispose();
        } catch (
                GRBException ex) {
            ex.printStackTrace();
        }
        return board;
    }
    public static double[] getFeatures(){
        return features;
    }

}
