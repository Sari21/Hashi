package main.solver.solvingTechniques.models;

import main.models.Bridge;

public class STBridge extends Bridge {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getLength() {
        if (!this.isVertical()) {
            return Math.abs(this.getEndIsland().getPosition().getX() - this.getStartIsland().getPosition().getX()) - 1;
        } else {
            return Math.abs(this.getEndIsland().getPosition().getY() - this.getStartIsland().getPosition().getY()) - 1;
        }
    }

}
