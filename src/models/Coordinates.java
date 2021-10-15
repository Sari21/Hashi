package models;

import interfaces.CsvPrintable;

public class Coordinates implements CsvPrintable {
    private int x, y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String printCsv() {
        return new StringBuilder().append(this.x).append(CSV_SEPARATOR)
                .append(this.y).toString();
    }
}
