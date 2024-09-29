package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    private Player p1;
    private Player p2;

    @BeforeEach
    void runBefore() {
        p1 = new Player(0, 0);
        p2 = new Player(1, 3);
    }

    @Test
    void moveTest() {
        p1.move(1, 0);

        Vector2 position = p1.getPosition();

        int pointX = position.getX();
        int pointY = position.getY();

        assertEquals(pointX, 1);
        assertEquals(pointY, 0);

        p1.move(1, 0);

        p2.move(0, 1);

        position = p2.getPosition();

        pointX = position.getX();
        pointY = position.getY();

        assertEquals(pointX, 1);
        assertEquals(pointY, 4);
    }

    @Test
    void getPositionTest() {
        Vector2 position = p1.getPosition();

        int pointX = position.getX();
        int pointY = position.getY();

        assertEquals(pointX, 0);
        assertEquals(pointY, 0);

        position = p2.getPosition();

        pointX = position.getX();
        pointY = position.getY();

        assertEquals(pointX, 1);
        assertEquals(pointY, 3);
    }
}
