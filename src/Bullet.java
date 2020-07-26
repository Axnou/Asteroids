import javafx.scene.shape.Polygon;

public class Bullet extends Character{

    public Bullet(int x, int y){
        super(x ,y, new Polygon(2, -2, 2, 2, -2, 2, -2, -2));

    }
}
