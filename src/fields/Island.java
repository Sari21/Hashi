package fields;


import interfaces.CsvPrintable;

public class Island implements CsvPrintable {
    private int id;
    private int value = 0;
    private Coordinates position;
    private Bridge upBridges, downBridges, rightBridges, leftBridges;

    public Island(int value, Coordinates position, int id) {
        this.value = value;
        this.position = position;
        this.id = id;
    }
    public Island(){
    this.position = new Coordinates(0, 0);
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

    public Bridge getUpBridges() {
        return upBridges;
    }

    public void setUpBridges(Bridge upBridges) {
        this.upBridges = upBridges;
    }

    public Bridge getDownBridges() {
        return downBridges;
    }

    public void setDownBridges(Bridge downBridges) {
        this.downBridges = downBridges;
    }

    public Bridge getRightBridges() {
        return rightBridges;
    }

    public void setRightBridges(Bridge rightBridges) {
        this.rightBridges = rightBridges;
    }

    public Bridge getLeftBridges() {
        return leftBridges;
    }

    public void setLeftBridges(Bridge leftBridges) {
        this.leftBridges = leftBridges;
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
}
