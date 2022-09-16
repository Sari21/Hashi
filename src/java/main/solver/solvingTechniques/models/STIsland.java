package main.solver.solvingTechniques.models;

import main.models.Bridge;
import main.models.Island;

import java.util.ArrayList;
import java.util.List;

public class STIsland extends Island {
    private STBridge upBridges, downBridges, rightBridges, leftBridges;
    private STIsland upNeighbour, downNeighbour, rightNeighbour, leftNeighbour;
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

    public int getNumberOfRemainingBridgesOfUnfinishedNeighbours() {
        int remVal = 0;
        remVal += getRemainingBridges(Direction.DOWN);
        remVal += getRemainingBridges(Direction.UP);
        remVal += getRemainingBridges(Direction.LEFT);
        remVal += getRemainingBridges(Direction.RIGHT);

        return remVal;
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

    public int getNumberOfNeighboursWithBridges() {
        int n = 0;
        if (upBridges != null) {
            n++;
        }
        if (downBridges != null) {
            n++;
        }
        if (rightBridges != null) {
            n++;
        }
        if (leftBridges != null) {
            n++;
        }
        return n;
    }

    public int numberOfUnfinishedNeighboursWithValueOne() {
        int n = 0;
        if (upNeighbour != null && !upNeighbour.isFinished() && upNeighbour.getValue() == 1)
            n++;
        if (downNeighbour != null && !downNeighbour.isFinished() && downNeighbour.getValue() == 1)
            n++;
        if (rightNeighbour != null && !rightNeighbour.isFinished() && rightNeighbour.getValue() == 1)
            n++;
        if (leftNeighbour != null && !leftNeighbour.isFinished() && leftNeighbour.getValue() == 1)
            n++;
        return n;
    }

    public int numberOfUnfinishedNeighboursWithValue(int value) {
        int n = 0;
        if (upNeighbour != null && !upNeighbour.isFinished() && upNeighbour.getValue() == value)
            n++;
        if (downNeighbour != null && !downNeighbour.isFinished() && downNeighbour.getValue() == value)
            n++;
        if (rightNeighbour != null && !rightNeighbour.isFinished() && rightNeighbour.getValue() == value)
            n++;
        if (leftNeighbour != null && !leftNeighbour.isFinished() && leftNeighbour.getValue() == value)
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

    public int numberOfNeighboursWithValue(int value) {
        int n = 0;
        if (upNeighbour != null && upNeighbour.getValue() == value)
            n++;
        if (downNeighbour != null && downNeighbour.getValue() == value)
            n++;
        if (rightNeighbour != null && rightNeighbour.getValue() == value)
            n++;
        if (leftNeighbour != null && leftNeighbour.getValue() == value)
            n++;
        return n;
    }

    public List<STIsland> getNeighboursWithValue(int value) {
        ArrayList<STIsland> islands = new ArrayList<>();
        if (upNeighbour != null && upNeighbour.getValue() == value)
            islands.add(upNeighbour);
        if (downNeighbour != null && downNeighbour.getValue() == value)
            islands.add(downNeighbour);
        if (rightNeighbour != null && rightNeighbour.getValue() == value)
            islands.add(rightNeighbour);
        if (leftNeighbour != null && leftNeighbour.getValue() == value)
            islands.add(leftNeighbour);
        return islands;
    }

    public int getNumberOfNeighboursWithoutBridges() {
        int n = 0;
        if (upNeighbour != null && upNeighbour.getNumberOfBridges() == 0)
            n++;
        if (downNeighbour != null && downNeighbour.getNumberOfBridges() == 0)
            n++;
        if (rightNeighbour != null && rightNeighbour.getNumberOfBridges() == 0)
            n++;
        if (leftNeighbour != null && leftNeighbour.getNumberOfBridges() == 0)
            n++;
        return n;
    }

    public ArrayList<STIsland> getNeighboursWithoutBridges() {
        ArrayList<STIsland> neighboursWithoutBridges = new ArrayList<>();
        if (upNeighbour != null && upNeighbour.getNumberOfBridges() == 0)
            neighboursWithoutBridges.add(upNeighbour);
        if (downNeighbour != null && downNeighbour.getNumberOfBridges() == 0)
            neighboursWithoutBridges.add(downNeighbour);
        if (rightNeighbour != null && rightNeighbour.getNumberOfBridges() == 0)
            neighboursWithoutBridges.add(rightNeighbour);
        if (leftNeighbour != null && leftNeighbour.getNumberOfBridges() == 0)
            neighboursWithoutBridges.add(leftNeighbour);
        return neighboursWithoutBridges;
    }

    public int numberOfUnfinishedNeighboursWithFreeBridges() {
        int n = 0;
        if ((upNeighbour != null && !upNeighbour.isFinished) && (upBridges == null || !upBridges.isDouble()))
            n++;
        if ((downNeighbour != null && !downNeighbour.isFinished) && (downBridges == null || !downBridges.isDouble()))
            n++;
        if ((rightNeighbour != null && !rightNeighbour.isFinished) && (rightBridges == null || !rightBridges.isDouble()))
            n++;
        if ((leftNeighbour != null && !leftNeighbour.isFinished) && (leftBridges == null || !leftBridges.isDouble()))
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

    public int getRemainingValue() {
        return remainingValue;
    }

    public void setRemainingValue(int remainingValue) {
        this.remainingValue = remainingValue;
    }

    public int getRemainingBridges(Direction direction) {
        STIsland neighbour;
        STBridge bridge;
        if (direction == Direction.DOWN) {
            neighbour = downNeighbour;
            bridge = downBridges;
        } else if (direction == Direction.UP) {
            neighbour = upNeighbour;
            bridge = upBridges;
        } else if (direction == Direction.LEFT) {
            neighbour = leftNeighbour;
            bridge = leftBridges;
        } else {
            neighbour = rightNeighbour;
            bridge = rightBridges;
        }
        int remVal = 0;
        if (neighbour != null && !neighbour.isFinished()) {
            remVal = Math.min(remainingValue, 2);
            if (bridge != null) {
                if (bridge.isDouble()) {
                    return 0;
                }
                if (remVal > 0) {
                    return 1;
                }
            }
        }
        return remVal;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
