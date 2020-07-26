import javafx.scene.shape.Polygon;

public class Spaceship extends Character {

    public Spaceship(int x, int y) {
        super(x, y, new Polygon(-5, -5, 10, 0, -5, 5, 0, 0));
    }


}
