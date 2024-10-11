package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

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
    void constructorNoArgsTest() {
        Player player = new Player();
        assertEquals(player.getPosition().getX(), 0);
        assertEquals(player.getPosition().getY(), 0);
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

    @Test
    void setPositionTest() {
        Vector2 position = new Vector2(5, 7);
        p2.setPosition(position);

        assertEquals(position.getX(), 5);
        assertEquals(position.getY(), 7);
    }

    @Test
    void getCompletedLevelsTest() {
        Level l1 = new Level("Level S", 1, 5, 8, 8, 100, 100, 0, 2);
        Level l2 = new Level("Level H", 1, 5, 8, 8, 100, 100, 0, 2);

        p2.addCompletedLevel(l1);
        p2.addCompletedLevel(l2);

        LinkedList<Level> levels = p2.getCompletedLevels();

        assertEquals(levels.getFirst(), l1);
        assertEquals(levels.getLast(), l2);
    }

    @Test
    void addCompletedLevelTest() {
        Level l1 = new Level("Level M", 1, 13, 2, 8, 101, 100, 0, 2);
        Level l2 = new Level("Level SH", 1, 5, 8, 3, 100, 100, 0, 2);

        p2.addCompletedLevel(l1);
        p2.addCompletedLevel(l2);

        LinkedList<Level> levels = p2.getCompletedLevels();

        assertEquals(levels.getFirst(), l1);
        assertEquals(levels.getLast(), l2);
    }

    @Test
    void hasCompletedLevelTest() {
        Level l1 = new Level("Level M", 1, 13, 2, 8, 101, 100, 0, 2);
        Level l2 = new Level("Level SH", 1, 5, 8, 3, 100, 100, 0, 2);

        p2.addCompletedLevel(l1);
        p2.addCompletedLevel(l2);

        assertTrue(p2.hasCompletedLevel(l2));
    }
}
