package persistence;

import model.LevelStats;
import model.Player;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Player player = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCompletedLevelStats() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCompletedLevelStats.json");
        try {
            Player player = reader.read();
            assertEquals(0, player.getCompletedLevelStats().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCompletedLevelStats() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCompletedLevelStats.json");
        try {
            Player player = reader.read();
            LinkedList<TreeSet<LevelStats>> statsList = player.getCompletedLevelStats();
            assertEquals(2, statsList.get(0).size());
            checkLevelStats("level Z", 2, 3.0, statsList.get(0).first());
            checkLevelStats("level F", 20, 6.3, statsList.get(0).last());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
