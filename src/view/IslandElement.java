package view;

import models.Coordinates;
import models.Island;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class IslandElement implements ViewElement {
    private final Text id;
    private Circle circle;
    private Text number;
    private Coordinates coordinates;
    private Island island;
    private static final int RADIUS = 35;
    private boolean isMarked = false;

    public IslandElement(Island island) {
        this.island = island;
        this.coordinates = island.getPosition();

        circle = new Circle();
        circle.setCenterX(island.getPosition().getX() * FIELD_WIDTH - 40);
        circle.setCenterY(island.getPosition().getY() * FIELD_WIDTH - 40);
        circle.setRadius(RADIUS);
        circle.setFill(Color.BISQUE);

        number = new Text();
        number.setText(String.valueOf(island.getValue()));
        number.setFont(Font.font("Verdana", 20));
        number.setX(island.getPosition().getX() * FIELD_WIDTH - 45);
        number.setY(island.getPosition().getY() * FIELD_WIDTH - 35);
        id = new Text();
        id.setText(String.valueOf(island.getId()));
        id.setFont(Font.font("Verdana", 20));
        id.setX(island.getPosition().getX() * FIELD_WIDTH - 45);
        id.setY(island.getPosition().getY() * FIELD_WIDTH - 50);


//        circle.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                    circle.setFill(Color.DIMGREY);
//                }
//            });
    }

    public Text getId() {
        return id;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public void mark() {
        isMarked = !isMarked;
        if (isMarked) {
            this.circle.setFill(Color.GRAY);
        } else {
            this.circle.setFill(Color.BISQUE);
        }
    }

    public void addStroke() {
        this.circle.setStroke(Color.BURLYWOOD);
    }

    public void removeStroke() {
        this.circle.setStroke(null);
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    public Text getNumber() {
        return number;
    }

    public void setNumber(Text number) {
        this.number = number;
    }
}
