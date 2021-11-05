package solver;

public class SolverModel {
    private int [][] neighbours;
    private int [][] G;
    private int [] d;
    private int n;

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
