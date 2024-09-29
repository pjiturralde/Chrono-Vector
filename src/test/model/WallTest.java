package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WallTest {
    private Wall w1;
    private Wall w2;

    @BeforeEach
    void runBefore() {
        w1 = new Wall(1, 1, 3, 3);
        w2 = new Wall(2, 2, 5, 7);
    }

    @Test
    void getPositionTest() {
        Vector2 position = w1.getPosition();

        int pointX = position.getX();
        int pointY = position.getY();

        assertEquals(pointX, 1);
        assertEquals(pointY, 1);

        position = w2.getPosition();

        pointX = position.getX();
        pointY = position.getY();

        assertEquals(pointX, 2);
        assertEquals(pointY, 2);
    }

    @Test
    void getSizeTest() {
        Vector2 size = w1.getSize();

        int pointX = size.getX();
        int pointY = size.getY();

        assertEquals(pointX, 3);
        assertEquals(pointY, 3);

        size = w2.getSize();

        pointX = size.getX();
        pointY = size.getY();

        assertEquals(pointX, 5);
        assertEquals(pointY, 7);
    }
}
