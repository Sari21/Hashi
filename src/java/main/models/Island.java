package main.models;


import main.interfaces.CsvPrintable;

public class Island implements CsvPrintable, Comparable<Island> {
    private int id;
    private int value = 0;
    private Coordinates position;

    public Island(Coordinates position, int id) {
        this.position = position;
        this.id = id;
    }

    public Island() {
        this.position = new Coordinates(0, 0);
    }

    public Island getIsland() {
        return this;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String printCsv() {
        StringBuilder printedIsland = new StringBuilder();
        printedIsland.append(this.id).append(CSV_SEPARATOR)
                .append(this.value).append(CSV_SEPARATOR)
                .append(position.printCsv());
        return printedIsland.toString();
    }

    @Override
    public int compareTo(Island o) {
        if (o.getPosition().getY() == this.getPosition().getY()) {
            return Integer.compare(this.getPosition().getX(), o.getPosition().getX());
        } else {
            return Integer.compare(this.getPosition().getY(), o.getPosition().getY());
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
