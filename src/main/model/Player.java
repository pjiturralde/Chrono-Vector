package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.TreeSet;

// A class representing a Player object with a position and completedLevels list
public class Player implements Writable {
    private Vector2 position;
    private LinkedList<TreeSet<LevelStats>> completedLevelStats;
    

    // EFFECTS: constructs a Player object
    public Player(int posX, int posY) {
        this.position = new Vector2(posX, posY);
        this.completedLevelStats = new LinkedList<TreeSet<LevelStats>>();
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
    // EFFECTS: turns given Level into LevelStats and adds to completedLevelStats
    public void addCompletedLevelStats(Level level, int attemptNum) {
        LevelStats stats = new LevelStats(level.getName(), level.getMovesTaken(), level.getTimeTaken(), attemptNum);

        if (level.getLevelIndex() > completedLevelStats.size() - 1) {
            TreeSet<LevelStats> statsHistory = new TreeSet<LevelStats>();
            statsHistory.add(stats);
            completedLevelStats.add(statsHistory);
        } else {
            completedLevelStats.get(level.getLevelIndex()).add(stats);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds given LevelStats to completedLevelStats
    public void addCompletedLevelStats(LevelStats stats, int levelIndex) {
        if (levelIndex > completedLevelStats.size() - 1) {
            TreeSet<LevelStats> statsHistory = new TreeSet<LevelStats>();
            statsHistory.add(stats);
            completedLevelStats.add(statsHistory);
        } else {
            completedLevelStats.get(levelIndex).add(stats);
        }
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
    public LinkedList<TreeSet<LevelStats>> getCompletedLevelStats() {
        return completedLevelStats;
    }

    // EFFECTS: returns if Player has completed level
    public boolean hasCompletedLevel(Level level) {
        if (level.getLevelIndex() <= completedLevelStats.size() - 1) {
            return true;
        }

        return false;
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("completedLevelStats", levelStatsToJson());
        return json;
    }

    // EFFECTS: returns LevelStats in completedLevelStats as a JSON array
    private JSONArray levelStatsToJson() {
        JSONArray statsJsonArray = new JSONArray();

        for (TreeSet<LevelStats> statsHistory : completedLevelStats) {
            JSONArray historyJsonArray = new JSONArray();
            for (LevelStats levelStats : statsHistory) {
                historyJsonArray.put(levelStats.toJson());
            }
            statsJsonArray.put(historyJsonArray);
        }

        return statsJsonArray;
    }
}
