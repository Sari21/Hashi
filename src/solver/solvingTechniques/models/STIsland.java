package solver.solvingTechniques.models;

import models.Bridge;
import models.Island;

public class STIsland extends Island {
    private STBridge upBridges, downBridges, rightBridges, leftBridges;
    private STIsland upNeighbour, downNeighbour, rightNeighbour, leftNeighbour;
    private boolean hasUpNeighbour = true, hasDownNeighbour = true, hasRightNeighbour = true, hasLeftNeighbour = true;
    private boolean isFinished = false;
    private int remainingValue;


    public int getNumberOfNeighbours() {
        int n = 0;
        if (upNeighbour != null)
            n++;
        if (downNeighbour != null)
            n++;
        if (rightNeighbour != null)
            n++;
        if (leftNeighbour != null)
            n++;
        return n;
    }

    public int getNumberOfUnfinishedNeighbours() {
        int n = 0;
        if (upNeighbour != null && !upNeighbour.isFinished)
            n++;
        if (downNeighbour != null && !downNeighbour.isFinished)
            n++;
        if (rightNeighbour != null && !rightNeighbour.isFinished)
            n++;
        if (leftNeighbour != null && !leftNeighbour.isFinished)
            n++;
        return n;
    }

    public int getRemainingValueOfUnfinishedNeighbours() {
        int n = 0;
        if (upNeighbour != null && !upNeighbour.isFinished) {
            if (upNeighbour.remainingValue >= 2)
                n++;
            n++;
        }
        if (downNeighbour != null && !downNeighbour.isFinished) {
            if (downNeighbour.remainingValue >= 2)
                n++;
            n++;
        }
        if (rightNeighbour != null && !rightNeighbour.isFinished) {
            if (rightNeighbour.remainingValue >= 2) {
                n++;
            }
            n++;
        }
        if (leftNeighbour != null && !leftNeighbour.isFinished) {
            if (leftNeighbour.remainingValue >= 2)
                n++;
            n++;
        }
        return n;
    }


    public int getNumberOfBridges() {
        int n = 0;
        if (upBridges != null) {
            if (upBridges.isDouble()) {
                n++;
            }
            n++;
        }
        if (downBridges != null) {
            if (downBridges.isDouble()) {
                n++;
            }
            n++;
        }
        if (rightBridges != null) {
            if (rightBridges.isDouble()) {
                n++;
            }
            n++;
        }
        if (leftBridges != null) {
            if (leftBridges.isDouble()) {
                n++;
            }
            n++;
        }
        return n;
    }

//    public boolean checkNumberOfBridges() {
//        this.isFinished = this.getNumberOfBridges() == this.getValue();
//        return this.isFinished;
//    }

    public int numberOfNeighboursWithValueOne() {
        int n = 0;
        if (upNeighbour != null && upNeighbour.getValue() == 1)
            n++;
        if (downNeighbour != null && downNeighbour.getValue() == 1)
            n++;
        if (rightNeighbour != null && rightNeighbour.getValue() == 1)
            n++;
        if (leftNeighbour != null && leftNeighbour.getValue() == 1)
            n++;
        return n;
    }
    public int numberOfNeighboursWithValueTwo() {
        int n = 0;
        if (upNeighbour != null && upNeighbour.getValue() == 2)
            n++;
        if (downNeighbour != null && downNeighbour.getValue() == 2)
            n++;
        if (rightNeighbour != null && rightNeighbour.getValue() == 2)
            n++;
        if (leftNeighbour != null && leftNeighbour.getValue() == 2)
            n++;
        return n;
    }

    public int numberOfNeighboursWithRemainingValueOne() {
        int n = 0;
        if (upNeighbour != null && upNeighbour.getRemainingValue() == 1)
            n++;
        if (downNeighbour != null && downNeighbour.getRemainingValue() == 1)
            n++;
        if (rightNeighbour != null && rightNeighbour.getRemainingValue() == 1)
            n++;
        if (leftNeighbour != null && leftNeighbour.getRemainingValue() == 1)
            n++;
        return n;
    }

    public int numberOfUnfinishedNeighbours() {
        int n = 0;
        if (upNeighbour != null && !upNeighbour.isFinished)
            n++;
        if (downNeighbour != null && !downNeighbour.isFinished)
            n++;
        if (rightNeighbour != null && !rightNeighbour.isFinished)
            n++;
        if (leftNeighbour != null && !leftNeighbour.isFinished)
            n++;
        return n;
    }

    public int checkRemainingValue() {
        this.remainingValue = this.getValue() - getNumberOfBridges();
        return remainingValue;
    }

    public Bridge getUpBridges() {
        return upBridges;
    }

    public void setUpBridges(STBridge upBridges) {
        this.upBridges = upBridges;
    }

    public STBridge getDownBridges() {
        return downBridges;
    }

    public void setDownBridges(STBridge downBridges) {
        this.downBridges = downBridges;
    }

    public STBridge getRightBridges() {
        return rightBridges;
    }

    public void setRightBridges(STBridge rightBridges) {
        this.rightBridges = rightBridges;
    }

    public STBridge getLeftBridges() {
        return leftBridges;
    }

    public void setLeftBridges(STBridge leftBridges) {
        this.leftBridges = leftBridges;
    }

    public STIsland getDownNeighbour() {
        return downNeighbour;
    }

    public void setDownNeighbour(STIsland downNeighbour) {
        this.downNeighbour = downNeighbour;
    }

    public STIsland getRightNeighbour() {
        return rightNeighbour;
    }

    public void setRightNeighbour(STIsland rightNeighbour) {
        this.rightNeighbour = rightNeighbour;
    }

    public STIsland getLeftNeighbour() {
        return leftNeighbour;
    }

    public void setLeftNeighbour(STIsland leftNeighbour) {
        this.leftNeighbour = leftNeighbour;
    }

    public STIsland getUpNeighbour() {
        return upNeighbour;
    }

    public void setUpNeighbour(STIsland upNeighbour) {
        this.upNeighbour = upNeighbour;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isHasUpNeighbour() {
        return hasUpNeighbour;
    }

    public void setHasUpNeighbour(boolean hasUpNeighbour) {
        this.hasUpNeighbour = hasUpNeighbour;
    }

    public boolean isHasDownNeighbour() {
        return hasDownNeighbour;
    }

    public void setHasDownNeighbour(boolean hasDownNeighbour) {
        this.hasDownNeighbour = hasDownNeighbour;
    }

    public boolean isHasRightNeighbour() {
        return hasRightNeighbour;
    }

    public void setHasRightNeighbour(boolean hasRightNeighbour) {
        this.hasRightNeighbour = hasRightNeighbour;
    }

    public boolean isHasLeftNeighbour() {
        return hasLeftNeighbour;
    }

    public void setHasLeftNeighbour(boolean hasLeftNeighbour) {
        this.hasLeftNeighbour = hasLeftNeighbour;
    }

    public int getRemainingValue() {
        return remainingValue;
    }

    public void setRemainingValue(int remainingValue) {
        this.remainingValue = remainingValue;
    }
}
