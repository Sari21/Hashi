package solver.converters;

import gurobi.GRB;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;
import models.Board;
import models.Bridge;
import solver.SolverModel;

public class BoardToSolverModelConverter {
    public SolverModel boardToSolverModel(Board board){
        SolverModel model = new SolverModel();

        // n -> szigetek száma
        model.setN(board.getIslands().size());

        model.setG(new int[model.getN()][model.getN()]);
        for (int i = 0; i < model.getN(); i++) {
            for (int j = i + 1; j < model.getN(); j++) {
                if (i != j) {
                    if (board.getIslands().get(i).getPosition().getX() == board.getIslands().get(j).getPosition().getX()
                            || board.getIslands().get(i).getPosition().getY() == board.getIslands().get(j).getPosition().getY()) {
                        model.getG()[i][j] = 1;
                    }
                    else
                        model.getG()[i][j] = 0;
                }
                else
                    model.getG()[i][j] = 0;
            }
        }
        for (int i = 0; i < model.getN(); i++) {
            for (int j = 0; j < model.getN(); j++) {
//                    System.out.println(Y[i][j].get(GRB.StringAttr.VarName)
//                            + " " +Y[i][j].get(GRB.DoubleAttr.X));
                System.out.print(" " + model.getNeighbours()[i][j]);
            }
            System.out.println("");
        }

        //hidak száma szigetenként
        model.setD(new int[model.getN()]);
        for(int i = 1; i < model.getN(); i++){
           model.getD()[i] =  board.getIslands().get(i).getValue();
        }




        return model;


    }
}
