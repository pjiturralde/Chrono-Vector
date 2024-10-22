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
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads player from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Player read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        Player player = new Player();
        addLevelStats(player, jsonObject);
        return player;
    }

    // MODIFIES: player
    // EFFECTS: parses levelstats list from JSON object and adds them to player
    private void addLevelStats(Player player, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("completedLevelStats");
        for (Object json : jsonArray) {
            JSONObject nextLevelStats = (JSONObject) json;
            addLevelStatsHelper(player, nextLevelStats);
        }
    }

    // MODIFIES: player
    // EFFECTS: parses levelstats from JSON object and adds it to player
    private void addLevelStatsHelper(Player player, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int leastMovesTaken = jsonObject.getInt("leastMovesTaken");
        double leastTimeTaken = jsonObject.getDouble("leastTimeTaken");
        LevelStats stats = new LevelStats(name, leastMovesTaken, leastTimeTaken);
        player.addCompletedLevelStats(stats);
    }
}

