package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

// A class representing a Player object with a position and completedLevels list
public class Player implements Writable {
    private static final int MOVES_THEN_TIME_SORT = 0;
    private static final int TIME_THEN_MOVES_SORT = 1;
    private static final int ATTEMPT_SORT = 2;
    private Vector2 position;
    private List<List<LevelStats>> completedLevelStats;
    

    // EFFECTS: constructs a Player object
    public Player(int posX, int posY) {
        this.position = new Vector2(posX, posY);
        this.completedLevelStats = new LinkedList<List<LevelStats>>();
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
            List<LevelStats> statsHistory = new LinkedList<LevelStats>();
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
            List<LevelStats> statsHistory = new LinkedList<LevelStats>();
            statsHistory.add(stats);
            completedLevelStats.add(statsHistory);
        } else {
            completedLevelStats.get(levelIndex).add(stats);
        }
    }

    // MODIFIES: this
    // EFFECTS: sorts completedLevelStats.get(levelIndex)
    public void sortCompletedlevelStats(int levelIndex, int sortBy) {
        Comparator<LevelStats> comparator;

        if (sortBy == MOVES_THEN_TIME_SORT) {
            comparator = (o1, o2) -> {
                int leastMovesComparison = Integer.compare(o1.getLeastMovesTaken(), o2.getLeastMovesTaken());
                int leastTimeComparison = Double.compare(o1.getLeastTimeTaken(), o2.getLeastTimeTaken());
                return (leastMovesComparison != 0) ? leastMovesComparison : leastTimeComparison;
            };
        } else if (sortBy == TIME_THEN_MOVES_SORT) {
            comparator = (o1, o2) -> {
                int leastTimeComparison = Double.compare(o1.getLeastTimeTaken(), o2.getLeastTimeTaken());
                int leastMovesComparison = Integer.compare(o1.getLeastMovesTaken(), o2.getLeastMovesTaken());
                return (leastTimeComparison != 0) ? leastTimeComparison : leastMovesComparison;
            };
        } else {
            comparator = (o1, o2) -> {
                int leastAttemptsComparison = Integer.compare(o1.getAttemptNum(), o2.getAttemptNum());
                return leastAttemptsComparison;
            };
        }

        completedLevelStats.get(levelIndex).sort(comparator);
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
    public List<List<LevelStats>> getCompletedLevelStats() {
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

        for (List<LevelStats> statsHistory : completedLevelStats) {
            JSONArray historyJsonArray = new JSONArray();
            for (LevelStats levelStats : statsHistory) {
                historyJsonArray.put(levelStats.toJson());
            }
            statsJsonArray.put(historyJsonArray);
        }

        return statsJsonArray;
    }
}
