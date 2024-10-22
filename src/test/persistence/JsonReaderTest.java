package persistence;

import model.LevelStats;
import model.Player;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
            List<LevelStats> statsList = player.getCompletedLevelStats();
            assertEquals(2, statsList.size());
            checkLevelStats("level Z", 2, 3.0, statsList.get(0));
            checkLevelStats("level F", 20, 6.3, statsList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
