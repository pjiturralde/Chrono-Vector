package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LevelStatsTest {
    private LevelStats levelStats;

    @BeforeEach
    void runBefore() {
        levelStats = new LevelStats("level x", 1);
    }

    @Test 
    void updateNoneTakenTest() {
        Level l1 = new Level("level x", 1, 0, 0, 10, 10, 100, 100, 1, 0);
        levelStats.update(l1);

        assertEquals(levelStats.getLeastMovesTaken(), 0);

        assertEquals(levelStats.getLeastTimeTaken(), 0);
    }

    @SuppressWarnings("methodlength")
    @Test
    void updateTest() {
        Level l1 = new Level("level x", 1, 0, 0, 10, 10, 100, 100, 1, 0);

        l1.updateMovesTaken();
        l1.updateMovesTaken();

        l1.startTime();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("something interrupted");
        } 

        l1.endTime();

        levelStats.update(l1);

        assertEquals(levelStats.getLeastMovesTaken(), 2);
        boolean exceeds = levelStats.getLeastTimeTaken() >= 1;

        assertTrue(exceeds);

        l1.updateMovesTaken();
        l1.updateMovesTaken();

        l1.startTime();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("something interrupted");
        } 

        l1.endTime();

        levelStats.update(l1);

        assertEquals(levelStats.getLeastMovesTaken(), 2);
        exceeds = levelStats.getLeastTimeTaken() >= 1;

        assertTrue(exceeds);
    }
}
