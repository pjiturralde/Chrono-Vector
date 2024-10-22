package persistence;

import model.LevelStats;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkLevelStats(String name, int leastMovesTaken, double leastTimeTaken, LevelStats stats) {
        assertEquals(name, stats.getName());
        assertEquals(leastMovesTaken, stats.getLeastMovesTaken());
        assertEquals(leastTimeTaken, stats.getLeastTimeTaken());
    }
}
