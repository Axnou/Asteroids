
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AsteroidsTheGame extends Application {

    public static int width = 600;
    public static int height = 400;

    private long lastShot;

    @Override
    public void start(Stage stage) throws Exception {
        Pane screen = new Pane();
        screen.setPrefSize(width, height);

        Text Points = new Text(10, 10, "Points: 0");
        AtomicInteger changingPoints = new AtomicInteger();
        Spaceship ship = new Spaceship(width / 2, height / 2);

        //listat + asteroidit
        Random rand = new Random();
        List<Character> asteroids = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Asteroid asteroid = new Asteroid(rand.nextInt(100), rand.nextInt(100));
            asteroids.add(asteroid);
        }

        List<Character> bullets = new ArrayList<>();

        //GUI komponenttien piirtaminen
        screen.getChildren().add(ship.getShape());
        screen.getChildren().add(Points);
        asteroids.stream().forEach(ast -> screen.getChildren().add(ast.getShape()));

        //stage asennus
        Scene scene = new Scene(screen);
        stage.setTitle("1979 Asteroids!");
        stage.setScene(scene);
        stage.show();

        Map<KeyCode, Boolean> keyPresses = new HashMap<>();

        //mahdollistaa takkuilemattomat kaannokset

        scene.setOnKeyPressed(keyEvent -> {
            keyPresses.put(keyEvent.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(keyEvent -> {
            keyPresses.put(keyEvent.getCode(), Boolean.FALSE);
        });

        Boolean firstTime = true;

        AnimationTimer timer = new AnimationTimer() {
            //Huolehtii painallusten ja toiminnallisuuden valisesta yhteydesta
            @Override
            public void handle(long l) {
                if (keyPresses.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                if (keyPresses.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }

                if (keyPresses.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }

                long current = System.nanoTime();
                long time = current - lastShot;

                if (keyPresses.getOrDefault(KeyCode.SPACE, false)
                        && bullets.size() < 4
                        && (time < 0 || time > 300000000)) {

                    Bullet bullet = new Bullet((int) ship.getShape().getTranslateX(), (int) ship.getShape().getTranslateY());
                    bullet.getShape().setRotate(ship.getShape().getRotate());
                    bullets.add(bullet);

                    bullet.accelerate();
                    bullet.setMovement(bullet.getMovement().normalize().multiply(3));

                    screen.getChildren().add(bullet.getShape());
                    lastShot = System.nanoTime();
                }


                //liikuttaminen
                asteroids.stream().

                        forEach(ast -> ast.move());
                bullets.stream().

                        forEach(bullet -> bullet.move());
                ship.move();

                //aluksen ja asteroidien tormaysten tutkiminen ja pelin lopettaminen jos niin kay
                asteroids.stream().

                        forEach(ast ->

                        {
                            if (ship.collisionBoolean(ast)) {
                                stop();
                            }
                        });

                //luotien ja asteroidien tormaysten tutkiminen ja tuhoutuneiden poistaminen listoilta
                //Pistetilanteen paivittaminen
                bullets.stream().

                        forEach(bullet ->

                        {
                            asteroids.stream().forEach(asteroid -> {
                                if (bullet.collisionBoolean(asteroid)) {
                                    bullet.setAlive(false);
                                    asteroid.setAlive(false);
                                }
                            });

                            if (!bullet.isAlive()) {
                                Points.setText("Points: " + changingPoints.addAndGet(1000));
                            }
                        });

                deleteNotAlive(bullets, screen);

                deleteNotAlive(asteroids, screen);

                if (Math.random() < 0.005) {
                    Asteroid asteroid = new Asteroid(width, height);
                    if (!asteroid.collisionBoolean(ship)) {
                        screen.getChildren().add(asteroid.getShape());
                        asteroids.add(asteroid);
                    }
                }
            }
        };

        timer.start();
    }

    public void deleteNotAlive(List<Character> checkIfAlive, Pane screen) {
        checkIfAlive.stream().filter(character -> !character.isAlive()).forEach(character -> screen.getChildren().remove(character.getShape()));
        checkIfAlive.removeAll(checkIfAlive.stream().filter(character -> !character.isAlive()).collect(Collectors.toList()));
    }
    public static void main(String[] args) {
        launch(AsteroidsTheGame.class);
    }
}
