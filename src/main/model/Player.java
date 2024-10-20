package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Iterator;
import java.util.LinkedList;

// A class representing a Player object with a position and completedLevels list
public class Player implements Writable {
    private Vector2 position;
    private LinkedList<LevelStats> completedLevelStats;

    // EFFECTS: constructs a Player object
    public Player(int posX, int posY) {
        this.position = new Vector2(posX, posY);
        this.completedLevelStats = new LinkedList<LevelStats>();
    }

    // EFFECTS: constructs a Player object
    public Player() {
        this(0, 0);
    }

    // REQUIRES: (dirX, dirY) must be normalized eg. (1,0), (0,1), (-1, 0), or
    // (0,-1)
    // MODIFIES: this
    // EFFECTS: moves player one cell in the given direction
    public void move(int dirX, int dirY) {
        this.position.add(new Vector2(dirX, dirY));
    }

    // MODIFIES: this
    // EFFECTS: updates LevelStats in completedLevelStats with the given Level
    public void updateCompletedLevelStats(Level level) {
        boolean foundLevel = false;
        Iterator<LevelStats> iterator = completedLevelStats.iterator();

        while (iterator.hasNext() && !foundLevel) {
            LevelStats stats = iterator.next();

            if (stats.getName().equals(level.getName())) {
                stats.update(level);
                foundLevel = true;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: turns given Level into LevelStats and adds to completedLevelStats
    public void addCompletedLevelStats(Level level) {
        LevelStats stats = new LevelStats(level.getName(), level.getMovesTaken(), level.getTimeTaken());
        completedLevelStats.add(stats);
    }

    // MODIFIES: this
    // EFFECTS: adds given LevelStats to completedLevelStats
    public void addCompletedLevelStats(LevelStats stats) {
        completedLevelStats.add(stats);
    }

    // EFFECTS: returns Player's position
    public Vector2 getPosition() {
        return position;
    }

    // EFFECTS: sets Player's position
    public void setPosition(Vector2 position) {
        this.position.setVector(position);
    }

    // EFFECTS: returns Player's completed Levels
    public LinkedList<LevelStats> getCompletedLevelStats() {
        return completedLevelStats;
    }

    // EFFECTS: returns if Player has completed level
    public boolean hasCompletedLevel(Level level) {
        for (LevelStats stats : completedLevelStats) {
            if (stats.getName().equals(level.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("completedLevelStats", levelStatsToJson());
        return json;
    }

    // EFFECTS: returns LevelStats in completedLevelStats as a JSON array
    private JSONArray levelStatsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (LevelStats levelStats : completedLevelStats) {
            jsonArray.put(levelStats.toJson());
        }

        return jsonArray;
    }
}
