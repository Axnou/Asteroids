import javafx.scene.shape.Polygon;

import java.util.Random;

public class PentagonRandomer {

    public PentagonRandomer() {

    }

    public Polygon CreatePolygon() {
        Random rand = new Random();

        //arvottu koko -> asteroidit eri kokoisia
        double size = 10 + rand.nextInt(10);

        //Pisteiden selvittamisessa kaytetty matematiikka:
        //https://mathworld.wolfram.com/RegularPentagon.html
        Polygon polygon = new Polygon();
        double c1 = Math.cos(Math.PI * 2 / 5);
        double c2 = Math.cos(Math.PI / 5);
        double s1 = Math.sin(Math.PI * 2 / 5);
        double s2 = Math.sin(Math.PI * 4 / 5);

        //Muodostetaan viisikulmio yläpuolella olevien pisteiden pohjalta.
        polygon.getPoints().addAll(size, 0.0,
                size * c1, -1 * size * s1,
                -1 * size * c2, -1 * size * s2,
                -1 * size * c2, size * s2,
                size * c1, size * s1);

        //Muutetaan vielä hieman jokaisen pisteen paikkaa viisikulmiossa, jotta kaikki viisikulmiot eivät ole säännöllisiä.
        for (int i = 0; i < polygon.getPoints().size(); i++) {
            int variance = rand.nextInt(5) - 2;
            polygon.getPoints().set(i, polygon.getPoints().get(i) + variance);
        }

        return polygon;
    }
}
