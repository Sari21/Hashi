package solver;

import gurobi.*;
import models.Board;
import models.Bridge;

public class Solver {
    public static Board solve(Board board) {
        try {

            //TODO index / id?

            // Create empty environment, set options, and start
            GRBEnv env = new GRBEnv(true);
            env.set("logFile", "mip1.log");
            env.start();

            // Create empty model
            GRBModel model = new GRBModel(env);
            // n -> szigetek száma
            int n = board.getIslands().size();
//
//            GRBVar[] islands = new GRBVar[n];
//            for (int i = 0; i < n; i++) {
//                    String st = "I_" + String.valueOf(i);
//                islands[i] = model.addVar(0.0, 2.0, , GRB.INTEGER, st);
//                    System.out.println(islands[i]);
//                }
            // Set<Pair> neighbours = new HashSet<>();
            // lehetséges szomszédok
            board.sortIslands();
            int[][] neighbours = new int[n][n];
            for (int j = 0; j < n; j++) {
                for (int i = 0; i <= j; i++) {
                    if (i != j) {
                        if ((board.getIslands().get(i).getPosition().getX() == board.getIslands().get(j).getPosition().getX() &&
                                board.getIslands().get(j).getPosition().getY() - board.getIslands().get(i).getPosition().getY() > 1)
                                || (board.getIslands().get(i).getPosition().getY() == board.getIslands().get(j).getPosition().getY())
                                && board.getIslands().get(j).getPosition().getX() - board.getIslands().get(i).getPosition().getX() > 1) {
                            neighbours[i][j] = 1;
                        } else
                            neighbours[i][j] = 0;
                    } else
                        neighbours[i][j] = 0;
                }
            }


            for (int i = 0; i < n; i++) {
                System.out.println(board.getIslands().get(i).getValue() + " (" + board.getIslands().get(i).getPosition().getX() + "," + board.getIslands().get(i).getPosition().getY() + ")");
            }

            //kiírás
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
//                    System.out.println(Y[i][j].get(GRB.StringAttr.VarName)
//                            + " " +Y[i][j].get(GRB.DoubleAttr.X));
                    System.out.print(" " + neighbours[i][j]);
                }
                System.out.println("");

            }

            //xij integer - hidak száma i és j között
            GRBVar[][] X = new GRBVar[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    String st = "X_" + String.valueOf(i) + "_" + String.valueOf(j);
                    X[i][j] = model.addVar(0.0, 2.0, 0.0, GRB.INTEGER, st);
                }
            }
//            yij bináris változó  - i és j össze van-e kötve
            GRBVar[][] Y = new GRBVar[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    String st = "Y_" + String.valueOf(i) + "_" + String.valueOf(j);
                    Y[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, st);
                }
            }


            // hidak száma szigetenként (1)
            GRBLinExpr expr;
            // nem használt fele a mátrixnak 0
            for (int i = 0; i < n; i++) {
                expr = new GRBLinExpr();
                for (int j = i + 1; j < n; j++) {
                    expr.addTerm(1.0, X[i][j]);
                }

                model.addConstr(expr, GRB.EQUAL, 0.0, "c0");
            }
            // hidak száma
            for (int i = 0; i < n; i++) {
                expr = new GRBLinExpr();
                for (int j = 0; j <= i; j++) {
//                    if (neighbours.contains(new Pair(i, j)))
//                    if(neighbours[i][j] == 1)
                    expr.addTerm(1.0, X[i][j]);
                }
                for (int j = i; j < n; j++) {
//                    if (neighbours.contains(new Pair(i, j)))
//                    if(neighbours[j][i] == 1)
                    expr.addTerm(1.0, X[j][i]);
                }
                //TODO ide jön a d
                model.addConstr(expr, GRB.EQUAL, board.getIslands().get(i).getValue(), "c0");
            }


            //nincsenek hurokélek
            for (int i = 0; i < n; i++) {
                expr = new GRBLinExpr();
                expr.addTerm(1.0, X[i][i]);
                model.addConstr(expr, GRB.EQUAL, 0.0, "c0");
            }

            // TODO (2)
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    GRBLinExpr exprY = new GRBLinExpr();
                    GRBLinExpr exprX = new GRBLinExpr();
                    exprX.addTerm(1.0, X[i][j]);
                    exprY.addTerm(1.0, Y[i][j]);
                    model.addConstr(exprY, GRB.LESS_EQUAL, exprX, "c0");
                    exprY = new GRBLinExpr();
                    exprY.addTerm(2.0, Y[i][j]);
                    model.addConstr(exprY, GRB.LESS_EQUAL, exprX, "c0");

                }

            }

            model.optimize();

//            hidak hozzáadása a táblához
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
//                    System.out.print(X[i][j].get(GRB.StringAttr.VarName)
//                            + " " +X[i][j].get(GRB.DoubleAttr.X));
//                    System.out.print(" " + X[i][j].get(GRB.DoubleAttr.X));

                    if (X[i][j].get(GRB.DoubleAttr.X) == 1.0) {
                        Bridge bridge = new Bridge(board.getIslands().get(i), board.getIslands().get(j));
                        board.addBridge(bridge);
                    } else if (X[i][j].get(GRB.DoubleAttr.X) == 2.0) {
                        Bridge bridge = new Bridge(board.getIslands().get(i), board.getIslands().get(j));
                        bridge.setDouble(true);
                        board.addBridge(bridge);
                    }
                }
            }


            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
//                    System.out.println(Y[i][j].get(GRB.StringAttr.VarName)
//                            + " " +Y[i][j].get(GRB.DoubleAttr.X));
                    System.out.print(" " + Y[i][j].get(GRB.DoubleAttr.X));
                }
                System.out.println("");

            }
            model.dispose();
            env.dispose();

        } catch (GRBException ex) {
            ex.printStackTrace();
        }

        return board;
    }

}
