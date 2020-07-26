import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;


public abstract class Character {
    private Polygon shape;
    private Point2D movement;
    private boolean alive;

    public Character(int x, int y, Polygon polygon) {
        this.shape = polygon;
        this.shape.setTranslateX(x);
        this.shape.setTranslateY(y);
        this.alive = true;

        this.movement = new Point2D(0, 0);
    }

    public void setAlive(boolean state) {
        this.alive = state;
    }

    public boolean isAlive() {
        return alive;
    }

    public Polygon getShape() {
        return shape;
    }

    public void turnRight() {
        this.shape.setRotate(this.shape.getRotate() + 5);
    }

    public void turnLeft() {
        this.shape.setRotate(this.shape.getRotate() - 5);
    }

    public void move() {
        this.shape.setTranslateX(this.shape.getTranslateX() + this.movement.getX());
        this.shape.setTranslateY(this.shape.getTranslateY() + this.movement.getY());

        if (this.shape.getTranslateX() < 0) {
            this.shape.setTranslateX(this.shape.getTranslateX() + AsteroidsTheGame.width);
        }
        if (this.shape.getTranslateX() > AsteroidsTheGame.width) {
            this.shape.setTranslateX(this.shape.getTranslateX() % AsteroidsTheGame.width);
        }
        if (this.shape.getTranslateY() < 0) {
            this.shape.setTranslateY(this.shape.getTranslateY() + AsteroidsTheGame.height);
        }
        if (this.shape.getTranslateX() > 0) {
            this.shape.setTranslateY(this.shape.getTranslateY() % AsteroidsTheGame.height);
        }
    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.shape.getRotate())) * 0.05;
        double changeY = Math.sin(Math.toRadians(this.shape.getRotate())) * 0.05;

        this.movement = this.movement.add(changeX, changeY);
    }

    public boolean collisionBoolean(Character other) {
        Shape collisionArea = Shape.intersect(this.shape, other.getShape());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }

}
