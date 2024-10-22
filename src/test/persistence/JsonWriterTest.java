package persistence;

import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;

import model.LevelStats;
import model.Player;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Player player = new Player();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCompletedLevelStats() {
        try {
            Player player = new Player();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCompletedLevelStats.json");
            writer.open();
            writer.write(player);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCompletedLevelStats.json");
            player = reader.read();
            assertEquals(0, player.getCompletedLevelStats().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCompletedLevelStats() {
        try {
            Player player = new Player();
            player.addCompletedLevelStats(new LevelStats("level S"));
            player.addCompletedLevelStats(new LevelStats("level L", 3, 2.0));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCompletedLevelStats.json");
            writer.open();
            writer.write(player);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCompletedLevelStats.json");
            player = reader.read();
            List<LevelStats> statsList = player.getCompletedLevelStats(); 
            assertEquals(2, statsList.size());
            checkLevelStats("level S", -1, -1, statsList.get(0));
            checkLevelStats("level L", 3, 2.0, statsList.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}