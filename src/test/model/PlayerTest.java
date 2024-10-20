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
    void completedLevelsTest() {
        Level l1 = new Level("Level S", 1, 5, 8, 8, 100, 100, 0, 2);
        Level l2 = new Level("Level H", 1, 5, 8, 8, 100, 100, 0, 2);

        p2.addCompletedLevelStats(l1);
        p2.addCompletedLevelStats(l2);

        LinkedList<LevelStats> statsList = p2.getCompletedLevelStats();

        assertEquals(statsList.getFirst().getName(), l1.getName());
        assertEquals(statsList.getFirst().getLeastMovesTaken(), l1.getMovesTaken());
        assertEquals(statsList.getFirst().getLeastTimeTaken(), l1.getTimeTaken());
        assertEquals(statsList.getLast().getName(), l2.getName());
        assertEquals(statsList.getLast().getLeastMovesTaken(), l2.getMovesTaken());
        assertEquals(statsList.getLast().getLeastTimeTaken(), l2.getTimeTaken());

        LevelStats stats = new LevelStats("level Y", 0, 0);

        p2.addCompletedLevelStats(stats);

        assertEquals(statsList.getLast().getName(), stats.getName());
        assertEquals(statsList.getLast().getLeastMovesTaken(), stats.getLeastMovesTaken());
        assertEquals(statsList.getLast().getLeastTimeTaken(), stats.getLeastTimeTaken());
    }

    @Test
    void hasCompletedLevelTest() {
        Level l1 = new Level("Level M", 1, 13, 2, 8, 101, 100, 0, 2);
        Level l2 = new Level("Level SH", 1, 5, 8, 3, 100, 100, 0, 2);

        p2.addCompletedLevelStats(l1);
        p2.addCompletedLevelStats(l2);

        assertTrue(p2.hasCompletedLevel(l2));
    }

    @Test
    void updateCompletedLevelsTest() {
        Level l1 = new Level("Level S", 1, 5, 8, 8, 100, 100, 0, 2);

        l1.updateMovesTaken();
        l1.updateMovesTaken();

        l1.startTime();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("something interrupted");
        } 

        l1.endTime();

        p2.addCompletedLevelStats(l1);

        LinkedList<LevelStats> stats = p2.getCompletedLevelStats();

        assertEquals(stats.getFirst().getLeastMovesTaken(), 2);
        boolean exceeds = stats.getFirst().getLeastTimeTaken() >= 1;

        assertTrue(exceeds);

        l1.reset();

        p2.updateCompletedLevelStats(l1);

        assertEquals(stats.getFirst().getName(), l1.getName());
        assertEquals(stats.getFirst().getLeastMovesTaken(), l1.getMovesTaken());
        assertEquals(stats.getFirst().getLeastTimeTaken(), l1.getTimeTaken());
    }
}
