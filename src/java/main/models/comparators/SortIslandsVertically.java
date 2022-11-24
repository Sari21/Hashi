package main.models.comparators;

import main.models.Island;

import java.util.Comparator;

public class SortIslandsVertically  implements Comparator<Island> {

    @Override
    public int compare(Island o1, Island o2) {
        if (o1.getPosition().getY() == o2.getPosition().getY()) {
            return Integer.compare(o1.getPosition().getX(), o2.getPosition().getX());
        } else {
            return Integer.compare(o1.getPosition().getY(), o2.getPosition().getY());
        }
    }
}
