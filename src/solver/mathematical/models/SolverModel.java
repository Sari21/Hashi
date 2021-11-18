package solver.mathematical.models;

import java.util.ArrayList;

public class SolverModel {
    private int [][] neighbours;
    private int [][] G;
    private int [] d;
    private int n;
    private ArrayList<BridgePairs> intersectingBridges = new ArrayList<>();

    public ArrayList<BridgePairs> getIntersectingBridges() {
        return intersectingBridges;
    }

    public void setIntersectingBridges(ArrayList<BridgePairs> intersectingBridges) {
        this.intersectingBridges = intersectingBridges;
    }

    public int[][] getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(int[][] neighbours) {
        this.neighbours = neighbours;
    }

    public int[][] getG() {
        return G;
    }

    public void setG(int[][] g) {
        G = g;
    }

    public int[] getD() {
        return d;
    }

    public void setD(int[] d) {
        this.d = d;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
