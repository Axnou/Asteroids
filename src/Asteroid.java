import java.util.Random;

public class Asteroid extends Character{

    private double rotation;

    public Asteroid(int x, int y) {
        super(x, y, new PentagonRandomer().CreatePolygon());

        Random rand = new Random();

        this.getShape().setRotate(rand.nextInt(360));

        int accelerationAmount = 1 + rand.nextInt(10);
        for (int i = 0; i < accelerationAmount; i++) {
            this.accelerate();
        }

        this.rotation = 0.5 - rand.nextDouble();
    }

    @Override
    public void move() {
        super.move();
        this.getShape().setRotate(this.getShape().getRotate() + rotation);
    }
}
