package persistence;

import model.LevelStats;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkLevelStats(String name, int leastMovesTaken, double leastTimeTaken, LevelStats stats) {
        assertEquals(name, stats.getName());
        assertEquals(leastMovesTaken, stats.getLeastMovesTaken());
        assertEquals(leastTimeTaken, stats.getLeastTimeTaken());
    }
}
