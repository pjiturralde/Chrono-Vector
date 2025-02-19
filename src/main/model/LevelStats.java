package model;

import org.json.JSONObject;

import persistence.Writable;

// A class representing a LevelStats object with leastMovesTaken, 
// leastTimeTaken, and levelNum components
public class LevelStats implements Writable {
    private String name;
    private int leastMovesTaken;
    private double leastTimeTaken;
    private int attemptNum;

    // EFFECTS: constructs a LevelStats object
    public LevelStats(String name, int leastMovesTaken, double leastTimeTaken, int attemptNum) {
        this.name = name;
        this.attemptNum = attemptNum;
        this.leastMovesTaken = leastMovesTaken;
        this.leastTimeTaken = leastTimeTaken;
    }

    // EFFECTS: constructs a LevelStats object with
    // negative leastTimeTaken and negative levelNum
    public LevelStats(String name, int attemptNum) {
        this.name = name;
        this.attemptNum = attemptNum;
        this.leastMovesTaken = -1;
        this.leastTimeTaken = -1;
    }

    // REQUIRES: Level name must be the same as LevelStats name
    // MODIFIES: this
    // EFFECTS: compares Level movesTaken and timeTaken and updates if
    // either are less than their LevelStats counterpart
    public void update(Level level) {
        if (level.getMovesTaken() < leastMovesTaken || leastMovesTaken == -1) {
            leastMovesTaken = level.getMovesTaken();
        }

        if (level.getTimeTaken() < leastTimeTaken || leastTimeTaken == -1) {
            leastTimeTaken = level.getTimeTaken();
        }
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("leastMovesTaken", leastMovesTaken);
        json.put("leastTimeTaken", leastTimeTaken);
        json.put("attemptNum", attemptNum);
        return json;
    }

    // EFFECTS: returns LevelStats' attemptNum
    public int getAttemptNum() {
        return attemptNum;
    }

    // EFFECTS: returns LevelStats' name
    public String getName() {
        return name;
    }

    // EFFECTS: returns LevelStats' leastMovesTaken
    public int getLeastMovesTaken() {
        return leastMovesTaken;
    }

    // EFFECTS: returns LevelStats' leastTimeTaken
    public double getLeastTimeTaken() {
        return leastTimeTaken;
    }
}
