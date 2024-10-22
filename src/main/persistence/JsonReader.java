package persistence;

import model.Player;
import model.LevelStats;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads player from JSON data stored in file
public class JsonReader {

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        // stub
    }

    // EFFECTS: reads player from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Player read() throws IOException {
        return null; // stub
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return null; // stub
    }

    // EFFECTS: parses player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        return null; // stub
    }

    // MODIFIES: player
    // EFFECTS: parses levelstats list from JSON object and adds them to player
    private void addLevelStats(Player player, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: player
    // EFFECTS: parses levelstats from JSON object and adds it to player
    private void addLevelStatsHelper(Player player, JSONObject jsonObject) {
        // stub
    }
}
