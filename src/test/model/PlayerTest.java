package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
        Level l1 = new Level("Level S", 1, 1, 5, 8, 8, 100, 100, 0, 2);
        Level l2 = new Level("Level H", 2, 1, 5, 8, 8, 100, 100, 0, 2);

        p2.addCompletedLevelStats(l1, 1);
        p2.addCompletedLevelStats(l2, 2);

        List<List<LevelStats>> statsList = p2.getCompletedLevelStats();

        List<LevelStats> level1History = statsList.get(l1.getLevelIndex());
        LevelStats level1Stat = level1History.get(0);

        List<LevelStats> level2History = statsList.get(l2.getLevelIndex());
        LevelStats level2Stat = level2History.get(0);

        assertEquals(level1Stat.getName(), l1.getName());
        assertEquals(level1Stat.getLeastMovesTaken(), l1.getMovesTaken());
        assertEquals(level1Stat.getLeastTimeTaken(), l1.getTimeTaken());
        assertEquals(level2Stat.getName(), l2.getName());
        assertEquals(level2Stat.getLeastMovesTaken(), l2.getMovesTaken());
        assertEquals(level2Stat.getLeastTimeTaken(), l2.getTimeTaken());

        LevelStats stats = new LevelStats("level Y", 0, 0, 1);

        p2.addCompletedLevelStats(stats, 2);

        assertEquals(statsList.get(2).get(0).getName(), stats.getName());
        assertEquals(statsList.get(2).get(0).getLeastMovesTaken(), stats.getLeastMovesTaken());
        assertEquals(statsList.get(2).get(0).getLeastTimeTaken(), stats.getLeastTimeTaken());
    }

    @Test
    void hasCompletedLevelTest() {
        Level l1 = new Level("Level M", 1, 1, 13, 2, 8, 101, 100, 0, 2);
        Level l2 = new Level("Level SH", 1, 1, 5, 8, 3, 100, 100, 0, 2);

        p2.addCompletedLevelStats(l1, 1);
        p2.addCompletedLevelStats(l2, 2);

        assertTrue(p2.hasCompletedLevel(l2));
    }
}
