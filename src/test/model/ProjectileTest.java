package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProjectileTest {
    private Projectile p1;
    private Projectile p2;
    private Projectile p3;

    @BeforeEach
    void runBefore() {
        p1 = new Projectile(0, 0, 1, 1, 1);
        p2 = new Projectile(1, 2, 1, 0, 1);
        p3 = new Projectile(0, 0, 1, 1, 1, true);
    }

    @Test
    void additionTest() {
        p1.changeDirection(3, 2);
        int pointX = p1.getDirection().getX();
        int pointY = p1.getDirection().getY();

        assertEquals(pointX, 3);
        assertEquals(pointY, 2);
    }

    @Test
    void moveForwardTest() {
        p1.moveForward();

        int pointX = p1.getPosition().getX();
        int pointY = p1.getPosition().getY();

        assertEquals(pointX, 1);
        assertEquals(pointY, 1);
    }

    @Test
    void moveBackwardTest() {
        p1.moveBackward();

        int pointX = p1.getPosition().getX();
        int pointY = p1.getPosition().getY();

        assertEquals(pointX, 0);
        assertEquals(pointY, 0);

        p3.moveBackward();

        pointX = p3.getPosition().getX();
        pointY = p3.getPosition().getY();

        assertEquals(pointX, 1);
        assertEquals(pointY, 1);
    }

    @Test
    void getPositionTest() {
        int pointX = p1.getPosition().getX();
        int pointY = p1.getPosition().getY();

        assertEquals(pointX, 0);
        assertEquals(pointY, 0);

        pointX = p2.getPosition().getX();
        pointY = p2.getPosition().getY();

        assertEquals(pointX, 1);
        assertEquals(pointY, 2);
    }

    @Test
    void getDirectionTest() {
        int pointX = p1.getDirection().getX();
        int pointY = p1.getDirection().getY();

        assertEquals(pointX, 1);
        assertEquals(pointY, 1);

        pointX = p2.getDirection().getX();
        pointY = p2.getDirection().getY();

        assertEquals(pointX, 1);
        assertEquals(pointY, 0);
    }
}
