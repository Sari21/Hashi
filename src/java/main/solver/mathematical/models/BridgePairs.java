package main.solver.mathematical.models;

public class BridgePairs {
    //szigetek indexe
    private int startIdx1;
    private int endIdx1;
    private int startIdx2;
    private int endIdx2;

    public BridgePairs(int startIdx1, int endIdx1, int startIdx2, int endIdx2) {
        this.startIdx1 = startIdx1;
        this.endIdx1 = endIdx1;
        this.startIdx2 = startIdx2;
        this.endIdx2 = endIdx2;
    }

    public int getStartIdx1() {
        return startIdx1;
    }

    public void setStartIdx1(int startIdx1) {
        this.startIdx1 = startIdx1;
    }

    public int getEndIdx1() {
        return endIdx1;
    }

    public void setEndIdx1(int endIdx1) {
        this.endIdx1 = endIdx1;
    }

    public int getStartIdx2() {
        return startIdx2;
    }

    public void setStartIdx2(int startIdx2) {
        this.startIdx2 = startIdx2;
    }

    public int getEndIdx2() {
        return endIdx2;
    }

    public void setEndIdx2(int endIdx2) {
        this.endIdx2 = endIdx2;
    }
}
